package com.yangpan.background.query;

import org.apache.commons.lang3.StringUtils;

import com.yangpan.core.domain.Menu;

/**
 *	Menu查询条件,继承公共的查询条件类
 */
public class MenuQuery extends BaseQuery {
	
	private String name;
	
	/**
	 * 构造器
	 * 调用父类的构造器传入查询员工需要的类名，用于拼接hql
	 */
	public MenuQuery() {
		super(Menu.class.getName());
	}
	
	/**
	 * 覆写父类的回调得到where方法，里面调用父类拼接查询条件hql的where语句
	 */
	@Override
	protected void callBackGetWhere() {
		if(StringUtils.isNotBlank(name)){
			setWhereHql("o.name like ?", "%" + name + "%");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

