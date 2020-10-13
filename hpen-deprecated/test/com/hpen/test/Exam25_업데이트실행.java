package com.hpen.test;

import com.hpen.update.UpdateProgram;

public class Exam25_업데이트실행 {
	public static void main(String[] args) throws Exception {
//		UpdateProgram.main(new String[] {"2.4.2", System.getProperty("user.dir")});
//		UpdateProgram.main(new String[] {"2.4.2"});
		String desktop = System.getProperty("user.home")+"/desktop";
		Runtime.getRuntime().exec("cmd /c "+desktop+"/hPen/hpen_updater.exe 2.4.2");
	}
}
