import { build } from 'esbuild';
import { mkdir, copyFile } from 'node:fs/promises';
await mkdir('dist', { recursive: true });
await mkdir('dist/assets', { recursive: true });
for (const file of ['hpen.png', 'hpen.ico']) await copyFile(`assets/${file}`, `dist/assets/${file}`);
await build({ entryPoints: ['src/main.ts', 'src/preload.ts'], bundle: true, platform: 'node', external: ['electron'], outdir: 'dist', format: 'cjs' });
await build({ entryPoints: ['src/renderer.ts'], bundle: true, platform: 'browser', outfile: 'dist/renderer.js' });
await build({ entryPoints: ['src/capture.ts'], bundle: true, platform: 'browser', outfile: 'dist/capture.js' });
for (const file of ['index.html', 'style.css', 'capture.html', 'capture.css']) await copyFile(`src/${file}`, `dist/${file}`);
