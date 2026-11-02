import { app, BrowserWindow, clipboard, desktopCapturer, ipcMain, nativeImage, screen } from 'electron';
import { mkdir, writeFile } from 'node:fs/promises';
import path from 'node:path';
import { pixelSelection, type Selection } from './capture-geometry';

type InlineSource={data:string;inkData:string;logical:{width:number;height:number}};
export function installCapture(options:{ trusted:(e:Electron.IpcMainInvokeEvent)=>void; prepare:(includeVisibleInk:boolean)=>void; inlineBounds:(display:Electron.Display)=>Selection|null; showInline:(source:InlineSource)=>void; hideInline:()=>void; restore:(message?:string)=>void; smoke:boolean }) {
  let panel:BrowserWindow|null=null,image:Electron.NativeImage|null=null,inlineImage:Electron.NativeImage|null=null;
  let logical={width:1,height:1},inlineLogical={width:1,height:1},inlineExpected={width:1,height:1},inkData:string|null=null,includesInk=false,busy=false,inline=false;
  const checkPanel=(e:Electron.IpcMainInvokeEvent)=>{if(!panel || e.sender!==panel.webContents || e.senderFrame!==panel.webContents.mainFrame) throw new Error('Capture only');};
  const checkCapture=(e:Electron.IpcMainInvokeEvent)=>{if(inline)options.trusted(e);else checkPanel(e);};
  function close(message?:string) { const old=panel,wasInline=inline;panel=null;image=null;inlineImage=null;inkData=null;includesInk=false;inline=false;if(wasInline)options.hideInline();old?.destroy();options.restore(message); }
  async function beginCapture(requestedInkData?:string) {
    if(busy || panel || inline) return;busy=true;
    try {
      const d=screen.getDisplayNearestPoint(screen.getCursorScreenPoint());
      const includeVisibleInk=typeof requestedInkData==='string';includesInk=includeVisibleInk;
      if(requestedInkData){
        if(requestedInkData.length>100_000_000||!requestedInkData.startsWith('data:image/png;base64,'))throw new Error('필기 이미지가 올바르지 않습니다.');
        const requestedInk=nativeImage.createFromDataURL(requestedInkData);if(requestedInk.isEmpty())throw new Error('필기 이미지가 비어 있습니다.');inkData=requestedInkData;
      }
      const trace=async(stage:string)=>{if(options.smoke)await writeFile(path.join(process.cwd(),'capture-stage.json'),JSON.stringify({stage}));};
      await trace('preparing overlay');
      options.prepare(includeVisibleInk);
      // Wait for the toolbar-only visibility change, or for the whole overlay
      // to leave the clean global-capture snapshot.
      await new Promise(r=>setTimeout(r,40));
      await trace('reading screen image');
      let timer:ReturnType<typeof setTimeout>|undefined;
      const sources=await Promise.race([
        desktopCapturer.getSources({types:['screen'],thumbnailSize:{width:Math.round(d.size.width*d.scaleFactor),height:Math.round(d.size.height*d.scaleFactor)}}),
        new Promise<never>((_,reject)=>{timer=setTimeout(()=>reject(new Error('화면 캡처 응답 시간이 초과되었습니다. 다시 시도하세요.')),12000);})
      ]).finally(()=>clearTimeout(timer));
      const source=sources.find(s=>s.display_id===String(d.id));
      if(!source || source.thumbnail.isEmpty()) throw new Error('화면을 캡처할 수 없습니다.');
      image=source.thumbnail;logical={width:d.bounds.width,height:d.bounds.height};
      if(includeVisibleInk){
        const bounds=options.inlineBounds(d);if(!bounds)throw new Error('필기 화면 위치를 확인할 수 없습니다.');
        inline=true;inlineLogical={width:bounds.width,height:bounds.height};
        const preview=image.crop(pixelSelection(bounds,logical,image.getSize()));inlineExpected=preview.getSize();
        options.showInline({data:preview.toDataURL(),inkData:inkData!,logical:inlineLogical});
        await trace('inline selection ready');
      }else{
        await trace('creating selection panel');
        panel=new BrowserWindow({...d.bounds,frame:false,resizable:false,alwaysOnTop:true,skipTaskbar:true,show:false,backgroundColor:'#000000',title:'hPen 영역 캡처',webPreferences:{preload:path.join(__dirname,'preload.js'),contextIsolation:true,nodeIntegration:false,sandbox:true,backgroundThrottling:false}});
        panel.setMenuBarVisibility(false);
        panel.webContents.setWindowOpenHandler(()=>({action:'deny'}));
        panel.webContents.on('will-navigate',e=>e.preventDefault());
        panel.on('closed',()=>{if(panel)close();});
        await panel.loadFile(path.join(__dirname,'capture.html'));panel.show();panel.focus();await trace('selection panel ready');
      }
    } catch(error) {close(`캡처 시작 실패: ${String(error)}`);throw error;} finally {busy=false;}
  }
  ipcMain.handle('begin-capture',async(e,requestedInkData?:unknown)=>{options.trusted(e);await beginCapture(typeof requestedInkData==='string'?requestedInkData:undefined);});
  ipcMain.handle('set-capture-composite',(e,data:unknown)=>{
    options.trusted(e);if(!inline||typeof data!=='string'||data.length>150_000_000||!data.startsWith('data:image/png;base64,'))throw new Error('합성 캡처 이미지가 올바르지 않습니다.');
    const composite=nativeImage.createFromDataURL(data),size=composite.getSize();if(composite.isEmpty()||size.width!==inlineExpected.width||size.height!==inlineExpected.height)throw new Error('합성 캡처 이미지 크기가 화면과 다릅니다.');inlineImage=composite;
  });
  ipcMain.handle('capture-source',e=>{checkPanel(e);return {data:image!.toDataURL(),logical,includesInk};});
  ipcMain.handle('cancel-capture',e=>{checkCapture(e);close('캡처를 취소했습니다.');});
  async function commitCapture(rect:Selection) {
    if(busy || !image || (inline&&!inlineImage)) throw new Error('캡처 처리 중입니다.');busy=true;
    try {
      const sourceImage=inline?inlineImage!:image,sourceLogical=inline?inlineLogical:logical;
      const pixels=pixelSelection(rect,sourceLogical,sourceImage.getSize());
      const crop=sourceImage.crop(pixels);
      clipboard.writeImage(crop);
      const folder=options.smoke?path.join(app.getPath('userData'),'captures'):path.join(app.getPath('pictures'),'hPen');
      await mkdir(folder,{recursive:true});
      const filename=path.join(folder,`hPen-${new Date().toISOString().replace(/[:.]/g,'-')}.png`);
      await writeFile(filename,crop.toPNG(),{flag:'wx'});
      close(`클립보드 복사 · 저장 완료: ${filename}`);
      return filename;
    } catch(error) {throw new Error(`클립보드에 복사되었을 수 있습니다. 파일 저장 결과를 확인하고 재시도하세요: ${String(error)}`);} finally {busy=false;}
  }
  ipcMain.handle('commit-capture',async(e,rect:Selection)=>{checkCapture(e);return commitCapture(rect);});
  return {beginCapture,isSelecting:()=>busy || !!panel || inline,cancel:()=>close('캡처를 취소했습니다.'),commitCapture,source:()=>({data:(inlineImage||image)!.toDataURL(),logical:inline?inlineLogical:logical,inkData,inkBounds:null,includesInk})};
}
