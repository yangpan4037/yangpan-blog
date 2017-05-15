package com.yangpan.core.domain;

/**
 *	权限
 */
public class Permission {
	/**主键*/
	private Long id;
	/**名称*/
	private String name;
	/**控制方法*/
	private String method;
	/**备注*/
	private String descs;
	
	public Permission() {
		super();
	}

	public Permission(Long id) {
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
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
}
