package com.hpen.value;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hpen.util.DialogManager;
import com.hpen.util.file.VersionManager;

public class Version {
	public static final String name = "hPen";
	public static final String version;
	public static final String value;
	
	static {
		version = VersionManager.load();
		value = name+" v"+version;
	}
}
