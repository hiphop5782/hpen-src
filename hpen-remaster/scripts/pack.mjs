import { build, Platform, Arch } from 'electron-builder';
await build({targets:Platform.WINDOWS.createTarget(['dir'],Arch.x64)});
