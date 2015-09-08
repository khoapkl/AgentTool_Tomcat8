<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.DiscountAdjustment"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>

<%
DiscountAdjustment orders =(DiscountAdjustment) request.getAttribute(Constants.LIST_ORDER_LIST_DISCOUNT);
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
%>

<TABLE BORDER=0 CELLPADDING=2 WIDTH=600>
		<tr>
			<td width="200"><label>Max Discount Percentage </label></td>
			<td width="100"><input type="text" size="10" id="textMaxdisvalue" name="maxdisvalue" maxlength="3" value="<%=orders.getPercdiscount() %>" /></td>
			
		</tr>
		
		<tr>
			<td width="200"><label>Customer - Held Order Expiration </label></td>
			<td width="100">
			<SELECT NAME="agent_ID" id=textExpirationdate>
				<OPTION VALUE=''>Select One</OPTION>
				<OPTION VALUE='1'>1 day</OPTION>
				<OPTION VALUE='2'>2 days</OPTION>
				<OPTION VALUE='3'>3 days</OPTION>
				<OPTION VALUE='4'>4 days</OPTION>
				<OPTION VALUE='5'>5 days</OPTION>
				<OPTION VALUE='6'>6 days</OPTION>
				<OPTION VALUE='7'>7 days</OPTION>					
			</SELECT>		
			
			</td>
			<td><%if(isAdmin){ %><input type="button" value="Update" name="update" id="updateDiscount"/><%} %></td>
		</tr>
</TABLE>
<div id="messageResult"></div>
<%@include file="/html/scripts/orderScript.jsp" %> 

