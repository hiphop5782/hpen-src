package com.hpen.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class English2KoreanParser{
	
	private static English2KoreanParser parser = new English2KoreanParser();
	public static English2KoreanParser getParser() {
		return parser;
	}
	private English2KoreanParser() {}
	
	private String english = "rRseEfaqQtTdwWczxvgkoiOjpuPhynbml";
	private String korean = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎㅏㅐㅑㅒㅓㅔㅕㅖㅗㅛㅜㅠㅡㅣ";
	private String first = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
	private String second = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ";
	private String third = "ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ";
	
	public String parse(String eng) {
		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // 초성, 중성, 종성

		for (int i = 0; i < eng.length(); i++) {
			char ch = eng.charAt(i);
			int p = english.indexOf(ch);
			if (p == -1) { // 영자판이 아님
				// 남아있는 한글이 있으면 처리
				if (nCho != -1) {
					if (nJung != -1) // 초성+중성+(종성)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // 초성만
						buffer.append(first.charAt(nCho));
				} else {
					if (nJung != -1) // 중성만
						buffer.append(second.charAt(nJung));
					else if (nJong != -1) // 복자음
						buffer.append(third.charAt(nJong));
				}
				nCho = -1;
				nJung = -1;
				nJong = -1;
				buffer.append(ch);
			} else if (p < 19) { // 자음
				if (nJung != -1) {
					if (nCho == -1) { // 중성만 입력됨, 초성으로
						buffer.append(second.charAt(nJung));
						nJung = -1;
						nCho = first.indexOf(korean.charAt(p));
					} else { // 종성이다
						if (nJong == -1) { // 종성 입력 중
							nJong = third.indexOf(korean.charAt(p));
							if (nJong == -1) { // 종성이 아니라 초성이다
								buffer.append(mergeKorean(nCho, nJung, nJong));
								nCho = first.indexOf(korean.charAt(p));
								nJung = -1;
							}
						} else if (nJong == 0 && p == 9) { // ㄳ
							nJong = 2;
						} else if (nJong == 3 && p == 12) { // ㄵ
							nJong = 4;
						} else if (nJong == 3 && p == 18) { // ㄶ
							nJong = 5;
						} else if (nJong == 7 && p == 0) { // ㄺ
							nJong = 8;
						} else if (nJong == 7 && p == 6) { // ㄻ
							nJong = 9;
						} else if (nJong == 7 && p == 7) { // ㄼ
							nJong = 10;
						} else if (nJong == 7 && p == 9) { // ㄽ
							nJong = 11;
						} else if (nJong == 7 && p == 16) { // ㄾ
							nJong = 12;
						} else if (nJong == 7 && p == 17) { // ㄿ
							nJong = 13;
						} else if (nJong == 7 && p == 18) { // ㅀ
							nJong = 14;
						} else if (nJong == 16 && p == 9) { // ㅄ
							nJong = 17;
						} else { // 종성 입력 끝, 초성으로
							buffer.append(mergeKorean(nCho, nJung, nJong));
							nCho = first.indexOf(korean.charAt(p));
							nJung = -1;
							nJong = -1;
						}
					}
				} else { // 초성 또는 (단/복)자음이다
					if (nCho == -1) { // 초성 입력 시작
						if (nJong != -1) { // 복자음 후 초성
							buffer.append(third.charAt(nJong));
							nJong = -1;
						}
						nCho = first.indexOf(korean.charAt(p));
					} else if (nCho == 0 && p == 9) { // ㄳ
						nCho = -1;
						nJong = 2;
					} else if (nCho == 2 && p == 12) { // ㄵ
						nCho = -1;
						nJong = 4;
					} else if (nCho == 2 && p == 18) { // ㄶ
						nCho = -1;
						nJong = 5;
					} else if (nCho == 5 && p == 0) { // ㄺ
						nCho = -1;
						nJong = 8;
					} else if (nCho == 5 && p == 6) { // ㄻ
						nCho = -1;
						nJong = 9;
					} else if (nCho == 5 && p == 7) { // ㄼ
						nCho = -1;
						nJong = 10;
					} else if (nCho == 5 && p == 9) { // ㄽ
						nCho = -1;
						nJong = 11;
					} else if (nCho == 5 && p == 16) { // ㄾ
						nCho = -1;
						nJong = 12;
					} else if (nCho == 5 && p == 17) { // ㄿ
						nCho = -1;
						nJong = 13;
					} else if (nCho == 5 && p == 18) { // ㅀ
						nCho = -1;
						nJong = 14;
					} else if (nCho == 7 && p == 9) { // ㅄ
						nCho = -1;
						nJong = 17;
					} else { // 단자음을 연타
						buffer.append(first.charAt(nCho));
						nCho = first.indexOf(korean.charAt(p));
					}
				}
			} else { // 모음
				if (nJong != -1) { // (앞글자 종성), 초성+중성
					// 복자음 다시 분해
					int newCho; // (임시용) 초성
					if (nJong == 2) { // ㄱ, ㅅ
						nJong = 0;
						newCho = 9;
					} else if (nJong == 4) { // ㄴ, ㅈ
						nJong = 3;
						newCho = 12;
					} else if (nJong == 5) { // ㄴ, ㅎ
						nJong = 3;
						newCho = 18;
					} else if (nJong == 8) { // ㄹ, ㄱ
						nJong = 7;
						newCho = 0;
					} else if (nJong == 9) { // ㄹ, ㅁ
						nJong = 7;
						newCho = 6;
					} else if (nJong == 10) { // ㄹ, ㅂ
						nJong = 7;
						newCho = 7;
					} else if (nJong == 11) { // ㄹ, ㅅ
						nJong = 7;
						newCho = 9;
					} else if (nJong == 12) { // ㄹ, ㅌ
						nJong = 7;
						newCho = 16;
					} else if (nJong == 13) { // ㄹ, ㅍ
						nJong = 7;
						newCho = 17;
					} else if (nJong == 14) { // ㄹ, ㅎ
						nJong = 7;
						newCho = 18;
					} else if (nJong == 17) { // ㅂ, ㅅ
						nJong = 16;
						newCho = 9;
					} else { // 복자음 아님
						newCho = first.indexOf(third.charAt(nJong));
						nJong = -1;
					}
					if (nCho != -1) // 앞글자가 초성+중성+(종성)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // 복자음만 있음
						buffer.append(third.charAt(nJong));

					nCho = newCho;
					nJung = -1;
					nJong = -1;
				}
				if (nJung == -1) { // 중성 입력 중
					nJung = second.indexOf(korean.charAt(p));
				} else if (nJung == 8 && p == 19) { // ㅘ
					nJung = 9;
				} else if (nJung == 8 && p == 20) { // ㅙ
					nJung = 10;
				} else if (nJung == 8 && p == 32) { // ㅚ
					nJung = 11;
				} else if (nJung == 13 && p == 23) { // ㅝ
					nJung = 14;
				} else if (nJung == 13 && p == 24) { // ㅞ
					nJung = 15;
				} else if (nJung == 13 && p == 32) { // ㅟ
					nJung = 16;
				} else if (nJung == 18 && p == 32) { // ㅢ
					nJung = 19;
				} else { // 조합 안되는 모음 입력
					if (nCho != -1) { // 초성+중성 후 중성
						buffer.append(mergeKorean(nCho, nJung, nJong));
						nCho = -1;
					} else // 중성 후 중성
						buffer.append(second.charAt(nJung));
					nJung = -1;
					buffer.append(korean.charAt(p));
				}
			}
		}

		// 마지막 한글이 있으면 처리
		if (nCho != -1) {
			if (nJung != -1) // 초성+중성+(종성)
				buffer.append(mergeKorean(nCho, nJung, nJong));
			else // 초성만
				buffer.append(first.charAt(nCho));
		} else {
			if (nJung != -1) // 중성만
				buffer.append(second.charAt(nJung));
			else { // 복자음
				if (nJong != -1)
					buffer.append(third.charAt(nJong));
			}
		}

		return buffer.toString();
	}
	public String merge(String kor) {
		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // 초성, 중성, 종성

		for (int i = 0; i < kor.length(); i++) {
			char ch = kor.charAt(i);
			int p = korean.indexOf(ch);
			if (p == -1) { // 영자판이 아님
				// 남아있는 한글이 있으면 처리
				if (nCho != -1) {
					if (nJung != -1) // 초성+중성+(종성)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // 초성만
						buffer.append(first.charAt(nCho));
				} else {
					if (nJung != -1) // 중성만
						buffer.append(second.charAt(nJung));
					else if (nJong != -1) // 복자음
						buffer.append(third.charAt(nJong));
				}
				nCho = -1;
				nJung = -1;
				nJong = -1;
				buffer.append(ch);
			} else if (p < 19) { // 자음
				if (nJung != -1) {
					if (nCho == -1) { // 중성만 입력됨, 초성으로
						buffer.append(second.charAt(nJung));
						nJung = -1;
						nCho = first.indexOf(korean.charAt(p));
					} else { // 종성이다
						if (nJong == -1) { // 종성 입력 중
							nJong = third.indexOf(korean.charAt(p));
							if (nJong == -1) { // 종성이 아니라 초성이다
								buffer.append(mergeKorean(nCho, nJung, nJong));
								nCho = first.indexOf(korean.charAt(p));
								nJung = -1;
							}
						} else if (nJong == 0 && p == 9) { // ㄳ
							nJong = 2;
						} else if (nJong == 3 && p == 12) { // ㄵ
							nJong = 4;
						} else if (nJong == 3 && p == 18) { // ㄶ
							nJong = 5;
						} else if (nJong == 7 && p == 0) { // ㄺ
							nJong = 8;
						} else if (nJong == 7 && p == 6) { // ㄻ
							nJong = 9;
						} else if (nJong == 7 && p == 7) { // ㄼ
							nJong = 10;
						} else if (nJong == 7 && p == 9) { // ㄽ
							nJong = 11;
						} else if (nJong == 7 && p == 16) { // ㄾ
							nJong = 12;
						} else if (nJong == 7 && p == 17) { // ㄿ
							nJong = 13;
						} else if (nJong == 7 && p == 18) { // ㅀ
							nJong = 14;
						} else if (nJong == 16 && p == 9) { // ㅄ
							nJong = 17;
						} else { // 종성 입력 끝, 초성으로
							buffer.append(mergeKorean(nCho, nJung, nJong));
							nCho = first.indexOf(korean.charAt(p));
							nJung = -1;
							nJong = -1;
						}
					}
				} else { // 초성 또는 (단/복)자음이다
					if (nCho == -1) { // 초성 입력 시작
						if (nJong != -1) { // 복자음 후 초성
							buffer.append(third.charAt(nJong));
							nJong = -1;
						}
						nCho = first.indexOf(korean.charAt(p));
					} else if (nCho == 0 && p == 9) { // ㄳ
						nCho = -1;
						nJong = 2;
					} else if (nCho == 2 && p == 12) { // ㄵ
						nCho = -1;
						nJong = 4;
					} else if (nCho == 2 && p == 18) { // ㄶ
						nCho = -1;
						nJong = 5;
					} else if (nCho == 5 && p == 0) { // ㄺ
						nCho = -1;
						nJong = 8;
					} else if (nCho == 5 && p == 6) { // ㄻ
						nCho = -1;
						nJong = 9;
					} else if (nCho == 5 && p == 7) { // ㄼ
						nCho = -1;
						nJong = 10;
					} else if (nCho == 5 && p == 9) { // ㄽ
						nCho = -1;
						nJong = 11;
					} else if (nCho == 5 && p == 16) { // ㄾ
						nCho = -1;
						nJong = 12;
					} else if (nCho == 5 && p == 17) { // ㄿ
						nCho = -1;
						nJong = 13;
					} else if (nCho == 5 && p == 18) { // ㅀ
						nCho = -1;
						nJong = 14;
					} else if (nCho == 7 && p == 9) { // ㅄ
						nCho = -1;
						nJong = 17;
					} else { // 단자음을 연타
						buffer.append(first.charAt(nCho));
						nCho = first.indexOf(korean.charAt(p));
					}
				}
			} else { // 모음
				if (nJong != -1) { // (앞글자 종성), 초성+중성
					// 복자음 다시 분해
					int newCho; // (임시용) 초성
					if (nJong == 2) { // ㄱ, ㅅ
						nJong = 0;
						newCho = 9;
					} else if (nJong == 4) { // ㄴ, ㅈ
						nJong = 3;
						newCho = 12;
					} else if (nJong == 5) { // ㄴ, ㅎ
						nJong = 3;
						newCho = 18;
					} else if (nJong == 8) { // ㄹ, ㄱ
						nJong = 7;
						newCho = 0;
					} else if (nJong == 9) { // ㄹ, ㅁ
						nJong = 7;
						newCho = 6;
					} else if (nJong == 10) { // ㄹ, ㅂ
						nJong = 7;
						newCho = 7;
					} else if (nJong == 11) { // ㄹ, ㅅ
						nJong = 7;
						newCho = 9;
					} else if (nJong == 12) { // ㄹ, ㅌ
						nJong = 7;
						newCho = 16;
					} else if (nJong == 13) { // ㄹ, ㅍ
						nJong = 7;
						newCho = 17;
					} else if (nJong == 14) { // ㄹ, ㅎ
						nJong = 7;
						newCho = 18;
					} else if (nJong == 17) { // ㅂ, ㅅ
						nJong = 16;
						newCho = 9;
					} else { // 복자음 아님
						newCho = first.indexOf(third.charAt(nJong));
						nJong = -1;
					}
					if (nCho != -1) // 앞글자가 초성+중성+(종성)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // 복자음만 있음
						buffer.append(third.charAt(nJong));

					nCho = newCho;
					nJung = -1;
					nJong = -1;
				}
				if (nJung == -1) { // 중성 입력 중
					nJung = second.indexOf(korean.charAt(p));
				} else if (nJung == 8 && p == 19) { // ㅘ
					nJung = 9;
				} else if (nJung == 8 && p == 20) { // ㅙ
					nJung = 10;
				} else if (nJung == 8 && p == 32) { // ㅚ
					nJung = 11;
				} else if (nJung == 13 && p == 23) { // ㅝ
					nJung = 14;
				} else if (nJung == 13 && p == 24) { // ㅞ
					nJung = 15;
				} else if (nJung == 13 && p == 32) { // ㅟ
					nJung = 16;
				} else if (nJung == 18 && p == 32) { // ㅢ
					nJung = 19;
				} else { // 조합 안되는 모음 입력
					if (nCho != -1) { // 초성+중성 후 중성
						buffer.append(mergeKorean(nCho, nJung, nJong));
						nCho = -1;
					} else // 중성 후 중성
						buffer.append(second.charAt(nJung));
					nJung = -1;
					buffer.append(korean.charAt(p));
				}
			}
		}

		// 마지막 한글이 있으면 처리
		if (nCho != -1) {
			if (nJung != -1) // 초성+중성+(종성)
				buffer.append(mergeKorean(nCho, nJung, nJong));
			else // 초성만
				buffer.append(first.charAt(nCho));
		} else {
			if (nJung != -1) // 중성만
				buffer.append(second.charAt(nJung));
			else { // 복자음
				if (nJong != -1)
					buffer.append(third.charAt(nJong));
			}
		}

		return buffer.toString();
	}
	private char mergeKorean(int nCho, int nJung, int nJong) {
		char result = 0xac00;
		result += nCho * 21 * 28;
		result += nJung * 28;
		result += nJong + 1;
		return result;
	}
}
