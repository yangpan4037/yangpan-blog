package com.yangpan.core.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 员工表
 */
public class User {
	
	/**主键*/
	private Long id;
	
	/**用户名*/
	private String username;
	
	/**密码*/
	private String password;
	
	/**电子邮件*/
	private String email;
	
	/**头像*/
	private String headImage;
	
	/**年龄*/
	private Integer age;
	
	/**角色列表*/
	private Set<Role> roles = new HashSet<Role>(); 

	
	
	public User() {}
	
	public User(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
