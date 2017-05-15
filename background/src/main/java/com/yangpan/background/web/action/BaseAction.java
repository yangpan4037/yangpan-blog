package com.yangpan.background.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *	公共的Action
 *	定义公共的Action的重载页面结果视图名称及一些公共的方法
 */
public class BaseAction extends ActionSupport {
	
	private static final long serialVersionUID = -6961641101870087291L;

	//定义日志对象
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	//重载页面的结果视图名称
	public static final String RELOAD = "reload";
	
	//定义后台主页的试图名称
	public static final String MAIN = "main";

	//定义HttpSession里的用户
	public static final String USER_IN_SESSION = "userInSession";
	
	//添加到值栈
	protected void putContent(String key , Object value) {
		ActionContext.getContext().put(key, value);
	}
	
}
