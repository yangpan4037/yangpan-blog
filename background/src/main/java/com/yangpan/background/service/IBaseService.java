package com.yangpan.background.service;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.yangpan.background.query.BaseQuery;
import com.yangpan.background.query.PageList;

/**
 * 公共的业务层接口，定义一些公共CRUD的方法
 */
public interface IBaseService<T> {
	
	/**保存或更新*/
	public void saveOrUpdate(T obj);
	
	/**根据id删除一个*/
	public void delete(Serializable id);
	
	/**根据id获取一个*/
	public T get(Serializable id);
	
	/**获取全部*/
	public List<T> getAll();
	
	/**根据条件对象返回分页对象*/
	public PageList findByQuery(BaseQuery baseQuery) throws Exception ;
	
	/**下载导出*/
	public InputStream download(List<String[]> list, String[] heads) throws Exception;
	
	/**上传文件指xlsx文件*/
	public List<String[]> imp(File upload) throws Exception;
}
