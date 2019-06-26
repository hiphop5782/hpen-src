package com.hpen.test;

import com.hpen.draw.ui.DrawingFrame;
import com.hpen.draw.ui.State;
import com.hpen.util.ClassManager;

public class Exam35_DrawingFrameTest {
	public static void main(String[] args) {
		ClassManager.initialize();
		DrawingFrame.start(State.TRANSPARENT);
	}
}
