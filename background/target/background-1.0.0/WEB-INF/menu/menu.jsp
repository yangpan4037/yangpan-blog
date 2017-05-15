<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
</head>
<body>
	<a href="<s:url action="bar" namespace="/foo"/>/employee_input">添加员工</a>
	<table border="1" cellpadding="15" cellspacing="0">
		<tr>
			<th>id</th>
			<th>用户名</th>
			<th>部门</th>
			<th>操作</th>
		</tr>
		<s:iterator value="#emps">
			<tr>
				<td>
					<s:property value="id"/>
				</td>
				<td>
					<s:property value="name"/>
				</td>
				<td>
					<s:property value="dept.name"/>
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/employee_input?employee.id=${id }">编辑</a>
					<a href="${pageContext.request.contextPath}/employee_delete?employee.id=${id }">删除</a>
				</td>
			</tr>
		</s:iterator>
	</table>

</body>
</html>