export type Selection={ x:number; y:number; width:number; height:number };
export function selectionRect(a:{x:number;y:number},b:{x:number;y:number},width:number,height:number):Selection {
  const clamp=(v:number,max:number)=>Math.min(max,Math.max(0,v));
  const x1=clamp(a.x,width),x2=clamp(b.x,width),y1=clamp(a.y,height),y2=clamp(b.y,height);
  return {x:Math.min(x1,x2),y:Math.min(y1,y2),width:Math.abs(x2-x1),height:Math.abs(y2-y1)};
}
export function pixelSelection(r:Selection,logical:{width:number;height:number},pixels:{width:number;height:number}):Selection {
  if(![r.x,r.y,r.width,r.height].every(Number.isFinite) || r.width<=0 || r.height<=0 || logical.width<=0 || logical.height<=0) throw new Error('캡처 영역을 다시 선택하세요.');
  const left=Math.max(0,Math.min(pixels.width,Math.floor(r.x*pixels.width/logical.width)));
  const top=Math.max(0,Math.min(pixels.height,Math.floor(r.y*pixels.height/logical.height)));
  const right=Math.max(left,Math.min(pixels.width,Math.ceil((r.x+r.width)*pixels.width/logical.width)));
  const bottom=Math.max(top,Math.min(pixels.height,Math.ceil((r.y+r.height)*pixels.height/logical.height)));
  if(right===left || bottom===top) throw new Error('캡처 영역이 비어 있습니다.');
  return {x:left,y:top,width:right-left,height:bottom-top};
}
