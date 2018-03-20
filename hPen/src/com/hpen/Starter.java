package com.hpen;

import javax.swing.UIManager;

import com.hpen.util.ClassManager;
import com.hpen.util.TrayManager;
import com.hpen.util.file.RegisterManager;
import com.hpen.util.file.VersionManager;

public class Starter {
	public static void main(String[] args) {
		try{
			VersionManager.checkNewestVersionOnGithub();
			RegisterManager.makelnk(System.getProperty("user.dir"), "hpen.exe");
			ClassManager.initialize();
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}catch(Exception e){
			e.printStackTrace();
		}
		TrayManager.start();
	}
}
