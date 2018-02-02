<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String typeID = request.getAttribute("TypeID").toString();
	String townID = request.getAttribute("TownID").toString();
%>
<%@ page import="com.zsglj.dao.*"%>
<%@ page import="com.zsglj.util.*"%>
<%@ page import="com.zsglj.util.ConnectSQL"%>
<%@ page import="java.sql.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<link rel="Shortcut Icon" href="/ZsGljSystem/icons/home/imgTitle.png" />
<title><%=request.getAttribute("jspTitle")%></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>styles/Admin/HomePage/public.css"
	rel="stylesheet" type="text/css">
<script src="<%=basePath%>script/jQuery/jquery.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>script/jQuery/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>script/jQuery/global.js"
	type="text/javascript"></script>

<link href="<%=basePath%>styles/jQuery/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<style type="text/css">
td.tdLeft {
	width: 20%;
	min-width: 200px;
	vertical-align: middle;
	text-align: center;
}

span.subtitle {
	font-size: 16px;
}
</style>
<script type="text/javascript"
	src="/ZsGljSystem/plugIn/ckeditor/ckeditor.js"></script>
<script src="<%=basePath%>script/InputCheck/InputCheck.js"
	type="text/javascript"></script>
<link href="<%=basePath%>plugIn/uploadify-v3.1/uploadify.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="<%=basePath%>plugIn/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript"
	src="<%=basePath%>plugIn/uploadify-v3.1/swfobject.js"></script>

</head>

