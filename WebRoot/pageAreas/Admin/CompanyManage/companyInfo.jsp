<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ page import="com.zsglj.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Map"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<link rel="Shortcut Icon" href="/ZsGljSystem/icons/home/imgTitle.png" /> 
<script src="<%=basePath%>script/jQuery/jquery.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>script/jQuery/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>script/jQuery/global.js"
	type="text/javascript"></script>
<link href="<%=basePath%>styles/HomePage/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>styles/Admin/News/newsIcon.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>styles/HomePage/public.css"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>styles/jQuery/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>script/InputCheck/InputCheck.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单位信息管理</title>
<style type="text/css">
td.tdLeft{
	width: 20%;
	min-width:200px;
	vertical-align:middle;
	text-align: center;
}
span.subtitle{
	font-size:13px;
	margin-left: 5px;
}
</style>
</head>

<body style="background-color: #FFF">
	<div class="mainBox"
		style="height: auto ! important; min-height: 0px; padding-bottom: 0px;padding-left: 0px;width: auto ! important;min-width: 1070px;">
		<h3>单位列表</h3>
	</div>
	<div name="queryBar" style="width: auto ! important;min-width: 1070px; margin-top: 30px;margin-left:auto;margin-right:auto;margin-right: 22px;">
	
<!-- 类别+下拉框 -->
		<span style="font-size:13px;"><strong>类别:</strong> </span>&nbsp;
		<select id="queryCompanyTypeName" style="width:10%;min-width: 75px;font-size:13px;">
			<option value=0>全部用户</option>
			<option value=1>管养单位</option>
			<option value=2>非管养单位</option>
		</select> 
		
<!-- 状态+下拉框 -->
		<span class="subtitle"><strong>状态：</strong></span>&nbsp;
		<select id="queryStatus" style="width:9%;min-width: 75px;font-size:13px;">
			<option value=3>全部</option>
			<option value=1>存在</option>
			<option value=0>被删除</option>
		</select> 
								
													
<!-- 名称+Input框 -->
		<span class="subtitle"><strong>名称：</strong> </span>
		<input type="text" style=" width:11% ;min-width: 140px; font-size:13px;" id="queryCompanyName"> 
		
<!-- 查询按钮 -->
		<input type="image" id="query"  src="/ZsGljSystem/icons/Admin/News/query.png" onclick="query()" style=" width:62px;height:30px;margin-left: 0px;padding-bottom: 10px;"/> 
		
