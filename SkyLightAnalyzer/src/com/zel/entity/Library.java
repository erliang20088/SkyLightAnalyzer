package com.zel.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.log4j.Logger;

import com.zel.interfaces.WoodInterface;
import com.zel.util.IOUtil;
import com.zel.util.ReadConfigUtil;

public class Library {
	// 做日志用
	private static Logger logger = Logger.getLogger(Library.class);

	// 辅助字符定位
	private static int temp_index = 0;
	private static int temp_count = 0;

	// 声明一个接口，来接Forest或是Branch，再添新词时经常要切换不同的对象
	private static WoodInterface woodInterface;

	// 暂存keyword中的每个char
	// public static char temp_char=0;

	/**
	 * 通过词典，构造分词的trie树结构
	 */
	public static void makeLibrary(Forest forest) {
		String dic_source = IOUtil.readFile(
				ReadConfigUtil.getValue("dic.path"), null);
		BufferedReader br = new BufferedReader(new StringReader(dic_source));// 通过StringReader来读取词典
		String line = null;
		String items[] = null;// 存储每条dic item的param
		String[] pojoParas = null;// 暂存每个词条后边储存的参数列表，包括权重、词性等.
		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					line = line.trim();// 先去下空白字符,以免影响参数的构成
					items = line.split("\t");// 用制表符/t去分开每个词条的参数列表

					pojoParas = new String[items.length - 1];
					for (int i = 1; i < items.length; i++) {
						pojoParas[i - 1] = items[i];
					}
					insertKeyword(forest, items[0], pojoParas);
				}
			}
		} catch (Exception e) {
			logger.info("构造词典时候出现错误!");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.info("构造词典时候出现错误!");
			}
		}
	}

	// 将一关键词插入到Forest当中
	public static void insertKeyword(Forest forest, String keyword,
			String pojoParas[]) {
		woodInterface = forest;

		char[] charArray = keyword.toCharArray();
		temp_count = charArray.length;

		for (temp_index = 0; temp_index < temp_count; temp_index++) {
			// 当取到某个keyword的最后一个char时，做参数和status的插入
			if (temp_index == (temp_count - 1)) {
				woodInterface.insertBranch(new Branch(charArray[temp_index],
						new ParasPojo(pojoParas), 1));
			} else {
				// 只要不是keyword的最后一个词，不添加任何的paras
				woodInterface.insertBranch(new Branch(charArray[temp_index],
						null, 0));
			}
			woodInterface = woodInterface.getBranch(charArray[temp_index]);
		}
	}
	
	public static void insertKeyword(Forest forest, String keyword){
		insertKeyword(forest,keyword,null);
	}

	public void insertBranch(Branch branch) {

	}

	public static void main(String[] args) {
		Forest forest = new Forest();
		makeLibrary(forest);
	}

}
