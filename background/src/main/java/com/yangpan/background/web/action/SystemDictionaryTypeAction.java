package com.yangpan.background.web.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yangpan.background.query.PageList;
import com.yangpan.background.query.SystemDictionaryTypeQuery;
import com.yangpan.background.service.ISystemDictionaryTypeService;
import com.yangpan.core.domain.SystemDictionaryType;

/**
 *	SystemDictionaryTypeAction 
 */
@Controller//配置@Controller表明这是控制器层，action都这样配置
@Scope("prototype")//配置@Scope("prototype")表明这是个多例
public class SystemDictionaryTypeAction extends CRUDAction<SystemDictionaryType> {
	
	private static final long serialVersionUID = -317722766741341563L;

	//定义service
	@Autowired
	private ISystemDictionaryTypeService systemDictionaryTypeService;
	
	//创建查询对象
	private SystemDictionaryTypeQuery baseQuery = new SystemDictionaryTypeQuery();
	
	//修改和编辑传入和回显
	private SystemDictionaryType systemDictionaryType;
	
	//显示到前台的pageList
	private PageList pageList;
	
	/**
	 * 显示列表和查询分页
	 * 父类会回调这个方法，并在execute()内调用这个方法
	 */
	@Override
	protected void list() throws Exception {
		pageList = systemDictionaryTypeService.findByQuery(baseQuery);
	}
	
	/**
	 * 在input方法之前执行
	 * 根据是否有id决定是否需要回显
	 */
	@Override
	public void prepareInput() throws Exception {
		if (id != null) {
			systemDictionaryType = systemDictionaryTypeService.get(id);//回显
		}
	}
	
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	/**
	 * 在save方法之前执行
	 * 在保存之前根据id把信息查出来，然后save时页面会提交更改的数据,没有更改的数据由于查出来了，保存时就不会丢失
	 */
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			systemDictionaryType = systemDictionaryTypeService.get(id);//保证属性不丢失关键代码
		} else {
			systemDictionaryType = new SystemDictionaryType();
		}
	}
	
	/**
	 * save方法
	 * 根据id决定添加或修改
	 * 新增调到最后一页的实现方法
	 */
	@Override
	@InputConfig(methodName = INPUT)//保存出现错误后跳转的视图名称input，又回到编辑页面
	public String save() throws Exception {
		//新增把当前页设置为最大值，它就回跳到最后一页！！
		if(id == null){
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		systemDictionaryTypeService.saveOrUpdate(systemDictionaryType);
		return RELOAD;
	}
	
	/**
	 * 验证保存
	 */
	public void validateSave() {
		if(StringUtils.isBlank(systemDictionaryType.getName())){
			addFieldError("name", "名称不能为空");
		}
	}
	
	/**
	 * 	常规后台删除
	 * 	----------------------------------------
	 * 	@Override
	 *	public String delete() throws Exception {
	 *		if(id != null){
	 *			systemDictionaryTypeService.delete(id);
	 *		}
	 *		return RELOAD;
	 *	}
	 */
	
	/**
	 * 高级ajax删除
	 * 能减少请求次数，用js控制删除后的请求
	 * 有利有弊，根据相应情况选择，可能出现分页信息错乱，不象常规删除
	 */
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		/**
		 * 设置编码小技巧！
		 * 如果setContentType("text/html; charset=UTF-8");那么在前台必须指定json，如$get(url,fn,'json')
		 * 如果setContentType("text/json; charset=UTF-8");那么在前台不必指定json，如$get(url,fn)
		 */
		response.setContentType("text/json; charset=UTF-8");//在前台不必指定json，如$get(url,fn)
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				systemDictionaryTypeService.delete(id);
				out.print("{\"success\":true,\"msg\":\"记录删除成功\"}");
			} else {
				out.print("{\"success\":false,\"msg\":\"此记录缺少id不能删除\"}");
			}
		} catch (Exception e) {
			out.print("{\"success\":false,\"msg\":\"出现异常：" + e.getMessage() + "\"}");
		}
		return NONE;
	}
	
	
	public SystemDictionaryTypeQuery getBaseQuery() {
		return baseQuery;
	}

	public SystemDictionaryType getSystemDictionaryType() {
		return systemDictionaryType;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setSystemDictionaryTypeService(ISystemDictionaryTypeService systemDictionaryTypeService) {
		this.systemDictionaryTypeService = systemDictionaryTypeService;
	}
	
	
	/**
	 * ajax修改名称
	 */
	public String changeName() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		if (pk != null && value != null) {
			SystemDictionaryType tempSystemDictionaryType = systemDictionaryTypeService.get(pk);
			String tempName = tempSystemDictionaryType.getName();
			if (tempName != null && tempName.equals(value)) {
				//out.print("{\"msg\":true}");
			} else {
				tempSystemDictionaryType.setName(value);
				systemDictionaryTypeService.saveOrUpdate(tempSystemDictionaryType);
			}
		}
		return NONE;
	}
	
	//X-editable的需要注入
	private String value;
	
	private Long pk;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}
	
/************ 导出xlsx文件开始*******************************************************************/
	
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	//设置下载文件名称
	public String getDownloadFileName() throws UnsupportedEncodingException {
		return new String("systemDictionaryType.xlsx".getBytes("GBK"), "ISO8859-1");
	}
	
	/**
	 *	导出xlsx文件 
	 */
	public String download() throws Exception {
		//导出一般是导出全部文件，但是当前有分页，我们把pageSize设置成最大值，变成一页，这样就能导出全部数据
		baseQuery.setPageSize(Integer.MAX_VALUE);
		//用户想导出的文件可能有一些查询条件，我们要根据baseQuery查询出这些数据
		this.pageList = systemDictionaryTypeService.findByQuery(baseQuery);
		//定义xlsx文件头
		String[] heads = { "名称" };
		//定义xlsx文件的所有行，用集合装
		List<String[]> list = new ArrayList<String[]>();
		//得到查询的数据
		List<SystemDictionaryType> data = pageList.getData();
		//遍历并把数据封装
		for (SystemDictionaryType systemDictionaryType : data) {
			String[] strings = new String[heads.length];
			strings[0] = systemDictionaryType.getName();
			list.add(strings);
		}
		//使用因为struts2做下载，必须提供一个InputStream的对象
		this.inputStream = systemDictionaryTypeService.download(list, heads);
		return "download";
	}

	/************* 导出xlsx文件结束******************************************************************/

	
	/**
	 * 由于父类实现了ModelDriven（模型驱动），但是父类没有覆写，因为需要返回一个对象，但这个对象不确定
	 * 所以需要子类需要覆写这个方法，他的好处是，把systemDictionaryType进行了压栈，放到值栈的最顶部，然后jsp可以直接使用systemDictionaryType的属性，不必像以前那样systemDictionaryType.id
	 */
	@Override
	public SystemDictionaryType getModel() {
		return systemDictionaryType;
	}
}
