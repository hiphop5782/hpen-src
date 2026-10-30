import { contextBridge, ipcRenderer } from 'electron';
contextBridge.exposeInMainWorld('hpen', {
  displays: () => ipcRenderer.invoke('displays'),
  selectDisplay: (id: string) => ipcRenderer.invoke('select-display', id),
  cursor: () => ipcRenderer.invoke('cursor'),
  hide: () => ipcRenderer.invoke('hide'),
  overlay: (id: string) => ipcRenderer.invoke('overlay', id),
  overlayShortcut: () => ipcRenderer.invoke('overlay-shortcut'),
  toggleInput: () => ipcRenderer.invoke('toggle-input'),
  toolbarInput: (overToolbar: boolean) => ipcRenderer.invoke('toolbar-input', overToolbar),
  captureProtection: (active: boolean) => ipcRenderer.invoke('capture-protection', active),
  settings: () => ipcRenderer.invoke('settings'),
  beginCapture: () => ipcRenderer.invoke('begin-capture'),
  captureSource: () => ipcRenderer.invoke('capture-source'),
  commitCapture: (rect: {x:number;y:number;width:number;height:number}) => ipcRenderer.invoke('commit-capture',rect),
  cancelCapture: () => ipcRenderer.invoke('cancel-capture'),
  shortcuts: (magnify: string, input: string,capture:string) => ipcRenderer.invoke('shortcuts', magnify, input,capture),
  pin: (value: boolean) => ipcRenderer.invoke('pin', value),
  export: (data: string, copy: boolean) => ipcRenderer.invoke('export', data, copy),
  on: (name: string, callback: (value?: boolean | string) => void) => { if (['toggle-freeze', 'stop-capture', 'display-changed', 'input-mode', 'shortcut-state', 'monitor-selected','capture-status'].includes(name)) ipcRenderer.on(name, (_event, value) => callback(value)); }
});
