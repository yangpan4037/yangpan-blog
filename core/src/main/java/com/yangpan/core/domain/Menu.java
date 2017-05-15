package com.yangpan.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 */
public class Menu {
	/**主键*/
	private Long id;
	/**菜单名称*/
	private String name;
	/**点击地址*/
	private String url;
	/**显示图片*/
	private String icon;
	/**上级菜单*/
	private Menu parent;
	/**子菜单*/
	private List<Menu> children = new ArrayList<Menu>();
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", url=" + url + ", icon=" + icon + "]";
	}
	
}
