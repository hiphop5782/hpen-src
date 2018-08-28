package com.hpen.test;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import com.hpen.util.ProcessManager;

public class Exam20_process_force {
	public static void main(String[] args) throws Exception{
		String command = System.getenv("windir")+"\\system32\\magnify.exe";
		ProcessBuilder builder = new ProcessBuilder(new String[] {"cmd.exe", "/c", command});
		Process p = builder.start();
		System.out.println("µ∏∫∏±‚ Ω√¿€");
		
		Thread.sleep(3000);
		
		//int pid = ProcessManager.getPid(p);
		//System.out.println("pid = "+pid);
		
//		DefaultExecutor executor = new DefaultExecutor();
//		executor.execute(CommandLine.parse("tskill /a magnify"));
		builder = new ProcessBuilder(new String[] {"tskill", "/a", "magnify"});
		builder.start();
		System.out.println("µ∏∫∏±‚ ¡æ∑·");
	}
}
