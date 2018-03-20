package com.hpen.util.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;

import com.hpen.jna.Elevator;

public class RegisterManager {
	public static final File desktop = new File(System.getProperty("user.home"),"Desktop");
	public static void makelnk(String targetPath, String name) throws IOException, NoSuchMethodException, ScriptException {
        File scriptFile = new File("whatever.js");
    	scriptFile.createNewFile();
 
        try (PrintWriter script = new PrintWriter(scriptFile)) {
            script.printf("try {\n");
            script.printf("wshshell = WScript.CreateObject(\"WScript.Shell\")\n");
            String line = System.getProperty("user.dir") + "\\" + name + ".lnk";
            
            String lines2[] = line.split("\\\\");
            line = "";
            for(int i=0; i<lines2.length; i++)
            {
                if(i == lines2.length - 1)
                {
                    line += lines2[i];
                }
                else
                    line += lines2[i] + "\\\\";
            }
            line.substring(0,line.length()-10);
//            System.out.println(line);
            script.printf("shortcut = wshshell.CreateShortcut(\"%s\")\n", line);
            String lines[] = targetPath.split("\\\\");
            line = "";
            for(int i=0; i<lines.length; i++)
                line += lines[i] + "\\\\";
            script.printf("shortcut.TargetPath = \"%s\"\n", line+name);
            script.printf("shortcut.WindowStyle = 1\n");
            script.printf("shortcut.HotKey = \"\"\n");
            script.printf("shortcut.Description = \"%s\"\n", "");
            script.printf("shortcut.WorkingDirectory = \"%s\"\n", "");
            script.printf("shortcut.Save()\n");
            script.printf("} catch (err) {\n");
            // Commented by default
            script.printf("/*WScript.Echo(\"name:\")\nWScript.Echo(err.name)\n");
            script.printf("WScript.Echo(\"message:\")\nWScript.Echo(err.message)\n");
            script.printf("WScript.Echo(\"description:\")\nWScript.Echo(err.description)\n");
            script.printf("WScript.Echo(\"stack:\")\nWScript.Echo(err.stack)\n");
            script.printf("*/\n");
            script.printf("WScript.Quit(1)\n");
            script.printf("}\n");
            script.flush();
            script.close();
            
            String command = "cscript " + System.getProperty("user.dir")+"\\"+"whatever.js";
            Runtime.getRuntime().exec(command);
//            Elevator.runCommandAsAdmin(command);
            Runnable r = ()->{
            	try{
            		Thread.sleep(2000);
                	scriptFile.delete();
            	}catch(Exception e){}
            };
            Thread t = new Thread(r);
            t.start();
        }
	}
	
	public static void createShortcutToDesktop(){
		File link = new File("hpen.exe.lnk");
		File target = new File(desktop, "hpen.lnk");
		System.out.println("link exist = "+link.exists());
		System.out.println("desktop detected = "+desktop.exists());
		System.out.println("target exist = "+target.exists());
		try {
			FileUtils.copyFile(link, target);
		}catch(Exception e) {
		}
	}
	
	public static void removeShortcutFromDesktop(){
		File target = new File(System.getProperty("user.home")+"\\Desktop", "hpen.lnk");
		target.delete();
	}
	
	public static void registerStartProgram() {
		File target = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
		if (!target.exists())
			return;
		if (Arrays.toString(target.list()).contains("hpen.lnk"))
			return;

		File link = new File("hpen.exe.lnk");
		String command = "copy /Y " + link.getAbsolutePath()
				+ " \"%PROGRAMDATA%\\MicroSoft\\Windows\\Start Menu\\Programs\\Startup\\hpen.lnk\"";
		// String command = "notepad";
		Elevator.runCommandAsAdmin(command);
	}

	public static void unregisterStartProgram() {
		File target = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
		if (!target.exists())
			return;

		File f = new File(target, "hpen.lnk");
		f.delete();
	}

	public static boolean checkRegisterStartProgram() {
		File target = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
		if (!target.exists())
			return false;
		if (Arrays.toString(target.list()).contains("hpen.lnk"))
			return true;
		return false;
	}

	public static boolean checkRegisterDesktop() {
		File dir = new File(System.getProperty("user.home")+"\\Desktop");
		if (!dir.exists())
			return false;
		if (Arrays.toString(dir.list()).contains("hpen.lnk"))
			return true;
		return false;
	}
}
