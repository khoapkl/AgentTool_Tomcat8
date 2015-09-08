<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<link type="text/css" href="<%=request.getContextPath() %>/styles/jquery.ui.all.css" rel="stylesheet" />
<link type="text/css" href="<%=request.getContextPath() %>/styles/demos.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.position.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.dialog.js"></script>
<%
if (request.getAttribute("EXIST_USER") != null)
{
%>
	<div id="existUser"></div>
<%
}
%>

	

<div id="divAgent" style="padding:0px;text-align:center;vertical-align: middle;">
	<table border="0" width="100%">
			<tr>
				<td colspan="6"><font FACE="Arial, Helvetica" size="2"><b>Please enter agent information</b></font></td>
			</tr>
			<tr>
				<td></td>	
				<td></td>			
				<td colspan="4"><font FACE="Arial, Helvetica" size="2" color="red"><b>
				<div id="errorManage"></div>
				</b></font></td>	
			</tr>
			<tr>			
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Username</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="mngUsername" ID="mngUsername" size="8" MAXLENGTH="20" value="" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Email</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="mngEmail" id="mngEmail" size="15" MAXLENGTH="50" value="" >
				</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Password</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="password"  name="mngPassword" ID="mngPassword" size="8" MAXLENGTH="20" value="" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Full name</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="mngFullname" ID="mngFullname" MAXLENGTH="50" size="15" value="" >
				</font></td>	
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Re-enter Password</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="password"  name="mngRepassword" ID="mngRepassword" size="8" MAXLENGTH="20" value="" >
				</font></td>	
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Is Admin</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsAdmin" ID="mngIsAdmin">				
				<OPTION value="0" selected>NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>
				</font></td>					
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Level</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngUserLevel" ID="mngUserLevel">				
				<OPTION value="A" selected>&nbsp;&nbsp;A&nbsp;</OPTION>
				<OPTION value="B">&nbsp;&nbsp;B&nbsp;</OPTION>
				<OPTION value="C">&nbsp;&nbsp;C&nbsp;</OPTION>
				</SELECT>
				</font></td>	
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Active</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsActive" ID="mngIsActive">				
				<OPTION value="0">NO</OPTION>
				<OPTION value="1" selected>YES</OPTION>
				</SELECT>
				</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Type</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngUserType" ID="mngUserType">				
				<OPTION value="0" selected>NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Show Report</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsReport" ID="mngIsReport">				
				<OPTION value="0" selected>NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>
				</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Login Count</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="mngLoginCount" ID="mngLoginCount" size="8" MAXLENGTH="20" value="" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2"></font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				</font></td>	
			</tr>
			<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><input style="width:100;" type="submit" value="Add" name="button" onclick="validate(0); return false;"></td>
			<td>&nbsp;</td>			
			<td><input style="width:100;" type="button" value="Close" name="button" onclick="resetFields();  dialogManageClose();"></td>
			<td><input style="width:100;" type="button" value="Reset" name="button" onclick="resetFields();"></td>
			</tr>
	</table>
</div>

<script type="text/javascript">
<!--
$(document).ready(function() {	

	var ua = $.browser;
	var height = 230;
	var width = 550;
	
	if(ua.mozilla)
	{
		height = 220;
	}else if(ua.msie)
	{
		height = 450;
	}	
	$("#divAgent" ).dialog({
		modal: true,
		resizable: false,
		autoOpen: false,
		height : height,
		width:width
	}).prev().hide();
});

function dialogEditOpen()
{
	var ua = $.browser;
	var height = 250;
	var width = 550;
	
	if(ua.mozilla)
	{
		height = 220;
	}else if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
	{
		height = 200;
	}	
	$("#divAgent" ).dialog({
		modal: true,
		resizable: false,
		autoOpen: false,
		height : height,
		width:width
	}).prev().hide();
	var popup = $("#divAgent");
	popup.dialog('open');
}

function dialogManageClose()
{
	var popup = $("#divAgent");
	popup.dialog('close');
}

function dialogNewOpen()
{
	 
	var ua = $.browser;
	var height = 180;
	var width = 550;

	if(ua.mozilla)
	{
		height = 195;
	}
	else if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
	{		
		height = 155;
	}	
	$("#divAgent" ).dialog({
		modal: true,
		resizable: false,
		autoOpen: false,
		height : height,
		width:width
	}).prev().hide();
	var popup = $("#divAgent");
	popup.dialog('open');
}

function validate(isEdit)
{	
	if ($('#mngUsername').val() == "")
	{
		alert("Please enter Username.");
		$('#mngUsername').focus();
		return;
	}
	if ($('#mngPassword').val() == "")
	{
		alert("Please enter Password.");
		$('#mngPassword').focus();
		return;
	}
	if ($("#mngPassword").val() != $("#mngRepassword").val())
	{
		alert("Password does not match. Please check again");
		$("#mngRepassword").focus();
		return;
	}
	if ($('#mngFullname').val() == "")
	{
		alert("Please enter Fullname.");
		$('#mngFullname').focus();
		return;
	}

	if ($('#mngEmail').val() == "")
	{
		alert("Please enter Email.");
		$('#mngEmail').focus();
		return;
	}
	
	if (!checkForEmail($.trim($("#mngEmail").val())))
	{
		alert("Invalid email mask. Please check again");
		$("#mngEmail").focus();
		return;
	}
	
	if (!isEdit)
		isExistUser();
	else
		updateAgent();
}

