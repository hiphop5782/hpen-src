package com.hpen.test;

public class Exam05_越切歳軒貢杯帖奄 {
//	private static final ArrayList<Character> a1 = new ArrayList<>();
//	static{
//		a1.add('ぁ');/*ぁ*/		a1.add('あ');/*あ*/
//		a1.add('い');/*い*/		a1.add('ぇ');/*ぇ*/
//		a1.add('え');/*え*/		a1.add('ぉ');/*ぉ*/
//		a1.add('け');/*け*/		a1.add('げ');/*げ*/
//		a1.add('こ');/*こ*/		a1.add('さ');/*さ*/
//		a1.add('ざ');/*ざ*/		a1.add('し');/*し*/
//		a1.add('じ');/*じ*/		a1.add('す');/*す*/
//		a1.add('ず');/*ず*/		a1.add('せ');/*せ*/
//		a1.add('ぜ');/*ぜ*/		a1.add('そ');/*そ*/
//		a1.add('ぞ');/*ぞ*/
//	}
//	private static final ArrayList<Character> a2 = new ArrayList<>();
//	static{
//		a2.add('た');/*た*/		a2.add('だ');/*だ*/
//		a2.add('ち');/*ち*/		a2.add('ぢ');/*ぢ*/
//		a2.add('っ');/*っ*/		a2.add('つ');/*つ*/
//		a2.add('づ');/*づ*/		a2.add('て');/*て*/
//		a2.add('で');/*で*/		a2.add('と');/*と*/
//		a2.add('ど');/*ど*/		a2.add('な');/*な*/
//		a2.add('に');/*に*/		a2.add('ぬ');/*ぬ*/
//		a2.add('ね');/*ね*/		a2.add('の');/*の*/
//		a2.add('は');/*は*/		a2.add('ば');/*ば*/
//		a2.add('ぱ');/*ぱ*/		a2.add('ひ');/*ひ*/
//		a2.add('び');/*び*/
//	}
//	private static final ArrayList<Character> a3 = new ArrayList<>();
//	static{
//		a3.add((char)0x0000);/*蒸製*/	a3.add('ぁ');/*ぁ*/
//		a3.add('あ');/*あ*/		a3.add('ぃ');/*ぃ*/
//		a3.add('い');/*い*/		a3.add('ぅ');/*ぅ*/
//		a3.add('う');/*う*/		a3.add('ぇ');/*ぇ*/
//		a3.add('ぉ');/*ぉ*/		a3.add('お');/*お*/
//		a3.add('か');/*か*/		a3.add('が');/*が*/
//		a3.add('き');/*き*/		a3.add('ぎ');/*ぎ*/
//		a3.add('く');/*く*/		a3.add('ぐ');/*ぐ*/
//		a3.add('け');/*け*/		a3.add('げ');/*げ*/
//		a3.add('ご');/*ご*/		a3.add('さ');/*さ*/
//		a3.add('ざ');/*ざ*/		a3.add('し');/*し*/
//		a3.add('じ');/*じ*/		a3.add('ず');/*ず*/
//		a3.add('せ');/*せ*/		a3.add('ぜ');/*ぜ*/
//		a3.add('そ');/*そ*/		a3.add('ぞ');/*ぞ*/
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
////		int[] r = new int[]{'げ','の','お'};
////		System.out.println(merge(r));
////		
////		int[] r2 = seperate('��');
////		System.out.println(a1.get(r2[0]));
////		System.out.println(a2.get(r2[1]));
////		System.out.println(a3.get(r2[2]));
//		
////		21*28(588) 越切原陥 段失戚 郊会
////		28越切原陥 掻失戚 郊会
////		曽失精 恥 28鯵(27+null)
//		
////		亜(44032) ~ �R(55203)
////		for(char a = '亜'; a <= '�R'; a++){
////			System.out.println(a+", "+(int)a);
////		}
//		
////		ぁ(12593) ~ ぞ(12622)
////		for(char a = 'ぁ'; a <= 'ぞ'; a++){
////			System.out.println(a+", "+(int)a);
////		}
////		た(12623) ~ び(12643)
////		for(char a = 'た'; a <= 'び'; a++){
////			System.out.println(a+", "+(int)a);
////		}
//		
////		int ch = '伐';
////		ch -= 44032;
////		int s = ch / 588 + 12604;
////		System.out.println("s = "+s +", "+(int)'ぞ');
////		int m = ch / 28 % 21 + 12623;
////		System.out.println("m = "+m+", "+(int)'と');
////		int e = ch % 28 + 12594;
////		System.out.println("e = "+e+", "+(int)'し');
//		
//		//String sample = "戚訓吉舌";
//		//String text = KoreanCharacter.seperateString(sample);
//		
//		//String sample = "rudckfcjdckdansckdtkf thlcjfckdtkf, rjackfcjd ckdansckdtkf dhlcjfckdtkf";
//		String sample = "rkqtlek";
//		String text = KoreanCharacter.parseKorean(sample);
//		System.out.println(text);
//		
//		
////		Matcher研 戚遂馬食 伊事廃 及 replace稽 帖発
//		Matcher m = Pattern.compile("[ぁ-ぞ]([た-び]{1,2})?").matcher(text);
//		
//		while(m.find()){
//			String s = m.group();
//			if(s.length() == 2){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				if(KoreanCharacter.isJaum(a) && KoreanCharacter.isMoum(b)){
//					char c = merge(a, b);
//					System.out.println("是帖 : "+text.indexOf(s) + "\t" + s + " ≧ " + c);
//					text = text.replace(s, String.valueOf(c));
//				}
//			}else if(s.length() == 3){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				char c = s.charAt(2);
//				if(KoreanCharacter.isDoubleMoum(b, c)){
//					char n = KoreanCharacter.mergeDoubleMoum(b, c);
//					char d = merge(a, n);
//					System.out.println("是帖 : "+text.indexOf(s) + "\t" + s + " ≧ " + d);
//					text = text.replace(s, String.valueOf(d));
//				}
//			}
//		}
//		System.out.println(text);
//		m = Pattern.compile("[亜-�R][ぁ-ぞ]{1,2}").matcher(text);
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
