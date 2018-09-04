package com.hpen.util;

public class English2KoreanParser{
	private static English2KoreanParser parser = new English2KoreanParser();
	public static English2KoreanParser getParser() {
		return parser;
	}
	private English2KoreanParser() {}
	
	private String english = "rRseEfaqQtTdwWczxvgkoiOjpuPhynbml";
	private String korean = "¤¡¤¢¤¤¤§¤¨¤©¤±¤²¤³¤µ¤¶¤·¤¸¤¹¤º¤»¤¼¤½¤¾¤¿¤À¤Á¤Â¤Ã¤Ä¤Å¤Æ¤Ç¤Ë¤Ì¤Ð¤Ñ¤Ó";
	private String first = "¤¡¤¢¤¤¤§¤¨¤©¤±¤²¤³¤µ¤¶¤·¤¸¤¹¤º¤»¤¼¤½¤¾";
	private String second = "¤¿¤À¤Á¤Â¤Ã¤Ä¤Å¤Æ¤Ç¤È¤É¤Ê¤Ë¤Ì¤Í¤Î¤Ï¤Ð¤Ñ¤Ò¤Ó";
	private String third = "¤¡¤¢¤£¤¤¤¥¤¦¤§¤©¤ª¤«¤¬¤­¤®¤¯¤°¤±¤²¤´¤µ¤¶¤·¤¸¤º¤»¤¼¤½¤¾";
	
