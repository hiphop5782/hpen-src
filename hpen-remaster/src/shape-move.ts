export type ShapePoint = { x: number; y: number };
export type MovableShape = { tool: string; width: number; points: ShapePoint[]; text?: string;fontSize?:number };

const distance = (a: ShapePoint, b: ShapePoint) => Math.hypot(a.x-b.x,a.y-b.y);

function segmentDistance(p:ShapePoint,a:ShapePoint,b:ShapePoint) {
  const dx=b.x-a.x,dy=b.y-a.y,lengthSquared=dx*dx+dy*dy;
  if(!lengthSquared) return distance(p,a);
  const t=Math.max(0,Math.min(1,((p.x-a.x)*dx+(p.y-a.y)*dy)/lengthSquared));
  return distance(p,{x:a.x+t*dx,y:a.y+t*dy});
}

function hitsSegments(p:ShapePoint,points:ShapePoint[],tolerance:number,closed=false) {
  if(points.length===1) return distance(p,points[0])<=tolerance;
  for(let i=1;i<points.length;i++) if(segmentDistance(p,points[i-1],points[i])<=tolerance) return true;
  return closed && points.length>2 && segmentDistance(p,points[points.length-1],points[0])<=tolerance;
}

function sampledQuadratic(a:ShapePoint,control:ShapePoint,b:ShapePoint) {
  const points:ShapePoint[]=[];
  for(let i=0;i<=24;i++) {
    const t=i/24,u=1-t;
    points.push({x:u*u*a.x+2*u*t*control.x+t*t*b.x,y:u*u*a.y+2*u*t*control.y+t*t*b.y});
  }
  return points;
}

export function shapeHitTest(shape:MovableShape,p:ShapePoint) {
  const a=shape.points[0],b=shape.points[shape.points.length-1];
  if(!a||!b) return false;
  const tolerance=Math.max(8,shape.width/2+5);
  if(shape.tool==='text') {
    const fontSize=shape.fontSize||28,lineHeight=Math.round(fontSize*1.3),lines=(shape.text||'').split('\n'),width=Math.max(1,...lines.map(line=>Array.from(line).length))*fontSize;
    return p.x>=a.x-tolerance && p.x<=a.x+width+tolerance && p.y>=a.y-tolerance && p.y<=a.y+lines.length*lineHeight+tolerance;
  }
  if(shape.tool==='rect') return hitsSegments(p,[a,{x:b.x,y:a.y},b,{x:a.x,y:b.y}],tolerance,true);
  if(shape.tool==='ellipse') {
    const cx=(a.x+b.x)/2,cy=(a.y+b.y)/2,rx=Math.abs(b.x-a.x)/2,ry=Math.abs(b.y-a.y)/2;
    if(!rx||!ry) return hitsSegments(p,[a,b],tolerance);
    const points=Array.from({length:49},(_,i)=>{const angle=i/48*Math.PI*2;return{x:cx+Math.cos(angle)*rx,y:cy+Math.sin(angle)*ry};});
    return hitsSegments(p,points,tolerance);
  }
  if(shape.tool==='cross') return hitsSegments(p,[a,b],tolerance)||hitsSegments(p,[{x:a.x,y:b.y},{x:b.x,y:a.y}],tolerance);
  if(shape.tool==='curve') return hitsSegments(p,sampledQuadratic(a,{x:(a.x+b.x)/2,y:(a.y+b.y)/2-(b.x-a.x)/3},b),tolerance);
  if(shape.tool==='thunder') {
    const dx=b.x-a.x,dy=b.y-a.y,len=Math.hypot(dx,dy)||1,offset=Math.min(len/8,15);
    return hitsSegments(p,[a,{x:a.x+dx*.45-dy/len*offset,y:a.y+dy*.45+dx/len*offset},{x:a.x+dx*.55+dy/len*offset,y:a.y+dy*.55-dx/len*offset},b],tolerance);
  }
  return hitsSegments(p,shape.points,tolerance);
}

export function translateShape<T extends MovableShape>(shape:T,dx:number,dy:number):T {
  return {...shape,points:shape.points.map(point=>({x:point.x+dx,y:point.y+dy}))};
}
