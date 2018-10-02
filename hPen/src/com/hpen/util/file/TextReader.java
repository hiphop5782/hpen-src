package com.hpen.util.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class TextReader {
	public static String loadText(String url) throws IOException {
		StringBuffer buffer = new StringBuffer();
		Scanner s = new Scanner(new URL(url).openStream(), "UTF-8");
		while(s.hasNextLine()) {
			String line = s.nextLine();
			buffer.append(line);
			buffer.append("\n");
		}
		s.close();
		return buffer.toString();
	}
}
