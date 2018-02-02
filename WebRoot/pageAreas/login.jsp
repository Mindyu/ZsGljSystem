<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>中山公路局应急事件信息管理</title>
	<link rel="stylesheet" href="<%=basePath%>styles/reset.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>styles/style11.css" type="text/css">
</head>

<body style="background-color: #e3e3e3;">
	<div id="contain">
	    <img id="i1" src="<%=basePath%>icons/Login/img14.png">
	    
	    <div id="d1">
	        <h1>应急事件信息管理登录页</h1>
	        <form action="<%=basePath%>actionLogin/login" method="post">
	            <div class="d11">
	                <label class="l1" for="UserName">登录名&nbsp;:</label>
	                <input class="l2" type="text" name="UserName" id="UserName" />
	            </div>
	            <div class="d12">
	                <label class="l1" for="Password">密&nbsp;&nbsp;&nbsp;码&nbsp;:</label>
	                <input class="l2" type="password" name="Password" id="Password" />
	            </div>
	            <div class="d13">
	                <input class="l3" type="submit" value="确&nbsp;&nbsp;定">
	            </div>
	            <div class="d14">
	                <a href="<%=basePath%>pageAreas/login.jsp"><input class="l3" type="button" value="取&nbsp;&nbsp;消"></a>
	            </div>
	        </form>
	    </div>
	</div>
</body>
</html>