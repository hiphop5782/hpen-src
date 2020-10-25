package com.hacademy.hpen.ui.option;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyLoader {
	
	public static Properties load(String propertyFileName) throws IOException {
		Properties props = new Properties();
		try(
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(propertyFileName)), "UTF-8"));
		){
			props.load(reader);
		}
		return props;
	}
	
}
