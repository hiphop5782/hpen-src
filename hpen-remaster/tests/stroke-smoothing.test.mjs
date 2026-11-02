import { test } from 'node:test';
import assert from 'node:assert/strict';
import { build } from 'esbuild';
const result=await build({entryPoints:['src/stroke-smoothing.ts'],bundle:true,format:'esm',write:false});
const {smoothStroke}=await import(`data:text/javascript;base64,${Buffer.from(result.outputFiles[0].text).toString('base64')}`);

test('smoothStroke creates continuous midpoint curves ending at the final sample',()=>{
  const points=[{x:0,y:0},{x:10,y:20},{x:20,y:0},{x:30,y:10}];
  assert.deepEqual(smoothStroke(points),[
    {control:{x:10,y:20},end:{x:15,y:10}},
    {control:{x:20,y:0},end:{x:25,y:5}},
    {control:{x:30,y:10},end:{x:30,y:10}}
  ]);
});

test('smoothStroke leaves one and two point strokes to the straight fallback',()=>{
  assert.deepEqual(smoothStroke([{x:1,y:2}]),[]);
  assert.deepEqual(smoothStroke([{x:1,y:2},{x:3,y:4}]),[]);
});
