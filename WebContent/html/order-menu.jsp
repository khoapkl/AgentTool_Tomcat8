<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%
Agent agent =(Agent)session.getAttribute(Constants.AGENT_INFO);
Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
long expiryDays = 0;
if(session.getAttribute("expiryDays") !=null)
	{ 
		expiryDays = (Long)session.getAttribute("expiryDays");
	}
%>
<table id="MenuTable" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
		<tr>
			<td width="580" valign="middle" align="right"><a
				class="nounderline"
				href="doStartPage.do"
				target="_parent"><b>Shop as : <%=agent.getUserName() %></b></a>
				<a
				class="nounderline"
				href="order_db.do"
				target="_parent"><b>Orders </b></a>
				
			<%			
			if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){
			%>
				<a
				class="nounderline"
				href="shopper_manager.do"
				target="_parent"><b>Shoppers </b></a>			
			
			<ul id="menu">
			 	 <li><a id="LastLink" class="nounderline"
							href="#" 
							target="_parent" name="Toolbar"><b><span
							id="Toolbar3exit.asp">Logged in as: <%=agent.getUserName()%></span></b></a>
			         <ul class="sub-menu">
			            <li>
							<a class="tooltip-change" href="change_password.do?method=editPassword&expire=0">Change Password
							</a>	
							<span id="tooltip-changepwd">
    							Your password will expire in <%=expiryDays %> days
							</span>		                
			            </li>
			            <li>
			                <a href="authenticate.do?method=logout">Log Out</a>
			            </li>
			        </ul>
			    </li>
			</ul>
			<%
			}else{
			%>
			
			<a id="ExitLink" class="nounderline" href="login.do?method=logout"
				target="_parent" name="Toolbar"><b><span id="Toolbar3exit.asp">Exit (<%=agent.getUserName() %>)</span></b></a>&nbsp;	
			<%
			}
			%>
			</td>
			<td valign="MIDDLE" align="RIGHT" nowrap="nowrap"
				width="321" valign="top" height="62"><b><span
				id="topTitle"></span></b></td>
		</tr>
	</tbody>
</table>