<!-- 添加按钮 -->
		<input type="<%=request.getAttribute("addBtnType")%>" id="add" src="/ZsGljSystem/icons/Admin/News/add.png" 
					style="  width:62px;height:30px; float:right;margin-left:5px;padding-bottom: 10px;" onclick="location.href='/ZsGljSystem/actionsAdminCompany/putupdateCom.action'" />
	</div>
	
	<div style="margin-top: 20px;margin-right: 22px;">
		<table id="dg" class="easyui-datagrid"
			style="width: auto ! important;min-width: 1070px; height: 200px;min-height:372px; margin-top: -200px;padding-right: 0px;"
			url="/ZsGljSystem/actionsAdminCompany/companylistData" rownumbers="false"
			fitColumns="true" singleSelect="true" pagination="true" method="post"
			pagination="true" collapsible="true">
			<thead>
				<tr>
					<!-- th field="companyID" style="width:10%;">单位ID</th> -->
					<th field="companyName" style="width:15%;">单位名称</th>
					<th field="comType" style="width:10%;" formatter = "formatCompanyType">单位类别</th>
					<th field="status" style="width:10%;" formatter="formatUserStatus">状态</th>
					<th field="comLocation" style="width:15%;">单位地址</th>
					<th field="comIntroduction" style="width:20%;">单位简介</th>
					<th field="edit"style="width:30%;" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>

	</div>
	<script language="JavaScript">
	
	function formatUserStatus(value, rowData, rowIndex) {
		var result;
		if (value == null) {
			return null;
		} else if (value == "0") {
			result = "被删除";
		} else if (value == "1") {
			result = "存在";
		}
		return result;
	}
	
	function formatCompanyType(value, rowData, rowIndex) {
		var result;
		if (value == null) {
			return null;
		} else if (value == "1") {
			result = "管养单位";
		} else if (value == "2") {
			result = "非管养单位";
		}
		return result;
	}
	//添加操作按钮列
	function formatOper(val, row, index) {
		var usertype = '<%=session.getAttribute("UserType")%>';
		var checkstatus=row.status;
		if(usertype == 1)
		{
			if(checkstatus != 0){
				return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Admin/News/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" onclick="showCompany('
				+ index
				+ ')" />'
				+ '<input type="image" style="margin-left:3px;" id="update" src="/ZsGljSystem/icons/Admin/News/update.png" onmousedown="updateMouseDown(this)" onmouseover="updateMouseOver(this)" onmouseout="updateMouseOut(this)" onclick="updateCompany('
				+ index
				+ ')"/>'
				+ '<input type="image" style="margin-left:3px;" id="delete" src="/ZsGljSystem/icons/Admin/News/delete.png" onmousedown="deleteMouseDown(this)" onmouseover="deleteMouseOver(this)" onmouseout="deleteMouseOut(this)" onclick="deleteCompany('
				+ index
				+ ')"/>';
			}
			else{
				return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Admin/News/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" onclick="showCompany('
				+ index
				+ ')" />'
				+ '<input type="image" style="margin-left:3px;" id="update" src="/ZsGljSystem/icons/Admin/News/update.png" onmousedown="updateMouseDown(this)" onmouseover="updateMouseOver(this)" onmouseout="updateMouseOut(this)" onclick="updateCompany('
				+ index
				+ ')"/>';
			}
		}
		
	}
		
	//按钮点击时图片变化
	function viewMouseOver(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/viewHover.png");
	}
	function viewMouseDown(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/viewPressed.png");
	}
	function viewMouseOut(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/view.png");
	}
	function updateMouseDown(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/updatePressed.png");
	}
	function updateMouseOver(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/updateHover.png");
	}
	function updateMouseOut(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/update.png");
	}
	function deleteMouseDown(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/deletePressed.png");
	}
	function deleteMouseOver(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/deleteHover.png");
	}
	function deleteMouseOut(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/delete.png");
	}
	function checkMouseDown(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/checkPressed.png");
	}
	function checkMouseOver(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/checkHover.png");
	}
	function checkMouseOut(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/check.png");
	}
	function cancelCheckMouseDown(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/cancelCheckPressed.png");
	}
	function cancelCheckMouseOver(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/cancelCheckHover.png");
	}
	function cancelCheckMouseOut(obj) {
		$(obj).attr("src", "/ZsGljSystem/icons/Admin/News/cancelCheck.png");
	}

	function showUse(index) {
		alert(index+"!");
	}
	
	//查看单位
	function showCompany(index,obj) {
		$('#dg').datagrid('selectRow', index);
		var row = $('#dg').datagrid('getSelected');
		var companyId = row.companyID;
		var status = row.status;
		location.href="/ZsGljSystem/actionsAdminCompany/showCompany.action?companyId="+companyId;
	}
	
	//更新单位
	function updateCompany(index,obj) {
		$('#dg').datagrid('selectRow', index);
		var row = $('#dg').datagrid('getSelected');
		var companyId = row.companyID;
		var status = row.status;
		location.href="/ZsGljSystem/actionsAdminCompany/putupdateCom.action?companyId="+companyId;
	}
	
	//删除单位
	function deleteCompany(index,obj) {
		$('#dg').datagrid('selectRow', index);
		var row = $('#dg').datagrid('getSelected');
		var string = row.companyName;
		string = encodeURI(encodeURI(string));
		if (row) {
			$.messager.defaults = { ok: "是", cancel: "否" };
			$.messager.confirm('Confirm', '确定删除单位' + row.companyName + '?', function(r) {
				if (r) {
					var url = "/ZsGljSystem/actionsAdminCompany/deleteCompany.action?" + "companyName=" + string;
					$.ajax({
						type : "post",
						url : url,
						cache : false,
						success : function(data, dataStatus) {
							if (dataStatus) {
								$('#dg').datagrid('reload'); 
							}
						}
					});
				}
			});
		}
	}
	//按照查询条件进行查询
	function query(){
		var companyTypevalue = $("#queryCompanyTypeName option:selected").val();
		var status =  $('#queryStatus').val();
		var queryCompanyName = $('#queryCompanyName').val();
		//防止乱码
		queryCompanyName = encodeURI(queryCompanyName);
		var url = "/ZsGljSystem/actionsAdminCompany/querycompanylist.action?" +"&queryStatus="+status+"&companyTypevalue="+companyTypevalue+"&queryCompanyName="+queryCompanyName;
			$.ajax({
				type : "post",
				url : url,
				cache : false,
				success : function(data, dataStatus) {
					if (dataStatus) {
						$('#dg').datagrid('reload'); 
					}
				}
			});
	}
	
</script>
</body>

</html>
