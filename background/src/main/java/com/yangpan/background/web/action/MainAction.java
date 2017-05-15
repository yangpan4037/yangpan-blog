package com.yangpan.background.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.yangpan.background.service.IMenuService;
import com.yangpan.core.domain.Menu;
import com.yangpan.core.domain.User;

/**
 *	主页Action
 */
@Controller//配置@Controller表明这是控制器层，action都这样配置
@Scope("prototype")//配置@Scope("prototype")表明这是个多例
public class MainAction extends BaseAction {
	private static final long serialVersionUID = -7058712164162400420L;
	
	//定义menuService
	@Autowired
	private IMenuService menuService;
	
	@Override
	public String execute() throws Exception {
		
		//从session得到用户
//		User employee = (User)ActionContext.getContext().getSession().get(BaseAction.USER_IN_SESSION);
//		
//		//查询出登录用户的菜单
//		List<Menu> menus = menuService.findByLoginUserId(employee.getId());
//		
//		//添加到值栈
//		putContent("menus", menus);
		
		return SUCCESS;
	}
	
	//后台主页的内容页面
	public String content() throws Exception {
		return "content";
	}
}
