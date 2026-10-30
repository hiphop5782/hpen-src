import { test } from 'node:test';
import assert from 'node:assert/strict';
import { build } from 'esbuild';
const result=await build({entryPoints:['src/shortcuts.ts'],bundle:true,format:'esm',write:false});
const { shortcutFromEvent, validShortcut, defaultShortcuts }=await import(`data:text/javascript;base64,${Buffer.from(result.outputFiles[0].text).toString('base64')}`);
const base={ctrlKey:false,shiftKey:false,altKey:false,metaKey:false};
test('Ctrl+Backquote is default and captured independently of IME character',()=>{
  assert.equal(defaultShortcuts.input,'Control+`');
  assert.equal(defaultShortcuts.capture,'Shift+`');
  assert.equal(shortcutFromEvent({...base,shiftKey:true,code:'Backquote'}),'Shift+`');
  assert.equal(shortcutFromEvent({...base,ctrlKey:true,code:'Backquote'}),'Control+`');
  assert.equal(shortcutFromEvent({...base,ctrlKey:true,code:'Space'}),'Control+Space');
  assert.equal(shortcutFromEvent({...base,ctrlKey:true,shiftKey:true,code:'KeyQ'}),'Control+Shift+Q');
});
test('unmodified text and modifier-only presses cannot become global shortcuts',()=>{
  assert.equal(shortcutFromEvent({...base,code:'Space'}),null);
  assert.equal(shortcutFromEvent({...base,ctrlKey:true,code:'ControlLeft'}),null);
  assert.equal(validShortcut('Alt+Tab'),false);
  assert.equal(validShortcut(''),true);
  assert.equal(validShortcut('Control+Space'),true);
  assert.equal(validShortcut('Control+`'),true);
});
