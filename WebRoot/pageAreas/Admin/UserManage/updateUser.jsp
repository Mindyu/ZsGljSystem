<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="com.zsglj.dao.*"%>
<%@ page import="com.zsglj.util.*"%>
<%@ page import="com.zsglj.util.ConnectSQL"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.zsglj.dao.SqlOperate" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<base href="<%=basePath%>">
<link rel="Shortcut Icon" href="/ZsGljSystem/icons/home/imgTitle.png" /> 
<title><%= request.getAttribute("jspTitle")%></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
<script type="text/javascript" src="/ZsGljSystem/plugIn/ckeditor/ckeditor.js"></script>
<script src="<%=basePath%>script/InputCheck/InputCheck.js"
	type="text/javascript"></script>
	<link href="<%=basePath %>plugIn/uploadify-v3.1/uploadify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>plugIn/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="<%=basePath %>plugIn/uploadify-v3.1/swfobject.js"></script>

</head>

<body>
	<p>
	
		<div class="mainBox" style="height: auto ! important; min-height: 0px; padding-bottom: 0px;padding-left: 0px;width: auto ! important;min-width: 1070px;">
			<h3><%= request.getAttribute("jspTitle")%></h3>
		</div>
	
	<font color="red"><s:fielderror/></font>
	
	<form id="detailForm" action=<%= request.getAttribute("action")%> method="post" onsubmit="return CheckForm(this)">
	<input type="hidden" name="UserId" value=<%= request.getAttribute("userId")%>>
	
	<div>
	<table class ="table table-striped table-bordered" style="width:99%;min-width:1070px;margin-right: 22px;">
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>用户名称：</strong> </span></td>
			<td><input type="text" name="UserName" id="UserName" style="width:350px;height:36px;margin-left:5px;" maxlength="10" onblur = "checkUserExists(this)" 
			required  value=<%=request.getAttribute("userName")%>>&nbsp;&nbsp;&nbsp;<span  id="UserNameFlag"  name="UserNameFlag" ></span></td>
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>用户类别：</strong> </span></td>
			<td>
				<select style="width:350px;height:36px;" id="sel" name="UserType" >  
        			<option value =1 <%= request.getAttribute("typeValue0")%>>系统管理员</option>  
        			<option value=2  <%= request.getAttribute("typeValue2")%>>公路局</option> 
        			<option value=3  <%= request.getAttribute("typeValue3")%>>管养单位</option>
        		</select>
        	</td>
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>用户状态：</strong> </span></td>
			<td>
				<select style="width:350px;height:36px;" id="selstatus" name="UserStatus" > 
        			<option value=1 <%= request.getAttribute("statusValue1")%>>存在</option>  
        			<option value =0 <%= request.getAttribute("statusValue0")%>>被删除</option>  
        		</select>
        	</td>
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>用户简介：</strong> </span></td>
			<td>
				<textarea type="text" id="userintroduction" name="userintroduction" style="width:350px;height:72px;margin-left:5px;" 
								placeholder="请填写简介（40字内）" maxlength="40"><%=request.getAttribute("userintroduction")%>
				</textarea>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>用户密码：</strong> </span></td>
			<td>
				<input type="text" id="password" name="password" style="width:350px;height:36px;margin-left:5px;" 
							required onblur="checkPassword(this)" value=<%=request.getAttribute("password")%>>
							&nbsp;&nbsp;&nbsp;
				<span  id="passwordFlag"  name="passwordFlag" ></span>
			</td>
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>确认密码：</strong> </span></td>
			<td>
				<input type="text" id="passwordAgain" name="passwordAgain" style="width:350px;height:36px;margin-left:5px;" placeholder="请填写密码（10字符内）" maxlength="10" 
							required onblur="checkPasswordAgain(this)" value=<%=request.getAttribute("password")%>>&nbsp;&nbsp;&nbsp;
				<span  id="passwordAgainFlag"  name="passwordAgainFlag" ></span>
			</td>
		</tr>
		
		<tr>
