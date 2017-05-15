package com.yangpan.background.query;

import org.apache.commons.lang3.StringUtils;

import com.yangpan.core.domain.Permission;

/**
 *	Permission查询条件,继承公共的查询条件类
 */
public class PermissionQuery extends BaseQuery {
	
	private String name;
	
	private String method;
	
	/**
	 * 构造器
	 * 调用父类的构造器传入查询员工需要的类名，用于拼接hql
	 */
	public PermissionQuery() {
		super(Permission.class.getName());
	}
	
	/**
	 * 覆写父类的回调得到where方法，里面调用父类拼接查询条件hql的where语句
	 */
	@Override
	protected void callBackGetWhere() {
		if(StringUtils.isNotBlank(name)){
			setWhereHql("o.name like ?", "%" + name + "%");
		}
		if(StringUtils.isNotBlank(method)){
			setWhereHql("o.method like ?", "%" + method + "%");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}

