<%
if (request.getAttribute("AGENT_IN_USE") != null)
{
%>
	<div id="agentInUse"></div>
<%
}
%>
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
				<input type="text" tabindex="1"  name="mngUsername" ID="mngUsername" size="8" MAXLENGTH="20" value="" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Email</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" tabindex="4" name="mngEmail" id="mngEmail" MAXLENGTH="50" size="15" value="" >
				</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Password</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="password" tabindex="2" name="mngPassword" ID="mngPassword" MAXLENGTH="20" size="8" value="" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Full name</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" tabindex="5" name="mngFullname" ID="mngFullname" MAXLENGTH="50" size="15" value="" >
				</font></td>	
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Re-enter Password</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="password" tabindex="3" name="mngRepassword" ID="mngRepassword" MAXLENGTH="20" size="8" value="" >
				</font></td>	
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Is Admin</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsAdmin" ID="mngIsAdmin"  tabindex="6"  >				
				<OPTION value="0" selected>NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>
				</font></td>					
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Level</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngUserLevel" ID="mngUserLevel" tabindex="7">				
				<OPTION value="A" selected>&nbsp;&nbsp;A&nbsp;</OPTION>
				<OPTION value="B">&nbsp;&nbsp;B&nbsp;</OPTION>
				<OPTION value="C">&nbsp;&nbsp;C&nbsp;</OPTION>
				</SELECT>
				</font></td>	
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Active</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsActive" ID="mngIsActive" tabindex="8">				
				<OPTION value="0">NO</OPTION>
				<OPTION value="1" selected>YES</OPTION>
				</SELECT>
				</font></td>
			</tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Type</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngUserType" ID="mngUserType" tabindex="9">				
				<OPTION value="0" selected>NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Show Report</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsReport" ID="mngIsReport" tabindex="10">				
				<OPTION value="0" selected>NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>
				</font></td>
			<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><input style="width:100;" type="submit" value="Add" name="button" tabindex="11" onclick="validate(0); return false;"></td>
			<td>&nbsp;</td>			
			<td><input style="width:100;" type="button" value="Close" name="button" tabindex="12" onclick="resetFields();  dialogManageClose();"></td>
			<td><input style="width:100;" type="button" value="Reset" name="button" tabindex="13" onclick="resetFields();"></td>
			</tr>
	</table>