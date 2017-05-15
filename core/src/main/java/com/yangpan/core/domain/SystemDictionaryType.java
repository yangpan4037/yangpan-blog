package com.yangpan.core.domain;

/**
 * 数据字典类型
 */
public class SystemDictionaryType {
	
	// 定义2个常量：系统初始化的时候
	public static final String PRODUCT_BRAND = "productBrand";//产品品牌
	public static final String PRODUCT_UNIT = "productUnit";//产品单位
	
	/**主键*/
	private Long id;
	
	/**编码*/
	private String sn;
	
	/**名称*/
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
