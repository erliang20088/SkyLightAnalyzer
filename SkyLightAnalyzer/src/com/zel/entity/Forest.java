package com.zel.entity;

import com.zel.interfaces.WoodInterface;

/**
 * 基于Trie树结构的根路径对象Forest
 * 
 * @author zel
 */
public class Forest implements WoodInterface {
	public static WoodInterface[] root = new WoodInterface[65536];
	public static int max_length = root.length;

	@Override
	public void insertBranch(Branch branch) {
		WoodInterface woodInterface = this.getBranch(branch.getCharNode());
		if (woodInterface == null) {// 说明这个char在Forest中是不存在的，直接插入就可以了
			// root[branch.getCharNode()] = branch;
			root[branch.getCharNode()] = branch;
		} else {
			switch (woodInterface.getStatus()) {//先判断在root中的status
			case 0:
				if (branch.getStatus() == 1) {//现在已经是个词了
					woodInterface.setStatus(2);
				}
				break;
			case 1:
				if (branch.getStatus() == 0) {//原先是个词，现在新加的有延长，故设置为2
					woodInterface.setStatus(2);
				}
				break;
			}
			root[branch.getCharNode()] = woodInterface;
		}
	}

	@Override
	public WoodInterface getBranch(char oneChar) {
		// 如果oneChar已经大于max_length，则返回null,此时认为为不可识别的字符
		if (oneChar > max_length) {
			return null;
		}
		return root[oneChar];
	}

	public static void main(String[] args) {
		System.out.println(root.length);
	}

	@Override
	public Branch[] getSubBranches() {// 暂时没用
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char getChar() {// 暂时没用
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStatus() {// 暂时没用
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStatus(int status) {// 暂时没用
		// TODO Auto-generated method stub

	}
}
