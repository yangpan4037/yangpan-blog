package com.yangpan.background.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangpan.background.service.IMenuService;
import com.yangpan.core.domain.Menu;

/**
 *	【Menu】业务层实现类
 *	继承公共的业务层实现类，实现【Menu】业务层接口，具有【Menu】的CRUD方法
 */
@Service//配置注解service层（service层一般配置@Service）
@Transactional//配置给这个类的方法添加事务
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService{
	
	@Override
	public List<Menu> findByLoginUserId(Long longUserId) {
		
		//定义返回登录用户的菜单集合
		List<Menu> resultMenu = new ArrayList<Menu>();
		
		//定义hql，通过employee连接到menu
		String hql = "select distinct m from Employee e join e.roles r join r.menus m where e.id = ?";
		
		//通过hql和登录用户的id查询出所有菜单
		List<Menu> menus = baseDao.findByHql(hql, longUserId);
		
		//分离一级菜单根据parent是否为空
		for (Menu menu : menus) {
			if(menu.getParent() == null){
				resultMenu.add(menu);
			}
		}
		
		//分离二级菜单根据parent不等于空
		for (Menu menu : menus) {
			if(menu.getParent() != null){
				//循环一级菜单，根据二级菜单的parent，把二级菜单装到一级菜单里面
				for (Menu firstMenu : resultMenu) {
					if(menu.getParent().getId() == firstMenu.getId()){
						firstMenu.getChildren().add(menu);
					}
				}
			}
		}
		
		//返回登录用户的菜单
		return resultMenu;
	}
	
	
	/**
	 * 模糊查询菜单 
	 */
	@Override
	public List<String[]> searchMenuByLike(String likeStr) {
		//定义hql
		String hql = "select o.name, o.url from Menu o where o.name like ? and o.parent.id is not null";
		return baseDao.findByHql(hql, "%" + likeStr + "%");
	}

}
