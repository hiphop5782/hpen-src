package com.hpen.update;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import com.hpen.update.subutil.DownloadManager;
import com.hpen.update.subutil.ExecuteManager;
import com.hpen.update.subutil.ZipManager;
import com.hpen.util.file.VersionManager;
import com.hpen.value.Version;

public class UpdateProgram {
	public static void main(String[] args) {
		try {
//			if(args.length < 1) {
//				System.out.println("매개변수를 넣으세요");
//				System.out.println("[1] 업데이트할 버전정보");
//				System.out.println("[2] 업데이트를 설치할 폴더(생략시 현재폴더)");
//				return;
//			}
//			String version = args[0];
//			String destFolderPath = args.length >= 2?args[1]:System.getProperty("user.dir");
			
			String latestVersion = VersionManager.getLatestVersionOnGithub();
			if(!VersionManager.before(latestVersion)) {
				System.out.println("최신 버전 사용중");
				System.exit(0);
			}
			String destFolderPath = System.getProperty("user.dir");
			File f = DownloadManager.getManager().download("https://github.com/hiphop5782/hPen/archive/master.zip", "hpen.zip");
			ZipManager.getManager().unzip(f.getAbsolutePath(), destFolderPath, true, true);
//			JOptionPane.showMessageDialog(null, "프로그램 업데이트가 완료되었습니다", "재시작 알림", JOptionPane.PLAIN_MESSAGE);
			ExecuteManager.getManager().execute("cmd.exe /c "+destFolderPath+File.separator+"hpen.exe");
			System.exit(0);
		}catch(Exception e) {
			try(PrintWriter p = new PrintWriter(new FileWriter(new File("error.log")))){
				e.printStackTrace(p);
			}catch(Exception err) {}
			JOptionPane.showMessageDialog(null, "업데이트 과정에 오류가 발생했습니다", "오류", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}
