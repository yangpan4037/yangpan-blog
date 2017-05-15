package com.yangpan.background.web.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.code.kaptcha.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yangpan.background.service.IUserService;
import com.yangpan.core.domain.User;


/**
 *	登录Action
 */
@Controller//配置@Controller表明这是控制器层，action都这样配置
@Scope("prototype")//配置@Scope("prototype")表明这是个多例
public class LoginAction extends BaseAction{
	
	private static final long serialVersionUID = 208981911131634731L;

	//用户名
	private String username;
	
	//密码
	private String password;
	
	//验证码
	private String randomCode;
	
	//定义userService
	@Autowired
	IUserService userService;
	
	@Override
	public String execute() throws Exception {
		return LOGIN;
	}
	
	/**
	 * 验证登录请求方法 
	 */
	public void validateCheck() {
		
		//获取session里的验证码
		String sessionCode = (String)ActionContext.getContext().getSession().get(Constants.KAPTCHA_SESSION_KEY);
		
		if(StringUtils.isBlank(username)){
			clearErrors();
			addFieldError("username", "用户名不能为空");
			return;
		}
		if(StringUtils.isBlank(password)){
			clearErrors();
			addFieldError("password", "密码不能为空");
			return;
		}else if(sessionCode!=null && !sessionCode.equals(randomCode)){
			clearErrors();
			addFieldError("randomCode", "验证码不正确");
		}else{
			clearErrors();
		}
	}
	
	/**
	 *	登录请求方法 
	 */
	@InputConfig(resultName = LOGIN)//验证错误跳转到的结果视图名称
	public String check() throws Exception {
		//根据用户名和密码查询，返回一个用户
		User employee = userService.findByLogin(username, password);
		
		//如果employee不为空，就登录成功,并设置到HttpSession，跳转到主页
		if(employee != null){
			ActionContext.getContext().getSession().put(USER_IN_SESSION, employee);
			return MAIN;
		}
		
		// 业务逻辑异常
		clearErrors();
		addActionError("登录失败,请确认用户名或密码");
		
		//不满足上述条件登录失败，返回登陆页面
		return LOGIN;
	}
	
	/**
	 * 退出 
	 */
	public String logout() throws Exception {
		//清空session里面的user
		ActionContext.getContext().getSession().clear();
		return LOGIN;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}
	
}
