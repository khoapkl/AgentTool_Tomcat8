<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%Agent agent =(Agent)session.getAttribute(Constants.AGENT_INFO);%>
<table id="MenuTable" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
		
		<tr>
			<td width="580" valign="bottom" align="right">		<a 
							class="nounderline"
							href="doStartPage.do" name="Toolbar"><b><span
							id="Toolbar3default_tools.asp">Products</span></b></a>
				
				
				
				<a target="_parent" href="order_db.do" class="nounderline"><b>Orders </b></a>
				
				<span id="menuShopper">												
				
				<a target="_parent" href="shopper_manager.do" class="nounderline"><b>Shoppers </b></a>
				</span>
				
			<a class="nounderline"
							href="authenticate.do?method=logout" 
							target="_parent" name="Toolbar"><b><span
							id="Toolbar3exit.asp">Exit(<%=agent.getUserName() %>)</span></b></a>&nbsp;
			</td>
			<td valign="MIDDLE"  align="RIGHT" nowrap="" colspan="2"><b><span
				id="topTitle"></span></b></td>
			
		</tr>
		<tr>
			<td></td>
			
		</tr>
	</tbody>
</table>