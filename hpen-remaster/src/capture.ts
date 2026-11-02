import { selectionRect, type Selection } from './capture-geometry';
const api=(window as unknown as {hpen:{captureSource():Promise<{data:string;logical:{width:number;height:number};includesInk:boolean}>;commitCapture(rect:Selection):Promise<string>;cancelCapture():Promise<void>}}).hpen;
const canvas=document.getElementById('selection') as HTMLCanvasElement,ctx=canvas.getContext('2d')!,hint=document.getElementById('hint')!;
const image=new Image(),composite=document.createElement('canvas'),compositeContext=composite.getContext('2d')!;
let anchor:{x:number;y:number}|null=null,rect:Selection|null=null,busy=false,ready=false;
function draw() {
  canvas.width=Math.round(innerWidth*devicePixelRatio);canvas.height=Math.round(innerHeight*devicePixelRatio);
  ctx.drawImage(composite,0,0,canvas.width,canvas.height);
  if(rect) {
    const sx=composite.width/innerWidth,sy=composite.height/innerHeight;
    ctx.strokeStyle='#76e0c2';ctx.lineWidth=2*devicePixelRatio;ctx.strokeRect(rect.x*devicePixelRatio,rect.y*devicePixelRatio,rect.width*devicePixelRatio,rect.height*devicePixelRatio);
    hint.textContent=`${Math.round(rect.width*sx)} × ${Math.round(rect.height*sy)} px · 놓으면 클립보드 복사 + 파일 저장 · Esc 취소`;
  }
}
canvas.onpointerdown=e=>{if(busy||!ready||e.button!==0)return;anchor={x:e.clientX,y:e.clientY};rect=null;if(e.isTrusted)canvas.setPointerCapture(e.pointerId);};
canvas.onpointermove=e=>{if(!anchor||busy)return;rect=selectionRect(anchor,{x:e.clientX,y:e.clientY},innerWidth,innerHeight);draw();};
canvas.onpointerup=async e=>{
  if(!anchor||busy)return;rect=selectionRect(anchor,{x:e.clientX,y:e.clientY},innerWidth,innerHeight);anchor=null;
  if(rect.width<2||rect.height<2){hint.textContent='너비와 높이가 2px 이상인 영역을 다시 선택하세요.';return;}
  busy=true;hint.textContent='복사 및 저장 중…';
  try {
    await api.commitCapture(rect);
  }catch(error){busy=false;hint.textContent=String(error);}
};
canvas.onpointercancel=()=>{anchor=null;rect=null;if(ready)draw();};
document.onkeydown=e=>{if(e.key==='Escape'&&!busy)void api.cancelCapture();};
const load=(target:HTMLImageElement,source:string)=>new Promise<void>((resolve,reject)=>{target.onload=()=>resolve();target.onerror=()=>reject(new Error('캡처 이미지를 불러올 수 없습니다.'));target.src=source;});
void api.captureSource().then(async source=>{
  await load(image,source.data);
  composite.width=image.width;composite.height=image.height;compositeContext.drawImage(image,0,0);
  ready=true;draw();
}).catch(error=>{hint.textContent=String(error);});
