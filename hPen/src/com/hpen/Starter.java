package com.hpen;

import javax.swing.UIManager;

import com.hpen.util.ClassManager;
import com.hpen.util.TrayManager;
import com.hpen.util.file.RegisterManager;

public class Starter {
	public static void main(String[] args) {
		try{
			ClassManager.initialize();
//			VersionManager.checkNewestVersionOnGithub();
			RegisterManager.makelnk(System.getProperty("user.dir"), "hpen.exe");
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}catch(Exception e){
			e.printStackTrace();
		}
		TrayManager.start();
	}
}
