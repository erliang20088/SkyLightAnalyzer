package com.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.zel.core.SkyLightAnalyzer;
import com.zel.entity.Forest;
import com.zel.entity.Library;

public class RandomTest extends TestCase {
	public static int binarySearch(ArrayList<String> branches, String c) {
		int high = branches.size() - 1;
		if (branches.size() < 1) {
			return high;
		}
		int low = 0;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int cmp = branches.get(mid).compareTo(c);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}

	public static void testBinarySearch() {
		ArrayList<String> list = new ArrayList<String>();
		String c = "b";
		int pos = binarySearch(list, c);
		System.out.println(pos);

		list.add(c);
		// list.add("b");
		pos = binarySearch(list, "a");
		System.out.println(pos);

	}

	public void testStringSub() {
		System.out.println("123".substring(0, 1));
	}

	public static void main(String[] args) {
		// Library library = new Library();
		Forest forest = new Forest();
		Library.makeLibrary(forest);
		Library.insertKeyword(forest, "中国");
		Library.insertKeyword(forest, "中国人");
		Library.insertKeyword(forest, "中国人民");
		Library.insertKeyword(forest, "中国人民解放");
		Library.insertKeyword(forest, "中国人民解放军");
		Library.insertKeyword(forest, "爱你");
		Library.insertKeyword(forest, "ab");
		Library.insertKeyword(forest, "abc");
		Library.insertKeyword(forest, "1234");

		String content = "1234你好,爱你!";

		SkyLightAnalyzer analyzer = new SkyLightAnalyzer(forest, content);
		
		List<String> list = analyzer.getSplitWords();
		System.out.println("getSplitWords抽词结果************************");
		for (String temp : list) {
			System.out.print(temp + "    ");
		}
		System.out.println();
		
		list = analyzer.getSplitResult();
		System.out.println("getSplitResult分词结果************************");
		for (String temp : list) {
			System.out.print(temp + "    ");
		}
		System.out.println();
		System.out.println("运行完成!");
	}
}

