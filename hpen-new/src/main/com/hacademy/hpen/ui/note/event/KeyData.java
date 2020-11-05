package com.hacademy.hpen.ui.note.event;

import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class KeyData {
	private int code;
	private boolean win, alt, ctrl, shift, extend;
	
	public static KeyData valueOf(GlobalKeyEvent event) {
		return KeyData.builder()
						.code(event.getVirtualKeyCode())
						.win(event.isWinPressed())
						.alt(event.isMenuPressed())
						.shift(event.isShiftPressed())
						.ctrl(event.isControlPressed())
						.extend(event.isExtendedKey())
					.build();
	}
}
