//package com.hpen.test;
//
//import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.hpen.util.KoreanCharacter;
//
//public class Exam05_글자조합찾기_정규식 {
//	public static void main(String[] args){
//		String str = "dkssudgktpdy qksrkqtmqslek. zzzzz";
//		str = KoreanCharacter.parseKorean(str);
//		System.out.println(str);
//		
//		System.out.println("=====[ 1번 정규식 : 1,3글자 ] =====");
//		String regex1 = "[ㄱ-ㅎ]([ㅏ-ㅣ]([ㄱ-ㅎ]{1,2})?)?";
//		Matcher m1 = Pattern.compile(regex1).matcher(str);
//		while(m1.find()){
//			System.out.println(m1.group());
//		}
//		
//		System.out.println("=====[ 2번 정규식 : 2글자 ] =====");
//		String regex2 = "[ㄱ-ㅎ][ㅏ-ㅣ]?";
//		Matcher m2 = Pattern.compile(regex2).matcher(str);
//		StringBuffer buffer = new StringBuffer();
//		while(m2.find()){
//			System.out.println(m2.group());
//		}
//	}
//}
//
//
//
//
//
//
//
//
//
