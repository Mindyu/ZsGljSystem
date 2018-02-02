<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.zsglj.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Map"%> 
<%@ page import ="java.util.ArrayList" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<link rel="Shortcut Icon" href="/ZsGljSystem/icons/Home/imgTitle.png" /> 
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
		<h3>应急事件信息管理</h3>
	</div>
	<div>
		<table class ="table table-striped table-bordered" style="width:99%;min-width: 1070px;margin-right: 22px;margin-top: 35px;margin-top: 10px;">
			
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>事件日期：</strong></span></td>
				<td><span><%=request.getAttribute("eventDate") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>发生时间：</strong></span></td>
				<td><span><%=request.getAttribute("eventTime") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>事件类型：</strong></span></td>
				<td><span><%=request.getAttribute("typeID") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片&nbsp;：</strong></span></td>
				<td><img src="<%=request.getAttribute("picture")%>" id="pic_view"></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>路段位置：</strong></span></td>
				<td><span><%=request.getAttribute("roadLocation") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>事件情况：</strong></span></td>
				<td><span><%=request.getAttribute("eventCase") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>交通状况：</strong></span></td>
				<td><span><%=request.getAttribute("trafficCase") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>所属镇区：</strong></span></td>
				<td><span><%=request.getAttribute("townID") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>完成处置时间：</strong></span></td>
				<td><span><%=request.getAttribute("finishTime") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>处置后情况：</strong></span></td>
				<td><span><%=request.getAttribute("finishcase") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>出动人员:</strong></span></td>
				<td><span><%=request.getAttribute("peopleNum") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>出动车辆:</strong></span></td>
				<td><span><%=request.getAttribute("carNum") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>上报时间：</strong></span></td>
				<td><span><%=request.getAttribute("reportedTime") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>上&nbsp;&nbsp;报&nbsp;&nbsp;人：</strong></span></td>
				<td><span><%=request.getAttribute("userID") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>值班人数：</strong></span></td>
				<td><span><%=request.getAttribute("dutyNum") %></span></td>
			</tr>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;：</strong></span></td>
				<td style="width:597px;"><span><%=request.getAttribute("remark") %></span></td>
			</tr>
			<% 
				String status = request.getAttribute("status").toString();
				if("3".equals(status)||"4".equals(status)){
			%>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>申请撤回原因：</strong></span></td>
				<td style="width:597px;"><span><%=request.getAttribute("revokeReason") %></span></td>
			</tr>
			<%
				}else if("5".equals(status)){
			%>
			<tr>
				<td class="tdLeft"><span class="subtitle"><strong>打回原因：</strong></span></td>
				<td style="width:597px;"><span><%=request.getAttribute("returnReason") %></span></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>