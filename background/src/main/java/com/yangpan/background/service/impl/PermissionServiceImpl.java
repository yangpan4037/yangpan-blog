package com.yangpan.background.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangpan.background.service.IPermissionService;
import com.yangpan.core.domain.Permission;

/**
 *	【Permission】业务层实现类
 *	继承公共的业务层实现类，实现【Permission】业务层接口，具有【Permission】的CRUD方法
 */
@Service//配置注解service层（service层一般配置@Service）
@Transactional//配置给这个类的方法添加事务
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService{
	
	/**
	 *	得到权限表里的所有method字段 ，也就是得到所有权限
	 */
	@Override
	public List<String> getAllPermissionMethods() {
		return baseDao.findCacheByHql("select p.method from Permission p");
	}
	
	/**
	 * 根据登录用户的id查询他的权限
	 */
	@Override
	public List<String> findPermissionMethodByLoginUserId(Long loginUserId) {
		String hql = "select distinct p.method from Employee e join e.roles r join r.permissions p where e.id=?";
		return baseDao.findByHql(hql, loginUserId);
	}

}
