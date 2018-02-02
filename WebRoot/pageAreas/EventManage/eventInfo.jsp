<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.zsglj.dao.EventTypeDao"%>
<%@ page import="com.zsglj.dao.CompanyDao"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
	<!-- 以下三个是日历显示的相关布局和 js -->
	<script src="/ZsGljSystem/script/jQuery/jquery-1.12.4.js"></script>
	<script src="/ZsGljSystem/script/jQuery/jquery-ui.js"></script>
	
	<link rel="stylesheet" href="/ZsGljSystem/styles/jQuery/jquery-ui.css">
	<link rel="Shortcut Icon" href="/ZsGljSystem/icons/Home/imgTitle.png" />
	
	<script src="<%=basePath%>script/jQuery/jquery.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>script/jQuery/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>script/jQuery/global.js" type="text/javascript"></script>
	
	<link href="<%=basePath%>styles/jQuery/easyui.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>styles/HomePage/public.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>styles/jQuery/bootstrap.min.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="<%=basePath%>script/InputCheck/InputCheck.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>应急事件列表</title>
	
	<style type="text/css">
		td.tdLeft {
			width: 20%;
			min-width: 200px;
			vertical-align: middle;
			text-align: center;
		}
		
		span.subtitle {
			font-size: 13px;
			margin-left: 5px;
		}
		
		/* 输入框关闭按钮 */
		#close { 
			margin:8px;
		    width:10px; 
		    height:10px; 
		    cursor:pointer; 
		    position:absolute; 
		    right:5px; 
		    top:5px; 
		    text-indent:-999em;
   		}
   		
   		/* 确定提交打回原因 */
		#confirm {
		    cursor:pointer;
   		}
		
		#mask { 
		    background-color:#000;
		    opacity: .7;
		    filter: alpha(opacity=70); 
		    position:absolute; 
		    left:0;
		    top:0;
		    z-index:1000;
		}
		
		#login { 
	    	position:fixed;
	    	z-index:1001;
	    }
	    
		.loginCon {
		    position:relative; 
		    width:300px;
		    height:400px;
		    background:lightblue;
		    text-align:center;
	    }
	    
	    #confirmReturn {
	    	
	    }
		
	</style>
</head>

