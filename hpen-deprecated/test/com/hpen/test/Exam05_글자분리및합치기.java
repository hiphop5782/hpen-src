package com.hpen.test;

public class Exam05_글자분리및합치기 {
//	private static final ArrayList<Character> a1 = new ArrayList<>();
//	static{
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/		a1.add('��');/*��*/
//		a1.add('��');/*��*/
//	}
//	private static final ArrayList<Character> a2 = new ArrayList<>();
//	static{
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/		a2.add('��');/*��*/
//		a2.add('��');/*��*/
//	}
//	private static final ArrayList<Character> a3 = new ArrayList<>();
//	static{
//		a3.add((char)0x0000);/*����*/	a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
//		a3.add('��');/*��*/		a3.add('��');/*��*/
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
////		int[] r = new int[]{'��','��','��'};
////		System.out.println(merge(r));
////		
////		int[] r2 = seperate('��');
////		System.out.println(a1.get(r2[0]));
////		System.out.println(a2.get(r2[1]));
////		System.out.println(a3.get(r2[2]));
//		
////		21*28(588) ���ڸ��� �ʼ��� �ٲ�
////		28���ڸ��� �߼��� �ٲ�
////		������ �� 28��(27+null)
//		
////		��(44032) ~ �R(55203)
////		for(char a = '��'; a <= '�R'; a++){
////			System.out.println(a+", "+(int)a);
////		}
//		
////		��(12593) ~ ��(12622)
////		for(char a = '��'; a <= '��'; a++){
////			System.out.println(a+", "+(int)a);
////		}
////		��(12623) ~ ��(12643)
////		for(char a = '��'; a <= '��'; a++){
////			System.out.println(a+", "+(int)a);
////		}
//		
////		int ch = 'Ȳ';
////		ch -= 44032;
////		int s = ch / 588 + 12604;
////		System.out.println("s = "+s +", "+(int)'��');
////		int m = ch / 28 % 21 + 12623;
////		System.out.println("m = "+m+", "+(int)'��');
////		int e = ch % 28 + 12594;
////		System.out.println("e = "+e+", "+(int)'��');
//		
//		//String sample = "�̷�����";
//		//String text = KoreanCharacter.seperateString(sample);
//		
//		//String sample = "rudckfcjdckdansckdtkf thlcjfckdtkf, rjackfcjd ckdansckdtkf dhlcjfckdtkf";
//		String sample = "rkqtlek";
//		String text = KoreanCharacter.parseKorean(sample);
//		System.out.println(text);
//		
//		
////		Matcher�� �̿��Ͽ� �˻��� �� replace�� ġȯ
//		Matcher m = Pattern.compile("[��-��]([��-��]{1,2})?").matcher(text);
//		
//		while(m.find()){
//			String s = m.group();
//			if(s.length() == 2){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				if(KoreanCharacter.isJaum(a) && KoreanCharacter.isMoum(b)){
//					char c = merge(a, b);
//					System.out.println("��ġ : "+text.indexOf(s) + "\t" + s + " �� " + c);
//					text = text.replace(s, String.valueOf(c));
//				}
//			}else if(s.length() == 3){
//				char a = s.charAt(0);
//				char b = s.charAt(1);
//				char c = s.charAt(2);
//				if(KoreanCharacter.isDoubleMoum(b, c)){
//					char n = KoreanCharacter.mergeDoubleMoum(b, c);
//					char d = merge(a, n);
//					System.out.println("��ġ : "+text.indexOf(s) + "\t" + s + " �� " + d);
//					text = text.replace(s, String.valueOf(d));
//				}
//			}
//		}
//		System.out.println(text);
//		m = Pattern.compile("[��-�R][��-��]{1,2}").matcher(text);
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
