<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
			<td class="tdLeft"><span class="subtitle"><strong>单位名称：</strong></span></td>
			<td><span><%=request.getAttribute("companyName") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>单&nbsp;&nbsp;位&nbsp;&nbsp;ID：</strong></span></td>
			<td><span><%=request.getAttribute("companyId") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>单位类型：</strong></span></td>
			<td><span><%=request.getAttribute("comType") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>单位简介：</strong></span></td>
			<td><span><%=request.getAttribute("comIntroduction") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>单位地址：</strong></span></td>
			<td><span><%=request.getAttribute("companyLoc") %></span></td>
		</tr>
		<tr>
			<td class="tdLeft"><span class="subtitle"><strong>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp;：</strong></span></td>
			<td><span><%=request.getAttribute("status") %></span></td>
		</tr>
	</table>
	</div>
</body>
</html>
