<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%
	Agent agent = (Agent)request.getAttribute("EDIT_AGENT");
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
				<td><font FACE="Arial, Helvetica" size="2">Agent ID</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" disabled="disabled" name="mngAgentId" ID="mngAgentId" size="8" value="<%=agent.getAgentId() %>" >
				</font></td>
				<td colspan="3"></td>
			</tr>
			<tr>			
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Username</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" disabled="disabled" name="mngUsername" MAXLENGTH="20" ID="mngUsername" size="8" value="<%=agent.getUserName() %>" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Email</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="mngEmail" id="mngEmail" size="15" MAXLENGTH="50" value="<%=agent.getAgentEmail()%>" >
				</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Password</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="password"  name="mngPassword" ID="mngPassword" size="8" MAXLENGTH="20" value="<%=agent.getPassword()%>" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Full name</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="mngFullname" ID="mngFullname" size="15" MAXLENGTH="50" value="<%=agent.getAgentName()%>" >
				</font></td>	
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Re-enter Password</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="password"  name="mngRepassword" ID="mngRepassword" size="8" MAXLENGTH="20" value="<%=agent.getPassword()%>" >
				</font></td>	
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Is Admin</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsAdmin" ID="mngIsAdmin">				
				<OPTION value="0" <%=agent.isAdmin()?"":"selected"%>>NO</OPTION>
				<OPTION value="1" <%=agent.isAdmin()?"selected":""%>>YES</OPTION>
				</SELECT>
				</font></td>					
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Level</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngUserLevel" ID="mngUserLevel">				
				<OPTION value="A" <%=agent.getUserLevel().equalsIgnoreCase("A")?"selected":""%>>&nbsp;&nbsp;A&nbsp;</OPTION>
				<OPTION value="B" <%=agent.getUserLevel().equalsIgnoreCase("B")?"selected":""%>>&nbsp;&nbsp;B&nbsp;</OPTION>
				<OPTION value="C" <%=agent.getUserLevel().equalsIgnoreCase("C")?"selected":""%>>&nbsp;&nbsp;C&nbsp;</OPTION>
				</SELECT>
				</font></td>	
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Active</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsActive" ID="mngIsActive">				
				<OPTION value="0" <%=agent.isActive()?"":"selected"%>>NO</OPTION>
				<OPTION value="1" <%=agent.isActive()?"selected":""%>>YES</OPTION>
				</SELECT>
				</font></td>
			</tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Type</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngUserType" ID="mngUserType">				
				<OPTION value="0" <%=agent.isUserType()?"":"selected"%>>NO</OPTION>
				<OPTION value="1" <%=agent.isUserType()?"selected":""%>>YES</OPTION>
				</SELECT>
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Show Report</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="mngIsReport" ID="mngIsReport">				
				<OPTION value="0" <%=agent.isReport()?"":"selected"%>>NO</OPTION>
				<OPTION value="1" <%=agent.isReport()?"selected":""%>>YES</OPTION>
				</SELECT>
				</font></td>
			<tr>
			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Login Count</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="mngLoginCount" ID="mngLoginCount" size="8" MAXLENGTH="20" value="<%=agent.getLoginCount()%>" >
				</font></td>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2"></font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				</font></td>	
			</tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><input style="width:100;" type="submit" value="Update" name="button" onclick="validate(1); return false;"></td>
			<td>&nbsp;</td>			
			<td><input style="width:100;" type="button" value="Close" name="button" onclick="resetFields();  dialogManageClose();"></td>
			<td><input style="width:100;" type="button" value="Reset" name="button" onclick="resetEditFields();"></td>
			</tr>
</table>
