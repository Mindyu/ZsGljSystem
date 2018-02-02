<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="com.zsglj.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Map"%> 
<%@ page import ="java.util.ArrayList" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<base href="<%=basePath%>">
<link rel="Shortcut Icon" href="/ZsGljSystem/icons/home/imgTitle.png" /> 
<link href="<%=basePath%>styles/HomePage/public.css"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>styles/jQuery/bootstrap.min.css"
	rel="stylesheet" type="text/css">	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
td.tdLeft{
	width: 20%;
	min-width:200px;
	vertical-align:middle;
	text-align: center;
}
span.subtitle{
	font-size:16px;
}
</style>	
</head>
<body style="background-color: #FFF">
	<div class="mainBox" style="height: auto ! important; min-height: 0px; padding-bottom: 0px;padding-left: 0px;width: auto ! important;min-width: 1070px;">
		<h3>用户信息</h3>
	</div>
	<div>
	<table class ="table table-striped table-bordered" style="width:99%;min-width: 1070px;margin-right: 22px;margin-top: 35px;margin-top: 10px;">
		
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>用户名称：</strong></span></td>
			<td><span><%=request.getAttribute("userName") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>用&nbsp;&nbsp;户&nbsp;&nbsp;ID：</strong></span></td>
			<td><span><%=request.getAttribute("userId") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>用户类型：</strong></span></td>
			<td><span><%=request.getAttribute("userType") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>用户简介：</strong></span></td>
			<td><span><%=request.getAttribute("userIntroduction") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;：</strong></span></td>
			<td><span><%=request.getAttribute("password") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>所属单位：</strong></span></td>
			<td><span><%=request.getAttribute("company") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>联系电话：</strong></span></td>
			<td><span><%=request.getAttribute("telephone") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp;：</strong></span></td>
			<td><span><%=request.getAttribute("status") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;：</strong></span></td>
			<td style="width:597px;"><span><%=request.getAttribute("remark") %></span></td>
		</tr>
	</table>
	</div>
</body>
</html>
