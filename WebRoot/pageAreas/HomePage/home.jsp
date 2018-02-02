<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<title>Tomato 西红柿科技</title>
<link href="<%=basePath%>styles/HomePage/public.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="<%=basePath%>script/jQuery/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>script/jQuery/global.js"></script>
<link rel="Shortcut Icon" href="<%=basePath%>icons/home/imgTitle.png" />
</head>
<body style="overflow:hidden;">
	<header> 
	<img class="i3" href="<%=basePath%>pageAreas/HomePage/home.jsp"
		src="<%=basePath%>icons/Home/img1.png">
	<h2>
		<span style="color: #fff;font-size: 15px;font-family: Microsoft YaHei, Arial, Helvetica, sans-serif;">您好，<%=request.getAttribute("UserName")%></span>
	</h2>
	<img class="i4" float="right" src="<%=basePath%>icons/Home/img3.png">
	<h3>
		<a href="<%=basePath%>actionLogin/logout.action">注销</a>
	</h3>
	</header>

	<!-- dcHead 结束 -->
	<div id="dcLeft">
		<div id="menu">
			<ul class="top">
			</ul>
			<ul>
				<li id="liHomePage" class="cur"
					onclick="setIframe('liHomePage','<%=basePath%>pageAreas/HomePage/homePage.jsp')"><a>
						<i class="home"></i><em>个人主页</em></a></li>
			</ul>
			<ul>
				<li id="liEventReport"
					onclick="setIframe('liEventReport','<%=basePath%>actionEvent/putupdateEvent')"><a><i
						class="show"></i><em>事件上报</em></a></li>
			</ul>
			<ul>
				<li id="liEventManage"
					onclick="setIframe('liEventManage','<%=basePath%>actionEvent/eventInfo')"><a><i
						class="show"></i><em>应急事件管理</em></a></li>
			</ul>
			<ul>
				<li id="liEventCollect"
					onclick="setIframe('liEventCollect','<%=basePath%>pageAreas/EventManage/eventCollect.jsp')"><a><i
						class="show"></i><em>统计汇总</em></a></li>
			</ul>
			<ul class="bot">
				<li id="liAbout"
					onclick="setIframe('liAbout','<%=basePath%>pageAreas/HomePage/homePage.jsp')"><a><i
						class="theme"></i><em>关于我们</em></a></li>
			</ul>
		</div>
	</div>
	<div id="dcMain" style="padding-bottom: 0px; ">
		<div class="mainBox"
			style="height: auto ! important; min-height: 500px; padding-top: 0px; padding-right: 0px; border-top-width: 30px; margin-top: 0px;padding-bottom: 0px;">
			<Iframe id="iframeAdmin"
				src="<%=basePath%>pageAreas/HomePage/homePage.jsp" width="100%"
				height="80%" style="margin-bottom: 0px;" scrolling="yes"
				frameborder="0"></iframe>
		</div>

	</div>
	<div class="clear"></div>
	<div id="dcFooter">
		<div id="footer">
			<div class="line"></div>
			<ul><li>版权所有 © 2017-2018 中山市公路局，并保留所有权利。</li></ul>
		</div>
	</div>
	<!-- dcFooter 结束 -->
	<div class="clear"></div>
	

	<script language="javascript">
		//设置页面跳转的方法
		function setIframe(liId, iframeSrc) {
			//先初始化所有空间的class属性
			$("#liHomePage").removeClass("cur");
			$("#liEventReport").removeClass("cur");
			$("#liEventManage").removeClass("cur");
			$("#liEventCollect").removeClass("cur");
			$("#liAbout").removeClass("cur");
			//设置跳转页面为iframeSrc,并设置对应的class属性为cur
			$("#iframeAdmin").attr("src", iframeSrc);
			$("#" + liId).addClass("cur");
		}
		//退出系统
		function quit() {
			var url = "/ZsGljSystem/login/systemQuit.action";
			$.ajax({
				type : "post",
				url : url,
				cache : false,
				success : function(data, dataStatus) {
					if (dataStatus) {
						self.opener = null;
						self.close();
					}
				}
			});
		}
	</script>
</body>
</html>
