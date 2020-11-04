package com.hacademy.hpen.util.keyboard;

import java.util.Scanner;

import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GlobalKeyboardEvent를 위한 클래스
 * - 메인 키 + 각종 확장키 여부를 설정할 수 있음
 * - 상수로 모든 키 설정
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KeyData {
	private int code;
	private boolean upper;
	private boolean ctrl;
	private boolean alt;
	private boolean windows;
	private boolean shift;
	
	public static KeyData valueOf(String keyStr) {
		if(keyStr == null) return null;

		KeyData data = new KeyData();
		
		Scanner sc = new Scanner(keyStr);
		sc.useDelimiter("[,+\\s]");
		while(sc.hasNext()) {
			String token = sc.next();
			switch(token.toLowerCase()) {
			case "shift": data.shift = true; break;
			case "ctrl": data.ctrl = true; break;
			case "alt": data.alt = true; break;
			case "windows": case "window": case "win": 
				data.windows = true; break;
			default:
//				data.code = convert(token);
				break;
			}
		}
		sc.close();
		
		return data;
	}
	
	public static final KeyData A = KeyData.builder().code(GlobalKeyEvent.VK_A).upper(true).build();
	public static final KeyData B = KeyData.builder().code(GlobalKeyEvent.VK_B).upper(true).build();
	public static final KeyData C = KeyData.builder().code(GlobalKeyEvent.VK_C).upper(true).build();
	public static final KeyData D = KeyData.builder().code(GlobalKeyEvent.VK_D).upper(true).build();
	public static final KeyData E = KeyData.builder().code(GlobalKeyEvent.VK_E).upper(true).build();
	public static final KeyData F = KeyData.builder().code(GlobalKeyEvent.VK_F).upper(true).build();
	public static final KeyData G = KeyData.builder().code(GlobalKeyEvent.VK_G).upper(true).build();
	public static final KeyData H = KeyData.builder().code(GlobalKeyEvent.VK_H).upper(true).build();
	public static final KeyData I = KeyData.builder().code(GlobalKeyEvent.VK_I).upper(true).build();
	public static final KeyData J = KeyData.builder().code(GlobalKeyEvent.VK_J).upper(true).build();
	public static final KeyData K = KeyData.builder().code(GlobalKeyEvent.VK_K).upper(true).build();
	public static final KeyData L = KeyData.builder().code(GlobalKeyEvent.VK_L).upper(true).build();
	public static final KeyData M = KeyData.builder().code(GlobalKeyEvent.VK_M).upper(true).build();
	public static final KeyData N = KeyData.builder().code(GlobalKeyEvent.VK_N).upper(true).build();
	public static final KeyData O = KeyData.builder().code(GlobalKeyEvent.VK_O).upper(true).build();
	public static final KeyData P = KeyData.builder().code(GlobalKeyEvent.VK_P).upper(true).build();
	public static final KeyData Q = KeyData.builder().code(GlobalKeyEvent.VK_Q).upper(true).build();
	public static final KeyData R = KeyData.builder().code(GlobalKeyEvent.VK_R).upper(true).build();
	public static final KeyData S = KeyData.builder().code(GlobalKeyEvent.VK_S).upper(true).build();
	public static final KeyData T = KeyData.builder().code(GlobalKeyEvent.VK_T).upper(true).build();
	public static final KeyData U = KeyData.builder().code(GlobalKeyEvent.VK_U).upper(true).build();
	public static final KeyData V = KeyData.builder().code(GlobalKeyEvent.VK_V).upper(true).build();
	public static final KeyData W = KeyData.builder().code(GlobalKeyEvent.VK_W).upper(true).build();
	public static final KeyData X = KeyData.builder().code(GlobalKeyEvent.VK_X).upper(true).build();
	public static final KeyData Y = KeyData.builder().code(GlobalKeyEvent.VK_Y).upper(true).build();
	public static final KeyData Z = KeyData.builder().code(GlobalKeyEvent.VK_Z).upper(true).build();

	public static final KeyData a = KeyData.builder().code(GlobalKeyEvent.VK_A).build();
	public static final KeyData b = KeyData.builder().code(GlobalKeyEvent.VK_B).build();
	public static final KeyData c = KeyData.builder().code(GlobalKeyEvent.VK_C).build();
	public static final KeyData d = KeyData.builder().code(GlobalKeyEvent.VK_D).build();
	public static final KeyData e = KeyData.builder().code(GlobalKeyEvent.VK_E).build();
	public static final KeyData f = KeyData.builder().code(GlobalKeyEvent.VK_F).build();
	public static final KeyData g = KeyData.builder().code(GlobalKeyEvent.VK_G).build();
	public static final KeyData h = KeyData.builder().code(GlobalKeyEvent.VK_H).build();
	public static final KeyData i = KeyData.builder().code(GlobalKeyEvent.VK_I).build();
	public static final KeyData j = KeyData.builder().code(GlobalKeyEvent.VK_J).build();
	public static final KeyData k = KeyData.builder().code(GlobalKeyEvent.VK_K).build();
	public static final KeyData l = KeyData.builder().code(GlobalKeyEvent.VK_L).build();
	public static final KeyData m = KeyData.builder().code(GlobalKeyEvent.VK_M).build();
	public static final KeyData n = KeyData.builder().code(GlobalKeyEvent.VK_N).build();
	public static final KeyData o = KeyData.builder().code(GlobalKeyEvent.VK_O).build();
	public static final KeyData p = KeyData.builder().code(GlobalKeyEvent.VK_P).build();
	public static final KeyData q = KeyData.builder().code(GlobalKeyEvent.VK_Q).build();
	public static final KeyData r = KeyData.builder().code(GlobalKeyEvent.VK_R).build();
	public static final KeyData s = KeyData.builder().code(GlobalKeyEvent.VK_S).build();
	public static final KeyData t = KeyData.builder().code(GlobalKeyEvent.VK_T).build();
	public static final KeyData u = KeyData.builder().code(GlobalKeyEvent.VK_U).build();
	public static final KeyData v = KeyData.builder().code(GlobalKeyEvent.VK_V).build();
	public static final KeyData w = KeyData.builder().code(GlobalKeyEvent.VK_W).build();
	public static final KeyData x = KeyData.builder().code(GlobalKeyEvent.VK_X).build();
	public static final KeyData y = KeyData.builder().code(GlobalKeyEvent.VK_Y).build();
	public static final KeyData z = KeyData.builder().code(GlobalKeyEvent.VK_Z).build();

	public static final KeyData num1 = KeyData.builder().code(GlobalKeyEvent.VK_1).build();
	public static final KeyData num2 = KeyData.builder().code(GlobalKeyEvent.VK_2).build();
	public static final KeyData num3 = KeyData.builder().code(GlobalKeyEvent.VK_3).build();
	public static final KeyData num4 = KeyData.builder().code(GlobalKeyEvent.VK_4).build();
	public static final KeyData num5 = KeyData.builder().code(GlobalKeyEvent.VK_5).build();
	public static final KeyData num6 = KeyData.builder().code(GlobalKeyEvent.VK_6).build();
	public static final KeyData num7 = KeyData.builder().code(GlobalKeyEvent.VK_7).build();
	public static final KeyData num8 = KeyData.builder().code(GlobalKeyEvent.VK_8).build();
	public static final KeyData num9 = KeyData.builder().code(GlobalKeyEvent.VK_9).build();
	public static final KeyData num0 = KeyData.builder().code(GlobalKeyEvent.VK_0).build();

	public static final KeyData numpad1 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD1).build();
	public static final KeyData numpad2 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD2).build();
	public static final KeyData numpad3 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD3).build();
	public static final KeyData numpad4 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD4).build();
	public static final KeyData numpad5 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD5).build();
	public static final KeyData numpad6 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD6).build();
	public static final KeyData numpad7 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD7).build();
	public static final KeyData numpad8 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD8).build();
	public static final KeyData numpad9 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD9).build();
	public static final KeyData numpad0 = KeyData.builder().code(GlobalKeyEvent.VK_NUMPAD0).build();

	public static final KeyData f1 = KeyData.builder().code(GlobalKeyEvent.VK_F1).build();
	public static final KeyData f2 = KeyData.builder().code(GlobalKeyEvent.VK_F2).build();
	public static final KeyData f3 = KeyData.builder().code(GlobalKeyEvent.VK_F3).build();
	public static final KeyData f4 = KeyData.builder().code(GlobalKeyEvent.VK_F4).build();
	public static final KeyData f5 = KeyData.builder().code(GlobalKeyEvent.VK_F5).build();
	public static final KeyData f6 = KeyData.builder().code(GlobalKeyEvent.VK_F6).build();
	public static final KeyData f7 = KeyData.builder().code(GlobalKeyEvent.VK_F7).build();
	public static final KeyData f8 = KeyData.builder().code(GlobalKeyEvent.VK_F8).build();
	public static final KeyData f9 = KeyData.builder().code(GlobalKeyEvent.VK_F9).build();
	public static final KeyData f10 = KeyData.builder().code(GlobalKeyEvent.VK_F10).build();
	public static final KeyData f11 = KeyData.builder().code(GlobalKeyEvent.VK_F11).build();
	public static final KeyData f12 = KeyData.builder().code(GlobalKeyEvent.VK_F12).build();
	public static final KeyData esc = KeyData.builder().code(GlobalKeyEvent.VK_ESCAPE).build();
	
	public static final KeyData alt_a = KeyData.builder().code(GlobalKeyEvent.VK_A).alt(true).build();
	public static final KeyData alt_b = KeyData.builder().code(GlobalKeyEvent.VK_B).alt(true).build();
	public static final KeyData alt_c = KeyData.builder().code(GlobalKeyEvent.VK_C).alt(true).build();
	public static final KeyData alt_d = KeyData.builder().code(GlobalKeyEvent.VK_D).alt(true).build();
	public static final KeyData alt_e = KeyData.builder().code(GlobalKeyEvent.VK_E).alt(true).build();
	public static final KeyData alt_f = KeyData.builder().code(GlobalKeyEvent.VK_F).alt(true).build();
	public static final KeyData alt_g = KeyData.builder().code(GlobalKeyEvent.VK_G).alt(true).build();
	public static final KeyData alt_h = KeyData.builder().code(GlobalKeyEvent.VK_H).alt(true).build();
	public static final KeyData alt_i = KeyData.builder().code(GlobalKeyEvent.VK_I).alt(true).build();
	public static final KeyData alt_j = KeyData.builder().code(GlobalKeyEvent.VK_J).alt(true).build();
	public static final KeyData alt_k = KeyData.builder().code(GlobalKeyEvent.VK_K).alt(true).build();
	public static final KeyData alt_l = KeyData.builder().code(GlobalKeyEvent.VK_L).alt(true).build();
	public static final KeyData alt_m = KeyData.builder().code(GlobalKeyEvent.VK_M).alt(true).build();
	public static final KeyData alt_n = KeyData.builder().code(GlobalKeyEvent.VK_N).alt(true).build();
	public static final KeyData alt_o = KeyData.builder().code(GlobalKeyEvent.VK_O).alt(true).build();
	public static final KeyData alt_p = KeyData.builder().code(GlobalKeyEvent.VK_P).alt(true).build();
	public static final KeyData alt_q = KeyData.builder().code(GlobalKeyEvent.VK_Q).alt(true).build();
	public static final KeyData alt_r = KeyData.builder().code(GlobalKeyEvent.VK_R).alt(true).build();
	public static final KeyData alt_s = KeyData.builder().code(GlobalKeyEvent.VK_S).alt(true).build();
	public static final KeyData alt_t = KeyData.builder().code(GlobalKeyEvent.VK_T).alt(true).build();
	public static final KeyData alt_u = KeyData.builder().code(GlobalKeyEvent.VK_U).alt(true).build();
	public static final KeyData alt_v = KeyData.builder().code(GlobalKeyEvent.VK_V).alt(true).build();
	public static final KeyData alt_w = KeyData.builder().code(GlobalKeyEvent.VK_W).alt(true).build();
	public static final KeyData alt_x = KeyData.builder().code(GlobalKeyEvent.VK_X).alt(true).build();
	public static final KeyData alt_y = KeyData.builder().code(GlobalKeyEvent.VK_Y).alt(true).build();
	public static final KeyData alt_z = KeyData.builder().code(GlobalKeyEvent.VK_Z).alt(true).build();
	
	public static final KeyData ctrl_a = KeyData.builder().code(GlobalKeyEvent.VK_A).ctrl(true).build();
	public static final KeyData ctrl_b = KeyData.builder().code(GlobalKeyEvent.VK_B).ctrl(true).build();
	public static final KeyData ctrl_c = KeyData.builder().code(GlobalKeyEvent.VK_C).ctrl(true).build();
	public static final KeyData ctrl_d = KeyData.builder().code(GlobalKeyEvent.VK_D).ctrl(true).build();
	public static final KeyData ctrl_e = KeyData.builder().code(GlobalKeyEvent.VK_E).ctrl(true).build();
	public static final KeyData ctrl_f = KeyData.builder().code(GlobalKeyEvent.VK_F).ctrl(true).build();
	public static final KeyData ctrl_g = KeyData.builder().code(GlobalKeyEvent.VK_G).ctrl(true).build();
	public static final KeyData ctrl_h = KeyData.builder().code(GlobalKeyEvent.VK_H).ctrl(true).build();
	public static final KeyData ctrl_i = KeyData.builder().code(GlobalKeyEvent.VK_I).ctrl(true).build();
	public static final KeyData ctrl_j = KeyData.builder().code(GlobalKeyEvent.VK_J).ctrl(true).build();
	public static final KeyData ctrl_k = KeyData.builder().code(GlobalKeyEvent.VK_K).ctrl(true).build();
	public static final KeyData ctrl_l = KeyData.builder().code(GlobalKeyEvent.VK_L).ctrl(true).build();
	public static final KeyData ctrl_m = KeyData.builder().code(GlobalKeyEvent.VK_M).ctrl(true).build();
	public static final KeyData ctrl_n = KeyData.builder().code(GlobalKeyEvent.VK_N).ctrl(true).build();
	public static final KeyData ctrl_o = KeyData.builder().code(GlobalKeyEvent.VK_O).ctrl(true).build();
	public static final KeyData ctrl_p = KeyData.builder().code(GlobalKeyEvent.VK_P).ctrl(true).build();
	public static final KeyData ctrl_q = KeyData.builder().code(GlobalKeyEvent.VK_Q).ctrl(true).build();
	public static final KeyData ctrl_r = KeyData.builder().code(GlobalKeyEvent.VK_R).ctrl(true).build();
	public static final KeyData ctrl_s = KeyData.builder().code(GlobalKeyEvent.VK_S).ctrl(true).build();
	public static final KeyData ctrl_t = KeyData.builder().code(GlobalKeyEvent.VK_T).ctrl(true).build();
	public static final KeyData ctrl_u = KeyData.builder().code(GlobalKeyEvent.VK_U).ctrl(true).build();
	public static final KeyData ctrl_v = KeyData.builder().code(GlobalKeyEvent.VK_V).ctrl(true).build();
	public static final KeyData ctrl_w = KeyData.builder().code(GlobalKeyEvent.VK_W).ctrl(true).build();
	public static final KeyData ctrl_x = KeyData.builder().code(GlobalKeyEvent.VK_X).ctrl(true).build();
	public static final KeyData ctrl_y = KeyData.builder().code(GlobalKeyEvent.VK_Y).ctrl(true).build();
	public static final KeyData ctrl_z = KeyData.builder().code(GlobalKeyEvent.VK_Z).ctrl(true).build();
}
