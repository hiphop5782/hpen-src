import { test } from 'node:test';
import assert from 'node:assert/strict';
import { build } from 'esbuild';
const result=await build({entryPoints:['src/shape-move.ts'],bundle:true,format:'esm',write:false});
const { shapeHitTest,translateShape }=await import(`data:text/javascript;base64,${Buffer.from(result.outputFiles[0].text).toString('base64')}`);

test('translateShape moves every point without mutating history source',()=>{
  const shape={tool:'pen',width:4,color:'#000',points:[{x:10,y:20},{x:30,y:40}]};
  const moved=translateShape(shape,5,-7);
  assert.deepEqual(moved.points,[{x:15,y:13},{x:35,y:33}]);
  assert.deepEqual(shape.points,[{x:10,y:20},{x:30,y:40}]);
  assert.notEqual(moved,shape);
});

test('shapeHitTest finds strokes and ignores empty interior or distant points',()=>{
  assert.equal(shapeHitTest({tool:'line',width:4,points:[{x:10,y:10},{x:110,y:10}]},{x:50,y:15}),true);
  assert.equal(shapeHitTest({tool:'line',width:4,points:[{x:10,y:10},{x:110,y:10}]},{x:50,y:30}),false);
  const rect={tool:'rect',width:4,points:[{x:20,y:20},{x:100,y:80}]};
  assert.equal(shapeHitTest(rect,{x:22,y:50}),true);
  assert.equal(shapeHitTest(rect,{x:60,y:50}),false);
  assert.equal(shapeHitTest({tool:'ellipse',width:4,points:[{x:0,y:0},{x:100,y:60}]},{x:50,y:1}),true);
});

test('shapeHitTest includes text bounds',()=>{
  const text={tool:'text',width:4,text:'한글\ntext',points:[{x:100,y:200}]};
  assert.equal(shapeHitTest(text,{x:120,y:220}),true);
  assert.equal(shapeHitTest(text,{x:80,y:170}),false);
});