<body style="background-color: #FFF">
	<div class="mainBox"
		style="height: auto ! important; min-height: 0px; padding-bottom: 0px;padding-left: 0px;width: auto ! important;min-width: 1070px;">
		<h3>应急事件列表</h3>
	</div>

	<div id="queryBar"
		style="width: auto ! important;min-width: 1070px; margin-top: 30px;margin-left:auto;margin-right:auto;margin-right: 22px;">
		<span style="font-size:13px;"><strong>事件类型:</strong> </span>&nbsp; 
		<select id="queryTypeName" style="width:10%;min-width: 75px;font-size:13px;">
			<option value=0>全部</option>
			<%
				ResultSet rs = EventTypeDao.queryAllEventType();
				while(rs!=null&&rs.next()){
			%>
					<option value="<%=rs.getString("TypeID")%>"><%=rs.getString("TypeName")%></option>
			<%
				} 
				rs.close();
			%>
		</select> 
		<span class="subtitle"><strong>上报人：</strong> </span> 
		<input type="text" style=" width:6% ;min-width: 70px;font-size:13px;"
			id="queryUploadName"> 
		<span class="subtitle"><strong>状态：</strong> </span>&nbsp; 
		<select id="queryStatus" style="width:9%;min-width: 75px;font-size:13px;">
			<option value=0>全部</option>
			<option value=1>已保存</option>
			<option value=2>已提交</option>
			<option value=3>申请撤回</option>
			<option value=4>已撤回</option>
			<option value=5>已打回</option>
		</select> 
		<span class="subtitle"><strong>事件时间：</strong> </span> 
		<input type="text" class="datepicker" style=" width:6% ;min-width: 70px; font-size:13px;"
			name="queryUploadTimeFrom" id="queryUploadTimeFrom"> 
		<span style="font-size:13px;"><strong>-</strong> </span> 
		<input type="text" class="datepicker" style=" width:6% ;min-width: 70px; font-size:13px;"
			name="queryUploadTimeTo" id="queryUploadTimeTo"> 
		<input type="image" id="query" src="/ZsGljSystem/icons/Event/query.png" onclick="query()"
			style=" width:62px;height:30px;margin-left: 0px;padding-bottom: 10px;" />
			
		<input type="<%=request.getAttribute("addBtnType")%>" id="add"
			src="/ZsGljSystem/icons/Event/add.png"
			style="  width:62px;height:30px; float:right;margin-left:5px;padding-bottom: 10px;"
			onclick="location.href='/TomatoWebSys/actionsUser/putupdateUser.action'" />
	</div> 

	<div style="margin-top: 20px;margin-right: 22px;">
		<table id="dg" class="easyui-datagrid"
			style="width: auto ! important;min-width: 1070px; height: 200px;min-height:372px; margin-top: -200px;padding-right: 0px;"
			url="<%=basePath%>actionEvent/eventList" rownumbers="false" fitColumns="true" singleSelect="true" 
			pagination="true" method="post" pagination="true" collapsible="true">
			<thead>
				<tr>
					<th field="eventID" style="width:11%;">事件ID</th>
					<th field="companyID" style="width:8%;">管养所</th>
					<th field="eventDate" style="width:8%;">事件日期</th>
					<th field="eventTime" style="width:7%;">发生时间</th>
					<th field="typeID" style="width:9%;">事件类型</th>
					<th field="roadLocation" style="width:7%;">路段位置</th>
					<th field="eventCase" style="width:7%;">事件情况</th>
					<th field="trafficCase" style="width:7%;">交通情况</th>
					<th field="status" style="width:7%;" formatter="formatStatus">当前状态</th>
					<th field="edit" style="width:29%;" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<script language="JavaScript">
		// 设置日历的显示
		$$$( function() {
			$$$( ".datepicker" ).datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat:"yy-mm-dd",
			});
		});
	
	function formatStatus(value, rowData, rowIndex) {
		var result;
		if (value == null) {
			return null;
		} else if (value == "1") {
			result = "已保存";
		} else if (value == "2") {
			result = "已提交";
		} else if (value == "3") {
			result = "申请撤回";
		} else if (value == "4") {
			result = "已撤回";
		} else if (value == "5") {
			result = "已打回";
		} 
		return result;
	}
	
	// 添加操作按钮列
	function formatOper(val, row, index) {
			var usertype = '<%=session.getAttribute("UserType")%>';
			var checkstatus = row.status; /* 当前事件的状态   */ 
			if (usertype == 3) {
				if (checkstatus == 3) { /* 状态为3（申请撤回），那么可以进行 查看  */
					return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" onclick="showEvent('
							+ index + ')" />';
				} else if (checkstatus == 2) { 	/* 状态为2（已提交），那么可以进行 查看 申请撤回 */
					return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" onclick="showEvent('
							+ index
							+ ')" />'
							+ '<input type="image" style="margin-left:3px;" id="apply" src="/ZsGljSystem/icons/Event/apply.png" onmousedown="applyMouseDown(this)" onmouseover="applyMouseOver(this)" onmouseout="applyMouseOut(this)" onclick="applyEvent('
							+ index + ')"/>';
				} else { 						/* 查看 提交 修改 删除   */
					return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" onclick="showEvent('
							+ index
							+ ')" />'
							+ '<input type="image" style="margin-left:3px;" id="alter" src="/ZsGljSystem/icons/Event/alter.png" onmousedown="alterMouseDown(this)" onmouseover="alterMouseOver(this)" onmouseout="alterMouseOut(this)" onclick="alterEvent('
							+ index
							+ ')"/>'
							+ '<input type="image" style="margin-left:3px;"  id="submit" src="/ZsGljSystem/icons/Event/submit.png" onmousedown="submitMouseDown(this)" onmouseover="submitMouseOver(this)" onmouseout="submitMouseOut(this)" onclick="submitEvent('
							+ index
							+ ')"/>'
							+ '<input type="image" style="margin-left:3px;" id="delete" src="/ZsGljSystem/icons/Event/delete.png" onmousedown="deleteMouseDown(this)" onmouseover="deleteMouseOver(this)" onmouseout="deleteMouseOut(this)" onclick="deleteEvent('
							+ index + ')"/>';
				}
			} else if (usertype == 2) { /*  用户类型为公路局   */
				if (checkstatus == 3) { /*  事件为申请撤回      则可以查看  同意撤回  打回 */
					return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" '
							+ 'onClick = "showEvent(' + index + ')" />'
							+ '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/agree.png" onmousedown="agreeMouseDown(this)" onmouseover="agreeMouseOver(this)" onmouseout="agreeMouseOut(this)" '
							+ 'onClick = "agreeReturn(' + index + ')" />'
							+ '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/return.png" onmousedown="returnMouseDown(this)" onmouseover="returnMouseOver(this)" onmouseout="returnMouseOut(this)" '
							+ 'onClick = "returnReason(' + index + ')" />';
				} else if (checkstatus == 2) {	/*   事件为已提交      则可以查看   打回   */
					return '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/view.png" onmousedown="viewMouseDown(this)" onmouseover="viewMouseOver(this)" onmouseout="viewMouseOut(this)" '
							+ 'onClick = "showEvent(' + index + ')" />'
							+ '<input type="image" style="margin-left:3px;" id="view" src="/ZsGljSystem/icons/Event/return.png" onmousedown="returnMouseDown(this)" onmouseover="returnMouseOver(this)" onmouseout="returnMouseOut(this)" '
							+ 'onClick = "returnReason(' + index + ')" />';
				}
			}
		}
	
		// 按钮点击时图片变化
		function viewMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/viewHover.png");
		}
		function viewMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/viewPressed.png");
		}
		function viewMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/view.png");
		}
		function alterMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/alterPressed.png");
		}
		function alterMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/alterHover.png");
		}
		function alterMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/alter.png");
		}
		function applyMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/applyPressed.png");
		}
		function applyMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/applyHover.png");
		}
		function applyMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/apply.png");
		}
		function deleteMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/deletePressed.png");
		}
		function deleteMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/deleteHover.png");
		}
		function deleteMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/delete.png");
		}
		function submitMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/submitPressed.png");
		}
		function submitMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/submitHover.png");
		}
		function submitMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/submit.png");
		}
		function returnMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/returnPressed.png");
		}
		function returnMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/returnHover.png");
		}
		function returnMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/return.png");
		}
		function agreeMouseDown(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/agreePressed.png");
		}
		function agreeMouseOver(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/agreeHover.png");
		}
		function agreeMouseOut(obj) {
			$(obj).attr("src", "/ZsGljSystem/icons/Event/agree.png");
		}

		function showUse(index) {
			alert(index + "!");
		}

		// 提交应急事件
		function submitEvent(index, obj) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			//alert("submitEvent函数！");
			var eventID = row.eventID;
			//alert(eventID);
			if (row) {
				if (row.status == 2) {
					alert("该应急事件已经提交，不能重复提交！！");
				} else{
					$.messager.confirm("操作提示","是否提交该应急事件?",
						function(data) {
							if (data) {
								var url = "/ZsGljSystem/actionEvent/submitEvent.action?" + "eventID=" + eventID;
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
		}

		// 查看用户
		function showEvent(index, obj) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var eventID = row.eventID;
			//alert(eventID);
			var status = row.status;
			location.href = "/ZsGljSystem/actionEvent/showEvent.action?eventID=" + eventID;

		}

		// 修改事件信息
		function alterEvent(index, obj) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var eventID = row.eventID;
			var status = row.status;
			location.href = "/ZsGljSystem/actionEvent/putupdateEvent.action?eventID="+ eventID;
		}
		
		// 申请撤回事件
		function applyEvent(index) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var eventID = row.eventID;
			// 获取页面的高度和宽度
			var pageHeight = document.body.scrollHeight;
			var pageWidth = document.body.scrollWidth;
			// 可视区域的高度和宽度
			var clientHeight = document.body.clientHeight;
			// 创建遮罩层节点
			var mask = document.createElement('div');
			mask.id = 'mask';
			mask.style.width = pageWidth + 'px';
			mask.style.height = pageHeight + 'px';
			document.body.appendChild(mask);
			
			// 输入框
			var revokeReasonDiv = document.createElement('div');
			revokeReasonDiv.id = 'login';
			revokeReasonDiv.innerHTML = "<div class='loginCon'>"
											+ "<p style='font-size:18px;padding-top:10px'>请输入撤回原因</p>"
											+ "<input type='image' id='close' src='/ZsGljSystem/icons/Event/icon_close_normal.png'></input>"
											+ "<textarea id='textarea' rows='14' cols='30'></textarea></br>"
											+ "<input type='image' id='confirm' src='/ZsGljSystem/icons/Event/btn_sure_normal.png'></input>"
										+ "</div>";
			document.body.appendChild(revokeReasonDiv);
			
			// 获取输入框的宽和高
			var divWidth = revokeReasonDiv.offsetWidth;
    		var divHeight = revokeReasonDiv.offsetHeight;
			
		    // 设置输入框的left和top
		    revokeReasonDiv.style.left = (pageWidth - divWidth) / 2 + "px";
		    revokeReasonDiv.style.bottom = (clientHeight- divHeight) / 2 + "px";
		    
		    var close = document.getElementById("close");
		    var confirm = document.getElementById("confirm");
		    
		    // 点击确定按钮以提交撤回原因
		    confirm.onclick = function() {
		    	var $text = $('#textarea');	// 撤回原因
		    	text = $text.val();
				var url = "/ZsGljSystem/actionEvent/revokeEvent.action?"
												+ "eventID=" + eventID
												+ "&revokeReason=" + text;
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
		    	// 关闭输入框
		    	document.body.removeChild(revokeReasonDiv);
		        document.body.removeChild(mask);
		    }
		    
		    confirm.onmouseover = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_hover.png");
		    }
		    
		    confirm.onmouseout = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_normal.png");
		    }
		    
		    confirm.onmousedown = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_selected.png")
		    }
		    
		    confirm.onmouseup = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_hover.png")
		    }
		    
		    // 点击关闭按钮可以关闭输入框
		    close.onclick = function() {
		        document.body.removeChild(revokeReasonDiv);
		        document.body.removeChild(mask);
		    };
		    
		    close.onmouseover = function() {
		    	$(close).attr("src", "/ZsGljSystem/icons/Event/icon_close_hover.png");
		    }
		    
		    close.onmouseout = function() {
		    	$(close).attr("src", "/ZsGljSystem/icons/Event/icon_close_normal.png");
		    }
		}

		//删除应急事件
		function deleteEvent(index, obj) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var eventID = row.eventID;
			if (row) {
				$.messager.defaults = {
					ok : "是",
					cancel : "否"
				};
				$.messager .confirm("操作提示","确定删除应急事件：\n" + row.eventID + "?",
								function(r) {
									if (r) {
										var url = "/ZsGljSystem/actionEvent/deleteEvent.action?" + "eventID=" + eventID;
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

		// 公路局同意撤回
		function agreeReturn(index, obj) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var eventID = row.eventID;
			// 获取页面的高度和宽度
			var pageHeight = document.body.scrollHeight;
			var pageWidth = document.body.scrollWidth;
			// 可视区域的高度和宽度
			var clientHeight = document.body.clientHeight;
			// 创建遮罩层节点
			var mask = document.createElement('div');
			mask.id = 'mask';
			mask.style.width = pageWidth + 'px';
			mask.style.height = pageHeight + 'px';
			document.body.appendChild(mask);
			
			// 输入框
			var revokeReasonDiv = document.createElement('div');
			revokeReasonDiv.id = 'login';
			revokeReasonDiv.innerHTML = "<div class='loginCon'>"
											+ "<p style='font-size:18px;padding-top:10px'>以下是申请原因</p>"
											+ "<input type='image' id='close' src='/ZsGljSystem/icons/Event/icon_close_normal.png'></input>"
											+ "<p id='revokeReason' align='left' style='padding-left:10px'></p></br>"
											+ "<input type='image' id='confirm' src='/ZsGljSystem/icons/Event/btn_sure_normal.png'></input>"
										+ "</div>";
			var url = "/ZsGljSystem/actionEvent/viewRevokeReason.action?" + "eventID=" + eventID;
			$.ajax({
					type:"post",
					url:url,
					success:function(data) {
						$('#revokeReason').text(data);
					},
					error:function() {
						alert("系统异常，请稍后重试！");
					}
				});
			document.body.appendChild(revokeReasonDiv);
			
			// 获取输入框的宽和高
			var divWidth = revokeReasonDiv.offsetWidth;
    		var divHeight = revokeReasonDiv.offsetHeight;
			
		    // 设置输入框的left和top
		    revokeReasonDiv.style.left = (pageWidth - divWidth) / 2 + "px";
		    revokeReasonDiv.style.bottom = (clientHeight- divHeight) / 2 + "px";
		    
		    var close = document.getElementById("close");
		    var confirm = document.getElementById("confirm");
		    
		    // 点击确定按钮以同意撤回
		    confirm.onclick = function() {
		    	var url = "/ZsGljSystem/actionEvent/agreeReturnEvent.action?" + "eventID=" + eventID;
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
				document.body.removeChild(revokeReasonDiv);
		        document.body.removeChild(mask);
		    }
		    
		    confirm.onmouseover = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_hover.png");
		    }
		    
		    confirm.onmouseout = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_normal.png");
		    }
		    
		    // 点击关闭按钮可以关闭输入框
		    close.onclick = function() {
		        document.body.removeChild(revokeReasonDiv);
		        document.body.removeChild(mask);
		    };
		    
		    close.onmouseover = function() {
		    	$(close).attr("src", "/ZsGljSystem/icons/Event/icon_close_hover.png");
		    }
		    
		    close.onmouseout = function() {
		    	$(close).attr("src", "/ZsGljSystem/icons/Event/icon_close_normal.png");
		    }
		}

		// 按照查询条件进行查询
		function query() {
			var eventTypevalue = $("#queryTypeName option:selected").val();
			var status = $('#queryStatus').val();
			var queryUploadName = $('#queryUploadName').val();
			var queryUploadTimeFrom = $('#queryUploadTimeFrom').val();
			var queryUploadTimeTo = $('#queryUploadTimeTo').val();
			//防止乱码
			queryUploadName = encodeURI(encodeURI(queryUploadName));
			var d1 = new Date(queryUploadTimeFrom.replace(/\-/g, "\/"));
			var d2 = new Date(queryUploadTimeTo.replace(/\-/g, "\/"));
			// alert(d1+"--"+d2);
			if ((queryUploadTimeFrom == "" && queryUploadTimeTo != "")
					|| (queryUploadTimeFrom != "" && queryUploadTimeTo == "")) {
				alert("请填写完整的查询时间");
				//return false; 
			} else if (queryUploadTimeFrom != "" && queryUploadTimeTo != ""
					&& d1 >= d2) {
				alert("开始时间不能大于结束时间！");
				//return false;  
			} else {
				var url = "/ZsGljSystem/actionEvent/queryeventlist.action?"
						+ "queryUploadTimeFrom=" + queryUploadTimeFrom
						+ "&queryUploadTimeTo=" + queryUploadTimeTo
						+ "&queryStatus=" + status + "&queryTypeName="
						+ eventTypevalue + "&queryUploadName=" + queryUploadName;
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
		}
		
		// 输入打回原因
		function returnReason(index, obj) {
			$('#dg').datagrid('selectRow', index);
			var row = $('#dg').datagrid('getSelected');
			var eventID = row.eventID;
			// 获取页面的高度和宽度
			var pageHeight = document.body.scrollHeight;
			var pageWidth = document.body.scrollWidth;
			// 可视区域的高度和宽度
			var clientHeight = document.body.clientHeight;
			// 创建遮罩层节点
			var mask = document.createElement('div');
			mask.id = 'mask';
			mask.style.width = pageWidth + 'px';
			mask.style.height = pageHeight + 'px';
			document.body.appendChild(mask);
			
			// 输入框
			var returnReasonDiv = document.createElement('div');
			returnReasonDiv.id = 'login';
			returnReasonDiv.innerHTML = "<div class='loginCon'>"
											+ "<p style='font-size:18px;padding-top:10px'>请输入打回原因</p>"
											+ "<input type='image' id='close' src='/ZsGljSystem/icons/Event/icon_close_normal.png'></input>"
											+ "<textarea id='returnReason' rows='14' cols='30'></textarea></br>"
											+ "<input type='image' id='confirm' src='/ZsGljSystem/icons/Event/btn_sure_normal.png'></input>"
										+ "</div>";
			document.body.appendChild(returnReasonDiv);
			
			// 获取输入框的宽和高
			var divWidth = returnReasonDiv.offsetWidth;
    		var divHeight = returnReasonDiv.offsetHeight;
			
		    // 设置输入框的left和top
		    returnReasonDiv.style.left = (pageWidth - divWidth) / 2 + "px";
		    returnReasonDiv.style.bottom = (clientHeight- divHeight) / 2 + "px";
		    
		    var close = document.getElementById("close");
		    var confirm = document.getElementById("confirm");
		    
		    // 点击确定按钮以提交打回原因
		    confirm.onclick = function() {
		    	var text = $('#returnReason');
		    	text = text.val();
		    	var url = "/ZsGljSystem/actionEvent/returnEvent.action?"
												+ "eventID=" + eventID
												+ "&returnReason=" + text;
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
				document.body.removeChild(returnReasonDiv);
		        document.body.removeChild(mask);
		    }
		    
		    confirm.onmouseover = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_hover.png");
		    }
		    
		    confirm.onmouseout = function() {
		    	$(confirm).attr("src", "/ZsGljSystem/icons/Event/btn_sure_normal.png");
		    }
		    
		    // 点击关闭按钮可以关闭输入框
		    close.onclick = function() {
		        document.body.removeChild(returnReasonDiv);
		        document.body.removeChild(mask);
		    };
		    
		    close.onmouseover = function() {
		    	$(close).attr("src", "/ZsGljSystem/icons/Event/icon_close_hover.png");
		    }
		    
		    close.onmouseout = function() {
		    	$(close).attr("src", "/ZsGljSystem/icons/Event/icon_close_normal.png");
		    }
		}
	</script>
</body>
</html>