	public String parse(String input) {

		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // ÃÊ¼º, Áß¼º, Á¾¼º

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			int p = english.indexOf(ch);
			if (p == -1) { // ¿µÀÚÆÇÀÌ ¾Æ´Ô
				// ³²¾ÆÀÖ´Â ÇÑ±ÛÀÌ ÀÖÀ¸¸é Ã³¸®
				if (nCho != -1) {
					if (nJung != -1) // ÃÊ¼º+Áß¼º+(Á¾¼º)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // ÃÊ¼º¸¸
						buffer.append(first.charAt(nCho));
				} else {
					if (nJung != -1) // Áß¼º¸¸
						buffer.append(second.charAt(nJung));
					else if (nJong != -1) // º¹ÀÚÀ½
						buffer.append(third.charAt(nJong));
				}
				nCho = -1;
				nJung = -1;
				nJong = -1;
				buffer.append(ch);
			} else if (p < 19) { // ÀÚÀ½
				if (nJung != -1) {
					if (nCho == -1) { // Áß¼º¸¸ ÀÔ·ÂµÊ, ÃÊ¼ºÀ¸·Î
						buffer.append(second.charAt(nJung));
						nJung = -1;
						nCho = first.indexOf(korean.charAt(p));
					} else { // Á¾¼ºÀÌ´Ù
						if (nJong == -1) { // Á¾¼º ÀÔ·Â Áß
							nJong = third.indexOf(korean.charAt(p));
							if (nJong == -1) { // Á¾¼ºÀÌ ¾Æ´Ï¶ó ÃÊ¼ºÀÌ´Ù
								buffer.append(mergeKorean(nCho, nJung, nJong));
								nCho = first.indexOf(korean.charAt(p));
								nJung = -1;
							}
						} else if (nJong == 0 && p == 9) { // ¤£
							nJong = 2;
						} else if (nJong == 3 && p == 12) { // ¤¥
							nJong = 4;
						} else if (nJong == 3 && p == 18) { // ¤¦
							nJong = 5;
						} else if (nJong == 7 && p == 0) { // ¤ª
							nJong = 8;
						} else if (nJong == 7 && p == 6) { // ¤«
							nJong = 9;
						} else if (nJong == 7 && p == 7) { // ¤¬
							nJong = 10;
						} else if (nJong == 7 && p == 9) { // ¤­
							nJong = 11;
						} else if (nJong == 7 && p == 16) { // ¤®
							nJong = 12;
						} else if (nJong == 7 && p == 17) { // ¤¯
							nJong = 13;
						} else if (nJong == 7 && p == 18) { // ¤°
							nJong = 14;
						} else if (nJong == 16 && p == 9) { // ¤´
							nJong = 17;
						} else { // Á¾¼º ÀÔ·Â ³¡, ÃÊ¼ºÀ¸·Î
							buffer.append(mergeKorean(nCho, nJung, nJong));
							nCho = first.indexOf(korean.charAt(p));
							nJung = -1;
							nJong = -1;
						}
					}
				} else { // ÃÊ¼º ¶Ç´Â (´Ü/º¹)ÀÚÀ½ÀÌ´Ù
					if (nCho == -1) { // ÃÊ¼º ÀÔ·Â ½ÃÀÛ
						if (nJong != -1) { // º¹ÀÚÀ½ ÈÄ ÃÊ¼º
							buffer.append(third.charAt(nJong));
							nJong = -1;
						}
						nCho = first.indexOf(korean.charAt(p));
					} else if (nCho == 0 && p == 9) { // ¤£
						nCho = -1;
						nJong = 2;
					} else if (nCho == 2 && p == 12) { // ¤¥
						nCho = -1;
						nJong = 4;
					} else if (nCho == 2 && p == 18) { // ¤¦
						nCho = -1;
						nJong = 5;
					} else if (nCho == 5 && p == 0) { // ¤ª
						nCho = -1;
						nJong = 8;
					} else if (nCho == 5 && p == 6) { // ¤«
						nCho = -1;
						nJong = 9;
					} else if (nCho == 5 && p == 7) { // ¤¬
						nCho = -1;
						nJong = 10;
					} else if (nCho == 5 && p == 9) { // ¤­
						nCho = -1;
						nJong = 11;
					} else if (nCho == 5 && p == 16) { // ¤®
						nCho = -1;
						nJong = 12;
					} else if (nCho == 5 && p == 17) { // ¤¯
						nCho = -1;
						nJong = 13;
					} else if (nCho == 5 && p == 18) { // ¤°
						nCho = -1;
						nJong = 14;
					} else if (nCho == 7 && p == 9) { // ¤´
						nCho = -1;
						nJong = 17;
					} else { // ´ÜÀÚÀ½À» ¿¬Å¸
						buffer.append(first.charAt(nCho));
						nCho = first.indexOf(korean.charAt(p));
					}
				}
			} else { // ¸ðÀ½
				if (nJong != -1) { // (¾Õ±ÛÀÚ Á¾¼º), ÃÊ¼º+Áß¼º
					// º¹ÀÚÀ½ ´Ù½Ã ºÐÇØ
					int newCho; // (ÀÓ½Ã¿ë) ÃÊ¼º
					if (nJong == 2) { // ¤¡, ¤µ
						nJong = 0;
						newCho = 9;
					} else if (nJong == 4) { // ¤¤, ¤¸
						nJong = 3;
						newCho = 12;
					} else if (nJong == 5) { // ¤¤, ¤¾
						nJong = 3;
						newCho = 18;
					} else if (nJong == 8) { // ¤©, ¤¡
						nJong = 7;
						newCho = 0;
					} else if (nJong == 9) { // ¤©, ¤±
						nJong = 7;
						newCho = 6;
					} else if (nJong == 10) { // ¤©, ¤²
						nJong = 7;
						newCho = 7;
					} else if (nJong == 11) { // ¤©, ¤µ
						nJong = 7;
						newCho = 9;
					} else if (nJong == 12) { // ¤©, ¤¼
						nJong = 7;
						newCho = 16;
					} else if (nJong == 13) { // ¤©, ¤½
						nJong = 7;
						newCho = 17;
					} else if (nJong == 14) { // ¤©, ¤¾
						nJong = 7;
						newCho = 18;
					} else if (nJong == 17) { // ¤², ¤µ
						nJong = 16;
						newCho = 9;
					} else { // º¹ÀÚÀ½ ¾Æ´Ô
						newCho = first.indexOf(third.charAt(nJong));
						nJong = -1;
					}
					if (nCho != -1) // ¾Õ±ÛÀÚ°¡ ÃÊ¼º+Áß¼º+(Á¾¼º)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // º¹ÀÚÀ½¸¸ ÀÖÀ½
						buffer.append(third.charAt(nJong));

					nCho = newCho;
					nJung = -1;
					nJong = -1;
				}
				if (nJung == -1) { // Áß¼º ÀÔ·Â Áß
					nJung = second.indexOf(korean.charAt(p));
				} else if (nJung == 8 && p == 19) { // ¤È
					nJung = 9;
				} else if (nJung == 8 && p == 20) { // ¤É
					nJung = 10;
				} else if (nJung == 8 && p == 32) { // ¤Ê
					nJung = 11;
				} else if (nJung == 13 && p == 23) { // ¤Í
					nJung = 14;
				} else if (nJung == 13 && p == 24) { // ¤Î
					nJung = 15;
				} else if (nJung == 13 && p == 32) { // ¤Ï
					nJung = 16;
				} else if (nJung == 18 && p == 32) { // ¤Ò
					nJung = 19;
				} else { // Á¶ÇÕ ¾ÈµÇ´Â ¸ðÀ½ ÀÔ·Â
					if (nCho != -1) { // ÃÊ¼º+Áß¼º ÈÄ Áß¼º
						buffer.append(mergeKorean(nCho, nJung, nJong));
						nCho = -1;
					} else // Áß¼º ÈÄ Áß¼º
						buffer.append(second.charAt(nJung));
					nJung = -1;
					buffer.append(korean.charAt(p));
				}
			}
		}

