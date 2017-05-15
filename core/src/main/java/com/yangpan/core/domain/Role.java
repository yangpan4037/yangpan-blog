package com.yangpan.core.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 */
public class Role {
	/**主键*/
	private Long id;

	/**角色名称*/
	private String name;
	
	/**权限列表*/
	private Set<Permission> permissions = new HashSet<Permission>();
	
	/**菜单列表*/
	private Set<Menu> menus = new HashSet<Menu>();
	
	public Role() {}

	public Role(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
}
