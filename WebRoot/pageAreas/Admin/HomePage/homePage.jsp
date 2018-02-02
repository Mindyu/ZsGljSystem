<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zsglj.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="java.util.Map"%>
<!-- 新闻列表页面 -->
<html>
<head>
<!-- 以下三个是日历显示的相关布局和js	 -->
<script src="/TomatoWebSys/script/jQuery/jquery-1.12.4.js"></script> 
<script src="/TomatoWebSys/script/jQuery/jquery-ui.js"></script>
<link rel="stylesheet" href="/TomatoWebSys/styles/Admin/News/jquery-ui.css">

<script src="<%=basePath%>script/jQuery/jquery.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>script/jQuery/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>script/jQuery/global.js"
	type="text/javascript"></script>
<link href="<%=basePath%>styles/Admin/HomePage/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>styles/HomePage/public.css"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>styles/jQuery/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台主页</title>
<link rel="Shortcut Icon" href="/TomatoWebSys/icons/home/imgTitle.png" /> 
</head>
<body style="background-color: #FFF;" >
	<div class="mainBox"
		style="height: auto ! important; min-height:0px; padding-bottom: 0px;padding-left: 0px;width: auto ! important;min-width: 1070px;">
		<h3>后台主页</h3>
	</div>

</body>

</html>
