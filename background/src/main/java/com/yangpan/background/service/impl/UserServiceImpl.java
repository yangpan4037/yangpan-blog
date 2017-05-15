package com.yangpan.background.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangpan.background.service.IUserService;
import com.yangpan.core.domain.User;

/**
 *	【员工】业务层实现类
 *	继承公共的业务层实现类，实现【员工】业务层接口，具有【员工】的CRUD方法
 */
@Service//配置注解service层（service层一般配置@Service）
@Transactional//配置给这个类的方法添加事务
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService{

	/**
	 * 通过用户名查询，返回布尔值，用于验证用户是否重复
	 */
	@Override
	public boolean findByUsername(String username) {
		String hql = "select count(o) from Employee o where o.username=?";
		List<Long> list = baseDao.findByHql(hql, username);
		if (list.get(0).intValue() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 登录查询，返回匹配的用户名密码正确的用户，否则返回空（登录）
	 */
	@Override
	public User findByLogin(String username, String password) {
		
		//定义hql，要用户名和密码都匹配
		String hql = "select o from Employee o where o.username=? and o.password=?";
		List<User> list = baseDao.findByHql(hql, username, password);
		
		//如果有结果，说明匹配到用户，返回该用户
		if (list.size() > 0) {
			return list.get(0);
		}
		
		//如果没查到则返回空
		return null;
	}
	
	/**
	 * 查询所有采购员，部门是采购部的所有员工 
	 */
	@Override
	public List<User> getBuyers() {
		return baseDao.findByHql("select o from Employee o where o.department.name = ?", "采购部");
	}

}
