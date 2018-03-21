package study;

import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hpen.util.file.VersionManager;
import com.hpen.value.Version;

public class Exam22_GitHubVersionCheck {
	public static void main(String[] args) throws Exception{
		URL url = new URL("https://github.com/hiphop5782/hPen/releases");
		Scanner s = new Scanner(url.openStream(), "UTF-8");
		StringBuffer buffer = new StringBuffer();
		while(s.hasNextLine()) {
			buffer.append(s.nextLine()+"\n");
		}
		s.close();
		String page = buffer.toString();
//		System.out.println(page);
//		String regex = "<div class=\"release\\s*label-latest\">.*?<h1 class=\"release-title\">.*?<a.*?>(\\d\\.\\d\\.\\d) Release</a>.*?</h1>";
		String regex = "(\\d\\.\\d\\.\\d)\\sRelease";
		Matcher m = Pattern.compile(regex).matcher(page);
		if(m.find()) {
			System.out.println(m.group(1));
			System.out.println(VersionManager.isNew(Version.getInstance().getVersion(), m.group(1)));
		}
		
//		System.out.println("2.2.1".compareTo("2.2.0"));
		
	}
}
