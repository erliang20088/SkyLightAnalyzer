package com.zel.entity;

import com.zel.interfaces.WoodInterface;
import com.zel.util.ArraysUtil;
import com.zel.util.StringUtil;

/**
 * Trie树结构的树枝对象Branch
 * 
 * @author zel
 * 
 */
public class Branch implements WoodInterface {
	// 用来存储一个Branch后边的Branch而添加
	private Branch[] branches = null;

	public Branch(char charNode, ParasPojo paras, int status) {
		this.charNode = charNode;
		this.paras = paras;
		this.status = status;
	}

	/**
	 * 关键词 权重 词性 该Branch的状态，0代表不是个词，1代表是个词，2代表不但是个词还可以往下查询
	 */
	private char charNode;

	@Override
	public void insertBranch(Branch branch) {
		if (branches == null) {// 说明这是跟在root的node下的第一个branch
			branches = new Branch[0];// 直接初始化一个Branch
		}
		int pos = ArraysUtil.binarySearch(branches, branch.getCharNode());
		if (pos < 0) {// 说明没找到,此时要进行元素插入操作
			Branch[] new_branches = new Branch[branches.length + 1];// 用来存放新branch数组,实现新branch的插入
			System.arraycopy(branches, 0, new_branches, 0, -(pos + 1));// 将插入位置之前的元素先复一下
			System.arraycopy(branches, -(pos + 1), new_branches, -pos,
					branches.length - (-(pos + 1)));// 将插入位置之后的元素复制一下
			new_branches[-(pos + 1)] = branch;// 插入到指定位置
			branches = new_branches;// 将新数组地址赋值给老数组
			// return branch;
		} else {// 说明是找到了,直接修改branch对象status即可
			Branch origi_branch = branches[pos];
			switch (origi_branch.getStatus()) {// 查看原始的branch中status的状态
			case 0:// 为0，指该原始branch不成任何词
				if (branch.getStatus() == 1) {
					origi_branch.setStatus(2);
					origi_branch.setParas(branch.getParas());// 将参数传入
				}
				break;
			case 1:// 说明原始的时候本已成词
				if (branch.getStatus() == 0) {
					origi_branch.setStatus(2);
				}
				break;
			}
		}
	}

	@Override
	public WoodInterface getBranch(char oneChar) {
		// 返回当前正在操作的branch对象
		// 现在假设一定会返回一个值
		int pos = ArraysUtil.binarySearch(branches, oneChar);
		return branches[pos];
	}

	public char getCharNode() {
		return charNode;
	}

	public void setCharNode(char charNode) {
		this.charNode = charNode;
	}

	private ParasPojo paras;

	public ParasPojo getParas() {
		return paras;
	}

	public void setParas(ParasPojo paras) {
		this.paras = paras;
	}

	private int status;

	public Branch() {

	}

	public int getStatus() {
		return status;
	}

	public int compareTo(char aidChar) {
		char temp_char = this.getCharNode();
		if (temp_char > aidChar) {
			return 1;
		} else if (temp_char < aidChar) {
			return -1;
		}
		return 0;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public Branch[] getSubBranches() {
		// TODO Auto-generated method stub
		if (this.branches == null) {
			this.branches = new Branch[0];
		}
		return this.branches;
	}

	@Override
	public char getChar() {
		// TODO Auto-generated method stub
		return this.getCharNode();
	}

}
