package com.yangpan.background.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yangpan.background.query.PageList;
import com.yangpan.background.query.UserQuery;
import com.yangpan.background.service.IRoleService;
import com.yangpan.background.service.IUserService;
import com.yangpan.core.domain.Role;
import com.yangpan.core.domain.User;

import net.coobird.thumbnailator.Thumbnails;

/**
 *	用户Action 
 */
@Controller//配置@Controller表明这是控制器层，action都这样配置
@Scope("prototype")//配置@Scope("prototype")表明这是个多例
public class UserAction extends CRUDAction<User> {
	
	private static final long serialVersionUID = -7659045474945359137L;
	
	//定义service
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	
	//创建查询对象
	private UserQuery baseQuery = new UserQuery();
	
	//修改和编辑传入和回显
	private User user;
	
	//显示到前台的pageList
	private PageList pageList;
	
	//角色id数组
	private Long[] ids;
	
	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	public UserQuery getBaseQuery() {
		return baseQuery;
	}

	public User getUser() {
		return user;
	}

	public PageList getPageList() {
		return pageList;
	}

	/**
	 * 显示列表和查询分页
	 * 父类会回调这个方法，并在execute()内调用这个方法
	 */
	@Override
	protected void list() throws Exception {
		pageList = userService.findByQuery(baseQuery);
//		putContent("departments", departmentService.getAll());
	}
	
	/**
	 * 在input方法之前执行
	 * 根据是否有id决定是否需要回显
	 */
	@Override
	public void prepareInput() throws Exception {
		if (id != null) {
			user = userService.get(id);//回显
			//得到当前员工的角色列表
			Set<Role> roles = user.getRoles();
			//将当前员工的角色列表的id放到ids数组里，也就是中间表
			ids = new Long[roles.size()];
			int index = 0;
			for (Role role : roles) {
				ids[index++] = role.getId();
			}
		}
	}
	
	@Override
	public String input() throws Exception {
		//部门回显
//		putContent("departments", departmentService.getAll());
		//角色列表(编辑页面需要)
		putContent("allRoles", roleService.getAll());
		return INPUT;
	}
	
	/**
	 * 在save方法之前执行
	 * 在保存之前根据id把信息查出来，然后save时页面会提交更改的数据,没有更改的数据由于查出来了，保存时就不会丢失
	 */
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			user = userService.get(id);//保证属性不丢失关键代码
//			user.setDepartment(null);
			user.getRoles().clear();//清除当前员工的角色列表，否则save()时会追加选择的权限
		} else {
			user = new User();
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
		
		//如果有上传文件就处理图片
		if (upload != null) {
			String webapp = ServletActionContext.getServletContext().getRealPath("/");
			
			//修改怎样处理原图:直接删除,月底在一次性删除
			if (id != null && StringUtils.isNotBlank(user.getHeadImage())) {
				File deleteFile = new File(webapp, user.getHeadImage());
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				File deleteSmallFile = new File(webapp,user.getHeadImage());
				if (deleteSmallFile.exists()) {
					deleteSmallFile.delete();
				}
			}

			// 上传文件命名,拷贝到webapp的位置
			Date date = new Date();
			String fileName = "upload/" + sdf.format(date) + ".png";
			File destFile = new File(webapp, fileName);
			File parentFile = destFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();//自动生成upload目录
			}
			
			/**
			 * 扩展：拷贝目录
			 * FileUtils.copyDirectory(srcDir, destDir);
			 */
			
			// 缩略图
			Thumbnails.of(upload).scale(0.1F).toFile(destFile);
			user.setHeadImage(fileName);
		}
		
		//这表示如果部门为请选择-1，就把部门设置为空