		// ¸¶Áö¸· ÇÑ±ÛÀÌ ÀÖÀ¸¸é Ã³¸®
		if (nCho != -1) {
			if (nJung != -1) // ÃÊ¼º+Áß¼º+(Á¾¼º)
				buffer.append(mergeKorean(nCho, nJung, nJong));
			else // ÃÊ¼º¸¸
				buffer.append(first.charAt(nCho));
		} else {
			if (nJung != -1) // Áß¼º¸¸
				buffer.append(second.charAt(nJung));
			else { // º¹ÀÚÀ½
				if (nJong != -1)
					buffer.append(third.charAt(nJong));
			}
		}

		return buffer.toString();
	}
	
	public String seperate(String input) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i < input.length(); i++) {
			char c = input.charAt(i);
			if(c >= '°¡' && c <= 'ÆR') {
				int cho = (c - 0xac00) / 28 / 21;
				int jung = (c - 0xac00) / 28 % 21;
				int jong = (c - 0xac00) % 28 - 1;
				buffer.append(first.charAt(cho));
				switch(second.charAt(jung)) {
				case '¤È':	buffer.append('¤Ç'); buffer.append('¤¿'); break;
				case '¤É':	buffer.append('¤Ç'); buffer.append('¤À'); break;
				case '¤Ê':	buffer.append('¤Ç'); buffer.append('¤Ó'); break;
				case '¤Í':	buffer.append('¤Ì'); buffer.append('¤Ã'); break;
				case '¤Î':	buffer.append('¤Ì'); buffer.append('¤Ä'); break;
				case '¤Ï':	buffer.append('¤Ì'); buffer.append('¤Ó'); break;
				case '¤Ò':	buffer.append('¤Ñ'); buffer.append('¤Ó'); break;
				default:	buffer.append(second.charAt(jung));
				}
				if(jong >= 0) {
					switch(third.charAt(jong)) {
					case '¤£':	buffer.append('¤¡'); buffer.append('¤µ'); break;
					case '¤¥':	buffer.append('¤¤'); buffer.append('¤¸'); break;
					case '¤¦':	buffer.append('¤¤'); buffer.append('¤¾'); break;
					case '¤ª':	buffer.append('¤©'); buffer.append('¤¡'); break;
					case '¤«':	buffer.append('¤©'); buffer.append('¤±'); break;
					case '¤¬':	buffer.append('¤©'); buffer.append('¤²'); break;
					case '¤­':	buffer.append('¤©'); buffer.append('¤µ'); break;
					case '¤®':	buffer.append('¤©'); buffer.append('¤¼'); break;
					case '¤¯':	buffer.append('¤©'); buffer.append('¤½'); break;
					case '¤°':	buffer.append('¤©'); buffer.append('¤¾'); break;
					case '¤´':	buffer.append('¤²'); buffer.append('¤µ'); break;
					default: buffer.append(third.charAt(jong));
					}
				}
			}
			else if(second.contains(String.valueOf(c))) {
				switch(c) {
				case '¤È':	buffer.append('¤Ç'); buffer.append('¤¿'); break;
				case '¤É':	buffer.append('¤Ç'); buffer.append('¤À'); break;
				case '¤Ê':	buffer.append('¤Ç'); buffer.append('¤Ó'); break;
				case '¤Í':	buffer.append('¤Ì'); buffer.append('¤Ã'); break;
				case '¤Î':	buffer.append('¤Ì'); buffer.append('¤Ä'); break;
				case '¤Ï':	buffer.append('¤Ì'); buffer.append('¤Ó'); break;
				case '¤Ò':	buffer.append('¤Ñ'); buffer.append('¤Ó'); break;
				default:	buffer.append(c);
				}
			}
			else if(third.contains(String.valueOf(c))) {
				switch(c) {
				case '¤£':	buffer.append('¤¡'); buffer.append('¤µ'); break;
				case '¤¥':	buffer.append('¤¤'); buffer.append('¤¸'); break;
				case '¤¦':	buffer.append('¤¤'); buffer.append('¤¾'); break;
				case '¤ª':	buffer.append('¤©'); buffer.append('¤¡'); break;
				case '¤«':	buffer.append('¤©'); buffer.append('¤±'); break;
				case '¤¬':	buffer.append('¤©'); buffer.append('¤²'); break;
				case '¤­':	buffer.append('¤©'); buffer.append('¤µ'); break;
				case '¤®':	buffer.append('¤©'); buffer.append('¤¼'); break;
				case '¤¯':	buffer.append('¤©'); buffer.append('¤½'); break;
				case '¤°':	buffer.append('¤©'); buffer.append('¤¾'); break;
				case '¤´':	buffer.append('¤²'); buffer.append('¤µ'); break;
				default: buffer.append(c);
				}
			}
			else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}
	
	public String merge(String input) {

		StringBuffer buffer = new StringBuffer();

		int nCho = -1, nJung = -1, nJong = -1; // ÃÊ¼º, Áß¼º, Á¾¼º

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			int p = korean.indexOf(ch);
			if (p == -1) { // ÇÑ±ÛÀÚÆÇÀÌ ¾Æ´Ô
				// ³²¾ÆÀÖ´Â ÇÑ±ÛÀÌ ÀÖÀ¸¸é Ã³¸®
				if (nCho != -1) {
					if (nJung != -1) // ÃÊ¼º+Áß¼º+(Á¾¼º)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // ÃÊ¼º¸¸
						buffer.append(first.charAt(nCho));
				} else {
					if (nJung != -1) // Áß¼º¸¸
						buffer.append(second.charAt(nJung));
					else if (nJong != -1) // º¹ÀÚÀ½
						buffer.append(third.charAt(nJong));
				}
				nCho = -1;
				nJung = -1;
				nJong = -1;
				buffer.append(ch);
			} else if (p < 19) { // ÀÚÀ½
				if (nJung != -1) {
					if (nCho == -1) { // Áß¼º¸¸ ÀÔ·ÂµÊ, ÃÊ¼ºÀ¸·Î
						buffer.append(second.charAt(nJung));
						nJung = -1;
						nCho = first.indexOf(korean.charAt(p));
					} else { // Á¾¼ºÀÌ´Ù
						if (nJong == -1) { // Á¾¼º ÀÔ·Â Áß
							nJong = third.indexOf(korean.charAt(p));
							if (nJong == -1) { // Á¾¼ºÀÌ ¾Æ´Ï¶ó ÃÊ¼ºÀÌ´Ù
								buffer.append(mergeKorean(nCho, nJung, nJong));
								nCho = first.indexOf(korean.charAt(p));
								nJung = -1;
							}
						} else if (nJong == 0 && p == 9) { // ¤£
							nJong = 2;
						} else if (nJong == 3 && p == 12) { // ¤¥
							nJong = 4;
						} else if (nJong == 3 && p == 18) { // ¤¦
							nJong = 5;
						} else if (nJong == 7 && p == 0) { // ¤ª
							nJong = 8;
						} else if (nJong == 7 && p == 6) { // ¤«
							nJong = 9;
						} else if (nJong == 7 && p == 7) { // ¤¬
							nJong = 10;
						} else if (nJong == 7 && p == 9) { // ¤­
							nJong = 11;
						} else if (nJong == 7 && p == 16) { // ¤®
							nJong = 12;
						} else if (nJong == 7 && p == 17) { // ¤¯
							nJong = 13;
						} else if (nJong == 7 && p == 18) { // ¤°
							nJong = 14;
						} else if (nJong == 16 && p == 9) { // ¤´
							nJong = 17;
						} else { // Á¾¼º ÀÔ·Â ³¡, ÃÊ¼ºÀ¸·Î
							buffer.append(mergeKorean(nCho, nJung, nJong));
							nCho = first.indexOf(korean.charAt(p));
							nJung = -1;
							nJong = -1;
						}
					}
				} else { // ÃÊ¼º ¶Ç´Â (´Ü/º¹)ÀÚÀ½ÀÌ´Ù
					if (nCho == -1) { // ÃÊ¼º ÀÔ·Â ½ÃÀÛ
						if (nJong != -1) { // º¹ÀÚÀ½ ÈÄ ÃÊ¼º
							buffer.append(third.charAt(nJong));
							nJong = -1;
						}
						nCho = first.indexOf(korean.charAt(p));
					} else if (nCho == 0 && p == 9) { // ¤£
						nCho = -1;
						nJong = 2;
					} else if (nCho == 2 && p == 12) { // ¤¥
						nCho = -1;
						nJong = 4;
					} else if (nCho == 2 && p == 18) { // ¤¦
						nCho = -1;
						nJong = 5;
					} else if (nCho == 5 && p == 0) { // ¤ª
						nCho = -1;
						nJong = 8;
					} else if (nCho == 5 && p == 6) { // ¤«
						nCho = -1;
						nJong = 9;
					} else if (nCho == 5 && p == 7) { // ¤¬
						nCho = -1;
						nJong = 10;
					} else if (nCho == 5 && p == 9) { // ¤­
						nCho = -1;
						nJong = 11;
					} else if (nCho == 5 && p == 16) { // ¤®
						nCho = -1;
						nJong = 12;
					} else if (nCho == 5 && p == 17) { // ¤¯
						nCho = -1;
						nJong = 13;
					} else if (nCho == 5 && p == 18) { // ¤°
						nCho = -1;
						nJong = 14;
					} else if (nCho == 7 && p == 9) { // ¤´
						nCho = -1;
						nJong = 17;
					} else { // ´ÜÀÚÀ½À» ¿¬Å¸
						buffer.append(first.charAt(nCho));
						nCho = first.indexOf(korean.charAt(p));
					}
				}
			} else { // ¸ðÀ½
				if (nJong != -1) { // (¾Õ±ÛÀÚ Á¾¼º), ÃÊ¼º+Áß¼º
					// º¹ÀÚÀ½ ´Ù½Ã ºÐÇØ
					int newCho; // (ÀÓ½Ã¿ë) ÃÊ¼º
					if (nJong == 2) { // ¤¡, ¤µ
						nJong = 0;
						newCho = 9;
					} else if (nJong == 4) { // ¤¤, ¤¸
						nJong = 3;
						newCho = 12;
					} else if (nJong == 5) { // ¤¤, ¤¾
						nJong = 3;
						newCho = 18;
					} else if (nJong == 8) { // ¤©, ¤¡
						nJong = 7;
						newCho = 0;
					} else if (nJong == 9) { // ¤©, ¤±
						nJong = 7;
						newCho = 6;
					} else if (nJong == 10) { // ¤©, ¤²
						nJong = 7;
						newCho = 7;
					} else if (nJong == 11) { // ¤©, ¤µ
						nJong = 7;
						newCho = 9;
					} else if (nJong == 12) { // ¤©, ¤¼
						nJong = 7;
						newCho = 16;
					} else if (nJong == 13) { // ¤©, ¤½
						nJong = 7;
						newCho = 17;
					} else if (nJong == 14) { // ¤©, ¤¾
						nJong = 7;
						newCho = 18;
					} else if (nJong == 17) { // ¤², ¤µ
						nJong = 16;
						newCho = 9;
					} else { // º¹ÀÚÀ½ ¾Æ´Ô
						newCho = first.indexOf(third.charAt(nJong));
						nJong = -1;
					}
					if (nCho != -1) // ¾Õ±ÛÀÚ°¡ ÃÊ¼º+Áß¼º+(Á¾¼º)
						buffer.append(mergeKorean(nCho, nJung, nJong));
					else // º¹ÀÚÀ½¸¸ ÀÖÀ½
						buffer.append(third.charAt(nJong));

					nCho = newCho;
					nJung = -1;
					nJong = -1;
				}
				if (nJung == -1) { // Áß¼º ÀÔ·Â Áß
					nJung = second.indexOf(korean.charAt(p));
				} else if (nJung == 8 && p == 19) { // ¤È
					nJung = 9;
				} else if (nJung == 8 && p == 20) { // ¤É
					nJung = 10;
				} else if (nJung == 8 && p == 32) { // ¤Ê
					nJung = 11;
				} else if (nJung == 13 && p == 23) { // ¤Í
					nJung = 14;
				} else if (nJung == 13 && p == 24) { // ¤Î
					nJung = 15;
				} else if (nJung == 13 && p == 32) { // ¤Ï
					nJung = 16;
				} else if (nJung == 18 && p == 32) { // ¤Ò
					nJung = 19;
				} else { // Á¶ÇÕ ¾ÈµÇ´Â ¸ðÀ½ ÀÔ·Â
					if (nCho != -1) { // ÃÊ¼º+Áß¼º ÈÄ Áß¼º
						buffer.append(mergeKorean(nCho, nJung, nJong));
						nCho = -1;
					} else // Áß¼º ÈÄ Áß¼º
						buffer.append(second.charAt(nJung));
					nJung = -1;
					buffer.append(korean.charAt(p));
				}
			}
		}

		// ¸¶Áö¸· ÇÑ±ÛÀÌ ÀÖÀ¸¸é Ã³¸®
		if (nCho != -1) {
			if (nJung != -1) // ÃÊ¼º+Áß¼º+(Á¾¼º)
				buffer.append(mergeKorean(nCho, nJung, nJong));
			else // ÃÊ¼º¸¸
				buffer.append(first.charAt(nCho));
		} else {
			if (nJung != -1) // Áß¼º¸¸
				buffer.append(second.charAt(nJung));
			else { // º¹ÀÚÀ½
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
