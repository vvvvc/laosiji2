package com.itheima.laosiji.Bean;

import java.util.ArrayList;
import java.util.List;

public class GrpBean {
	public List<SortBean.CategoryBean> list = new ArrayList<>();

	public int id;
	public boolean isLeafNode;
	public String name;
	public int parentId;
	public String pic;
	public String tag;

	public GrpBean(SortBean.CategoryBean bean) {
		this.id = bean.id;
		this.parentId = bean.parentId;
		this.name = bean.name;
	}
}
