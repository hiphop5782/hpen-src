import { cropRect } from './geometry';
import { shortcutFromEvent } from './shortcuts';
import { legacyTools, legacyColors } from './legacy-keys';
const overlayMode = new URLSearchParams(location.search).get('mode') === 'overlay';
if (overlayMode) document.body.classList.add('overlay');
// One toolbar, rather than separate capture and drawing rows.
if(overlayMode) {
  const tools=document.querySelector('.tools')!;
  const capture=document.querySelector('.capture-controls')!;
  for(const child of Array.from(capture.children).reverse()) if(child.id!=='display' && child.id!=='overlay') tools.prepend(child);
  capture.classList.add('merged');
}
type Display = { id: string; label: string; bounds: { width: number; height: number }; scaleFactor: number };
type Point = { x: number; y: number };
type Shape = { tool: string; color: string; width: number; points: Point[]; text?: string };
declare global { interface Window { hpen: {
  displays(): Promise<{ displays: Display[]; shortcutOK: boolean; shortcut: string; inputShortcut: string; overlayShortcutOK: boolean; captureShortcut:string;captureShortcutOK:boolean;selectedDisplay: string }>;
  selectDisplay(id: string): Promise<void>; cursor(): Promise<Point | null>; hide(): Promise<void>;
  overlay(id: string): Promise<void>; overlayShortcut(): Promise<boolean>; toggleInput(): Promise<void>;
  toolbarInput(overToolbar: boolean): Promise<boolean>; captureProtection(active: boolean): Promise<void>; settings(): Promise<void>;
  shortcuts(magnify: string, input: string,capture:string): Promise<{ magnify: string; input: string;capture:string; failed: boolean }>;
  pin(value: boolean): Promise<void>; export(data: string, copy: boolean): Promise<boolean>;
  on(name: string, callback: (value?: boolean | string) => void): void;
} } }
const el = <T extends HTMLElement>(id: string) => document.getElementById(id) as T;
const captureSetting=document.createElement('label');captureSetting.textContent='영역 캡처 ';
const captureKeyInput=document.createElement('input');captureKeyInput.id='captureKey';captureKeyInput.type='text';captureKeyInput.readOnly=true;captureKeyInput.placeholder='클릭 후 키 조합 입력';captureKeyInput.setAttribute('aria-label','영역 캡처 단축키');
const clearCaptureKey=document.createElement('button');clearCaptureKey.textContent='해제';clearCaptureKey.dataset.clearKey='captureKey';
captureSetting.append(captureKeyInput,clearCaptureKey);el('applyKeys').before(captureSetting);
const canvas = el<HTMLCanvasElement>('canvas'), ctx = canvas.getContext('2d')!;
const display = el<HTMLSelectElement>('display'), zoom = el<HTMLInputElement>('zoom');
const editor = el<HTMLTextAreaElement>('editor');
const video = document.createElement('video'); video.muted = true;
const snapshot = document.createElement('canvas');
let stream: MediaStream | null = null, frozen = overlayMode, busy = false, pinned = false;
let tool = 'pen', shapes: Shape[] = [], history: Shape[][] = [], future: Shape[][] = [], draft: Shape | null = null;
let directNotes: { shapes: Shape[]; history: Shape[][]; future: Shape[][] } | null = null;
const heldTools=new Set<string>();
let lastDrawing: Shape[]=[];
let dismissing=false;
const slots: (Shape[] | undefined)[]=new Array(10);
function record() { history.push(shapes.slice()); if(history.length > 200) history.shift(); future = []; }
let cursor = { x: .5, y: .5 }, textPoint = { x: 0, y: 0 };
let viewport = { x: 0, y: 0, w: 1, h: 1 }, captureGeneration = 0;
const status = (message: string) => { el('status').textContent = message; };
const button = (id: string, disabled: boolean) => { el<HTMLButtonElement>(id).disabled = disabled; };
function controls() {
  button('start', !!stream || busy); button('stop', !stream && !frozen); button('freeze', !stream || busy);
  display.disabled = !!stream || busy;
  el('freeze').textContent = frozen ? '실시간 복귀' : '멈춤';
  for (const id of ['copy','save','clear','undo','redo']) button(id, !frozen);
  document.querySelectorAll<HTMLButtonElement>('[data-tool]').forEach(b => b.disabled = !frozen);
  el('empty').hidden = !!stream || frozen;
  if(overlayMode) { el('copy').textContent='복사'; el('save').textContent='저장'; el('copy').title=stream?'확대 화면 복사':'필기만 복사'; el('save').title=stream?'확대 화면 PNG':'투명 필기 PNG'; }
}
async function loadDisplays() {
  const info = await window.hpen.displays();
  display.replaceChildren(...info.displays.map((d, i) => new Option(`${i + 1}. ${d.label} · ${d.bounds.width}×${d.bounds.height} · ${Math.round(d.scaleFactor * 100)}%`, d.id)));
  if(info.selectedDisplay) display.value=info.selectedDisplay;
  el<HTMLInputElement>('magnifyKey').value=info.shortcut;
  el<HTMLInputElement>('inputKey').value=info.inputShortcut;
  captureKeyInput.value=info.captureShortcut;
  el('inputMode').title=info.inputShortcut?`필기 ↔ 앱 조작 (${info.inputShortcut.replace('Control','Ctrl')})`:'필기 ↔ 앱 조작';
  if (!info.shortcutOK || !info.overlayShortcutOK || !info.captureShortcutOK) status('단축키가 꺼져 있거나 등록되지 않았습니다. 상단 버튼과 설정을 사용하세요.');
}
async function start() {
  if (busy || stream) return;
  busy = true; controls(); const generation = ++captureGeneration;
  let acquired: MediaStream | null = null;
  try {
    commitText();
    if(overlayMode) {
      directNotes={ shapes, history, future };
      await window.hpen.captureProtection(true);
    }
    await window.hpen.selectDisplay(display.value);
    acquired = await navigator.mediaDevices.getDisplayMedia({ video: { frameRate: { ideal: 30, max: 30 } }, audio: false });
    if (generation !== captureGeneration) { acquired.getTracks().forEach(t => t.stop()); return; }
    stream = acquired; video.srcObject = stream; await video.play();
    if(generation!==captureGeneration) { acquired.getTracks().forEach(t=>t.stop()); return; }
    frozen = false; shapes=[]; history=[]; future=[];
    document.body.classList.add('magnifying');
    stream.getVideoTracks()[0].addEventListener('ended', () => { if (stream === acquired) stop(); });
    status('실시간 확대 중 · 멈춤을 누르면 필기할 수 있습니다.');
  } catch (e) { acquired?.getTracks().forEach(t => t.stop()); stream = null; video.srcObject = null; if(overlayMode) { restoreDirect(); await window.hpen.captureProtection(false); } status(`돋보기 시작 실패: ${String(e)}`); }
  finally { busy = false; controls(); }
}
function commitText() {
  if (editor.hidden) return;
  if (editor.value.trim()) { record(); shapes.push({ tool: 'text', points: [textPoint], color: el<HTMLInputElement>('color').value, width: Number(el<HTMLInputElement>('width').value), text: editor.value }); lastDrawing=shapes.slice(); }
  editor.value = ''; editor.hidden = true; el('commit').hidden = true;
}
function toggleFreeze() {
  if (!stream || busy || !video.videoWidth) return;
  commitText(); draft = null;
  if (!frozen) {
    snapshot.width = video.videoWidth; snapshot.height = video.videoHeight;
    snapshot.getContext('2d')!.drawImage(video, 0, 0);
    shapes = []; history = []; future = []; frozen = true;
    status('멈춤 · 필기 가능 · 복귀 후 다음 멈춤에서는 새 장면이 열립니다.');
  } else { frozen = false; status('실시간 확대 중 · 이전 필기는 다음 멈춤 전까지 보관됩니다.'); }
  controls();
}
function stop() {
  ++captureGeneration; commitText(); draft = null;
  if (overlayMode) {
    const previous=stream; stream=null; previous?.getTracks().forEach(t=>t.stop()); video.pause(); video.srcObject=null;
    restoreDirect(); void window.hpen.captureProtection(false); controls();
    status('화면 직접 필기 · 배경은 계속 움직입니다.'); return;
  }
  if (stream && !frozen && video.videoWidth) {
    snapshot.width = video.videoWidth; snapshot.height = video.videoHeight;
    snapshot.getContext('2d')!.drawImage(video, 0, 0); shapes = []; history = []; future = []; frozen = true;
  }
  const previous = stream; stream = null; previous?.getTracks().forEach(t => t.stop());
  video.pause(); video.srcObject = null;
  status(frozen ? '캡처 종료 · 고정 화면은 필기·저장할 수 있습니다.' : '캡처 종료'); controls();
}
function restoreDirect() {
  if(directNotes) { ({ shapes, history, future }=directNotes); directNotes=null; }
  frozen=true; document.body.classList.remove('magnifying');
}
async function dismissInk() {
  if(dismissing) return;
  dismissing=true;
  try {
    commitText();
    const dismissed=shapes.slice();
    stop();
    if(dismissed.length) lastDrawing=dismissed;
    shapes=[]; history=[]; future=[]; draft=null;
    // Update the backing canvas immediately, before Windows caches a hidden window.
    draw();
    // Let Chromium present the cleared frame before hiding the native surface.
    await new Promise<void>(resolve=>requestAnimationFrame(()=>setTimeout(resolve,0)));
    await window.hpen.hide();
  } finally { dismissing=false; }
}
function drawShape(s: Shape) {
  const a = s.points[0], b = s.points[s.points.length - 1];
  ctx.strokeStyle = s.color; ctx.fillStyle = s.color; ctx.lineWidth = s.width; ctx.lineCap = 'round'; ctx.lineJoin = 'round';
  if (s.tool === 'text') { ctx.font = '28px "Malgun Gothic", sans-serif'; ctx.textBaseline = 'top'; s.text?.split('\n').forEach((line, i) => ctx.fillText(line, a.x, a.y + i * 36)); return; }
  ctx.beginPath();
  if (s.tool === 'rect') ctx.rect(a.x, a.y, b.x - a.x, b.y - a.y);
  else if(s.tool==='ellipse') ctx.ellipse((a.x+b.x)/2,(a.y+b.y)/2,Math.abs(b.x-a.x)/2,Math.abs(b.y-a.y)/2,0,0,Math.PI*2);
  else if(s.tool==='cross') {ctx.moveTo(a.x,a.y);ctx.lineTo(b.x,b.y);ctx.moveTo(a.x,b.y);ctx.lineTo(b.x,a.y);}
  else if(s.tool==='curve') {ctx.moveTo(a.x,a.y);ctx.quadraticCurveTo((a.x+b.x)/2,(a.y+b.y)/2-(b.x-a.x)/3,b.x,b.y);}
  else if(s.tool==='thunder') {
    const dx=b.x-a.x,dy=b.y-a.y,len=Math.hypot(dx,dy)||1,offset=Math.min(len/8,15);
    ctx.moveTo(a.x,a.y);ctx.lineTo(a.x+dx*.45-dy/len*offset,a.y+dy*.45+dx/len*offset);
    ctx.lineTo(a.x+dx*.55+dy/len*offset,a.y+dy*.55-dx/len*offset);ctx.lineTo(b.x,b.y);
  }
  else if(['arrow','reverseArrow','bothArrow'].includes(s.tool)) {
    ctx.moveTo(a.x,a.y);ctx.lineTo(b.x,b.y);
    const head=(tip:Point,from:Point)=>{const angle=Math.atan2(tip.y-from.y,tip.x-from.x),length=Math.max(12,s.width*3);for(const sign of [-1,1]) {ctx.moveTo(tip.x,tip.y);ctx.lineTo(tip.x-length*Math.cos(angle+sign*.45),tip.y-length*Math.sin(angle+sign*.45));}};
    if(s.tool!=='reverseArrow') head(b,a);if(s.tool!=='arrow') head(a,b);
  }
  else if (s.tool === 'line') { ctx.moveTo(a.x, a.y); ctx.lineTo(b.x, b.y); }
  else { ctx.moveTo(a.x, a.y); s.points.forEach(p => ctx.lineTo(p.x, p.y)); if (s.points.length === 1) ctx.lineTo(a.x + .01, a.y); }
  ctx.stroke();
}
function draw() {
  const w = Math.max(1, Math.round(canvas.clientWidth * devicePixelRatio)), h = Math.max(1, Math.round(canvas.clientHeight * devicePixelRatio));
  if (canvas.width !== w || canvas.height !== h) { canvas.width = w; canvas.height = h; }
  if (overlayMode && !stream) {
    ctx.setTransform(1,0,0,1,0,0); ctx.clearRect(0,0,w,h);
    // A nonzero alpha keeps blank desktop areas hit-testable on Windows.
    ctx.fillStyle='rgba(0,0,0,0.004)'; ctx.fillRect(0,0,w,h);
    viewport = { x:0, y:0, w:canvas.clientWidth, h:canvas.clientHeight };
    ctx.save(); ctx.scale(devicePixelRatio,devicePixelRatio); shapes.forEach(drawShape); if(draft) drawShape(draft); ctx.restore();
    return;
  }
  ctx.setTransform(1,0,0,1,0,0); ctx.fillStyle = '#080c13'; ctx.fillRect(0,0,w,h);
  const source = frozen ? snapshot : video;
  const sw = frozen ? snapshot.width : video.videoWidth, sh = frozen ? snapshot.height : video.videoHeight;
  if ((!stream && !frozen) || !sw || !sh) return;
  viewport = cropRect(sw, sh, Number(zoom.value), cursor.x, cursor.y);
  // Fit the captured aspect ratio without stretching, including when the window is resized.
  const scale = Math.min(w / viewport.w, h / viewport.h);
  const ox = (w - viewport.w * scale) / 2, oy = (h - viewport.h * scale) / 2;
  ctx.save(); ctx.beginPath(); ctx.rect(ox,oy,viewport.w*scale,viewport.h*scale); ctx.clip();
  ctx.drawImage(source, viewport.x,viewport.y,viewport.w,viewport.h, ox,oy,viewport.w*scale,viewport.h*scale);
  if (frozen) { ctx.translate(ox-viewport.x*scale,oy-viewport.y*scale); ctx.scale(scale,scale); shapes.forEach(drawShape); if(draft) drawShape(draft); }
  ctx.restore();
}
function frame() { if (!document.hidden) draw(); requestAnimationFrame(frame); }
let cursorPending = false;
setInterval(async () => {
  if (!stream || frozen || document.hidden || cursorPending || !el<HTMLInputElement>('track').checked) return;
  cursorPending = true;
  try { const p = await window.hpen.cursor(); if (p && p.x >= 0 && p.x <= 1 && p.y >= 0 && p.y <= 1) cursor = p; }
  finally { cursorPending = false; }
}, 33);
function point(e: PointerEvent): Point | null {
  const r = canvas.getBoundingClientRect(), w = canvas.width, h = canvas.height;
  const scale = Math.min(w/viewport.w,h/viewport.h), ox=(w-viewport.w*scale)/2, oy=(h-viewport.h*scale)/2;
  const x=(e.clientX-r.left)*w/r.width, y=(e.clientY-r.top)*h/r.height;
  if(x<ox || y<oy || x> w-ox || y>h-oy) return null;
  return { x: viewport.x+(x-ox)/scale, y: viewport.y+(y-oy)/scale };
}
canvas.addEventListener('pointerdown', e => {
  if (!frozen || e.button !== 0) return;
  commitText(); const p = point(e); if(!p) return;
  if (tool === 'text') { textPoint = p; editor.hidden = false; el('commit').hidden = false; editor.focus(); return; }
  const activeTool=heldTools.size===1 ? legacyTools[Array.from(heldTools)[0]] : tool;
  draft = { tool:activeTool, color: el<HTMLInputElement>('color').value, width: Number(el<HTMLInputElement>('width').value), points: [p] };
  if(e.isTrusted) canvas.setPointerCapture(e.pointerId);
});
canvas.addEventListener('pointermove', e => { if (draft) { const p = point(e); if (p) { if(draft.tool === 'pen') draft.points.push(p); else draft.points[1] = p; } } });
canvas.addEventListener('pointerup', () => { if(draft) { record(); shapes.push(draft); lastDrawing=shapes.slice(); draft=null; } });
canvas.addEventListener('pointercancel', () => draft=null);
window.addEventListener('blur', () => { heldTools.clear(); if (draft) { record(); shapes.push(draft); lastDrawing=shapes.slice(); draft=null; } });
canvas.addEventListener('wheel', e => { e.preventDefault(); zoom.value=String(Math.max(1,Math.min(10,Number(zoom.value)+(e.deltaY<0?.25:-.25)))); zoom.dispatchEvent(new Event('input')); }, {passive:false});
el('start').onclick=()=>void start(); el('stop').onclick=stop; el('freeze').onclick=toggleFreeze;
el('overlay').onclick=async()=>{ try { await window.hpen.overlay(display.value); } catch(e) { status(`필기 화면 열기 실패: ${String(e)}`); } };
el('inputMode').onclick=()=>{ commitText(); void window.hpen.toggleInput(); };
el('closeOverlay').onclick=()=>{ commitText(); void window.hpen.hide(); };
el('settings').onclick=()=>{ stop(); void window.hpen.settings(); };
el('applyKeys').onclick=async()=>{
  button('applyKeys',true);
  try {
    const result=await window.hpen.shortcuts(el<HTMLInputElement>('magnifyKey').value,el<HTMLInputElement>('inputKey').value,captureKeyInput.value);
    el<HTMLInputElement>('magnifyKey').value=result.magnify; el<HTMLInputElement>('inputKey').value=result.input;
    captureKeyInput.value=result.capture;
    status(result.failed?'단축키 설정 저장됨 · 일부 키는 등록 실패했습니다. 다른 조합으로 변경하세요.':(!result.magnify && !result.input?'단축키 해제 설정을 저장했습니다.':'단축키를 저장하고 적용했습니다. 다음 실행에도 유지됩니다.'));
  } catch(e) { status(String(e)); }
  finally { button('applyKeys',false); }
};
for(const id of ['magnifyKey','inputKey','captureKey']) {
  el<HTMLInputElement>(id).addEventListener('keydown',e=>{
    e.preventDefault(); e.stopPropagation();
    const key=shortcutFromEvent(e);
    if(key) el<HTMLInputElement>(id).value=key;
  });
}
document.querySelectorAll<HTMLButtonElement>('[data-clear-key]').forEach(b=>b.onclick=()=>{ el<HTMLInputElement>(b.dataset.clearKey!).value=''; });
window.hpen.on('shortcut-state',key=>{
  el<HTMLInputElement>('inputKey').value=typeof key==='string'?key:'';
  el('inputMode').title=key?`필기 ↔ 앱 조작 (${String(key).replace('Control','Ctrl')})`:'필기 ↔ 앱 조작';
});
el('commit').onclick=commitText;
el('hide').onclick=()=>{ stop(); void window.hpen.hide(); };
el('pin').onclick=()=>{ pinned=!pinned; void window.hpen.pin(pinned); el('pin').classList.toggle('selected',pinned); };
el('undo').onclick=()=>{ commitText(); const previous=history.pop(); if(previous) { future.push(shapes.slice()); shapes=previous; } };
el('redo').onclick=()=>{ const next=future.pop(); if(next) { history.push(shapes.slice()); shapes=next; } };
el('clear').onclick=()=>{ commitText(); if(shapes.length) { lastDrawing=shapes.slice(); record(); shapes=[]; status('모두 지움 · Ctrl+Z 또는 Ctrl+R로 복원할 수 있습니다.'); } };
document.querySelectorAll<HTMLButtonElement>('[data-tool]').forEach(b=>b.onclick=()=>{ commitText(); tool=b.dataset.tool!; document.querySelectorAll('[data-tool]').forEach(x=>x.classList.toggle('selected',x===b)); });
zoom.oninput=()=>{ el('zoomValue').textContent=`${Number(zoom.value)}×`; };
async function exportImage(copy:boolean) {
  if(!frozen) return; commitText(); draw();
  const output=document.createElement('canvas'); output.width=canvas.width; output.height=canvas.height;
  const outputContext=output.getContext('2d')!;
  if(overlayMode && !stream) {
    // Export actual transparent strokes without the hit-testing background.
    ctx.save(); ctx.clearRect(0,0,canvas.width,canvas.height); ctx.scale(devicePixelRatio,devicePixelRatio); shapes.forEach(drawShape); ctx.restore();
  }
  outputContext.drawImage(canvas,0,0); draw();
  try { const ok=await window.hpen.export(output.toDataURL('image/png'),copy); status(ok?(copy?'이미지를 복사했습니다.':'PNG를 저장했습니다.'):'저장을 취소했습니다. 필기는 유지됩니다.'); }
  catch(e) { status(`내보내기 실패: ${String(e)} · 필기는 유지됩니다.`); }
}
el('copy').onclick=()=>void exportImage(true); el('save').onclick=()=>void exportImage(false);
document.addEventListener('keydown', e=>{
  if(!e.isComposing && e.target===editor && e.key==='Escape') { e.preventDefault(); commitText(); tool='pen'; return; }
  if(e.isComposing || e.target instanceof HTMLTextAreaElement || e.target instanceof HTMLInputElement || e.target instanceof HTMLSelectElement) return;
  if(appInput) return;
  if(e.code==='Space' && stream) { e.preventDefault(); toggleFreeze(); }
  if(e.key==='Escape') {
    e.preventDefault(); heldTools.clear();
    if(tool==='text') {commitText();tool='pen';return;}
    void dismissInk(); return;
  }
  if(!frozen) return;
  if(e.ctrlKey && !e.altKey) {
    const key=e.code;
    if(['KeyZ','KeyY','KeyS','KeyR'].includes(key)) e.preventDefault();
    if(key==='KeyZ') el('undo').click(); if(key==='KeyY') el('redo').click();
    if(key==='KeyS') void exportImage(false);
    if(key==='KeyR' && lastDrawing.length) { record(); shapes=lastDrawing.slice(); }
    return;
  }
  if(/^Numpad[0-9]$/.test(e.code)) {
    e.preventDefault(); if(e.repeat) return; const index=Number(e.code.slice(-1));
    if(e.altKey) { slots[index]=shapes.slice(); status(`메모리 ${index} 저장`); }
    else if(slots[index]) { record(); shapes=slots[index]!.slice(); status(`메모리 ${index} 불러옴`); }
    return;
  }
  if(e.altKey || e.metaKey) return;
  if(legacyTools[e.code]) { e.preventDefault(); heldTools.add(e.code); }
  if(e.code==='KeyT') {e.preventDefault(); tool='text';status('텍스트 위치를 클릭하세요. Esc로 텍스트 모드 해제');}
  if(e.code==='KeyC') {e.preventDefault();if(!e.repeat) el('clear').click();}
  const colorIndex=/^F([1-9]|10)$/.exec(e.code);
  if(colorIndex && overlayMode && !document.hidden && tool!=='text') {
    e.preventDefault();
    el<HTMLInputElement>('color').value=legacyColors[Number(colorIndex[1])-1];
    status(`${e.code} · ${legacyColors[Number(colorIndex[1])-1]}`);
  }
});
document.addEventListener('keyup',e=>heldTools.delete(e.code));
window.hpen.on('toggle-freeze',()=>{ if(document.activeElement === editor) return; if(stream) toggleFreeze(); else void start(); });
window.hpen.on('stop-capture',stop);
document.addEventListener('hpen-monitor-change',()=>{ heldTools.clear(); stop(); draw(); });
window.hpen.on('monitor-selected',()=>{ void loadDisplays(); });
window.hpen.on('display-changed',()=>{ stop(); void loadDisplays(); status('모니터 구성이 바뀌었습니다. 다시 선택해 시작하세요.'); });
let appInput=false, toolbarHover=false;
const toolbar=document.querySelector<HTMLElement>('.tools')!;
let lastPointer={x:-1,y:-1};
function updateToolbarHit() {
  if(!overlayMode || !appInput) return;
  const r=toolbar.getBoundingClientRect();
  const over=lastPointer.x>=r.left && lastPointer.x<=r.right && lastPointer.y>=r.top && lastPointer.y<=r.bottom;
  if(over!==toolbarHover) { toolbarHover=over; void window.hpen.toolbarInput(over); }
}
document.addEventListener('mousemove',e=>{ lastPointer={x:e.clientX,y:e.clientY}; updateToolbarHit(); });
document.addEventListener('mouseleave',()=>{ if(appInput) { toolbarHover=false; void window.hpen.toolbarInput(false); } });
window.hpen.on('input-mode',passThrough=>{
  appInput=Boolean(passThrough);
  if(appInput) { commitText(); if(stream) stop(); }
  el('inputMode').textContent=appInput?'필기로 돌아가기':'원래 앱 조작';
  el('inputMode').classList.toggle('return-to-ink',appInput);
  status(appInput?'원래 앱 조작 중 · 상단의 필기로 돌아가기 버튼을 누르세요.':'화면 직접 필기');
  toolbarHover=false;
  if(appInput) updateToolbarHit();
});
document.addEventListener('visibilitychange',()=>{ if(document.hidden) stop(); });
controls(); void loadDisplays(); requestAnimationFrame(frame);
if(overlayMode) {
  el<HTMLInputElement>('color').value=legacyColors[0];
  el('color').title='F1 검정 · F2 빨강 · F3 파랑 · F4 노랑 · F5 초록 · F6 주황 · F7 하늘 · F8 분홍 · F9 보라 · F10 흰색';
  el('inputMode').hidden=false; el('closeOverlay').hidden=false;
  el('settings').hidden=false;
  const captureButton=document.createElement('button');captureButton.id='capture';captureButton.textContent='캡처';captureButton.title='영역 캡처 → 클립보드 복사 + 사진/hPen에 PNG 저장';
  captureButton.onclick=async()=>{
    captureButton.disabled=true;
    try {await (window.hpen as typeof window.hpen & {beginCapture():Promise<void>}).beginCapture();}
    catch(error){status(String(error));}finally{captureButton.disabled=false;}
  };
  toolbar.insertBefore(captureButton,el('settings'));
  el('start').textContent='돋보기'; el('stop').textContent='확대 종료';
  el('undo').textContent='↶'; el('undo').title='실행 취소 (Ctrl+Z)'; el('undo').setAttribute('aria-label','실행 취소');
  el('redo').textContent='↷'; el('redo').title='다시 실행 (Ctrl+Y)'; el('redo').setAttribute('aria-label','다시 실행');
  el('clear').textContent='지우기'; el('clear').title='모든 필기 지우기';
  el('empty').hidden=true;
  document.querySelector('footer span:last-child')!.textContent='배경은 계속 움직입니다 · 앱 조작 중 트레이 메뉴로 필기 복귀';
  status('화면 직접 필기 · Esc 닫기 · 트레이로 입력 모드 전환');
}
window.hpen.on('capture-status',message=>{if(typeof message==='string')status(message);});
