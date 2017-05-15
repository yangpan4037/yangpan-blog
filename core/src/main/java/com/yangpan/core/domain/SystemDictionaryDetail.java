package com.yangpan.core.domain;

/**
 *	数据字典明细
 */
public class SystemDictionaryDetail {
	/**主键*/
	private Long id;
	/**值*/
	private String name;
	/**所属类型*/
	private SystemDictionaryType types;

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
	public SystemDictionaryType getTypes() {
		return types;
	}
	public void setTypes(SystemDictionaryType types) {
		this.types = types;
	}
}
