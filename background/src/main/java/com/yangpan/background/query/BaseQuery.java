package com.yangpan.background.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 *	公共的查询条件类 ，定义成抽象的，不允许直接使用，只能使用他的子类
 */
@SuppressWarnings("all")
public abstract class BaseQuery {
	
	//当前页
	private int currentPage = 1;
	
	//每页多少条
	private int pageSize = 5;
	
	//查询带条件的总条数的hql，使用builder是为了拼接效率，get方法返回转换成string
	private StringBuilder countHql;
	
	//查询带条件和分页的数据的hql,使用builder是为了拼接效率，get方法返回转换成string
	private StringBuilder limitHql;
	
	//保存两条hql条件里面？的值的集合
	private List paramList;
	
	//查询条件，也就是where后面的内容
	private StringBuilder whereHql;
	
	/**
	 * 	排序的列
	 */
	private String orderByColumn;
	
	/**
	 * 	排序的类型
	 * 	DESC降序，ASC升序
	 */
	private String orderByType;
	
	/**
	 * 	通过子类的传入的类名来拼接两条hql
	 */
	public BaseQuery(String entityClass) {
		countHql = new StringBuilder("select count(o) from "+entityClass+" o");
		limitHql = new StringBuilder("select o from "+entityClass+" o");
		paramList = new ArrayList();
		whereHql = new StringBuilder();
	}
	
	/**
	 * 定义一个抽象方法，子类覆写,子类用来调用父类方法拼接查询条件where语句的hql
	 */
	protected abstract void callBackGetWhere();
	
	/**
	 * 拼接hql的where语句
	 */
	protected void setWhereHql(String whereAfter , Object... objects){
		//根据参数集合的size判断该拼接where还是and
		if(paramList.size() == 0){
			countHql.append(" where ").append(whereAfter);
			limitHql.append(" where ").append(whereAfter);
		}else{
			countHql.append(" and ").append(whereAfter);
			limitHql.append(" and ").append(whereAfter);
		}
		//把传进的条件的值objects放进paramList
		paramList.addAll(Arrays.asList(objects));
	}
	
	//定义回调得到where语句只调用一次，防止重复拼接
	private boolean flag = false;
	
	private void builderWhere(){
		if(!flag){
			callBackGetWhere();
			//添加排序
			if(StringUtils.isNotBlank(orderByColumn)){
				limitHql.append(" ORDER BY "+orderByColumn+" "+orderByType);
			}
			flag = true;
		}
	}
	
	/**
	 * 报表使用PurchaseBillItemQuery
	 * 拼接查询条件
	 */
	protected void addCondition(String condition, Object... objects) {
		if(paramList.size() == 0) {//拼接where
			countHql.append(" where ").append(condition);
			limitHql.append(" where ").append(condition);
			whereHql.append(" where ").append(condition);
		} else {//拼接and
			countHql.append(" and ").append(condition);
			limitHql.append(" and ").append(condition);
			whereHql.append(" and ").append(condition);
		}
		//把数组变成List对象，在使用addAll的方法
		paramList.addAll(Arrays.asList(objects));
	}
	
	//查询条件和hql的字段只要get
	public String getCountHql() {
		//调用拼接查询条件
		builderWhere();
		return countHql.toString();
	}

	public String getLimitHql() {
		//调用拼接查询条件
		builderWhere();
		return limitHql.toString();
	}

	public List getParamList() {
		//调用拼接查询条件
		builderWhere();
		return paramList;
	}
	
	/**
	 * 处理报表
	 * 提供给PurchaseBillItemServiceImpl获取
	 */
	public String getWhereHql() {
		//拼接子类查询条件
		builderWhere();
		return whereHql.toString();
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}
	
}
