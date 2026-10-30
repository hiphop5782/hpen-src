export const defaultShortcuts = { magnify: '', input: 'Control+`', capture: 'Shift+`' };

export function validShortcut(value: unknown): value is string {
  return typeof value === 'string' && (value === '' || /^(?:(?:Control|Shift|Alt|Super)\+)+(?:[A-Z0-9`]|Space|F(?:[1-9]|1[0-9]|2[0-4]))$/.test(value));
}

export function shortcutFromEvent(event: Pick<KeyboardEvent, 'code' | 'ctrlKey' | 'shiftKey' | 'altKey' | 'metaKey'>): string | null {
  const key = event.code === 'Backquote' ? '`' : event.code === 'Space' ? 'Space' : /^Key[A-Z]$/.test(event.code) ? event.code.slice(3) : /^Digit[0-9]$/.test(event.code) ? event.code.slice(5) : /^F(?:[1-9]|1[0-9]|2[0-4])$/.test(event.code) ? event.code : '';
  const modifiers = [event.ctrlKey ? 'Control' : '', event.shiftKey ? 'Shift' : '', event.altKey ? 'Alt' : '', event.metaKey ? 'Super' : ''].filter(Boolean);
  return key && modifiers.length ? [...modifiers, key].join('+') : null;
}
