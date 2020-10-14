package com.hpen.util;

import java.text.DecimalFormat;
import java.text.Format;

public class Sequence {
	private static int no = 1;
	private static Format f = new DecimalFormat("000000");
	public static String generate() {
		return f.format(no++);
	}
}
