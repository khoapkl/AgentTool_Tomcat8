<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.model.ShopperAs"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>


<%
Agent agent =(Agent)session.getAttribute(Constants.AGENT_INFO);
Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
Boolean idShopper = (Boolean)session.getAttribute(Constants.IS_SHOPPER);
long expiryDays = 0;
if(session.getAttribute("expiryDays") !=null)
	{ 
		expiryDays = (Long)session.getAttribute("expiryDays");
	}

Object isAdmin = request.getAttribute(Constants.IS_ADMIN);
%>
<table id="MenuTable" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
		<tr>
			<td width="100%" valign="center" align="left">
			<%
			if(isAdmin != null)
			{
			%>
			<a target="_parent" href="doStartPage.do" class="nounderline"><b>Shop as : <%=agent.getUserName() %></b></a>
			
			<%
			}else
			if(idShopper != null && idShopper){
			    String shopperName =(String) session.getAttribute(Constants.SHOPPER_NAME);
			    %>
				<a 
							class="nounderline"
							href="authenticate.do?method=shopAsAgent" name="Toolbar"><b><span
							id="Toolbar3default_tools.asp">Shopping As: <%=shopperName %></span></b></a>
				
			<%
			}else if(request.getAttribute(Constants.ATTR_CHECKOUT_SHOP_AS) != null && request.getAttribute(Constants.ATTR_CUSTOMER) != null)
			{
			    Customer customerH = (Customer)request.getAttribute(Constants.ATTR_CUSTOMER);
			%>
				<a class="nounderline" href="authenticate.do?method=shopAsAgent" name="Toolbar">
						<b>
							<span id="Toolbar3default_tools.asp">
									Shopping As: <%= customerH.getShip_to_fname() + " " +  customerH.getShip_to_lname() %>
							</span>
						</b>
				</a>
				
			<%	    
			}
			%>
			<a class="nounderline" href="doStartPage.do" name="Toolbar">
					<b><span id="Toolbar3default_tools.asp">Products</span></b>
			</a> 
			
			<%
			//Check Pesmission for Agent
			if(agent.isAdmin() ||(isCustomer == null &&  !agent.isAdmin() && !agent.getUserLevel().equals("C")) || isCustomer != null)
			{
			%>			
			<a class="nounderline" href="showBasket.do" target="_parent" name="Toolbar">
					<b><span id="Toolbar1basket.asp">Cart</span></b>
			</a>
			
			<%			
			}	
			if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
			{						
			    if(agent.isAdmin() || (!agent.isAdmin() && !agent.getUserLevel().equals("C")))
				{
			    %>
			    <a class="nounderline" href="cust_lookup.do" target="_parent" name="Toolbar">
						<b><span id="Toolbar1cust_lookup.asp">Checkout</span></b>
				</a>
				
			    <%    
				}				
				%>				
				
			<%if((!agent.getUserLevel().equals("B"))&& (!agent.getUserLevel().equals("C"))){ %>	
			<a class="nounderline"
							href="cust_lookup.do?manage=true" 
							target="_parent" name="Toolbar"><b><span
							id="Toolbar3exit.asp">Customer Setup</span></b></a>
							
			<% }%>
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
<!--			-->
				<a 
							class="nounderline"
							href="shopper.do?method=prepareCheckout&shopper_new=<%=session.getAttribute(Constants.SHOPPER_ID) %>&section=checkout"
							target="_parent" name="Toolbar"><b><span
							id="Toolbar1cust_lookup.asp">Checkout</span></b></a>
							
			<!--<a id="LastLink" class="nounderline"
							href="login.do?method=logout" 
							target="_parent" name="Toolbar"><b><span
							id="Toolbar3exit.asp">Exit (<%=agent.getUserName() %>)</span></b></a>&nbsp;
			-->
			<a id="ExitLink" class="nounderline" href="login.do?method=logout"
				target="_parent" name="Toolbar"><b><span id="Toolbar3exit.asp">Exit (<%=agent.getUserName() %>)</span></b></a>&nbsp;	
			<%
			}
			%>
			</td>
			<td valign="MIDDLE" align="RIGHT" nowrap="nowrap"
				valign="top" height="62"><b><span
				id="topTitle"></span></b></td>
		</tr>
	</tbody>
</table>
 <%@include file="/html/scripts/default_menu_script.jsp"%>