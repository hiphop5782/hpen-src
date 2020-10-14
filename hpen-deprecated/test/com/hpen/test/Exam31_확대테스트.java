package com.hpen.test;

import com.hpen.draw.ui.DrawingFrame;
import com.hpen.draw.ui.State;
import com.hpen.util.ClassManager;

public class Exam31_확대테스트 {
	public static void main(String[] args) throws Exception{
		ClassManager.initialize();
		DrawingFrame.start(State.WHITEBOARD);
	}
}
