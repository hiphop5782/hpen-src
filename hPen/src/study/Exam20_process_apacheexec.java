package study;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.launcher.CommandLauncher;
import org.apache.commons.exec.launcher.CommandLauncherImpl;

public class Exam20_process_apacheexec {
	public static void main(String[] args) throws Exception{
		ExecuteWatchdog dog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWatchdog(dog);
		executor.setExitValue(0);
		CommandLine line = CommandLine.parse("runas /user:Administrator \"magnify.exe\"");
		DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
		executor.execute(line, handler);
		
		Thread.sleep(1000);
		System.out.println("kill");
	}
}
