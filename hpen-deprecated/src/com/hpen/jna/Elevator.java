package com.hpen.jna;

import java.io.IOException;

import com.hpen.jna.Shell32X.SHELLEXECUTEINFO;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
 
public class Elevator {
	public static void executeAsAdmin(String command, String args) {
		SHELLEXECUTEINFO execInfo = new SHELLEXECUTEINFO();
		execInfo.lpFile = new WString(command);
		if (args != null) {
			execInfo.lpParameters = new WString(args);
		}
		execInfo.nShow = Shell32X.SW_SHOWDEFAULT;
		execInfo.fMask = Shell32X.SEE_MASK_NOCLOSEPROCESS;
		execInfo.lpVerb = new WString("runas");
		boolean result = Shell32X.INSTANCE.ShellExecuteEx(execInfo);
 
		if (!result) {
			int lastError = Kernel32.INSTANCE.GetLastError();
			String errorMessage = Kernel32Util.formatMessageFromLastErrorCode(lastError);
			throw new RuntimeException("Error performing elevation: " + lastError + ": " + errorMessage + " (apperror=" + execInfo.hInstApp + ")");
		}
	}

	public static void runCommand(String command) throws IOException {
		Runtime.getRuntime().exec("cmd /C " + command);
	}
	 
	public static void runCommandAsAdmin(String command) {
		Elevator.executeAsAdmin("c:\\windows\\system32\\cmd.exe", "/C " + command);
	}
	
	public static void runProgram(String target, String args) throws IOException {
		args = (args == null) ? "" : args;
		Runtime.getRuntime().exec("cmd /C start " + target + " " + args);
	}

	public static void runProgramAsAdmin(String target, String args) {
		args = (args == null) ? "" : args;
		Elevator.executeAsAdmin("c:\\windows\\system32\\cmd.exe", "/C start " + target + " " + args);
	}

}