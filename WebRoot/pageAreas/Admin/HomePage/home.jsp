<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<link href="<%=basePath%>styles/HomePage/public.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="<%=basePath%>script/jQuery/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>script/jQuery/global.js"></script>
<link rel="Shortcut Icon" href="/ZsGljSystem/icons/home/imgTitle.png" /> 
  </head>
  
 <body style="overflow:hidden;">
  	<header>
            <h2><span  style="color: #fff;font-size: 15px;font-family: Microsoft YaHei, Arial, Helvetica, sans-serif;">欢迎使用，<%=request.getAttribute("UserName") %></span></h2>
            <img class="i4" float="right" src="/ZsGljSystem/icons/Admin/HomePage/img3.png">
            <h3><a href="<%=basePath%>actionLogin/logout.action">注销</a></h3>
     </header>
    
		<div id="dcLeft" >
			<div id="menu">
				<ul class="top"></ul>
				<ul>
					<li id="liHomePage" class="cur"
						 onclick="setIframe('liHomePage','<%=basePath%>pageAreas/Admin/HomePage/homePage.jsp')">
						 <a><i class="home"></i><em>主页</em></a>
					</li>
					<%-- <li id="liHomeImage"
						onclick="setIframe('liHomeImage','<%=basePath%>tomatoAreas/Admin/HomePage/homePage.jsp')">
						<a><i class="article"></i><em>主页图片设置</em></a></li> --%>
					<li id="liUsers"
						onclick="setIframe('liUsers','<%=basePath%>actionsUser/userInfo.action')">
						<a><i class="nav"></i><em>用户信息表</em></a>
					</li>	
					<li id="liCompany" 
						onclick="setIframe('liCompany','<%=basePath%>actionsAdminCompany/companyInfo.action')">
						<a><i class="page"></i><em>单位表</em></a>
					</li>
				</ul>
				
				<ul class="bot">
					<li id="liEvents" >
						 <a><i class="show"></i><em>*--占位--*</em></a>
					</li>
					<li id="liEventType" >
						<a><i class="show"></i><em>事件类型表</em></a>
					</li>
					<li id="liTown" >
						<a><i class="show"></i><em>中山市镇区表</em></a>
					</li>
				</ul>
			</div>
			
		</div>
		<div id="dcMain" style="padding-bottom: 0px; ">
			<div class="mainBox"
					style="height: auto ! important; min-height: 500px; padding-top: 0px; padding-right: 0px; border-top-width: 30px; margin-top: 0px;padding-bottom: 0px;">
					<Iframe id="iframeAdmin" src="<%=basePath%>pageAreas/Admin/HomePage/homePage.jsp" width="100%" height="80%" style="margin-bottom: 0px;"
						scrolling="yes" frameborder="0"></iframe>
			</div> 
		</div>
		
		<div class="clear"></div>
		<div id="dcFooter">
			<div id="footer">
				<div class="line"></div>
				<ul>版权所有 © 2016-2017 西红柿科技，并保留所有权利。</ul>
			</div>
		</div>
		<!-- dcFooter 结束 -->
		<div class="clear"></div>
	
	
	<script language="javascript">
	//设置页面跳转的方法
	function setIframe(liId, iframeSrc) {
		//先初始化所有空间的class属性
		$("#liHomePage").removeClass("cur");
		$("#liHomeImage").removeClass("cur");
		$("#liUsers").removeClass("cur");
		$("#liCompany").removeClass("cur");
		//$("#liApplications").removeClass("cur");
		//$("#liCases").removeClass("cur");
		//$("#liNews").removeClass("cur");
		//$("#liStandard").removeClass("cur");
		//$("#liProblems").removeClass("cur");
		//$("#liCustomer").removeClass("cur");
		//$("#liAbout").removeClass("cur");
		//设置跳转页面为iframeSrc,并设置对应的class属性为cur
		$("#iframeAdmin").attr("src", iframeSrc);
		$("#" + liId).addClass("cur");
	}
	//退出系统
	function quit(){
		var url = "/ZsGljSystem/login/systemQuit.action";
		$.ajax({
			type : "post",
			url : url,
			cache : false,
			success : function(data, dataStatus) {
				if (dataStatus) {
					self.opener=null;
					self.close();
				}
			}
		});
	}
</script>
  </body>
</html>
