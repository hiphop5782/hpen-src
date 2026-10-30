import { test } from 'node:test';
import assert from 'node:assert/strict';
import { build } from 'esbuild';
const result = await build({ entryPoints: ['src/geometry.ts'], bundle: true, format: 'esm', write: false });
const { cropRect } = await import(`data:text/javascript;base64,${Buffer.from(result.outputFiles[0].text).toString('base64')}`);
test('zoom stays inside source at all four corners', () => {
  for (const width of [1920,3840]) for (const z of [1,2,10]) for(const x of [0,.5,1]) for(const y of [0,.5,1]) {
    const r = cropRect(width,1080,z,x,y);
    assert.ok(r.x>=0 && r.y>=0 && r.x+r.w<=width && r.y+r.h<=1080);
    assert.equal(r.w,width/z);
  }
});
test('center follows normalized cursor coordinates independent of desktop origin',()=>{
  assert.deepEqual(cropRect(3840,2160,2,.5,.5),{x:960,y:540,w:1920,h:1080});
});
