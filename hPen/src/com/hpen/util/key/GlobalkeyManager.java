package com.hpen.util.key;

import java.io.File;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class GlobalkeyManager {
	private static JIntellitype ins;
	static {
		setGlobalKeyEvent();
	}
	public static void setGlobalKeyEvent(){
		JIntellitype.setLibraryLocation(new File("jintellitype.dll"));
		ins = JIntellitype.getInstance();
		ins.addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int key) {
				ShortcutManager.execute(key);
			}
		});
		// .registerHotkey(식별번호, 값);
		ins.registerHotKey(ShortcutManager.getNote_int(), JIntellitype.MOD_ALT, ShortcutManager.getNote_char());
		ins.registerHotKey(ShortcutManager.getCapture_int(), JIntellitype.MOD_ALT, ShortcutManager.getCapture_char());
		ins.registerHotKey(ShortcutManager.getZoom_int(), JIntellitype.MOD_ALT, ShortcutManager.getZoom_char());
		ins.registerHotKey(ShortcutManager.getMagnify_int(), JIntellitype.MOD_ALT, ShortcutManager.getMagnify_char());
	}
}
