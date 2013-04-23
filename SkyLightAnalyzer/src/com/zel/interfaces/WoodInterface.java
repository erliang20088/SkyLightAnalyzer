package com.zel.interfaces;

import com.zel.entity.Branch;

/**
 * 对Forest和Branch的接口抽象
 * 
 * @author zel
 * 
 */
public interface WoodInterface {
	public void insertBranch(Branch branch);// 插入一个Branch

	public WoodInterface getBranch(char oneChar);// 得到一个char对应的branch对象

	public Branch[] getSubBranches();

	public char getChar();

	public int getStatus();
	
	public void setStatus(int status);
}
