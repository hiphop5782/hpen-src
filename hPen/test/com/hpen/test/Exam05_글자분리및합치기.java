package com.hpen.test;

public class Exam05_±ÛÀÚºÐ¸®¹×ÇÕÄ¡±â {
//	private static final ArrayList<Character> a1 = new ArrayList<>();
//	static{
//		a1.add('¤¡');/*¤¡*/		a1.add('¤¢');/*¤¢*/
//		a1.add('¤¤');/*¤¤*/		a1.add('¤§');/*¤§*/
//		a1.add('¤¨');/*¤¨*/		a1.add('¤©');/*¤©*/
//		a1.add('¤±');/*¤±*/		a1.add('¤²');/*¤²*/
//		a1.add('¤³');/*¤³*/		a1.add('¤µ');/*¤µ*/
//		a1.add('¤¶');/*¤¶*/		a1.add('¤·');/*¤·*/
//		a1.add('¤¸');/*¤¸*/		a1.add('¤¹');/*¤¹*/
//		a1.add('¤º');/*¤º*/		a1.add('¤»');/*¤»*/
//		a1.add('¤¼');/*¤¼*/		a1.add('¤½');/*¤½*/
//		a1.add('¤¾');/*¤¾*/
//	}
//	private static final ArrayList<Character> a2 = new ArrayList<>();
//	static{
//		a2.add('¤¿');/*¤¿*/		a2.add('¤À');/*¤À*/
//		a2.add('¤Á');/*¤Á*/		a2.add('¤Â');/*¤Â*/
//		a2.add('¤Ã');/*¤Ã*/		a2.add('¤Ä');/*¤Ä*/
//		a2.add('¤Å');/*¤Å*/		a2.add('¤Æ');/*¤Æ*/
//		a2.add('¤Ç');/*¤Ç*/		a2.add('¤È');/*¤È*/
//		a2.add('¤É');/*¤É*/		a2.add('¤Ê');/*¤Ê*/
//		a2.add('¤Ë');/*¤Ë*/		a2.add('¤Ì');/*¤Ì*/
//		a2.add('¤Í');/*¤Í*/		a2.add('¤Î');/*¤Î*/
//		a2.add('¤Ï');/*¤Ï*/		a2.add('¤Ð');/*¤Ð*/
//		a2.add('¤Ñ');/*¤Ñ*/		a2.add('¤Ò');/*¤Ò*/
//		a2.add('¤Ó');/*¤Ó*/
//	}
//	private static final ArrayList<Character> a3 = new ArrayList<>();
//	static{
//		a3.add((char)0x0000);/*¾øÀ½*/	a3.add('¤¡');/*¤¡*/
//		a3.add('¤¢');/*¤¢*/		a3.add('¤£');/*¤£*/
//		a3.add('¤¤');/*¤¤*/		a3.add('¤¥');/*¤¥*/
//		a3.add('¤¦');/*¤¦*/		a3.add('¤§');/*¤§*/
//		a3.add('¤©');/*¤©*/		a3.add('¤ª');/*¤ª*/
//		a3.add('¤«');/*¤«*/		a3.add('¤¬');/*¤¬*/
//		a3.add('¤­');/*¤­*/		a3.add('¤®');/*¤®*/
//		a3.add('¤¯');/*¤¯*/		a3.add('¤°');/*¤°*/
//		a3.add('¤±');/*¤±*/		a3.add('¤²');/*¤²*/
//		a3.add('¤´');/*¤´*/		a3.add('¤µ');/*¤µ*/
//		a3.add('¤¶');/*¤¶*/		a3.add('¤·');/*¤·*/
//		a3.add('¤¸');/*¤¸*/		a3.add('¤º');/*¤º*/
//		a3.add('¤»');/*¤»*/		a3.add('¤¼');/*¤¼*/
//		a3.add('¤½');/*¤½*/		a3.add('¤¾');/*¤¾*/
//	}
//	public static char merge(int[] s) throws IllegalArgumentException{
//		if (s.length != 3) throw new IllegalArgumentException();
//		s[0] = a1.indexOf((char)s[0]);
//		s[1] = a2.indexOf((char)s[1]);
//		s[2] = a3.indexOf((char)s[2]);
//		char c = (char)((((s[0] * 588) + s[1] * 28) + s[2]) + 44032);
//		return c;
//	}
//	
//	public static int[] seperate(char s){
//		int[] result = new int[3];
//		int a = s - 44032;
//		result[0] = a / 588;
//		result[1] = a / 28 % 21;
//		result[2] = a % 28;
//		return result;
//	}
//	
//	public static void main(String[] args) throws Exception {
////		int[] r = new int[]{'¤²','¤Î','¤ª'};
////		System.out.println(merge(r));
////		
////		int[] r2 = seperate('”î');
////		System.out.println(a1.get(r2[0]));
////		System.out.println(a2.get(r2[1]));
////		System.out.println(a3.get(r2[2]));
//		
////		21*28(588) ±ÛÀÚ¸¶´Ù ÃÊ¼ºÀÌ ¹Ù²ñ
////		28±ÛÀÚ¸¶´Ù Áß¼ºÀÌ ¹Ù²ñ
////		Á¾¼ºÀº ÃÑ 28°³(27+null)
//		
////		°¡(44032) ~ ÆR(55203)
////		for(char a = '°¡'; a <= 'ÆR'; a++){
////			System.out.println(a+", "+(int)a);
////		}
//		
////		¤¡(12593) ~ ¤¾(12622)
////		for(char a = '¤¡'; a <= '¤¾'; a++){
////			System.out.println(a+", "+(int)a);
////		}
////		¤¿(12623) ~ ¤Ó(12643)
////		for(char a = '¤¿'; a <= '¤Ó'; a++){
////			System.out.println(a+", "+(int)a);
////		}
//		
////		int ch = 'È²';
////		ch -= 44032;
////		int s = ch / 588 + 12604;
////		System.out.println("s = "+s +", "+(int)'¤¾');
////		int m = ch / 28 % 21 + 12623;
////		System.out.println("m = "+m+", "+(int)'¤È');
////		int e = ch % 28 + 12594;
////		System.out.println("e = "+e+", "+(int)'¤·');
//		
//		//String sample = "ÀÌ·±µÈÀå";
//		//String text = KoreanCharacter.seperateString(sample);
//		
//		//String sample = "rudckfcjdckdansckdtkf thlcjfckdtkf, rjackfcjd ckdansckdtkf dhlcjfckdtkf";
//		String sample = "rkqtlek";
//		String text = KoreanCharacter.parseKorean(sample);
//		System.out.println(text);
//		
//		
////		Matcher¸¦ ÀÌ¿ëÇÏ¿© °Ë»öÇÑ µÚ replace·Î Ä¡È¯
//		Matcher m = Pattern.compile("[¤¡-¤¾]([¤¿-¤Ó]{1,2})?").matcher(text);
//		
//		while(m.find()){
//			String s = m.group();
//			if(s.length() == 2){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				if(KoreanCharacter.isJaum(a) && KoreanCharacter.isMoum(b)){
//					char c = merge(a, b);
//					System.out.println("À§Ä¡ : "+text.indexOf(s) + "\t" + s + " ¡æ " + c);
//					text = text.replace(s, String.valueOf(c));
//				}
//			}else if(s.length() == 3){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				char c = s.charAt(2);
//				if(KoreanCharacter.isDoubleMoum(b, c)){
//					char n = KoreanCharacter.mergeDoubleMoum(b, c);
//					char d = merge(a, n);
//					System.out.println("À§Ä¡ : "+text.indexOf(s) + "\t" + s + " ¡æ " + d);
//					text = text.replace(s, String.valueOf(d));
//				}
//			}
//		}
//		System.out.println(text);
//		m = Pattern.compile("[°¡-ÆR][¤¡-¤¾]{1,2}").matcher(text);
//		while(m.find()){
//			String s = m.group();
//			if(s.length() == 3){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				char c = s.charAt(2);
//				if(KoreanCharacter.isDoubleJaum(b, c)){
//					int[] v = seperate(a);
//					char d = merge((char)a1.get(v[0]), (char)a2.get(v[1]), b, c);
//					text = text.replace(s, String.valueOf(d));
//				}
//			}else{//length == 2
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				int[] v = seperate(a);
//				if(KoreanCharacter.isLastLetter(b)){
//					char d = merge((char)a1.get(v[0]), (char)a2.get(v[1]), b);
//					text = text.replace(s, String.valueOf(d));
//				}else{
//					text = text.replace(s, String.valueOf(a)+b);
//				}
//			}
//		}
//		System.out.println(text);
//	}
//	public static char merge(char a, char b, char c, char d) throws Exception{
//		char r = KoreanCharacter.mergeDoubleJaum(c, d);
//		return merge(a, b, r);
//	}
//	public static char merge(char a, char b, char c){
//		int[] r = new int[]{a,b,c};
//		return merge(r);
//	}
//	public static char merge(char a, char b){
//		return merge(new int[]{a,b,0});
//	}
	
}