function checkForEmail(obj)
{
	 	var supported = 0;
	    if (window.RegExp) {
	      var tempStr = "a";
	      var tempReg = new RegExp(tempStr);
	      if (tempReg.test(tempStr)) supported = 1;
	    }
	    if (!supported) return (obj.indexOf(".") > 2) && (obj.indexOf("@") > 0);
	    var r1 = new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
	    var r2 = new RegExp("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
	    return (!r1.test(obj) && r2.test(obj));
}

function isExistUser()
{
	dialogOpen();
	var result = true;
	var mngUsername = $('#mngUsername').val();	
	$.ajax({
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			 method : "checkExistUser",	 
			 mngUsername : mngUsername
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");			
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{					
				if (data.toString().indexOf("id=\"existUser\"") == -1)	
					submitAgent();
				else
					$('#errorManage').html("<b>Username already exists.</b>");								
				}
		},
		error : function() {
			dialogClose();
			$('#errorManage').html("<b>Error execute in process!</b>");				
	}
	}); 

	return result;
}

function submitAgent()
{	
	dialogOpen();
	var mngUsername = $('#mngUsername').val();		
	var mngEmail = $('#mngEmail').val();		
	var mngPassword = $('#mngPassword').val();
	var mngFullname = $('#mngFullname').val();
	var mngIsAdmin = $('#mngIsAdmin').val();
	var mngUserLevel = $('#mngUserLevel').val();
	var mngIsActive = $('#mngIsActive').val();
	var mngUserType = $('#mngUserType').val();
	var mngIsReport = $('#mngIsReport').val();
	var mngLoginCount = $('#mngLoginCount').val();		
	
	$.ajax({
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			 method : "addAgent",	 
			 mngUsername : mngUsername,				 
			 mngEmail : mngEmail,
			 mngPassword : mngPassword,
			 mngFullname : mngFullname,
			 mngIsAdmin : mngIsAdmin ,
			 mngUserLevel : mngUserLevel,
			 mngIsActive : mngIsActive,
			 mngUserType : mngUserType,
			 mngIsReport : mngIsReport,
			 mngLoginCount : mngLoginCount
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");			
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{			
					 $('#errorManage').html("<font FACE='Arial, Helvetica' size='2' color='green'><b>Agent added successfully.</b></font>");									
				}
		},
		error : function() {
			dialogClose();
			$('#errorManage').html("<b>Error execute in add process!</b>");				
	}
	}); 
}

function updateAgent()
{	
	dialogOpen();
	var mngAgentId = $('#mngAgentId').val();
	var mngUsername = $('#mngUsername').val();		
	var mngEmail = $('#mngEmail').val();		
	var mngPassword = $('#mngPassword').val();
	var mngFullname = $('#mngFullname').val();
	var mngIsAdmin = $('#mngIsAdmin').val();
	var mngUserLevel = $('#mngUserLevel').val();
	var mngIsActive = $('#mngIsActive').val();
	var mngUserType = $('#mngUserType').val();
	var mngIsReport = $('#mngIsReport').val();
	var mngLoginCount = $('#mngLoginCount').val();		
	
	$.ajax({
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			 method : "updateAgent",	
			 mngAgentId : mngAgentId, 
			 mngUsername : mngUsername,				 
			 mngEmail : mngEmail,
			 mngPassword : mngPassword,
			 mngFullname : mngFullname,
			 mngIsAdmin : mngIsAdmin ,
			 mngUserLevel : mngUserLevel,
			 mngIsActive : mngIsActive,
			 mngUserType : mngUserType,
			 mngIsReport : mngIsReport,
			 mngLoginCount : mngLoginCount
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");			
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{			
					 $('#errorManage').html("<font FACE='Arial, Helvetica' size='2' color='green'><b>Agent updated successfully.</b></font>");	
					 getListAgent();								
				}
		},
		error : function() {
			dialogClose();
			$('#errorManage').html("<b>Error execute in add process!</b>");				
	}
	}); 
}

function resetFields()
{
	$("#mngUsername").val('');
	$("#mngEmail").val('');
	$("#mngPassword").val('');
	$("#mngFullname").val('');
	$("#mngRepassword").val('');
	$("#mngIsAdmin").val('0');
	$("#mngUserLevel").val('A');
	$("#mngIsActive").val('1');
	$("#mngUserType").val('0');
	$("#mngIsReport").val('0');
	$('#errorManage').html('');
}

function resetEditFields()
{
	$("#mngEmail").val('');
	$("#mngPassword").val('');
	$("#mngFullname").val('');
	$("#mngRepassword").val('');
	$("#mngIsAdmin").val('0');
	$("#mngUserLevel").val('A');
	$("#mngIsActive").val('1');
	$("#mngUserType").val('0');
	$("#mngIsReport").val('0');
	$('#errorManage').html('');
}

//-->
</script>



<style>
<!--
.ui-widget-overlay 
{
    background: url("images/phpThumb_generated_thumbnailpng.png") repeat scroll 50% 50% #666666;
    opacity: 0.5;
}
-->
</style>




