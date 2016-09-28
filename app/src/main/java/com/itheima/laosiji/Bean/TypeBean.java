package com.itheima.laosiji.Bean;

import java.util.ArrayList;
import java.util.List;

public class TypeBean {
	public List<GrpBean> list = new ArrayList<>();

	public int id;
	public boolean isLeafNode;
	public String name;
	public int parentId;
	public String pic;
	public String tag;

	public TypeBean(SortBean.CategoryBean bean) {
		this.id = bean.id;
		this.name = bean.name;
	}
}
