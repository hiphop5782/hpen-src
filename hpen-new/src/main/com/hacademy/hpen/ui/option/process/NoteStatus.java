package com.hacademy.hpen.ui.option.process;

import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Data;

@Data
@Component
public class NoteStatus {
	/**
	 * 상태 
	 * - TEXT_MODE : 타이핑 모드
	 * - POINTER_MODE : 그리기 모드
	 * - ICON_MODE : 아이콘 출력 모드
	 * - SELECTION_MODE : 선택 모드	
	 */
	public static final int TEXT_MODE = 1;
	public static final int POINTER_MODE = 2;
	public static final int ICON_MODE = 3;
	public static final int SELECTION_MODE = 4;
	
	private int mode = POINTER_MODE;
	
	private int x, y;
}








