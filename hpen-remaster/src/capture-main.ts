import { app, BrowserWindow, clipboard, desktopCapturer, ipcMain, nativeImage, screen } from 'electron';
import { mkdir, writeFile } from 'node:fs/promises';
import path from 'node:path';
import { pixelSelection, type Selection } from './capture-geometry';

export function installCapture(options:{ trusted:(e:Electron.IpcMainInvokeEvent)=>void; hide:()=>void; restore:(message?:string)=>void; smoke:boolean }) {
  let panel:BrowserWindow|null=null, image:Electron.NativeImage|null=null;
  let logical={width:1,height:1}, busy=false;
  const check=(e:Electron.IpcMainInvokeEvent)=>{if(!panel || e.sender!==panel.webContents || e.senderFrame!==panel.webContents.mainFrame) throw new Error('Capture only');};
  function close(message?:string) { const old=panel;panel=null;image=null;old?.destroy();options.restore(message); }
  async function beginCapture() {
    if(busy || panel) return;busy=true;
    try {
      const d=screen.getDisplayNearestPoint(screen.getCursorScreenPoint());
      const trace=async(stage:string)=>{if(options.smoke)await writeFile(path.join(process.cwd(),'capture-stage.json'),JSON.stringify({stage}));};
      await trace('hiding overlay');
      options.hide();
      await new Promise(r=>setTimeout(r,180));
      await trace('reading screen image');
      let timer:ReturnType<typeof setTimeout>|undefined;
      const sources=await Promise.race([
        desktopCapturer.getSources({types:['screen'],thumbnailSize:{width:Math.round(d.size.width*d.scaleFactor),height:Math.round(d.size.height*d.scaleFactor)}}),
        new Promise<never>((_,reject)=>{timer=setTimeout(()=>reject(new Error('화면 캡처 응답 시간이 초과되었습니다. 다시 시도하세요.')),12000);})
      ]).finally(()=>clearTimeout(timer));
      await trace('creating selection panel');
      const source=sources.find(s=>s.display_id===String(d.id));
      if(!source || source.thumbnail.isEmpty()) throw new Error('화면을 캡처할 수 없습니다.');
      image=source.thumbnail;logical={width:d.bounds.width,height:d.bounds.height};
      panel=new BrowserWindow({...d.bounds,frame:false,resizable:false,alwaysOnTop:true,skipTaskbar:true,show:false,backgroundColor:'#000000',title:'hPen 영역 캡처',webPreferences:{preload:path.join(__dirname,'preload.js'),contextIsolation:true,nodeIntegration:false,sandbox:true,backgroundThrottling:false}});
      panel.setMenuBarVisibility(false);
      panel.webContents.setWindowOpenHandler(()=>({action:'deny'}));
      panel.webContents.on('will-navigate',e=>e.preventDefault());
      panel.on('closed',()=>{if(panel){panel=null;image=null;options.restore();}});
      await panel.loadFile(path.join(__dirname,'capture.html'));panel.show();panel.focus();await trace('selection panel ready');
    } catch(error) {close(`캡처 시작 실패: ${String(error)}`);throw error;} finally {busy=false;}
  }
  ipcMain.handle('begin-capture',async e=>{options.trusted(e);await beginCapture();});
  ipcMain.handle('capture-source',e=>{check(e);return {data:image!.toDataURL(),logical};});
  ipcMain.handle('cancel-capture',e=>{check(e);close('캡처를 취소했습니다.');});
  async function commitCapture(rect:Selection) {
    if(busy || !image) throw new Error('캡처 처리 중입니다.');busy=true;
    try {
      const crop=image.crop(pixelSelection(rect,logical,image.getSize()));
      clipboard.writeImage(crop);
      const folder=options.smoke?path.join(app.getPath('userData'),'captures'):path.join(app.getPath('pictures'),'hPen');
      await mkdir(folder,{recursive:true});
      const filename=path.join(folder,`hPen-${new Date().toISOString().replace(/[:.]/g,'-')}.png`);
      await writeFile(filename,crop.toPNG(),{flag:'wx'});
      close(`클립보드 복사 · 저장 완료: ${filename}`);
      return filename;
    } catch(error) {throw new Error(`클립보드에 복사되었을 수 있습니다. 파일 저장 결과를 확인하고 재시도하세요: ${String(error)}`);} finally {busy=false;}
  }
  ipcMain.handle('commit-capture',async(e,rect:Selection)=>{check(e);return commitCapture(rect);});
  return {beginCapture,isSelecting:()=>busy || !!panel,cancel:()=>close('캡처를 취소했습니다.'),commitCapture,source:()=>({data:image!.toDataURL(),logical})};
}
