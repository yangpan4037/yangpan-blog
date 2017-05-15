package com.yangpan.background.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yangpan.background.dao.IBaseDao;
import com.yangpan.background.query.BaseQuery;
import com.yangpan.background.query.PageList;
/**
 * 公共的dao实现类 能够根据泛型对不同的对象实现CRUD的操作
 */
@Repository // 配置注解仓库（dao一般配置@Repository）
public class BaseDaoImpl<T> implements IBaseDao<T> {

	// 自动注入sessionFactory
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(T obj) {
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
	}

	@Override
	public void delete(Class<T> entityClass, Serializable id) {
		T obj = get(entityClass, id);
		if (obj != null) {
			sessionFactory.getCurrentSession().delete(obj);
		}
	}

	@Override
	public T get(Class<T> entityClass, Serializable id) {
		return (T) sessionFactory.getCurrentSession().get(entityClass, id);
	}

	@Override
	public List<T> getAll(Class<T> entityClass) {
		return sessionFactory.getCurrentSession().createCriteria(entityClass).list();
	}

	/**
	 * 根据查询条件query对象，返回PageList对象
	 */
	@Override
	public PageList findByQuery(BaseQuery baseQuery) {

		// 通过session得到查询对象Query
		Query queryCount = sessionFactory.getCurrentSession().createQuery(baseQuery.getCountHql());
		// 通过session得到查询对象Query
		Query queryData = sessionFactory.getCurrentSession().createQuery(baseQuery.getLimitHql());

		// 通过baseQuery得到查询条件的值的集合
		List paramList = baseQuery.getParamList();

		// 遍历集合，设置查询对象hql里面？的值
		for (int i = 0; i < paramList.size(); i++) {
			queryCount.setParameter(i, paramList.get(i));
			queryData.setParameter(i, paramList.get(i));
		}

		// 返回查询结果唯一的值(单行单列)
		Long countLong =  (Long) queryCount.uniqueResult();
		
		// 如果总条数为0则不继续查询（带有limit分页的查询）
		if (countLong.intValue() == 0) {
			// 返回空的pageList
			return new PageList();
		}

		// 根据query对象和总条数countLong创建pageList对象
		PageList pageList = new PageList(baseQuery.getPageSize(), baseQuery.getCurrentPage(),countLong.intValue());
		
		// 计算查询的起始位置，就是sql里面limit的第一个参数
		int begin = (pageList.getCurrentPage() - 1) * pageList.getPageSize();
		
		// 每页显示的条数
		int num = pageList.getPageSize();
		
		// 告诉hibernate从哪个位置取数据,索引从0开始
		queryData.setFirstResult(begin);
		
		// 告诉hibernate取多少条数据
		queryData.setMaxResults(num);
		
		// 返回查询结果
		List data = queryData.list();

		// 设置pageList当前页的数据
		pageList.setData(data);

		// 返回pageList
		return pageList;
	}

	/**
	 * 查询通过hql和参数对象，返回一个集合
	 */
	@Override
	public List findByHql(String hql, Object... objects) {
		Query query = sessionFactory.getCurrentSession().createFilter(objects, hql);
		return query.list();
		
	}

	/**
	 * 通过hql得到查询缓存的集合 查询缓存的方法，一般用于没有查询条件 第一次查询出来后，会放入查询缓存，下一次就会从查询缓存里面取值
	 */
	public List findCacheByHql(final String hql) {// 没有条件参数的可以使用此方法
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// 查询缓存生效
		query.setCacheable(true);
		return query.list();
	}
}
