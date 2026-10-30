import {test} from 'node:test';
import assert from 'node:assert/strict';
import {build} from 'esbuild';
const result=await build({entryPoints:['src/capture-geometry.ts'],bundle:true,format:'esm',write:false});
const {selectionRect,pixelSelection}=await import(`data:text/javascript;base64,${Buffer.from(result.outputFiles[0].text).toString('base64')}`);
test('capture works in both drag directions and clamps desktop edges',()=>{
  assert.deepEqual(selectionRect({x:100,y:120},{x:20,y:30},1920,1080),{x:20,y:30,width:80,height:90});
  assert.deepEqual(selectionRect({x:-10,y:-5},{x:2000,y:1200},1920,1080),{x:0,y:0,width:1920,height:1080});
});
test('capture uses actual image pixels at fractional DPI without border loss',()=>{
  assert.deepEqual(pixelSelection({x:10,y:20,width:100,height:50},{width:1280,height:720},{width:1920,height:1080}),{x:15,y:30,width:150,height:75});
  assert.throws(()=>pixelSelection({x:0,y:0,width:NaN,height:10},{width:1280,height:720},{width:1920,height:1080}));
});
