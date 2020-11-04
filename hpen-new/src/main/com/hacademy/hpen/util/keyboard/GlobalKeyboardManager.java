package com.hacademy.hpen.util.keyboard;

import java.util.HashMap;
import java.util.Map;

import com.hacademy.hpen.util.loader.annotation.Component;

import lc.kra.system.GlobalHookMode;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GlobalKeyboardManager {
	//true는 차단, false는 통과
	GlobalKeyboardHook hook = new GlobalKeyboardHook(GlobalHookMode.FINAL);

	public GlobalKeyboardManager() {}
	
	public void init() {
		hook.addKeyListener(new GlobalKeyListener() {
			@Override
			public void keyReleased(GlobalKeyEvent event) {
				
			}
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				log.debug("vkcode = {}", event.getVirtualKeyCode());
				log.debug("transition = {}, control = {}, shift = {}, menu = {}, win = {}", event.getTransitionState(), event.isControlPressed(), event.isShiftPressed(), event.isMenuPressed(), event.isWinPressed());
				Runnable action = allowKeys.get(event.getVirtualKeyCode());
				if(action != null) {
					action.run();
				}
			}
		});
	}
	
	@Getter
	private Map<Integer, Runnable> allowKeys = new HashMap<>();

	public void addKey(int keyCode, Runnable action) {
		allowKeys.put(keyCode, action);
	}
	public int getKeyCount() {
		return allowKeys.size();
	}
}
