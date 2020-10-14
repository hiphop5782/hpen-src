package com.hpen.update.subutil;

public class ExecuteManager {
	private static ExecuteManager manager = new ExecuteManager();
	public static ExecuteManager getManager() {
		return manager;
	}
	private ExecuteManager() {}
	
	public void execute(String command) {
		try {
			Runtime.getRuntime().exec(command);
		}catch(Exception e) {}
	}
}
