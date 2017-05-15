package com.yangpan.background.dao;

import java.io.Serializable;
import java.util.List;

import com.yangpan.background.query.BaseQuery;
import com.yangpan.background.query.PageList;

/**
 *	公共的dao接口
 *	定义一些针对指定泛型的对象CRUD的方法
 */
public interface IBaseDao<T> {
	
	/**保存或更新*/
	public void saveOrUpdate(T obj);
	
	/**根据id删除一个*/
	public void delete(Class<T> entityClass,Serializable id);
	
	/**根据id获取一个*/
	public T get(Class<T> entityClass,Serializable id);
	
	/**获取全部*/
	public List<T> getAll(Class<T> entityClass);
	
	/**根据条件对象返回分页对象*/
	public PageList findByQuery(BaseQuery baseQuery);
	
	/**
	 * 查询通过hql和参数对象，返回一个集合
	 */
	public List findByHql(String hql, Object... objects);
	
	/**
	 * 通过hql得到查询缓存的集合
	 */
	public List<String> findCacheByHql(String string);
}
