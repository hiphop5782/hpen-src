package com.hpen.util;

import java.util.regex.Pattern;

public class TextManager {
	public static boolean isWord(String str){
		String regex = "^[¤¡-¤¾¤¿-¤Ó°¡-ÆRa-zA-Z0-9`~!@#$%\\^&*()_+|()\\[\\]\\{\\}'\";.,<>/?]*$";
		return Pattern.matches(regex, str);
	}
}







