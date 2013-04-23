package com.zel.util;

import com.zel.entity.Branch;

public class ArraysUtil {
	public static int low = 0;
	public static int high = 0;
	public static int mid = 0;
	public static int val = 0;

	/**
	 * 由于Branches是按照char的大小来组织顺序的，所以采用二分法查找 二分法查找,
	 * 
	 * @param branches
	 * @param insertChar
	 * @return
	 */
	public static int binarySearch(Branch[] branches, char insertChar) {
		high = branches.length - 1;
		if (high < 0) {// 说明是第一个子节点
			return high;
		}
		low = 0;
		while (low <= high) {
			mid = (low + high) / 2;
			val = branches[mid].compareTo(insertChar);
			if (val == 1) {
				high = mid - 1;
			} else if (val == -1) {
				low = mid + 1;
			} else {
				return low;
			}
		}
		return -(low + 1);
	}
}
