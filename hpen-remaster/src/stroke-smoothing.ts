export type StrokePoint={x:number;y:number};
export type QuadraticStrokeSegment={control:StrokePoint;end:StrokePoint};

export function smoothStroke(points:StrokePoint[]):QuadraticStrokeSegment[] {
  if(points.length<3)return [];
  const segments:QuadraticStrokeSegment[]=[];
  for(let index=1;index<points.length-1;index++) {
    const control=points[index],next=points[index+1];
    segments.push({control,end:{x:(control.x+next.x)/2,y:(control.y+next.y)/2}});
  }
  segments.push({control:points[points.length-1],end:points[points.length-1]});
  return segments;
}
