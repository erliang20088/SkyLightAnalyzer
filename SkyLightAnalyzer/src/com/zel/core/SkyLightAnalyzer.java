package com.zel.core;

import java.util.LinkedList;
import java.util.List;

import com.zel.entity.Branch;
import com.zel.entity.Forest;
import com.zel.interfaces.WoodInterface;
import com.zel.util.ArraysUtil;
import com.zel.util.StringUtil;

/**
 * 查询分析器
 * 
 * @author zel
 * 
 */
public class SkyLightAnalyzer {
	// 要分词的源字符串
	private String content;

	private int begin;// 去遍历content对应的charArray的position值
	private int current;// 去遍历content对应的charArray的current值
	private int array_length;// 每个charArray的数组长度

	private Forest forest;

	private WoodInterface branch;// 临时存储branch对象

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SkyLightAnalyzer(Forest forest) {
		this.forest = forest;
	}

	public SkyLightAnalyzer(Forest forest, String content) {
		this.forest = forest;
		this.content = content;
	}

	private int temp_position = 0;
	private boolean isFound = false;// 指是否已经找到一个对应的词了
	private int found_pos = 0;// 指是最近找到的词的位置number
	private boolean endFor = false;// 结束内层for循环标志位
	private String temp_string = null;// 暂存程序中任意字符串
	private boolean isValidWord = false;
	boolean found_new=false;
	/**
	 * 主要是判断该字符串是不是合法的串，并非截断型的纯字母或纯数字字符串
	 * 
	 * @param tempWord
	 * @param begin
	 * @param currentOrFoundPos
	 * @param array_length
	 * @return
	 */
	public boolean isValidWord(String tempWord, int begin,
			int currentOrFoundPos, int array_length) {
		boolean isValid = false;
		isValid =(!StringUtil.isAllAlpha(tempWord) && !StringUtil.isAllDigital(tempWord)) || ((StringUtil.isAllAlpha(tempWord)
				&& (begin - 1 > -1
						&& (!StringUtil.isAlpha(this.content.charAt(begin - 1))) || begin - 1 <= -1) && (currentOrFoundPos + 1 < array_length
				&& (!StringUtil.isAlpha(this.content
						.charAt(currentOrFoundPos + 1))) || currentOrFoundPos + 1 == array_length))
				|| (StringUtil.isAllDigital(tempWord)
						&& (begin - 1 > -1
								&& (!StringUtil.isDigital(this.content
										.charAt(begin - 1))) || begin - 1 <= -1) && (currentOrFoundPos + 1 < array_length
						&& (!StringUtil.isDigital(this.content
								.charAt(currentOrFoundPos + 1))) || currentOrFoundPos + 1 >= array_length)));
		return isValid;
	}
	/**
	 * 该方法取得分词结果，只能得到分词后所有匹配的分词结果，如祖国，成功等词
	 * @return
	 */
	public List<String> getSplitWords() {
		List<String> list = new LinkedList<String>();// 存储数组
		if (content == null || content.trim().length() < 1) {
			return list;
		}
		begin = 0;
		current = 0;
		isFound = false;
		found_pos = 0;
		endFor = false;

		array_length = this.content.length();
		for (begin = 0; begin < array_length; begin++) {
			current = begin;
			branch = this.forest.getBranch(this.content.charAt(begin));
			if (branch == null) {// 没查着，说明trie树中没有该char,故略过,进入下一个char
				continue;//这里是为了加入全部的字符
			} else {// 说明有这个char,可以继续往下寻找
				// 首先判断该branch的staus是否为1，若为1则不往下找了
				switch (branch.getStatus()) {
				case 1:// 直接加入结果集
					temp_string = this.content.substring(begin, current + 1);
					// 主要是判断前后的字符是否为字母或数字的连续
					isValidWord = isValidWord(temp_string, begin, current,
							array_length);
					if (isValidWord) {// 后边有字符且该temp_string是全字母,且后边一个是字母的话就不添加了
						list.add(temp_string);// 将这个词加入分词结果集合
					}
					endFor = true;
					break;// 直接break掉，进入下一个最外层的for循环
				case 0:// 继续往下寻找
				case 2:// 继续往下循找
					for (;(current + 1) < array_length; current++) {// 数组的第二个下标向下遍历
						endFor = false;
						Branch[] branches = branch.getSubBranches();
						temp_position = ArraysUtil.binarySearch(branches,
								this.content.charAt(current + 1));
						if (temp_position >= 0) {// 说明找到下一个char
							switch (branch.getStatus()) {
							case 1:
								temp_string = this.content.substring(begin,
										current + 1);
								// 主要是判断前后的字符是否为字母或数字的连续
								isValidWord = isValidWord(temp_string, begin,
										current, array_length);
								if (isValidWord) {
									list.add(temp_string);// 将这个词加入分词结果集合
									begin = current;
								}
								endFor = true;
								break;
							case 2:
								isFound = true;// 此时说明已经找到了，但后边还有词，还得继续试着往下找
								found_pos = current;
							}
							branch = branches[temp_position];
						} else {// 说明没找到，current位置是该词的结束
							switch (branch.getStatus()) {
							case 0:
								if (isFound) {
									temp_string = this.content.substring(begin,
											found_pos + 1);
									// 主要是判断前后的字符是否为字母或数字的连续
									isValidWord = isValidWord(temp_string,
											begin, found_pos, array_length);
									if (isValidWord) {
										list.add(temp_string);// 将这个词加入分词结果集合
										begin = found_pos;
									}
								}
								endFor = true;
								break;
							case 1:// 说明是个词，后边没词了,此种情况不可能出现，故不做case
							case 2:// 后边还有词，但后边的不是它的

								temp_string = this.content.substring(begin,
										current + 1);
								// 主要是判断前后的字符是否为字母或数字的连续
								isValidWord = isValidWord(temp_string, begin,
										current, array_length);
								if (isValidWord) {
									list.add(temp_string);// 将这个词加入分词结果集合
									begin = current;
								}
								endFor = true;
								break;
							}
						}
						if (endFor) {// 表明前面已经产生新词加入集合了，这里跳出此次for循环
							break;
						}
					}
					if (current + 1 == array_length) {// 说明此时已经到头了直接判断这个是不是词就可以了!
						switch (branch.getStatus()) {
						case 0:
							if (isFound) {
								temp_string = this.content.substring(begin,
										found_pos + 1);
								// 主要是判断前后的字符是否为字母或数字的连续
								isValidWord = isValidWord(temp_string, begin,
										found_pos, array_length);
								if (isValidWord) {
									list.add(temp_string);// 将这个词加入分词结果集合
									begin = found_pos;
								}
							}
							break;
						case 1:
						case 2:
							temp_string = this.content.substring(begin,
									array_length);
							// 主要是判断前后的字符是否为字母或数字的连续
							isValidWord = isValidWord(temp_string, begin,
									array_length - 1, array_length);
							if (isValidWord) {
								list.add(temp_string);// 将这个词加入分词结果集合
								begin = current;
							}
							break;
						}
					}
					break;// break掉最外层的switch循环
				}
			}
			isFound = false;
		}
		return list;
	}
	/**
	 * 该方法取得分词结果，包括字符串的所有数据的分词结果
	 * @return
	 */
	public List<String> getSplitResult() {
		List<String> list = new LinkedList<String>();// 存储数组
		if (content == null || content.trim().length() < 1) {
			return list;
		}
		begin = 0;
		current = 0;
		isFound = false;
		found_pos = 0;
		endFor = false;

		array_length = this.content.length();
		for (begin = 0; begin < array_length; begin++) {
			current = begin;
			found_new=false;
			branch = this.forest.getBranch(this.content.charAt(begin));
			if (branch == null) {// 没查着，说明trie树中没有该char,故略过,进入下一个char
//				continue;//这里是为了加入全部的字符
			} else {// 说明有这个char,可以继续往下寻找
				// 首先判断该branch的staus是否为1，若为1则不往下找了
				switch (branch.getStatus()) {
				case 1:// 直接加入结果集
					temp_string = this.content.substring(begin, current + 1);
					// 主要是判断前后的字符是否为字母或数字的连续
					isValidWord = isValidWord(temp_string, begin, current,
							array_length);
					if (isValidWord) {// 后边有字符且该temp_string是全字母,且后边一个是字母的话就不添加了
						list.add(temp_string);// 将这个词加入分词结果集合
						found_new=true;
					}
					endFor = true;
					break;// 直接break掉，进入下一个最外层的for循环
				case 0:// 继续往下寻找
				case 2:// 继续往下循找
					for (;(current + 1) < array_length; current++) {// 数组的第二个下标向下遍历
						endFor = false;
						Branch[] branches = branch.getSubBranches();
						temp_position = ArraysUtil.binarySearch(branches,
								this.content.charAt(current + 1));
						if (temp_position >= 0) {// 说明找到下一个char
							switch (branch.getStatus()) {
							case 1:
								temp_string = this.content.substring(begin,
										current + 1);
								// 主要是判断前后的字符是否为字母或数字的连续
								isValidWord = isValidWord(temp_string, begin,
										current, array_length);
								if (isValidWord) {
									list.add(temp_string);// 将这个词加入分词结果集合
									begin = current;
									found_new=true;
								}
								endFor = true;
								break;
							case 2:
								isFound = true;// 此时说明已经找到了，但后边还有词，还得继续试着往下找
								found_pos = current;
								found_new=true;
							}
							branch = branches[temp_position];
						} else {// 说明没找到，current位置是该词的结束
							switch (branch.getStatus()) {
							case 0:
								if (isFound) {
									temp_string = this.content.substring(begin,
											found_pos + 1);
									// 主要是判断前后的字符是否为字母或数字的连续
									isValidWord = isValidWord(temp_string,
											begin, found_pos, array_length);
									if (isValidWord) {
										list.add(temp_string);// 将这个词加入分词结果集合
										begin = found_pos;
										found_new=true;
									}
								}
								endFor = true;
								break;
							case 1:// 说明是个词，后边没词了,此种情况不可能出现，故不做case
							case 2:// 后边还有词，但后边的不是它的

								temp_string = this.content.substring(begin,
										current + 1);
								// 主要是判断前后的字符是否为字母或数字的连续
								isValidWord = isValidWord(temp_string, begin,
										current, array_length);
								if (isValidWord) {
									list.add(temp_string);// 将这个词加入分词结果集合
									begin = current;
									found_new=true;
								}
								endFor = true;
								break;
							}
						}
						if (endFor) {// 表明前面已经产生新词加入集合了，这里跳出此次for循环
							break;
						}
					}
					if (current + 1 == array_length) {// 说明此时已经到头了直接判断这个是不是词就可以了!
						switch (branch.getStatus()) {
						case 0:
							if (isFound) {
								temp_string = this.content.substring(begin,
										found_pos + 1);
								// 主要是判断前后的字符是否为字母或数字的连续
								isValidWord = isValidWord(temp_string, begin,
										found_pos, array_length);
								if (isValidWord) {
									list.add(temp_string);// 将这个词加入分词结果集合
									begin = found_pos;
									found_new=true;
								}
							}
							break;
						case 1:
						case 2:
							temp_string = this.content.substring(begin,
									array_length);
							// 主要是判断前后的字符是否为字母或数字的连续
							isValidWord = isValidWord(temp_string, begin,
									array_length - 1, array_length);
							if (isValidWord) {
								list.add(temp_string);// 将这个词加入分词结果集合
								begin = current;
								found_new=true;
							}
							break;
						}
					}
					break;// break掉最外层的switch循环
				}
			}
			isFound = false;
			if(!found_new){
				list.add(this.content.substring(begin,(begin+1<=array_length?begin+1:array_length)));				
			}
		}
		return list;
	}
}
