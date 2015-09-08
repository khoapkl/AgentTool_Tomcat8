<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);
Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
Agent agent =(Agent)session.getAttribute(Constants.AGENT_INFO);
if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){
%>
        <div>
        <table width="600" cellpadding="10">
        <tbody><tr><td valign="top">
        	<table width="100%">
        	<tbody><tr><th bgcolor="#e0e0e0" align="left" colspan="2"> General Tools </th></tr>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=allOrder"> All Orders </a>           </th></tr>
        	<%
        	if(userLevel!=null && !userLevel.equals("C")){
        	%>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=heldOrderByAgent"> Agent-Held Orders </a> </th></tr>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=heldOrderByCustomer"> Agent store-Held Orders </a> </th></tr>
        	<%} %>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=shopOrder"> Orders by Shopper </a> </th></tr>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=yearOrder"> Orders by Date </a>       </th></tr>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=agentOrder"> Orders by Agent </a>    </th></tr>
        	</tbody></table>
        </td>

        <td valign="top">
        	<table width="100%">
        	<%
        	if(userLevel!=null ){
        	%>
        	<tbody><tr><th bgcolor="#e0e0e0" align="left" colspan="2"> Admin Tools </th></tr>       
        	<%if(isAdmin){ %> 	
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=listAllOrderPending"> All Pending Orders </a>    </th></tr>
        	
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=listDiscountPercentage"> Settings </a>    </th></tr>
        	<%} %>
        	<tr><td>&nbsp;</td><th align="left"> 
        		
        		<a href="order_db.do?method=viewCreditReport"> Credit Report </a>
        		
        		
        	</th></tr>
        	<tr><td>&nbsp;</td><th align="left"><a href="order_db.do?method=viewInventoryReport"> Inventory Summary Report </a></th></tr>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=agentReport"> Orders Summary Report </a></th></tr>
        	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=viewSoldAssetReport"> Sold Assets Report </a></th></tr>
        	<%
        	if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
			{						
			    if(agent.isAdmin() && (agent.getUserName().equalsIgnoreCase("tphan") || agent.getUserName().equalsIgnoreCase("anguyen")))
				{
			%>
			   
				<tr><td>&nbsp;</td><th align="left"> <a href="agent_setup.do">  Agent Setup </a>    </th></tr>
				<tr><td>&nbsp;</td><th align="left"> <a href="security_setup.do?method=securitySetup">  Security Settings </a>    </th></tr>
        
			<%	
				}
			}
			%>
				 
        		
        	</tbody>
        	<%} %>
        	</table>
        </td>

        </tr></tbody></table>
        </div>
<%
}else{    
%>
<table width="600" cellpadding="10">
<tbody><tr><td valign="top">
	<table width="100%">
	<tbody><tr><th bgcolor="#e0e0e0" align="left" colspan="2"> General Tools </th></tr>
	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=searchCustomerOrderOutsite"> Previous  Orders </a>           </th></tr>
	<tr><td>&nbsp;</td><th align="left"> <a href="order_db.do?method=heldCustomerOrder"> Held Orders </a> </th></tr>
	</tbody></table>
</td>
</tr></tbody></table>
<%} %>
<script>
$(document).ready(function() {
	$('#pageTitle').text("Order Manager");
	$('#topTitle').text("Order Manager");
});
</script>