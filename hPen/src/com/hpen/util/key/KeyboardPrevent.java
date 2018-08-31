package com.hpen.util.key;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

public class KeyboardPrevent {
	public static final int WINDOWS_LEFT = 0x5B;
	public static final int WINDOWS_RIGHT = 0x5C;
	public static final int MENU = 0x5D;
	public static final int ALT_RIGHT = 0x15;
	
	private static HHOOK hhk;
	private static LowLevelKeyboardProc keyboardHook;
	private static User32 lib;
	
	private static Map<Integer, Runnable> key = new HashMap<>();
	public static void addKey(int keyCode) {
		key.put(keyCode, null);
	}
	public static void addKey(int keyCode, Runnable action) {
		key.put(keyCode, action);
	}
	public static void removeKey(int keyCode) {
		key.remove(keyCode);
	}

	public static void blockWindowsKey() {
		if (isWindows()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					lib = User32.INSTANCE;
					HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
					keyboardHook = new LowLevelKeyboardProc() {
						public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
							if (nCode >= 0) {
//								System.out.println("nCode = "+nCode);
//								System.out.println("info.vkCode = "+info.vkCode);
//								System.out.println("info.flags = "+info.flags);
								if(info.flags == 1 && key.containsKey(info.vkCode)) {
									Runnable action = key.get(info.vkCode);
									if(action != null)
										action.run();
									return new LRESULT(1);
								}
							}
							return lib.CallNextHookEx(hhk, nCode, wParam, null);
						}
					};
					hhk = lib.SetWindowsHookEx(13, keyboardHook, hMod, 0);

					// This bit never returns from GetMessage
					MSG msg = new MSG();
					while (true) {
						int result = lib.GetMessage(msg, null, 0, 0);
						if (result == -1) {
							break;
						} else {
							lib.TranslateMessage(msg);
							lib.DispatchMessage(msg);
						}
					}
					lib.UnhookWindowsHookEx(hhk);
				}
			}).start();
		}
	}

	public static void unblockWindowsKey() {
		if (isWindows() && lib != null) {
			lib.UnhookWindowsHookEx(hhk);
		}
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
}
