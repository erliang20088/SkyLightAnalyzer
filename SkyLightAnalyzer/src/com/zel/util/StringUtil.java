package com.zel.util;

public class StringUtil {
	public static boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static boolean isDigital(char c) {
		return (c >= '0' && c <= '9');
	}

	public static boolean isAllDigital(String str) {
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (!isDigital(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAllAlpha(String str) {
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (!isAlpha(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// System.out.println("a---" + isAlpha('a'));
		// System.out.println("d---" + isAlpha('d'));
		// System.out.println("z---" + isAlpha('z'));
		// System.out.println("A---" + isAlpha('A'));
		// System.out.println("D---" + isAlpha('D'));
		// System.out.println("Z---" + isAlpha('Z'));
		// System.out.println("1---" + isAlpha('1'));
		// System.out.println("2---" + isAlpha('3'));
		System.out.println("a---" + isDigital('a'));
		System.out.println("d---" + isDigital('d'));
		System.out.println("z---" + isDigital('z'));
		System.out.println("A---" + isDigital('A'));
		System.out.println("D---" + isDigital('D'));
		System.out.println("Z---" + isDigital('Z'));
		System.out.println("1---" + isDigital('1'));
		System.out.println("2---" + isDigital('3'));
		System.out.println("9---" + isDigital('9'));
	}
}
