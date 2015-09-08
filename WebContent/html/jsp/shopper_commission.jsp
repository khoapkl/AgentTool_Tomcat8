<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
List<Note> listAdminInfo=(List<Note>) request.getAttribute(Constants.SHOPPER_LIST_ADMIN_INFO) ;
Note agent=(Note) request.getAttribute(Constants.SHOPPER_LIST_AGENT_INFO) ;
Shopper shopper = (Shopper) request.getAttribute(Constants.SHOPPER_INFO);
String userLevel=(String)session.getAttribute(Constants.USER_LEVEL);
String shopper_id=(String)request.getAttribute(Constants.SHOPPER_ID);
Boolean haveCommission=false;


%>

<%@page import="com.sun.org.apache.xerces.internal.impl.dv.xs.MonthDV"%><script>
		 $('#pageTitle').html("Edit Agent Commissions - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
		 $('#topTitle').html("Edit Agent Commissions - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
</script>



<%@page import="com.dell.enterprise.agenttool.model.Note"%>
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<FORM 
action="<%=request.getContextPath()%>/shopper_manager.do?method=updateAgentExpiration&shopperId=<%=shopper_id %>"
	method="POST" name="formShopperCommission">

<br>
<TABLE width="600" cellpadding=0 border = "0">
<TR><td>&nbsp;</TD>
	<td ALIGN="RIGHT" VALIGN="CENTER" NOWRAP><B>Agent</B> </td>
	<td>&nbsp;  </td>
	<td>
	<SELECT NAME="agent_ID" id=agentId>
	<%
	 		out.println("<OPTION VALUE='0'>Select One</OPTION>");
	       	        	
	        for(Note admin : listAdminInfo)         
	        {
	        	if(agent.getAgent_id()==admin.getAgent_id()){
	        	%>
	        	             <option value="<%=admin.getAgent_id()%>" selected><%=admin.getUserName() %></option>
	        	<% }
	        	else
	        	{
	        	%>
	        	    	<option value="<%=admin.getAgent_id()%>"><%=admin.getUserName() %></option>
	        	<%
	        	}
	        }
	        	
	        haveCommission = true;	        	 
	              	   
	%>	
	</SELECT></td>
	
</TR>


</TABLE>
<TABLE WIDTH = "150" BORDER = "0">
<TR><td COLSPAN = "5">&nbsp;</td></TR>
<TR><% if (haveCommission){%>
		<input type=hidden name=mode value="update" id="modeHidden">
    <% }else {%>
		<input type=hidden name=mode value="new" id="modeHidden">
    <%} %>
<td align=right valign=middle width = "25"><img src="images/blue_arrow.gif" border=0></td>
    <td valign=top align=left width = "50"><a onClick="javascript:submitIt();" href="javascript:void(0)"><font color="red"><u>Update</u></font></a></td>
    
	<td>&nbsp;</td>
	<td align=right valign=middle width = "25"><img src="images/blue_arrow.gif" border=0></td>
   <td valign=top align=left width = "50"><a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=shopper_id %>"><font color="red"><u>Cancel</u></font></a></td>
</TR>
<TR><td COLSPAN = "5">&nbsp;</td></TR>
</TABLE>
</FORM>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<!-- //
function submitIt() 
{ 	
	var agent_ID=$('#agentId').val();	
	var agent_id_exp_get=$('#agent_id_exp_get').val();	
	var modeHidden=$('#modeHidden').val();	
	if(agent_ID==0){
		if(agent_id_exp_get==""){
			if(modeHidden=="new"){
				window.alert('Incomplete Agent and Agent Expiration\n--------------------------------------------\nPlease select an Agent and enter a valid Agent Expiration before submitting.');
			}
			else{
				document.forms["formShopperCommission"].submit();
				
			}
		}
		else{
			window.alert('Incomplete Agent\n--------------------------------------------\nPlease select an Agent before submitting.');
		}
		
		
	}
	else{
		if (agent_id_exp_get=="") 
		{	window.alert('Incomplete Agent Expiration\n--------------------------------------------\nPlease enter a valid Agent Expiration before submitting.');
		}
		else
		{	
		document.forms["formShopperCommission"].submit();
		}
		
		}
}

// -->
</SCRIPT>


<%@include file="/html/scripts/calendarScript.jsp"%>