<!-- 			<td class="tdLeft"><span style="font-size:16px;"><strong>所属单位：</strong> </span></td> -->
<!-- 			<td><input type="text" name="Company" style="width:350px;height:36px;margin-left:5px;" maxlength="50" -->
<!-- 			required  oninvalid="setCustomValidity('请输入单位名称')" oninput="setCustomValidity('')" value=<%=request.getAttribute("company")%>></td> -->
				<td class="tdLeft"><span style="font-size:16px;"><strong>所属单位：</strong></span></td>
					<td>
					<select style="width:350px;height:36px;"id="Company" name="Company">
							<% ResultSet rs = CompanyDao.queryAllCompanies();
								  String CompanyID =  request.getAttribute("company").toString();
								  while(rs!=null && rs.next()){ %>
									 <option <%= CompanyID.equals( rs.getString("CompanyID") ) ?"selected":"" %> value="<%=rs.getString("CompanyID")%>"><%=rs.getString("CompanyName")%> </option>
							<%} //END WHILE
								rs.close();%>
					</select>
					</td>
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"> <strong>联系电话：</strong> </span></td>
			<td>
				<input id="Telephone" style="IME-MODE: disabled; width:350px;height:36px;margin-left:5px;"  name="Telephone" type="text" 
							required pattern="(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$" 
							value=<%=request.getAttribute("telephone")%>>
				</td> 
		</tr>
		
		<tr>
			<td class="tdLeft"><span style="font-size:16px;"><strong>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</strong> </span></td>
			<td>
				<textarea type="text" name="Remark" style="width:350px;height:136px;margin-left:5px;" placeholder="请填写备注（100字内）" 
								maxlength="100"><%= request.getAttribute("remark")%>
				</textarea>
			</td>
		</tr>
	</table>
	</div>
		 <div style="margin-left:auto;margin-right:auto;width:200px;margin-top: 20px;"> 
		<input type="submit" value="提交" id="submit" style = "font-size:16px;border-radius:20px;width:80px;height:40px;"/>
	</div>
	</form>
	
<script type="text/javascript">
	function checkUserExists(oCtl) {
		var checkkUserName = '<%= request.getAttribute("userName")%>';
		var username = oCtl.value;
		username = encodeURI(encodeURI(username));
		x=document.getElementById("UserNameFlag");
		x.innerHTML="";
		if(checkkUserName == null || checkkUserName!=username){
			var url= "/ZsGljSystem/actionsUser/checkUserName.action?" + "username=" + username;
			$.ajax({
				type : "post",
				url : url,
				cache : false,
				success : function(data, dataStatus) { 
					if (data == "false") {
						x.style.color="Red";
				    	x.innerHTML="用户名已存在！"; 
						return;
					}else{
						x.innerHTML="";
					}
				}
			});
		}
	}
	
	function checkPassword(oCtl) {
		var password = oCtl.value;
		x=document.getElementById("passwordFlag");
		if (password == "") {
			x.style.color="Red";
	    	x.innerHTML="密码不能为空！";
			return;
		}else{
			x.innerHTML="";
		}
		var passwordAgain=document.getElementById("passwordAgain").value;
		y=document.getElementById("passwordAgainFlag");
		
		if (password != passwordAgain) {
			y.style.color="Red";
	    	y.innerHTML="两次密码输入不一样！";
			return;
		}else{
			y.innerHTML="";
		}
	}
	function checkPasswordAgain(oCtl) {
		var passwordAgain = oCtl.value;
		var password=document.getElementById("password").value;
		x=document.getElementById("passwordAgainFlag");
		
		if (password != passwordAgain) {
			x.style.color="Red";
	    	x.innerHTML="两次密码输入不一样！";
			return;
		}else{
			x.innerHTML="";
		}
	}
	
	//检查表单，两次密码不一致或用户名重复时不能提交
	function CheckForm(form) {
		var password=document.getElementById("password").value;
		var passwordAgain = document.getElementById("passwordAgain").value;
		
		var x = document.getElementById("UserNameFlag").value;
		if(password!=passwordAgain){
			form.passwordAgain.focus();
			return false;
		}
		if(x=""){
			form.UserName.focus();
			return false;
		}
		return true;
	}
	//校验电话号码，以1开头，并且11位
	function checkTelephone(oCtl){
		var telephone = oCtl.value;
		var re = /^1\d{10}$/;
		x=document.getElementById("TelephoneFlag");
		if(!re.test(telephone)){
			x.style.color="Red";
		    x.innerHTML="联系电话不符合要求！";
		}else{
		   	x.innerHTML="";
		}		
		var re = /^0\d{2,3}-?\d{7,8}$/;
	   	if(re.test(str)){
	        alert("正确");
	    }else{
	        alert("错误");
	    }		
	}

</script>
</body>
</html>
