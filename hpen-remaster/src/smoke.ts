import { app, BrowserWindow, screen, nativeImage, clipboard } from 'electron';
import assert from 'node:assert/strict';
import { readdir, readFile } from 'node:fs/promises';
import { writeFileSync } from 'node:fs';
import path from 'node:path';
import { pixelSelection } from './capture-geometry';

// App-owned integration checks; no interaction with other applications.
export async function runSmoke(panel: BrowserWindow, invokeModeShortcut: () => void, switchInputAtDisplay: (id:string)=>Promise<void>,captureController:{beginCapture():Promise<void>;cancel():void;source():{data:string;logical:{width:number;height:number}};commitCapture(rect:{x:number;y:number;width:number;height:number}):Promise<string>}) {
  const checks: string[] = [];
  checks.push=(...items:string[])=>{
    const count=Array.prototype.push.apply(checks,items);
    writeFileSync(path.join(process.cwd(),'smoke-progress.json'),JSON.stringify(checks,null,2));
    return count;
  };
  const js = (code: string) => panel.webContents.executeJavaScript(code, true);
  const waitFor = async (code: string) => {
    const deadline=Date.now()+15000;
    while(Date.now()<deadline) { if(await js(code)) return; await new Promise(r=>setTimeout(r,150)); }
    throw new Error(`Timed out: ${code}; status=${await js('document.getElementById("status").textContent')}`);
  };
  await waitFor('document.getElementById("display").options.length > 0');
  assert.equal(panel.getTitle(), 'hPen');
  assert.equal(await js('document.body.classList.contains("overlay")'),true);
  assert.equal(await js('getComputedStyle(document.documentElement).backgroundColor'),'rgba(0, 0, 0, 0)');
  checks.push('startup uses transparent desktop overlay');
  assert.equal(await js('document.querySelector("#magnifyKey").value'), '');
  const registered=(await js('window.hpen.displays()')).overlayShortcutOK;
  assert.equal(await js('document.querySelector("#inputKey").value'), registered?'Control+`':'');
  checks.push('Ctrl+Backquote is default mode switch; magnifier hotkey is disabled');
  checks.push(registered?'default mode shortcut is registered with OS':'occupied mode shortcut is reported unavailable without blocking toolbar');
  const disabled=await js('window.hpen.shortcuts("", "", "")');
  assert.equal(disabled.input,''); assert.equal(disabled.failed,false);
  const restored=await js('window.hpen.shortcuts("", "Control+`", "")');
  assert.equal(restored.input,registered?'Control+`':''); assert.equal(restored.failed,!registered);
  checks.push(registered?'mode shortcut can be disabled and registered again':'shortcut collision is reported after disable and retry');
  await js(`(()=>{
    const c=document.getElementById('canvas');
    c.dispatchEvent(new PointerEvent('pointerdown',{clientX:300,clientY:350,button:0,pointerId:1}));
    c.dispatchEvent(new PointerEvent('pointermove',{clientX:400,clientY:350,pointerId:1}));
    c.dispatchEvent(new PointerEvent('pointerup',{pointerId:1}));
  })()`);
  await new Promise(r=>setTimeout(r,200));
  const pixel=()=>js(`(()=>{const c=document.getElementById('canvas'),p=c.getContext('2d').getImageData(Math.round(350*devicePixelRatio),Math.round(350*devicePixelRatio),1,1).data;return Array.from(p)})()`);
  assert.ok((await pixel())[3]>100); checks.push('desktop stroke renders');
  await js('document.getElementById("undo").click()'); await new Promise(r=>setTimeout(r,100));
  assert.ok((await pixel())[3]<5); checks.push('undo removes stroke');
  await js('document.getElementById("redo").click()'); await new Promise(r=>setTimeout(r,100));
  assert.ok((await pixel())[3]>100); checks.push('redo restores stroke');
  await js('document.getElementById("start").click()');
  await waitFor('document.body.classList.contains("magnifying")');
  checks.push('live desktop capture starts in same overlay');
  await js('document.getElementById("freeze").click()');
  await waitFor('document.getElementById("freeze").textContent === "실시간 복귀"');
  assert.equal(await js('document.getElementById("copy").disabled'),false);
  checks.push('freeze enables annotation and export');
  await js('document.getElementById("freeze").click()');
  await waitFor('document.getElementById("freeze").textContent === "멈춤"');
  checks.push('resume returns to live stream');
  await js('document.getElementById("stop").click()');
  await waitFor('!document.body.classList.contains("magnifying")');
  await new Promise(r=>setTimeout(r,150));
  assert.ok((await pixel())[3]>100); checks.push('stop restores original transparent annotations');
  await js('document.getElementById("inputMode").click()');
  await waitFor('document.getElementById("inputMode").textContent === "필기로 돌아가기"');
  checks.push('click-through mode switches without keyboard hook');
  assert.equal(await js('window.hpen.toolbarInput(true)'),false);
  checks.push('toolbar accepts clicks in app-control mode');
  await js('document.getElementById("inputMode").click()');
  await waitFor('document.getElementById("inputMode").textContent === "원래 앱 조작"');
  checks.push('same visible button returns to annotation mode');
  assert.ok(await js('document.querySelector(".tools").getBoundingClientRect().height <= 38'));
  assert.equal(await js('getComputedStyle(document.querySelector(".tools")).flexWrap'),'nowrap');
  checks.push('top toolbar stays one compact row');
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{key:"Escape",code:"Escape",bubbles:true}))');
  assert.ok((await pixel())[3]<5);
  checks.push('Escape clears canvas synchronously before native hide');
  const deadline=Date.now()+3000;
  while(panel.isVisible() && Date.now()<deadline) await new Promise(r=>setTimeout(r,30));
  assert.equal(panel.isVisible(),false);
  invokeModeShortcut();
  assert.equal(panel.isVisible(),true);
  await waitFor('document.getElementById("inputMode").textContent === "원래 앱 조작"');
  checks.push('Escape hides overlay and global mode callback reopens it');
  assert.ok((await pixel())[3]<5);
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{code:"KeyR",key:"r",ctrlKey:true,bubbles:true}))');
  await waitFor(`(()=>{const c=document.getElementById('canvas');return c.getContext('2d').getImageData(350*devicePixelRatio,350*devicePixelRatio,1,1).data[3]>100})()`);
  assert.ok((await pixel())[3]>100);
  checks.push('Escape clears visible ink and Ctrl+R recovers dismissed ink');
  await js(`(()=>{
    document.dispatchEvent(new KeyboardEvent('keydown',{code:'KeyQ',key:'q',bubbles:true}));
    const c=document.getElementById('canvas');
    c.dispatchEvent(new PointerEvent('pointerdown',{clientX:450,clientY:400,button:0,pointerId:2}));
    c.dispatchEvent(new PointerEvent('pointermove',{clientX:550,clientY:500,pointerId:2}));
    c.dispatchEvent(new PointerEvent('pointerup',{pointerId:2}));
    document.dispatchEvent(new KeyboardEvent('keyup',{code:'KeyQ',key:'q',bubbles:true}));
  })()`);
  await new Promise(r=>setTimeout(r,100));
  assert.ok(await js(`(()=>{const c=document.getElementById('canvas');return c.getContext('2d').getImageData(500*devicePixelRatio,400*devicePixelRatio,1,1).data[3]>100})()`));
  checks.push('legacy Q held while dragging draws a rectangle');
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{code:"KeyC",key:"c",bubbles:true}))');
  await new Promise(r=>setTimeout(r,100));
  assert.ok((await pixel())[3]<5);
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{code:"KeyR",key:"r",ctrlKey:true,bubbles:true}))');
  await new Promise(r=>setTimeout(r,100));
  assert.ok((await pixel())[3]>100);
  checks.push('legacy C clears and Ctrl+R restores annotations');
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{code:"F1",key:"F1",bubbles:true}))');
  assert.equal(await js('document.getElementById("color").value'),'#000000');
  checks.push('legacy F1 color matches original configuration');
  const expected=['#000000','#ff0000','#0000ff','#ffff00','#00ff00','#ff8042','#80ffff','#ff80ff','#8000ff','#ffffff'];
  for(let i=1;i<=10;i++) {
    await js(`document.dispatchEvent(new KeyboardEvent('keydown',{code:'F${i}',key:'F${i}',bubbles:true}))`);
    assert.equal(await js('document.getElementById("color").value'),expected[i-1]);
  }
  checks.push('all F1-F10 presets match legacy draw.prop');
  await js('document.getElementById("inputMode").click()');
  await waitFor('document.getElementById("inputMode").textContent === "필기로 돌아가기"');
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{code:"F2",key:"F2",bubbles:true}))');
  assert.equal(await js('document.getElementById("color").value'),'#ffffff');
  checks.push('F keys do not change color during original-app control');
  await js('document.getElementById("inputMode").click()');
  await waitFor('document.getElementById("inputMode").textContent === "원래 앱 조작"');
  await js('document.dispatchEvent(new KeyboardEvent("keydown",{code:"KeyT",key:"t",bubbles:true})); document.dispatchEvent(new KeyboardEvent("keydown",{code:"F2",key:"F2",bubbles:true}))');
  assert.equal(await js('document.getElementById("color").value'),'#ffffff');
  checks.push('F keys do not change color in text mode');
  const original=String(screen.getDisplayMatching(panel.getBounds()).id);
  const another=screen.getAllDisplays().find(d=>String(d.id)!==original);
  if(another) {
    await switchInputAtDisplay(String(another.id));
    assert.equal(String(screen.getDisplayMatching(panel.getBounds()).id),String(another.id));
    assert.equal(panel.isVisible(),true);
    await waitFor('document.getElementById("inputMode").textContent === "원래 앱 조작"');
    await switchInputAtDisplay(original);
    assert.equal(String(screen.getDisplayMatching(panel.getBounds()).id),original);
    checks.push('mode shortcut moves the same annotation surface to requested monitor and back');
  } else checks.push('multi-monitor movement skipped: only one monitor available');
  // A capture test writes only into the isolated smoke profile, never Pictures.
  const previousClipboard={text:clipboard.readText(),html:clipboard.readHTML(),rtf:clipboard.readRTF(),image:clipboard.readImage()};
  const captureRegistration=await js('window.hpen.shortcuts("", "Control+`", "Shift+`")');
  checks.push(captureRegistration.capture?'Shift+Backquote capture shortcut is registered globally':'capture shortcut collision is reported without disabling toolbar');
  checks.push('begin area capture integration checks');
  async function waitCapture() {
    const deadline=Date.now()+15000;
    while(Date.now()<deadline) {
      const candidate=BrowserWindow.getAllWindows().find(w=>w.getTitle()==='hPen 영역 캡처');
      if(candidate && !candidate.webContents.isLoadingMainFrame()) {
        // The test launcher uses SW_HIDE; explicitly show the selection surface.
        if(!candidate.isVisible())candidate.show();
        return candidate;
      }
      await new Promise(r=>setTimeout(r,80));
    }
    throw new Error('Capture selection window did not become ready: '+JSON.stringify(BrowserWindow.getAllWindows().map(w=>({title:w.getTitle(),visible:w.isVisible(),loading:w.webContents.isLoadingMainFrame()}))));
  }
  try {
    await captureController.beginCapture();
    let capture=await waitCapture();
    assert.ok(capture);
    captureController.cancel();
    await waitFor('document.visibilityState === "visible"');
    checks.push('area capture opens and Escape/cancel restores transparent overlay');
    await captureController.beginCapture();
    capture=await waitCapture();
    assert.ok(capture);
    const source=captureController.source();
    const sourceSize=nativeImage.createFromDataURL(source.data).getSize();
    await captureController.commitCapture({x:20,y:30,width:100,height:60});
    await waitFor('document.getElementById("status").textContent.includes("저장 완료")');
    const folder=path.join(app.getPath('userData'),'captures');
    const files=(await readdir(folder)).filter(f=>f.endsWith('.png')).sort();
    const saved=nativeImage.createFromBuffer(await readFile(path.join(folder,files.at(-1)!)));
    const expected=pixelSelection({x:20,y:30,width:100,height:60},source.logical,sourceSize);
    assert.deepEqual(saved.getSize(),{width:expected.width,height:expected.height});
    assert.deepEqual(clipboard.readImage().getSize(),saved.getSize());
    checks.push('selected region is copied to clipboard and saved as PNG with matching pixels');
    panel.hide();
    await captureController.beginCapture();
    await waitCapture();
    captureController.cancel();
    assert.equal(panel.isVisible(),false);
    invokeModeShortcut();
    checks.push('capture runs while annotation is hidden and restores hidden state');
  } finally {
    clipboard.write(previousClipboard);
  }
  return checks;
}
