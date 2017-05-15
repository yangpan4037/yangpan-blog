package com.yangpan.background.service;

import java.util.List;

import com.yangpan.core.domain.User;

/**
 *	【员工】业务层接口
 *	继承公共的业务层接口，具有操作【员工】CRUD的方法
 */
public interface IUserService extends IBaseService<User> {
	
	/**
	 * 通过用户名查询，返回布尔值，用于验证用户是否重复（验证用户是否重复，新增修改员工页面）
	 */
	public boolean findByUsername(String username);
	
	/**
	 * 登录查询，返回匹配的用户名密码正确的用户，否则返回空（登录）
	 */
	public User findByLogin(String username, String password);
	
	/**
	 * 查询所有采购员，部门是采购部的所有员工 
	 */
	public List<User> getBuyers();
	
}
