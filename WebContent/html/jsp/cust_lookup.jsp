<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.model.EstoreBasketItem"%>
<%@page import="java.util.List"%>
<div>
<%!
boolean rightToSetNewCustomer(boolean isAdmin, String userLevel)
{
	boolean result = false;
	if (isAdmin)
		result = true;
	else
	{
		if (userLevel.equalsIgnoreCase("A"))
				result = true;
		else if (userLevel.equalsIgnoreCase("B"))
				result = false;
		else if (userLevel.equalsIgnoreCase("C"))
				result = false;					
	}

	return result;
}
%>
<%
List<EstoreBasketItem> basketItemCheck = null ; 
if(request.getAttribute(Constants.ATTR_ITEM_BASKET) != null)
{
    basketItemCheck = (List<EstoreBasketItem>)request.getAttribute(Constants.ATTR_ITEM_BASKET);
}

Boolean customerManage = false ;
if(session.getAttribute(Constants.CUSTOMER_MANAGE) != null)
{
    customerManage = (Boolean)session.getAttribute(Constants.CUSTOMER_MANAGE);    
}
	
	
if(customerManage == true || (basketItemCheck != null && basketItemCheck.size() > 0) )
{
    
	Agent agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
	if (request.getAttribute("CustomerName") != null)
	{		
		String name = request.getAttribute("CustomerName").toString();
		String shopperID = session.getAttribute(Constants.SHOPPER_ID).toString();
			
		String agent_level = "";
		if (agent != null)
			 agent_level = agent.getUserLevel();		
		if(session.getAttribute(Constants.CUSTOMER_MANAGE)==null)
		{ 
		%>
		You are currently in the store as <b><%= request.getAttribute("CustomerName").toString()%></b> - <a href="#" onclick="buildPath('checkout','<%=shopperID%>','<%=name%>','<%=agent_level%>')">Proceed to Checkout</a> <%
		} 
		%>
	<%	
	}
	%>
<BR />
<img alt="Customer Lookup" src="images/customer_lookup.gif">
<BR />
<form name="frmSearch" action="" method = "post">
		<INPUT TYPE="HIDDEN" ID="results" VALUE="false">
		<INPUT TYPE=HIDDEN NAME="section" VALUE="">

		<table border="0" width="100%">

			<tr>

				<td colspan="9"><font FACE="Arial, Helvetica" size="2"><b>Please enter search condition:</b></font></td>

			</tr>

			<tr>

				<td colspan="2"><font FACE="Arial, Helvetica" size="2">Customer #</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text"  name="linknumber" ID="linknumber" size="8" value="" onkeypress="return isNumberKey(this,event,false);">
				</font></td>

				<td colspan="6"><font FACE="Arial, Helvetica" size="2">&nbsp;</font></td>

			</tr>

			<tr>

				<td><font FACE="Arial, Helvetica" size="2">Shipping:</font></td>

				<td><font FACE="Arial, Helvetica" size="2">First Name</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="ship_to_fname" id="ship_to_fname" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Last Name</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="ship_to_lname" id="ship_to_lname" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Company</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="ship_to_company" id="ship_to_company" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Phone</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="ship_to_phone" id="ship_to_phone" size="8" value="" >
				</font></td>

			</tr>

			<tr>

				<td><font FACE="Arial, Helvetica" size="2">Billing :</font></td>

				<td><font FACE="Arial, Helvetica" size="2">First Name</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="bill_to_fname" id="bill_to_fname" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Last Name</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="bill_to_lname" id="bill_to_lname" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Company</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="bill_to_company" id="bill_to_company" size="8" value="" >
				</font></td>

				<td><font FACE="Arial, Helvetica" size="2">Phone</font></td>

				<td><font FACE="Arial, Helvetica" size="2">
				<input type="text" name="bill_to_phone" id="bill_to_phone" size="8" value="" >
				</font></td>

			</tr>

			<tr>

				<td colspan="3" align="center"><font FACE="Arial, Helvetica" size="2"><input style="width:100;" type="submit" value="Filter" name="button" onclick="getListCustomer(); return false;"></font></td>

				<td colspan="3" align="center"><font FACE="Arial, Helvetica" size="2"><input style="width:100;" type="button" value="Clear Filter" name="button" onclick="resetForm();"></font></td>
<%
if ((session.getAttribute(Constants.IS_CUSTOMER)==null) && rightToSetNewCustomer(agent.isAdmin(),agent.getUserLevel())) {
%>
				<td colspan="3" align="center"><font FACE="Arial, Helvetica" size="2"><input style="width:100;" type="button" value="New Customer" name="button" onclick="newCustomer();"></font></td>
<% }%>
			</tr>
		</table>
</div>
</form>
<div id="search_results"><I>Please specify filter criteria</I></div>
<%@include file="/html/scripts/cust_lookupScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
<%
}else
{
%>
<BR />
<img alt="Customer Lookup" src="images/your_cart.gif">
<BR />
	<blockquote>
		<font size="2" face="Arial, Helvetica">
			<strong>Your cart is empty.</strong>
		</font>
	</blockquote>
<%    
}

if(request.getAttribute(Constants.ATTR_CREATE_CUSTOMER) != null && (Boolean)request.getAttribute(Constants.ATTR_CREATE_CUSTOMER))
{
%>
	<script type="text/javascript">
	    $(document).ready(function() {
	    	alert("Customer added successfully.");
	    });
	</script>
<%	
}
%>