//		if(user.getDepartment() != null && user.getDepartment().getId() == -1L){
//			user.setDepartment(null);
//		}
		//新增把当前页设置为最大值，它就回跳到最后一页！！
		if(id == null){
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		//保存时，根据传入的角色id数组，把角色数组添加到员工，也就是保存中间表
		if(ids != null){
			for (Long id : ids) {
				user.getRoles().add(new Role(id));
			}
		}
		userService.saveOrUpdate(user);
		return RELOAD;
	}
	
	//处理上传文件
	private File upload;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	//上传文件需要的时间
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssS");
	
	
	/**
	 * 验证保存
	 */
	public void validateSave() {
		if(StringUtils.isBlank(user.getUsername())){
			addFieldError("username", "用户名不能为空");
		}
		if(user.getAge() == null){
			addFieldError("age", "年龄不能为空");
		}
	}
	
	/**
	 * 	常规后台删除
	 * 	----------------------------------------
	 * 	@Override
	 *	public String delete() throws Exception {
	 *		if(id != null){
	 *			userService.delete(id);
	 *		}
	 *		return RELOAD;
	 *	}
	 */
	
	/**
	 * 高级ajax删除单个
	 * 能减少请求次数，用js控制删除后的请求
	 * 有利有弊，根据相应情况选择，可能出现分页信息错乱，不象常规删除
	 */
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		/**
		 * 设置编码小技巧！
		 * 如果setContentType("text/html; charset=UTF-8");那么在前台必须指定json，如$.get(url,fn,'json')
		 * 如果setContentType("text/json; charset=UTF-8");那么在前台不必指定json，如$.get(url,fn)
		 */
		response.setContentType("text/json; charset=UTF-8");//在前台不必指定json，如$.get(url,fn)
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				userService.delete(id);
				out.print("{\"success\":true,\"msg\":\"记录删除成功\"}");
			} else {
				out.print("{\"success\":false,\"msg\":\"此记录缺少id不能删除\"}");
			}
		} catch (Exception e) {
			out.print("{\"success\":false,\"msg\":\"出现异常：" + e.getMessage() + "\"}");
		}
		return NONE;
	}
	
	/**
	 * ajax批量删除
	 */
	public String deleteMore() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(empIds.length > 0){
			for (Long eId : empIds) {
				try {
					userService.delete(eId);
				} catch (Exception e) {
					out.print("{\"success\":false,\"msg\":\"出现异常： 有一条记录不能删除，原因可能是这条记录与其他表存在关联\"}");
					return NONE;
				}
			}
			out.print("{\"success\":true,\"msg\":\"记录删除成功\"}");
		}else{
			out.print("{\"success\":false,\"msg\":\"缺少id数组将不能删除\"}");
		}
		return NONE;
	}
	
	//批量删除的id数组
	private Long[] empIds;
	
	public Long[] getEmpIds() {
		return empIds;
	}

	public void setEmpIds(Long[] empIds) {
		this.empIds = empIds;
	}


	/************ 导出xlsx文件开始*******************************************************************/
	
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	//设置下载文件名称
	public String getDownloadFileName() throws UnsupportedEncodingException {
		return new String("员工列表.xlsx".getBytes("GBK"), "ISO8859-1");
	}
	
	/**
	 *	导出xlsx文件 
	 */
	public String download() throws Exception {
		//导出一般是导出全部文件，但是当前有分页，我们把pageSize设置成最大值，变成一页，这样就能导出全部数据
		baseQuery.setPageSize(Integer.MAX_VALUE);
		//用户想导出的文件可能有一些查询条件，我们要根据baseQuery查询出这些数据
		this.pageList = userService.findByQuery(baseQuery);
		//定义xlsx文件头
		String[] heads = { "用户名", "邮箱", "年龄", "部门名称" };
		//定义xlsx文件的所有行，用集合装
		List<String[]> list = new ArrayList<String[]>();
		//得到查询的数据
		List<User> data = pageList.getData();
		//遍历并把数据封装
		for (User user : data) {
			String[] strings = new String[heads.length];
			strings[0] = user.getUsername();
			strings[1] = user.getEmail();
			strings[2] = user.getAge() == null ? "" : user.getAge().toString();
//			Department department = user.getDepartment();
//			strings[3] = department == null ? "" : department.getName();
			list.add(strings);
		}
		//使用因为struts2做下载，必须提供一个InputStream的对象
		this.inputStream = userService.download(list, heads);
		return "download";
	}

	/************* 导出xlsx文件结束******************************************************************/
	
	/**
	 * ajax验证登录用户的密码是否正确
	 */
	public String checkPwd() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		//从session得到旧密码
		User emp = (User)ActionContext.getContext().getSession().get(BaseAction.USER_IN_SESSION);
		if(emp.getPassword().equals(oldPwd)){
			out.print("{\"valid\":true}");
		}else{
			out.print("{\"valid\":\"原密码不正确\"}");
		}
		return NONE;
	}
	
	//旧密码
	private String oldPwd;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	
	
	/**
	 * 修改密码
	 */
	public String changePwd() throws Exception {
		if (StringUtils.isNotBlank(newPwd)) {
			//从session得到user
			User emp = (User)ActionContext.getContext().getSession().get(BaseAction.USER_IN_SESSION);
			emp.setPassword(newPwd);
			userService.saveOrUpdate(emp);
			//清除session
			ActionContext.getContext().getSession().remove(BaseAction.USER_IN_SESSION);
		}
		return LOGIN;
	}
	
	//新密码
	private String newPwd;
	
	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * ajax验证用户名是否重复,需要定义username字段接收前台传来的值
	 */
	public String checkUsername() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		if (id == null) {// 新增后保存的验证
			out.print("{\"valid\":" + userService.findByUsername(username) + "}");
		} else {
			User tempUser = userService.get(id);
			String oldUsername = tempUser.getUsername();
			if (oldUsername.equals(username)) {
				// 如果用户名没有修改直接输出{\"valid\":true}
				out.print("{\"valid\":true}");
			} else {
				out.print("{\"valid\":" + userService.findByUsername(username) + "}");
			}
		}
		return NONE;
	}
	
	/**
	 * ajax修改用户名
	 */
	public String changeUsername() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		if (pk != null && value != null) {
			User tempUser = userService.get(pk);
			String tempUsername = tempUser.getUsername();
			if (tempUsername != null && tempUsername.equals(value)) {
				out.print("{\"msg\":true}");
			} else {
				tempUser.setUsername(value);
				userService.saveOrUpdate(tempUser);
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
	
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/*******************************************************************************/

	/**
	 * 由于父类实现了ModelDriven（模型驱动），但是父类没有覆写，因为需要返回一个对象，但这个对象不确定
	 * 所以需要子类需要覆写这个方法，他的好处是，把user进行了压栈，放到值栈的最顶部，然后jsp可以直接使用user的属性，不必像以前那样user.username
	 */
	@Override
	public User getModel() {
		return user;
	}
}
