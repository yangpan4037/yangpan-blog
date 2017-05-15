package com.yangpan.background.service;

import java.util.List;

import com.yangpan.core.domain.SystemDictionaryDetail;

/**
 *	【SystemDictionaryDetail】业务层接口
 *	继承公共的业务层接口，具有操作【SystemDictionaryDetail】CRUD的方法
 */
public interface ISystemDictionaryDetailService extends IBaseService<SystemDictionaryDetail> {

	/**
	 * 获取品牌列表
	 */
	List<SystemDictionaryDetail> getBrands();

	/**
	 * 获取单位列表
	 */
	List<SystemDictionaryDetail> getUnits();

}
