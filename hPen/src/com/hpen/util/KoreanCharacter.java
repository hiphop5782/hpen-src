package com.hpen.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KoreanCharacter {
	private static final ArrayList<Character> a1 = new ArrayList<>();
	public static final char getS(int index){ return a1.get(index); }
	static{
		a1.add('¤¡');/*¤¡*/		a1.add('¤¢');/*¤¢*/		a1.add('¤¤');/*¤¤*/		a1.add('¤§');/*¤§*/
		a1.add('¤¨');/*¤¨*/		a1.add('¤©');/*¤©*/		a1.add('¤±');/*¤±*/		a1.add('¤²');/*¤²*/
		a1.add('¤³');/*¤³*/		a1.add('¤µ');/*¤µ*/		a1.add('¤¶');/*¤¶*/		a1.add('¤·');/*¤·*/
		a1.add('¤¸');/*¤¸*/		a1.add('¤¹');/*¤¹*/		a1.add('¤º');/*¤º*/		a1.add('¤»');/*¤»*/
		a1.add('¤¼');/*¤¼*/		a1.add('¤½');/*¤½*/		a1.add('¤¾');/*¤¾*/
	}
	private static final ArrayList<Character> a2 = new ArrayList<>();
	public static final char getM(int index){ return a2.get(index); }
	static{
		a2.add('¤¿');/*¤¿*/		a2.add('¤À');/*¤À*/		a2.add('¤Á');/*¤Á*/		a2.add('¤Â');/*¤Â*/
		a2.add('¤Ã');/*¤Ã*/		a2.add('¤Ä');/*¤Ä*/		a2.add('¤Å');/*¤Å*/		a2.add('¤Æ');/*¤Æ*/
		a2.add('¤Ç');/*¤Ç*/		a2.add('¤È');/*¤È*/		a2.add('¤É');/*¤É*/		a2.add('¤Ê');/*¤Ê*/
		a2.add('¤Ë');/*¤Ë*/		a2.add('¤Ì');/*¤Ì*/		a2.add('¤Í');/*¤Í*/		a2.add('¤Î');/*¤Î*/
		a2.add('¤Ï');/*¤Ï*/		a2.add('¤Ð');/*¤Ð*/		a2.add('¤Ñ');/*¤Ñ*/		a2.add('¤Ò');/*¤Ò*/
		a2.add('¤Ó');/*¤Ó*/
	}
	private static final ArrayList<Character> a3 = new ArrayList<>();
	public static final char getE(int index){ return a3.get(index); }
	static{
		a3.add((char)0x0000);/*¾øÀ½*/	a3.add('¤¡');/*¤¡*/
		a3.add('¤¢');/*¤¢*/		a3.add('¤£');/*¤£*/		a3.add('¤¤');/*¤¤*/		a3.add('¤¥');/*¤¥*/
		a3.add('¤¦');/*¤¦*/		a3.add('¤§');/*¤§*/		a3.add('¤©');/*¤©*/		a3.add('¤ª');/*¤ª*/
		a3.add('¤«');/*¤«*/		a3.add('¤¬');/*¤¬*/		a3.add('¤­');/*¤­*/		a3.add('¤®');/*¤®*/
		a3.add('¤¯');/*¤¯*/		a3.add('¤°');/*¤°*/		a3.add('¤±');/*¤±*/		a3.add('¤²');/*¤²*/
		a3.add('¤´');/*¤´*/		a3.add('¤µ');/*¤µ*/		a3.add('¤¶');/*¤¶*/		a3.add('¤·');/*¤·*/
		a3.add('¤¸');/*¤¸*/		a3.add('¤º');/*¤º*/		a3.add('¤»');/*¤»*/		a3.add('¤¼');/*¤¼*/
		a3.add('¤½');/*¤½*/		a3.add('¤¾');/*¤¾*/
	}
	
	/**
	 * @param ascii	¾Æ½ºÅ° ÄÚµå 1°³(¾ËÆÄºª)
	 * @return À¯´Ï ÄÚµå 1°³(ÇÑ±Û), ¸ÅÄªµÇÁö ¾ÊÀ» °æ¿ì INVALID_CHARACTER ¹ÝÈ¯
	 * 1°³¾¿ ÀÔ·ÂµÇ´Â ÇÑ±Û ÀÚ/¸ðÀ½À» Á¶ÇÕÇÏ´Â Å¬·¡½º
	 *	¿¹¸¦µé¾î dkssudgktpdy¶ó¸é 
	 * d = ¤·		k = ¤¿		s = ¤¤
	 * s = ¤¤		u = ¤Å		d = ¤·
	 * g = ¤¾		k = ¤¿		
	 * t = ¤µ		p = ¤Ä		
	 * d = ¤·		y = ¤Ë
	 * ·Î º¯È¯ÇÏ¿© ÇÕÃÄ¾ß ÇÑ´Ù. ¹®Á¦´Â Á¾¼ºÀÌ ÀÖ´Â °æ¿ìµµ ÀÖ°í ¾ø´Â °æ¿ìµµ ÀÖ´Âµ¥ ÀÌ¸¦ ±¸º°ÇØ¾ß ÇÑ´Ù´Â °Í<br>
	 * ÀÚÀ½ ¾ËÆÄºª : qwertasdfgzxcv
	 * ¸ðÀ½ ¾ËÆÄºª : yuiophjklbnm
	 * 
	 * ÇÕ¼º ±ÔÄ¢
	 * 1.Á÷Àü ±ÛÀÚ°¡ ¾øÀ» °æ¿ì »õ±ÛÀÚ
	 * 2.Á÷Àü ±ÛÀÚ°¡ ÀÖÀ» °æ¿ì
	 * 	(1) ÀÔ·Â ±ÛÀÚ°¡ ÀÚÀ½ÀÌ¸é
	 * 		[1] Á÷Àü ±ÛÀÚ°¡ ÀÚÀ½ÀÌ¸é »õ±ÛÀÚ
	 * 		[2] Á÷Àü ±ÛÀÚ°¡ ¸ðÀ½ÀÌ¸é ¹ÞÄ§ ¶Ç´Â »õ±ÛÀÚ(´ÙÀ½ ±ÛÀÚ¸¦ È®ÀÎÇÒ ÇÊ¿ä°¡ ÀÖ´Ù)
	 * 	(2) ÀÔ·Â ±ÛÀÚ°¡ ¸ðÀ½ÀÌ¸é
	 * 		[1] Á÷Àü ±ÛÀÚ°¡ ÀÚÀ½ÀÌ¸é º´ÇÕ
	 * 		[2] Á÷Àü ±ÛÀÚ°¡ ¸ðÀ½ÀÌ¸é »õ±ÛÀÚ
	 */
	
	public static final int INVALID_CHARACTER = -1;
	public static int parseKorean(int ascii){
		switch(ascii){
		//ÀÚÀ½
		case 'Q': return '¤³';		case 'W': return '¤¹';		case 'E': return '¤¨';
		case 'R': return '¤¢';		case 'T': return '¤¶';		case 'q': return '¤²';
		case 'w': return '¤¸';		case 'e': return '¤§';		case 'r': return '¤¡';
		case 't' : return '¤µ';					case 'A': case 'a': return '¤±';		
		case 'S': case 's': return '¤¤';		case 'D': case 'd': return '¤·';		
		case 'F': case 'f': return '¤©';		case 'G': case 'g': return '¤¾';
		case 'Z': case 'z': return '¤»';		case 'X': case 'x': return '¤¼';		
		case 'C': case 'c': return '¤º';		case 'V': case 'v': return '¤½';
		//¸ðÀ½
		case 'Y': case 'y': return '¤Ë';		case 'U': case 'u': return '¤Å';		
		case 'I': case 'i': return '¤Á';
		case 'o': return '¤À';		case 'p': return '¤Ä';		
		case 'H': case 'h': return '¤Ç';		case 'J': case 'j': return '¤Ã';			
		case 'K': case 'k': return '¤¿';		case 'L': case 'l': return '¤Ó';
		case 'B': case 'b': return '¤Ð';		case 'N': case 'n': return '¤Ì';		
		case 'M': case 'm': return '¤Ñ';
		case 'O':return '¤Â';			case 'P': return '¤Æ';
		//¿¹¿Ü
		default: return ascii;
		}
	}
	public static String parseKorean(String str){
		StringBuffer buffer = new StringBuffer();
		for(char ch : str.toCharArray()){
			buffer.append((char)parseKorean(ch));
		}
		return buffer.toString();
	}
	/**
	 * @param korean ÇÑ±Û 1±ÛÀÚ
	 * @return ÀÚÀ½ÀÌ¸é true, ¾Æ´Ï¸é false
	 */
	public static boolean isJaum(int korean){
		return korean >= '¤¡' && korean <= '¤¾';
	}
	
	public static boolean isFirstLetter(int korean){
		return a1.indexOf((char)korean) >= 0;
	}
	/**
	 * @param korean ÇÑ±Û 1±ÛÀÚ
	 * @return ¸ðÀ½ÀÌ¸é true, ¾Æ´Ï¸é false
	 */
	public static boolean isMoum(int korean){
		return korean >= '¤¿' && korean <= '¤Ó';
	}
	
	public static final char[][] doubleJaum = new char[][]{
		{'¤¡','¤µ','¤£'},{'¤¤','¤¸','¤¥'},{'¤¤','¤¾','¤¦'},{'¤©','¤¡','¤ª'},{'¤©','¤±','¤«'},{'¤©','¤²','¤¬'},
		{'¤©','¤µ','¤­'},{'¤©','¤¼','¤®'},{'¤©','¤½','¤¯'},{'¤©','¤¾','¤°'},{'¤²','¤µ','¤´'}
	};
	public static boolean isDoubleJaum(int a, int b){
		for(int i=0; i<doubleJaum.length; i++){
			if((char)a == doubleJaum[i][0] && (char)b == doubleJaum[i][1])
				return true;
		}
		return false;
	}
	
	public static char mergeDoubleJaum(char c, char d) throws Exception{
		for(int i=0; i<doubleJaum.length; i++){
			if(doubleJaum[i][0] == c && doubleJaum[i][1] == d){
				return doubleJaum[i][2];
			}
		}
		throw new Exception();
	}
	
	public static char[] seperateJaum(char ch) throws Exception{
		for(int i=0; i<doubleJaum.length; i++){
			if(doubleJaum[i][2] == ch){
				return new char[]{doubleJaum[i][0], doubleJaum[i][1]};
			}
		}
		throw new Exception();
	}
	
	public static final char[][] doubleMoum = new char[][]{
		{'¤Ç','¤¿','¤È'},{'¤Ç','¤À','¤É'},{'¤Ç','¤Ó','¤Ê'},{'¤Ì','¤Ã','¤Í'},{'¤Ì','¤Ä','¤Î'},{'¤Ì','¤Ó','¤Ï'},{'¤Ñ','¤Ó','¤Ò'}
	};
	public static boolean isDoubleMoum(int a){
		for(int i=0; i<doubleMoum.length; i++){
			if((char)a2.get(a) == doubleMoum[i][2]){
				return true;
			}
		}
		return false;
	}
	public static boolean isDoubleMoum(int a, int b){
		for(int i=0; i<doubleMoum.length; i++){
			if((char)a == doubleMoum[i][0] && (char)b == doubleMoum[i][1])
				return true;
		}
		return false;
	}
	public static char mergeDoubleMoum(char c, char d) throws Exception{
		for(int i=0; i<doubleMoum.length; i++){
			if(doubleMoum[i][0] == c && doubleMoum[i][1] == d){
				return doubleMoum[i][2];
			}
		}
		throw new Exception();
	}
	public static char[] seperateMoum(int ch) throws Exception{
		for(int i=0; i<doubleMoum.length; i++){
			if(a2.get(ch) == doubleMoum[i][2]){
				return new char[]{doubleMoum[i][0], doubleMoum[i][1]};				
			}
		}
		throw new Exception();
	}
	public static char merge(int[] s) throws IllegalArgumentException{
		if (s.length != 3) throw new IllegalArgumentException();
		s[0] = a1.indexOf((char)s[0]);
		s[1] = a2.indexOf((char)s[1]);
		s[2] = a3.indexOf((char)s[2]);
		char c = (char)((((s[0] * 588) + s[1] * 28) + s[2]) + 44032);
		return c;
	}
	
	public static int[] seperate(char s){
		int[] result = new int[3];
		int a = s - 44032;
		result[0] = a / 588;
		result[1] = a / 28 % 21;
		result[2] = a % 28;
		return result;
	}
	
	public static char merge(char a, char b, char c, char d) throws Exception{
		char r = mergeDoubleJaum(c, d);
		return merge(a, b, r);
	}
	public static char merge(char a, char b, char c){
		int[] r = new int[]{a,b,c};
		return merge(r);
	}
	public static char merge(char a, char b){
		return merge(new int[]{a,b,0});
	}
	public static boolean isKoreanLetter(char ch){
		return Pattern.matches("[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+", String.valueOf(ch));
	}
	public static String seperateString(String sample) {
		for(int i=0; i<sample.length(); i++){
			char ch = sample.charAt(i);
			if(isKoreanLetter(ch)){
				if(!isJaum(ch) && !isMoum(ch)){//¿ÂÀüÇÑ ±ÛÀÚ
					int[] s = seperate(ch);
					//System.out.println(a1.get(s[0])+", "+a2.get(s[1])+", "+a3.get(s[2]) +"("+s[2]+")");
					String endString = "";
					if(isDoubleMoum(s[1])){
						try{
							char[] res = seperateMoum((char) s[1]);
							endString = String.valueOf(res[0])+res[1]+(a3.get(s[2]) == 0x0000?"":String.valueOf(a3.get(s[2])));
						}catch(Exception e){
							e.printStackTrace();
						}
					}else{
						endString = a3.get(s[2]) == 0x0000?"":String.valueOf(a3.get(s[2]));
					}
					String replaceStr = String.valueOf(a1.get(s[0]))
														+(isDoubleMoum(s[1])?"":String.valueOf(a2.get(s[1])))
														+endString;
														
					sample = sample.replace(String.valueOf(ch), replaceStr);
				}
			}
		}
//		System.out.println("ºÐÇØ °á°ú : "+sample);
		return sample;
	}
	public static String mergeString(String text) throws Exception{
		Matcher m = Pattern.compile("[¤¡-¤¾]([¤¿-¤Ó]{1,2})?").matcher(text);
		while(m.find()){
			String s = m.group();
			if(s.length() == 2){
				char a = s.charAt(0);
				char b = s.charAt(1);
				if(isJaum(a) && isMoum(b)){
					if(isFirstLetter(a)){
						char c = merge(a, b);
						text = text.replace(s, String.valueOf(c));
					}else{
						char[] r = seperateJaum(a);
						text = text.replace(s, ""+r[0]+r[1]+b);
					}
				}
			}else if(s.length() == 3){
				char a = s.charAt(0);
				char b = s.charAt(1);
				char c = s.charAt(2);
				if(isDoubleMoum(b, c)){
					char n = mergeDoubleMoum(b, c);
					char d = merge(a, n);
//					System.out.println("À§Ä¡ : "+text.indexOf(s) + "\t" + s + " ¡æ " + d);
					text = text.replace(s, String.valueOf(d));
				}
			}
		}
//		System.out.println("Á¶¸³ °á°ú(1Â÷) : "+text);
		m = Pattern.compile("[°¡-ÆR][¤¡-¤¾]{1,2}[¤¿-¤Ó]?").matcher(text);
		while(m.find()){
			String s = m.group();
			if(s.length() == 4){
				if(isMoum(s.charAt(3))){//¤¡¤¿¤²¤µ¤Ó ¡æ °©½Ã ·Î ¸¸µé±â À§ÇÑ Á¶°Ç
					char a = s.charAt(0);
					char b = s.charAt(1);
					char c = s.charAt(2);
					char d = s.charAt(3);
					int[] v = seperate(a);
					char r1 = merge((char)getS(v[0]), (char)getM(v[1]), b); 
					char r2 = merge(c, d);//µÞ±ÛÀÚ
					text = text.replace(s, ""+r1+r2);
				}
			}else if(s.length() == 3){//¤¡¤¿¤²¤µ ¡æ °ªÀ¸·Î ¸¸µé±â À§ÇÑ Á¶°Ç
				char a = s.charAt(0);
				char b = s.charAt(1);
				char c = s.charAt(2);
				if(isDoubleJaum(b, c)){
					int[] v = seperate(a);
					char d = merge((char)getS(v[0]), (char)getM(v[1]), b, c);
					text = text.replace(s, String.valueOf(d));
				}
			}else{//length == 2, ÀÏ¹Ý ¹ÞÄ§ ±ÛÀÚ
				char a = s.charAt(0);
				char b = s.charAt(1);
				int[] v = seperate(a);
				if(isLastLetter(b)){
					char d = merge((char)a1.get(v[0]), (char)a2.get(v[1]), b);
					text = text.replace(s, String.valueOf(d));
				}else{
					text = text.replace(s, String.valueOf(a)+b);
				}
			}
		}
//		System.out.println("Á¶¸³ °á°ú(ÃÖÁ¾) : "+text);
		return text;
	}
	public static boolean isLastLetter(char b) {
		return a3.contains(b);
	}
}
