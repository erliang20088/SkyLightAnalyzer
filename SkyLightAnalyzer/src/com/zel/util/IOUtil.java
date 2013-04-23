package com.zel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * 读取字典时的I/O工具类
 * 
 * @author zel
 * 
 */
public class IOUtil {
	private static Logger logger = Logger.getLogger(IOUtil.class);

	/**
	 * fileEncoding若为null,则采用系统默认编码
	 * 
	 * @param filePath
	 * @param fileEncoding
	 * @return
	 */
	public static String readFile(String filePath, String fileEncoding) {
		if (fileEncoding == null) {
			fileEncoding = System.getProperty("file.encoding");
		}
		File file = new File(filePath);
		BufferedReader br = null;

		String line = null;

		StringBuilder sb = new StringBuilder();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), fileEncoding));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			logger.info(e.getLocalizedMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.info(e.getLocalizedMessage());
					logger.info("关闭IOUtil流时出现错误!");
				}
			}
		}
		return null;
	}
	// public static void main(String[] args) {
	// // String source=readFile("resource/library.dic",null);
	// String source=readFile(ReadConfigUtil.getValue("dic.path"),null);
	// System.out.println(source);
	// }
}
