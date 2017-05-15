package com.yangpan.background.service;

import java.util.List;

import com.yangpan.core.domain.Permission;


/**
 *	【Permission】业务层接口
 *	继承公共的业务层接口，具有操作【Permission】CRUD的方法
 */
public interface IPermissionService extends IBaseService<Permission> {
	
	/**
	 *	得到权限表里的所有method字段 ，也就是得到所有权限
	 */
	public List<String> getAllPermissionMethods();
	
	/**
	 * 根据登录用户的id查询他的权限
	 */
	public List<String> findPermissionMethodByLoginUserId(Long loginUserId);

}
