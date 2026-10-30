import { app, BrowserWindow, desktopCapturer, screen, ipcMain, session, globalShortcut, dialog, clipboard, nativeImage, Tray, Menu } from 'electron';
import path from 'node:path';
import { writeFile, readFile, rename } from 'node:fs/promises';
import { defaultShortcuts, validShortcut } from './shortcuts';
import { installCapture } from './capture-main';
import { runSmoke } from './smoke';
const smoke = process.argv.includes('--smoke-test');
if(smoke) app.setPath('userData', path.join(process.cwd(), '.smoke-profile'));

let win: BrowserWindow;
let overlay: BrowserWindow | null = null;
let passThrough = false;
let tray: Tray;
let selectedDisplay = '';
let quitting = false;
let shortcutOK = true;
let overlayShortcutOK = false;
let shortcut = '';
let inputShortcut = '';
let captureShortcut='';
let captureShortcutOK=false;
if (!app.requestSingleInstanceLock()) app.quit();
else {
  app.on('second-instance', () => { if(overlay) { overlay.show(); overlay.focus(); } });
  app.whenReady().then(async () => {
    win = new BrowserWindow({ width: 1120, height: 780, minWidth: 800, minHeight: 600, show: false, title: 'hPen 설정', backgroundColor: '#10151f',
      webPreferences: { preload: path.join(__dirname, 'preload.js'), contextIsolation: true, nodeIntegration: false, sandbox: true } });
    win.setMenuBarVisibility(false);
    // Windows 11: keep the magnifier out of its own desktop capture.
    win.setContentProtection(true);
    win.webContents.setWindowOpenHandler(() => ({ action: 'deny' }));
    win.webContents.on('will-navigate', e => e.preventDefault());
    const trusted = (event: Electron.IpcMainInvokeEvent) => {
      const owner = BrowserWindow.fromWebContents(event.sender);
      if (!owner || (owner !== win && owner !== overlay) || event.senderFrame !== owner.webContents.mainFrame) throw new Error('Invalid sender');
    };
    let captureWasVisible=false,captureWasSettings=false,captureWasPassThrough=false;
    const captureController=installCapture({trusted,smoke,
      hide:()=>{captureWasVisible=!!overlay?.isVisible();captureWasSettings=win.isVisible();captureWasPassThrough=passThrough;overlay?.webContents.send('stop-capture');overlay?.hide();win.hide();},
      restore:message=>{
        if(quitting)return;
        if(overlay){
          passThrough=captureWasPassThrough;overlay.setIgnoreMouseEvents(passThrough,{forward:true});
          if(captureWasVisible){overlay.show();if(!passThrough)overlay.focus();}
          overlay.webContents.send('input-mode',passThrough);
          if(message)overlay.webContents.send('capture-status',message);
        }
        if(captureWasSettings){win.show();win.focus();}
      }
    });
    session.defaultSession.setDisplayMediaRequestHandler(async (request, callback) => {
      try {
        if (!overlay || request.frame !== overlay.webContents.mainFrame || !overlay.isVisible()) return callback({});
        const sources = await desktopCapturer.getSources({ types: ['screen'], thumbnailSize: { width: 0, height: 0 } });
        const source = sources.find(s => s.display_id === selectedDisplay);
        callback(source ? { video: source } : {});
      } catch { callback({}); }
    });
    ipcMain.handle('displays', event => { trusted(event); return { shortcutOK, shortcut, inputShortcut, overlayShortcutOK, captureShortcut,captureShortcutOK,selectedDisplay, displays: screen.getAllDisplays().map(d => ({ id: String(d.id), label: d.label || `모니터 ${d.id}`, bounds: d.bounds, scaleFactor: d.scaleFactor })) }; });
    ipcMain.handle('select-display', (event, id: string) => { trusted(event); if (!screen.getAllDisplays().some(d => String(d.id) === id)) throw new Error('모니터를 찾을 수 없습니다.'); selectedDisplay = id; });
    ipcMain.handle('cursor', event => { trusted(event); const d = screen.getAllDisplays().find(d => String(d.id) === selectedDisplay); const p = screen.getCursorScreenPoint(); return d ? { x: (p.x - d.bounds.x) / d.bounds.width, y: (p.y - d.bounds.y) / d.bounds.height } : null; });
    function toggleInput() {
      if (!overlay) return;
      if (!overlay.isVisible()) {
        passThrough=false; overlay.setIgnoreMouseEvents(false);
        win.hide(); overlay.show(); overlay.focus();
        overlay.webContents.send('input-mode',false);
        return;
      }
      passThrough = !passThrough;
      overlay.setIgnoreMouseEvents(passThrough, { forward: true });
      overlay.webContents.send('input-mode', passThrough);
      if (!passThrough) overlay.focus();
    }
    let switchingDisplay=false;
    async function switchInputAtDisplay(id: string) {
      if(switchingDisplay) return;
      const monitor=screen.getAllDisplays().find(d=>String(d.id)===id);
      if(!monitor) return;
      if(overlay && id===selectedDisplay) { toggleInput(); return; }
      switchingDisplay=true;
      try {
        if(!overlay) { await openOverlay(id); return; }
        // Stop magnification and repaint before moving the existing ink surface.
        await overlay.webContents.executeJavaScript("document.dispatchEvent(new Event('hpen-monitor-change'))");
        selectedDisplay=id;
        overlay.setBounds(monitor.workArea);
        passThrough=false; overlay.setIgnoreMouseEvents(false);
        win.hide(); overlay.show(); overlay.focus();
        overlay.webContents.send('input-mode',false);
        overlay.webContents.send('monitor-selected');
      } finally { switchingDisplay=false; }
    }
    function toggleInputAtCursor() {
      if(captureController.isSelecting())return;
      const id=String(screen.getDisplayNearestPoint(screen.getCursorScreenPoint()).id);
      void switchInputAtDisplay(id).catch(e=>dialog.showErrorBox('필기 화면 전환 실패',String(e)));
    }
    async function openOverlay(id: string) {
      const monitor = screen.getAllDisplays().find(d => String(d.id) === id);
      if (!monitor) throw new Error('모니터를 선택하세요.');
      selectedDisplay = id;
      if (overlay && !overlay.isDestroyed()) {
        const b = overlay.getBounds();
        if (b.x !== monitor.workArea.x || b.y !== monitor.workArea.y || b.width !== monitor.workArea.width || b.height !== monitor.workArea.height) {
          overlay.destroy(); overlay = null;
        }
      }
      if (!overlay) {
        overlay = new BrowserWindow({ ...monitor.workArea, frame: false, transparent: true, backgroundColor: '#00000000', alwaysOnTop: true, resizable: false, skipTaskbar: true, show: false,
          webPreferences: { preload: path.join(__dirname,'preload.js'), contextIsolation: true, nodeIntegration: false, sandbox: true } });
        overlay.setMenuBarVisibility(false);
        // Strokes stay visible in screen sharing; only the magnifier is capture-excluded.
        overlay.webContents.setWindowOpenHandler(() => ({ action: 'deny' }));
        overlay.webContents.on('will-navigate', e => e.preventDefault());
        overlay.on('closed', () => { overlay = null; });
        await overlay.loadFile(path.join(__dirname,'index.html'), { query: { mode: 'overlay' } });
      }
      passThrough = false; overlay.setIgnoreMouseEvents(false); overlay.webContents.send('input-mode', false);
      win.webContents.send('stop-capture'); win.hide(); overlay.show(); overlay.focus();
    }
    ipcMain.handle('overlay', async (event, id: string) => { trusted(event); await openOverlay(id); });
    ipcMain.handle('capture-protection', (event, active: boolean) => {
      trusted(event);
      if(BrowserWindow.fromWebContents(event.sender)!==overlay) throw new Error('Overlay only');
      overlay!.setContentProtection(Boolean(active));
    });
    function showSettings() { overlay?.webContents.send('stop-capture'); overlay?.hide(); win.show(); win.focus(); }
    ipcMain.handle('settings', event => { trusted(event); showSettings(); });
    ipcMain.handle('toggle-input', event => { trusted(event); toggleInput(); });
    ipcMain.handle('toolbar-input', (event, overToolbar: boolean) => {
      trusted(event);
      if(BrowserWindow.fromWebContents(event.sender)!==overlay) throw new Error('Overlay only');
      const ignore=passThrough && !overToolbar;
      overlay!.setIgnoreMouseEvents(ignore, { forward: true });
      return ignore;
    });
    ipcMain.handle('hide', event => { trusted(event); const owner = BrowserWindow.fromWebContents(event.sender)!; owner.hide(); });
    ipcMain.handle('pin', (event, pin: boolean) => { trusted(event); win.setAlwaysOnTop(Boolean(pin)); });
    ipcMain.handle('export', async (event, data: string, copy: boolean) => {
      trusted(event);
      if (typeof data !== 'string' || data.length > 100_000_000 || !data.startsWith('data:image/png;base64,')) throw new Error('잘못된 이미지입니다.');
      const image = nativeImage.createFromDataURL(data);
      if (image.isEmpty()) throw new Error('이미지가 비어 있습니다.');
      if (copy) { clipboard.writeImage(image); return true; }
      const result = await dialog.showSaveDialog(BrowserWindow.fromWebContents(event.sender)!, { defaultPath: `hPen-${Date.now()}.png`, filters: [{ name: 'PNG', extensions: ['png'] }] });
      if (result.canceled || !result.filePath) return false;
      await writeFile(result.filePath, image.toPNG()); return true;
    });
    function applyShortcuts(magnify: string, input: string, capture=defaultShortcuts.capture) {
      if(!validShortcut(magnify) || !validShortcut(input) || !validShortcut(capture)) throw new Error('Ctrl·Shift·Alt와 문자, 숫자, 백틱, Space, F키를 조합하세요.');
      const keys=[magnify,input,capture].filter(Boolean);
      if(new Set(keys).size!==keys.length) throw new Error('여러 기능에 같은 단축키를 지정할 수 없습니다.');
      for(const key of [shortcut,inputShortcut,captureShortcut]) if(key) globalShortcut.unregister(key);
      shortcut=''; inputShortcut='';captureShortcut='';
      shortcutOK=!magnify || globalShortcut.register(magnify, () => { if(overlay&&!captureController.isSelecting()) { overlay.show(); overlay.webContents.send('toggle-freeze'); } });
      if(shortcutOK) shortcut=magnify;
      overlayShortcutOK=!!input && globalShortcut.register(input,toggleInputAtCursor);
      if(overlayShortcutOK) inputShortcut=input;
      captureShortcutOK=!!capture && globalShortcut.register(capture,()=>{void captureController.beginCapture().catch(error=>dialog.showErrorBox('캡처 실패',String(error)));});
      if(captureShortcutOK)captureShortcut=capture;
      overlay?.webContents.send('shortcut-state', inputShortcut);
      return { magnify: shortcut, input: inputShortcut,capture:captureShortcut,failed: (!shortcutOK || (!!input && !overlayShortcutOK) || (!!capture && !captureShortcutOK)) };
    }
    const settingsPath=path.join(app.getPath('userData'),'shortcuts.json');
    let saved={...defaultShortcuts};
    try {
      const parsed=JSON.parse(await readFile(settingsPath,'utf8'));
      if(validShortcut(parsed.magnify) && validShortcut(parsed.input) && (!parsed.input || parsed.input!==parsed.magnify)) saved={...defaultShortcuts,...parsed};
    } catch { /* First run or unreadable preferences: use defaults. */ }
    if(saved.input==='Control+Space') {
      saved.input=defaultShortcuts.input;
      await writeFile(settingsPath,JSON.stringify(saved,null,2));
    }
    applyShortcuts(saved.magnify,saved.input,saved.capture);
    ipcMain.handle('shortcuts', async (event, magnify: string, input: string,capture=defaultShortcuts.capture) => {
      trusted(event);
      const result=applyShortcuts(magnify,input,capture);
      await writeFile(`${settingsPath}.tmp`,JSON.stringify({magnify,input,capture},null,2));
      await rename(`${settingsPath}.tmp`,settingsPath);
      return result;
    });
    ipcMain.handle('overlay-shortcut', event => { trusted(event); return overlayShortcutOK; });
    const icon = nativeImage.createFromPath(path.join(__dirname, 'assets', 'hpen.ico'));
    tray = new Tray(icon);
    tray.setToolTip('hPen — 실시간 돋보기');
    tray.setContextMenu(Menu.buildFromTemplate([{ label: '화면 필기 열기', click: () => { void openOverlay(selectedDisplay).catch(e=>dialog.showErrorBox('필기 열기 실패',String(e))); } }, { label: '필기 / 원래 앱 조작 전환', click: toggleInput }, { label: '필기 숨김', click: () => { overlay?.webContents.send('stop-capture'); overlay?.hide(); } }, { label: '설정', click: showSettings }, { label: '종료', click: () => app.quit() }]));
    tray.on('double-click', () => { void openOverlay(selectedDisplay); });
    win.on('close', event => { if (!quitting) { event.preventDefault(); win.webContents.send('stop-capture'); win.hide(); } });
    screen.on('display-removed', () => { overlay?.webContents.send('stop-capture'); overlay?.hide(); win.webContents.send('display-changed'); win.show(); });
    await win.loadFile(path.join(__dirname, 'index.html'));
    await openOverlay(String(screen.getDisplayNearestPoint(screen.getCursorScreenPoint()).id));
    if(smoke) {
      try {
        const checks=await runSmoke(overlay!,toggleInput,switchInputAtDisplay,captureController);
        await writeFile(path.join(process.cwd(),'smoke-result.json'),JSON.stringify({ passed:true,checks },null,2));
      } catch(e) {
        await writeFile(path.join(process.cwd(),'smoke-result.json'),JSON.stringify({ passed:false,error:e instanceof Error?e.stack:String(e) },null,2));
      } finally { app.quit(); }
    }
  }).catch(async error => {
    if(smoke) await writeFile(path.join(process.cwd(),'smoke-result.json'),JSON.stringify({passed:false,error:error instanceof Error?error.stack:String(error)},null,2));
    else dialog.showErrorBox('hPen 시작 실패',String(error));
    app.quit();
  });
}
app.on('before-quit', () => { quitting = true; globalShortcut.unregisterAll(); });