<body>
	<div class="mainBox"
		style="height: auto ! important; min-height: 0px; padding-bottom: 0px;padding-left: 0px;width: auto ! important;min-width: 1070px;">
		<h3><%=request.getAttribute("jspTitle")%></h3>
	</div>

	<font color="red"><s:fielderror /></font>

	<form id="detailForm" action=<%=request.getAttribute("action")%>
		method="post" onsubmit="return CheckForm(this)">

		<div>
			<table class="table table-striped table-bordered"
				style="width:99%;min-width:1070px;margin-right: 22px;">
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>事件发生日期：</strong>
					</span></td>
					<td><input type="text" name="EventDate" id="EventDate"
						style="width:350px;height:36px;margin-left:5px;" maxlength="10"
						value=<%=request.getAttribute("EventDate")%>>&nbsp;&nbsp;&nbsp;
						<span id="EventDateFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>事件发生时间：</strong>
					</span></td>
					<td><input type="text" name="EventTime" id="EventTime"
						style="width:350px;height:36px;margin-left:5px;" maxlength="10"
						value=<%=request.getAttribute("EventTime")%>>&nbsp;&nbsp;&nbsp;
						<span id="EventTimeFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>事件类型：</strong>
					</span></td>
					<td>&nbsp; <select style="width:350px;height:36px;"
						id="TypeID" name="TypeID">
							<%
								ResultSet rs = EventTypeDao.queryAllEventType();
								while (rs != null && rs.next()) {
							%>
							<option <%= typeID.equals(rs.getString("TypeID"))? "selected ":" " %>value="<%=rs.getString("TypeID")%>"><%=rs.getString("TypeName")%></option>
							<%
								}
								rs.close();
							%>
							</select>
					</td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>所属镇区：</strong>
					</span></td>
					<td>&nbsp; <select style="width:350px;height:36px;"
						id="TownID" name="TownID">
							<%
								rs = LocationDao.queryAllLocation();
								while (rs != null && rs.next()) {
							%>
							<option <%= townID.equals(rs.getString("TownID"))? "selected ":" " %>value="<%=rs.getString("TownID")%>"><%=rs.getString("TownName")%>-<%=rs.getString("StreetName")%></option>
							<%
								}
								rs.close();
							%>
					</select>
					</td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>路段位置：</strong>
					</span></td>
					<td><input type="text" name="RoadLocation" id="RoadLocation"
						style="width:350px;height:36px;margin-left:5px;" maxlength="10"
						value=<%=request.getAttribute("RoadLocation")%>>&nbsp;&nbsp;&nbsp;
						<span id="RoadLocationFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>事件情况：</strong>
					</span></td>
					<td><textarea type="text" id="EventCase" name="EventCase"
							style="width:350px;height:72px;margin-left:5px;"
							placeholder="请填写事件情况（255字以内）" maxlength="255"><%=request.getAttribute("EventCase")%></textarea></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>交通状况：</strong>
					</span></td>
					<td><textarea type="text" id="TrafficCase" name="TrafficCase"
							style="width:350px;height:72px;margin-left:5px;"
							placeholder="请填写交通状况（255字以内）" maxlength="255"><%=request.getAttribute("TrafficCase")%></textarea></td>
				</tr>
				<!-- 图片 -->
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>现场图片：</strong>
					</span></td>
					<td>
						<img src="<%=request.getAttribute("Picture")%>" width="150" height="180" style="margin-left:5px;" id="pic_view">
						<input type="file" name="uploadify" id="uploadify">
						<div id="fileQueue" style="width:200px;"></div> 
						<input type="hidden" name="EventPicture1" id="EventPicture1" value=<%=request.getAttribute("Picture")%>></td>
				</tr>

				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>完成处置时间：</strong>
					</span></td>
					<td><input type="text" name="FinishTime" id="FinishTime"
						style="width:350px;height:36px;margin-left:5px;" maxlength="10"
						value=<%=request.getAttribute("FinishTime")%>>&nbsp;&nbsp;&nbsp;
						<span id="FinishTimeFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>完成处置后情况：</strong>
					</span></td>
					<td><textarea type="text" id="FinishCase" name="FinishCase"
							style="width:350px;height:72px;margin-left:5px;"
							placeholder="请填写完成处置后情况（255字以内）" maxlength="255"><%=request.getAttribute("FinishCase")%></textarea></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>出动人员：</strong>
					</span></td>
					<td><input type="text" name="PeopleNum" id="PeopleNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("PeopleNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="PeopleNumFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>出动车辆：</strong>
					</span></td>
					<td><input type="text" name="CarNum" id="CarNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("CarNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="CarNumFlag"></span></td>
				</tr>

				<!-- 完成图片 -->
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>完成处置图片：</strong>
					</span></td>
					<td>
						<img src="<%=request.getAttribute("FinishPic")%>" width="150" height="180" style="margin-left:5px;" id="pic_view">
						<input type="file" name="uploadify" id="uploadify">
						<div id="fileQueue" style="width:200px;"></div>
						<input type="hidden" name="FinishPicture1" id="FinishPicture1" value=<%=request.getAttribute("FinishPic")%>></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>值班人数：</strong>
					</span></td>
					<td><input type="text" name="DutyNum" id="DutyNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("DutyNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="DutyNumFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>损坏路段数量：</strong>
					</span></td>
					<td><input type="text" name="BadRoadNum" id="BadRoadNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("BadRoadNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="BadRoadNumFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>处置值守数量：</strong>
					</span></td>
					<td><input type="text" name="UnattendedNum" id="UnattendedNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("UnattendedNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="UnattendedNumFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>清除杂物数量：</strong>
					</span></td>
					<td><input type="text" name="SundriesNum" id="SundriesNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("SundriesNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="SundriesNumFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>清除标志牌数量：</strong>
					</span></td>
					<td><input type="text" name="DenoterNum" id="DenoterNum"
						style="width:350px;height:36px;margin-left:5px;"
						value=<%=request.getAttribute("DenoterNum")%> maxlength="10">&nbsp;&nbsp;&nbsp;
						<span id="DenoterNumFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"><strong>其它情况：</strong>
					</span></td>
					<td><input type="text" name="OtherCase" id="OtherCase"
						style="width:350px;height:36px;margin-left:5px;" maxlength="10"
						value=<%=request.getAttribute("Other")%>>&nbsp;&nbsp;&nbsp;
						<span id="OtherCaseFlag"></span></td>
				</tr>
				<tr>
					<td class="tdLeft"><span style="font-size:16px;"> <strong>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</strong>
					</span></td>
					<td><textarea type="text" name="Remark" id="Remark"
							style="width:350px;height:136px;margin-left:5px;"
							placeholder="请填写备注（100字内）" maxlength="100"><%=request.getAttribute("Remark")%></textarea></td>
				</tr>
			</table>
		</div>
		<div
			style="margin-left:auto;margin-right:auto;width:200px;margin-top: 20px;">
			<input type="submit" value="提交" id="submit"
				style="font-size:16px;border-radius:20px;width:80px;height:40px;" />
		</div>
	</form>

	<script type="text/javascript">
	
	$(document).ready(function () {
		var savepathMid = "/images/EventsImage/";
		var picid="<%=request.getAttribute("eventID")%>"+"-pic1";
		$("#uploadify").uploadify({  
		    'swf'            : '<%=request.getContextPath()%>/plugIn/uploadify-v3.1/uploadify.swf',  
		    'uploader'       : '<%=request.getContextPath()%>/actionsUpload/uploaFile.action?savepathMid='+savepathMid+'&uuid='+picid, 
		    		//图片上传的action方法，参数是你的图片保存路径  
		    'queueID'        : 'fileQueue',  
		    'auto'           : true,  
		    'multi'          : true,  //是否可以上传多个文件
		    'buttonCursor'   : 'hand',  
		    'fileObjName'    : 'uploadify',  //上传文件的id属性
		    'buttonText'     : '上传图片',  //按钮的文字
		    'height'         : '25',  //按钮的高度
		    'progressData'   : 'percentage',  
		    'fileTypeDesc'   : '支持格式:jpg/gif/jpeg/png/bmp.',    
		    'fileTypeExts'   : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式  
		    onUploadSuccess  : function(file,data,response) {  
		        //设置图片预览  
		        var _arr_val = data.split(",");  
		        $("#pic_view").attr("src","<%=request.getContextPath()%>"+_arr_val[1]+'?'+Math.random());
		        $("#EventPicture1").val("<%=request.getContextPath()%>"+ _arr_val[1] );
			}
		});

	});

	//检查表单，两次密码不一致或用户名重复时不能提交
	function CheckForm(form) {

		return true;
	}
	</script>
</body>
</html>
