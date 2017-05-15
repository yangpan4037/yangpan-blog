package com.yangpan.background.web.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yangpan.background.query.MenuQuery;
import com.yangpan.background.query.PageList;
import com.yangpan.background.service.IMenuService;
import com.yangpan.core.domain.Menu;

/**
 *	MenuAction 
 */
@Controller//配置@Controller表明这是控制器层，action都这样配置
@Scope("prototype")//配置@Scope("prototype")表明这是个多例
public class MenuAction extends CRUDAction<Menu> {

	private static final long serialVersionUID = 8126603065265912841L;

	//定义service
	@Autowired
	private IMenuService menuService;
	
	//创建查询对象
	private MenuQuery baseQuery = new MenuQuery();
	
	//修改和编辑传入和回显
	private Menu menu;
	
	//显示到前台的pageList
	private PageList pageList;
	
	/**
	 * 显示列表和查询分页
	 * 父类会回调这个方法，并在execute()内调用这个方法
	 */
	@Override
	protected void list() throws Exception {
		pageList = menuService.findByQuery(baseQuery);
	}
	
	/**
	 * 在input方法之前执行
	 * 根据是否有id决定是否需要回显
	 */
	@Override
	public void prepareInput() throws Exception {
		if (id != null) {
			menu = menuService.get(id);//回显
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
			menu = menuService.get(id);//保证属性不丢失关键代码
		} else {
			menu = new Menu();
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
		menuService.saveOrUpdate(menu);
		return RELOAD;
	}
	
	/**
	 * 验证保存
	 */
	public void validateSave() {
		if(StringUtils.isBlank(menu.getName())){
			addFieldError("name", "名称不能为空");
		}
	}
	
	/**
	 * 	常规后台删除
	 * 	----------------------------------------
	 * 	@Override
	 *	public String delete() throws Exception {
	 *		if(id != null){
	 *			menuService.delete(id);
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
				menuService.delete(id);
				out.print("{\"success\":true,\"msg\":\"记录删除成功\"}");
			} else {
				out.print("{\"success\":false,\"msg\":\"此记录缺少id不能删除\"}");
			}
		} catch (Exception e) {
			out.print("{\"success\":false,\"msg\":\"出现异常：" + e.getMessage() + "\"}");
		}
		return NONE;
	}
	
	
	public MenuQuery getBaseQuery() {
		return baseQuery;
	}

	public Menu getMenu() {
		return menu;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	
	/**
	 * ajax查询菜单（用于搜索快速跳转）
	 */
	public String searchMenu() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map resultMap = new HashMap();
		if(StringUtils.isNotBlank(menuName)){
			List<String[]> menuList = menuService.searchMenuByLike(menuName);
			resultMap.put("success", true);
			resultMap.put("menuList", menuList);
			putContent("map", resultMap);
		}else{
			resultMap.put("success", false);
		}
		return "jsonResult";
	}
	
	//菜单名称
	private String menuName;
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * ajax修改名称
	 */
	public String changeName() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		if (pk != null && value != null) {
			Menu tempMenu = menuService.get(pk);
			String tempName = tempMenu.getName();
			if (tempName != null && tempName.equals(value)) {
				out.print("{\"msg\":true}");
			} else {
				tempMenu.setName(value);
				menuService.saveOrUpdate(tempMenu);
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
		return new String("menu.xlsx".getBytes("GBK"), "ISO8859-1");
	}
	
	/**
	 *	导出xlsx文件 
	 */
	public String download() throws Exception {
		//导出一般是导出全部文件，但是当前有分页，我们把pageSize设置成最大值，变成一页，这样就能导出全部数据
		baseQuery.setPageSize(Integer.MAX_VALUE);
		//用户想导出的文件可能有一些查询条件，我们要根据baseQuery查询出这些数据
		this.pageList = menuService.findByQuery(baseQuery);
		//定义xlsx文件头
		String[] heads = { "名称" };
		//定义xlsx文件的所有行，用集合装
		List<String[]> list = new ArrayList<String[]>();
		//得到查询的数据
		List<Menu> data = pageList.getData();
		//遍历并把数据封装
		for (Menu menu : data) {
			String[] strings = new String[heads.length];
			strings[0] = menu.getName();
			list.add(strings);
		}
		//使用因为struts2做下载，必须提供一个InputStream的对象
		this.inputStream = menuService.download(list, heads);
		return "download";
	}

	/************* 导出xlsx文件结束******************************************************************/

	
	/**
	 * 由于父类实现了ModelDriven（模型驱动），但是父类没有覆写，因为需要返回一个对象，但这个对象不确定
	 * 所以需要子类需要覆写这个方法，他的好处是，把menu进行了压栈，放到值栈的最顶部，然后jsp可以直接使用menu的属性，不必像以前那样menu.id
	 */
	@Override
	public Menu getModel() {
		return menu;
	}
}
