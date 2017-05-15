package com.yangpan.background.web.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 *	具有公共的CRUD,高级查询分页
 *	有这些需求的类就必须继承这个类
 */
public abstract class CRUDAction<T> extends BaseAction implements ModelDriven<T>,Preparable {
	
	private static final long serialVersionUID = 4210297912251623305L;
	
	/**
	 * 一般具有crud的Action，都需要id进行回显，持久化也需要id
	 * 因此在公共的CRUD类里面定义一个id是必要的，也是明智的
	 */
	protected Long id;
		
	/**
	 * 默认的执行方法，调用子类的查询和分页列表
	 * 这样子类覆写list方法而不需要覆写execute方法，就能实现默认访问页面呈现列表的效果
	 */
	@Override
	public String execute() throws Exception {
		list();
		return SUCCESS;
	}
	
	/**
	 * 列表 
	 * 需要子类覆写的显示列表页面的回调方法
	 */
	protected abstract void list() throws Exception;
	
	/**
	 * 需要CRUD的Action一般都存在回显效果，由父类规定好方法名，子类覆写即可
	 * （子类实现原理就是是在input之前根据id把信息查出来）
	 */
	public abstract void prepareInput() throws Exception;
	
	/**
	 *	需要CRUD的Action一般保存都会丢失属性，这个可以在保存前处理一下，由父类规定好方法名，子类覆写即可
	 * （子类实现原理就是是在保存之前根据id把信息查出来，然后save时页面会提交更改的数据,没有更改的数据由于查出来了，保存时就不会丢失）
	 */
	public abstract void prepareSave() throws Exception;
	
	/**
	 * 需要CRUD的Action都需要保存，由父类规定好方法名，子类覆写即可
	 */
	public abstract String save() throws Exception;
	
	/**
	 * 需要CRUD的Action都需要删除，由父类规定好方法名，子类覆写即可
	 */
	public abstract String delete() throws Exception;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**************华丽的分割线*****************************************************************************/
	
	/**
	 * 实现了Preparable接口必须覆写的方法，其实不必做什么事情
	 * 作用：它会在所有strtus方法之前自动执行：拦截器的顺序问题
	 */
	@Override
	public void prepare() throws Exception {
	}
}
