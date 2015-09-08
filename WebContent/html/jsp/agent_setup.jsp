<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%	
	Agent agent = (Agent)session.getAttribute(Constants.AGENT_INFO);

	//Hard code for tphan use only
	if(!(agent.getUserName().equalsIgnoreCase("tphan") || agent.getUserName().equalsIgnoreCase("anguyen")))
		return;


	if (request.getAttribute("CustomerName") != null)
	{		
		String name = request.getAttribute("CustomerName").toString();
		String shopperID = session.getAttribute(Constants.SHOPPER_ID).toString();
			
		String agent_level = "";
		if (agent != null)
			 agent_level = agent.getUserLevel();	
		
	
%>
<%	
	}
%>
<div>
<BR />
<img alt="Agent Lookup" src="images/agent_lookup.jpg">
<BR />
<form name="frmSearch" action="" method = "post">

		<table border="0" width="100%">
			<tr>
				<td colspan="9"><font FACE="Arial, Helvetica" size="2"><b>Please enter search condition:</b></font></td>
			</tr>

			<tr>			
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">Agent #</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="agentId" ID="agentId" size="8" value="" onkeypress="return isNumberKey(this,event,false);">
				</font></td>
				<td><font FACE="Arial, Helvetica" size="2">Full Name</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="fullname" id="fullname" size="15" value="" >
				</font></td>
				
				<td><font FACE="Arial, Helvetica" size="2">User Level</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="userLevel" ID="userLevel">
				<OPTION value="" selected>&nbsp;</OPTION>
				<OPTION value="A">&nbsp;&nbsp;A&nbsp;&nbsp;&nbsp;</OPTION>
				<OPTION value="B">&nbsp;&nbsp;B&nbsp;&nbsp;&nbsp;</OPTION>
				<OPTION value="C">&nbsp;&nbsp;C&nbsp;&nbsp;&nbsp;</OPTION>
				</SELECT>
				</font></td>
				<td width="30%"></td>		
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2">User Name</font></td>
				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="username" id="username" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Email</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="email" id="email" size="15" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Show Report</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="isReport" ID="isReport">
				<OPTION value="" selected>&nbsp;</OPTION>
				<OPTION value="0">NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>				
				</font></td>		

				<td colspan="2"></td>
			</tr>

			<tr>

				<td>&nbsp;</td>

				<td><font FACE="Arial, Helvetica" size="2">Is Admin</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="isAdmin" ID="isAdmin">
				<OPTION value="" selected>&nbsp;</OPTION>
				<OPTION value="0">NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>				
				</font></td>

			<td><font FACE="Arial, Helvetica" size="2">Active</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="isActive" ID="isActive">
				<OPTION value="" selected>&nbsp;</OPTION>
				<OPTION value="0">NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>				
				</font></td>
				
				<td><font FACE="Arial, Helvetica" size="2">User Type</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<SELECT NAME="userType" ID="userType">
				<OPTION value="" selected>&nbsp;</OPTION>
				<OPTION value="0">NO</OPTION>
				<OPTION value="1">YES</OPTION>
				</SELECT>				
				</font></td>

				<td colspan="2"></td>
			</tr>

			<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
				<td ><font FACE="Arial, Helvetica" size="2"><input style="width:100;" type="submit" value="Filter" name="button" onclick="getListAgent(); return false;"></font></td>
<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2"><input style="width:100;" type="button" value="Clear Filter" name="button" onclick="resetForm();"></font></td>
<%
if ((session.getAttribute(Constants.IS_CUSTOMER)==null) && agent.isAdmin()) {
%>
<td>&nbsp;</td>
				<td><font FACE="Arial, Helvetica" size="2"><input style="width:100;" type="button" value="New Agent" name="button" onclick="newAgent();"></font></td>
<% }%>
<td colspan="2"></td>
			</tr>
		</table>
</form>
</div>
<div id="search_results"><I>Please specify filter criteria</I></div>
<%@include file="/html/scripts/agent_setupScript.jsp"%>
<%@include file="/html/jsp/agent_manage.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
<div id="info"></div>


