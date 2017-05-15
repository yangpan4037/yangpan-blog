package com.yangpan.background.service;


import java.util.List;

import com.yangpan.core.domain.Menu;

/**
 *	【Menu】业务层接口
 *	继承公共的业务层接口，具有操作【Menu】CRUD的方法
 */
public interface IMenuService extends IBaseService<Menu> {
	
	/**
	 * 	根据登录id查询菜单集合
	 */
	List<Menu> findByLoginUserId(Long longUserId);
	
	/**
	 * 模糊查询菜单 
	 */
	List<String[]> searchMenuByLike(String likeStr);
}
