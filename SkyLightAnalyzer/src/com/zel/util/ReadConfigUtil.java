package com.zel.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 专门用来读取分词器的配置文件
 * 
 * @author zel
 */
public class ReadConfigUtil {
	private static Properties config = null;
	static {
		InputStream in = ReadConfigUtil.class.getClassLoader()
				.getResourceAsStream("config.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("No config.properties defined error");
		}
	}

	// 根据key读取value
	public static String getValue(String key) {
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
			return null;
		}
	}

	// public static void main(String args[]) {
	// System.out.println(getValue("dic_path"));
	// }
}
