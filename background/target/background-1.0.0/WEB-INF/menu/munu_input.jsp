<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工编辑</title>
</head>
<body>
	<s:form action="/employee_save" method="post">
		<s:hidden name="employee.id"/>
		<s:textfield name="employee.name" label="员工姓名"/>
		<s:select list="#depts" name="employee.dept.id" listValue="name" listKey="id" headerKey="-1" headerValue="--请选择--" label="部门名称" />
		<s:submit value="提交"/>
	</s:form>
</body>
</html>