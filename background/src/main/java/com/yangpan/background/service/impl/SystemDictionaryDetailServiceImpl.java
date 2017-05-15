package com.yangpan.background.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangpan.background.service.ISystemDictionaryDetailService;
import com.yangpan.core.domain.SystemDictionaryDetail;
import com.yangpan.core.domain.SystemDictionaryType;

/**
 *	【SystemDictionaryDetail】业务层实现类
 *	继承公共的业务层实现类，实现【SystemDictionaryDetail】业务层接口，具有【SystemDictionaryDetail】的CRUD方法
 */
@Service//配置注解service层（service层一般配置@Service）
@Transactional//配置给这个类的方法添加事务
public class SystemDictionaryDetailServiceImpl extends BaseServiceImpl<SystemDictionaryDetail> implements ISystemDictionaryDetailService{
	
	//定义hql
	private String hql = "select o from SystemDictionaryDetail o where o.types.sn=?";
	
	/**
	 * 获取品牌列表
	 */
	public List<SystemDictionaryDetail> getBrands() {
		return baseDao.findByHql(hql, SystemDictionaryType.PRODUCT_BRAND);
	}
	
	/**
	 * 获取单位列表
	 */
	public List<SystemDictionaryDetail> getUnits() {
		return baseDao.findByHql(hql, SystemDictionaryType.PRODUCT_UNIT);
	}

}
