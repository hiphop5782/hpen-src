package com.hpen.util.file;

import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hpen.Starter;
import com.hpen.util.DialogManager;
import com.hpen.value.Version;

public class VersionManager {
	
	/**
	 * X.X.X 형태의 버전을 00000 형태의 int 값으로 변환하는 메소드
	 * @param version	X.X.X 형태의 버전 값
	 * @return	int 버전 값
	 * @throws IllegalArgumentException	형식에 맞지 않는 버전일 경우
	 */
	private static int convert(String version) throws IllegalArgumentException{
		String regex = "^\\d\\.\\d\\.\\d$";
		if(!version.matches(regex))
			throw new IllegalArgumentException("잘못된 버전");
		int version_int = 0;
		for(int i=0; i<version.length(); i++) {
			if(i % 2 == 0) {
				version_int += version.charAt(i) - '0';
			}
			version_int *= 10;
		}
		version_int /= 10;
		return version_int;
	}
	
	public static boolean isNew(String originVersion, String targetVersion) {
		int origin = convert(originVersion);
		int target = convert(targetVersion);
		return target - origin > 0;
	}
	
	public static boolean isSame(String originVersion, String targetVersion) {
		int origin = convert(originVersion);
		int target = convert(targetVersion);
		return target - origin == 0;
	}
	
	public static boolean isBefore(String originVersion, String targetVersion) {
		int origin = convert(originVersion);
		int target = convert(targetVersion);
		return target - origin < 0;
	}
	
	public static String getLatestVersionOnGithub() {
		try {
			URL url = new URL("https://github.com/hiphop5782/hPen/releases");
			StringBuffer buffer = new StringBuffer();
			try(Scanner s = new Scanner(url.openStream(), "UTF-8");){
				while(s.hasNextLine()) {
					buffer.append(s.nextLine());
				}
			}
			String page = buffer.toString();
//			String regex = "<div class=\"release label-latest\">.*?<h1 class=\"release-title\">.*?<a.*?>(\\d\\.\\d\\.\\d) Release</a>.*?</h1>";
			String regex = "(\\d\\.\\d\\.\\d)\\sRelease";
			Matcher m = Pattern.compile(regex).matcher(page);
			if(m.find()) {
				return m.group(1);
			}
		}catch(Exception e) {
			//DialogManager.alert("최신 버전 정보를 찾을 수 없습니다");
		}
		return null;
	}
	
	public static final String download_message = 
			"<html>이전 버전을 사용중입니다<br><br>"
			+ "최신 버전은 아래 주소에서 다운받으실 수 있습니다<br><br>"
			+ "https://github.com/hiphop5782/hPen/releases<br><br>"
			+ "최신 버전을 다운받으시겠습니까?<br><br></html>";
	public static void checkNewestVersionOnGithub() {
		String originVersion = Version.getInstance().getVersion();
		String githubVersion = getLatestVersionOnGithub();
		if(githubVersion == null) return;
		
		if(isNew(originVersion, githubVersion)) {
			int select = DialogManager.confirm(download_message);
			if(select == 0) {
				try {
					Runtime.getRuntime().exec("cmd.exe /c hpen_updater.exe");
				}catch(Exception e) {}
				System.exit(0);
			}
		}
	}
}
