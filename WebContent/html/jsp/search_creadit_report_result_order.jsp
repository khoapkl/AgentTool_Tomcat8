<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.CreditReportOrder"%>
<%@include file="/html/scripts/search_creadit_report_result_orderScript.jsp"%>
<%@page import="java.util.List"%><%
List<CreditReportOrder> orders =(List<CreditReportOrder>) request.getAttribute(Constants.LIST_SEARCH_CREDIT_REPORT_ORDER);
String total =(String) request.getAttribute(Constants.VIEW_TOTAL_CREDIT_REPORT_ORDER);
String datepickerFrom=(String)request.getAttribute(Constants.DATAPICKER_FROM);
String datepickerTo= (String)request.getAttribute(Constants.DATAPICKER_TO);
if(null==total || total.trim().equals("")||total.isEmpty())
{
    total="0";
}
float totalResult=Float.parseFloat(total);


int totalPage = 0;
int currentPage = 0;
int rowOnPage = 0 ;
int rowCount = 0 ;

if (request.getAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE) != null && request.getAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE) != null)
{
    rowCount = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_ROW_COUNT).toString());
    totalPage = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE).toString());
    currentPage = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE).toString());
}

if(request.getAttribute(Constants.ATTR_ROW_ON_PAGE) != null)
{
    rowOnPage = (Integer)request.getAttribute(Constants.ATTR_ROW_ON_PAGE) ;
}

%>

<%
if(currentPage > 0 && rowCount > 0)
{
%>
 <table width="100%" cellspacing="0" cellpadding="2" border="0">
	<tbody>
		<tr>
			<td nowrap="nowrap" bgcolor="lightgrey" align="left">
				<input type="button" value="   &lt;&lt;   " id="btFistPage" name="btFistPage"> 
				<input type="button" value="   &lt;    "    id="btPreviousPage" name="btPreviousPage"> 
				<input type="button" value="    &gt;   " id="btNextPage" name="btNextPage">
				<input type="button" value="   &gt;&gt;   " id="btLastPage" name="btLastPage"> 
				<input type="hidden" value="<%= currentPage %>" id="currentPage" name="currentPage">
			</td>
			<th width="100%" bgcolor="lightgrey" align="LEFT"><nobr>&nbsp;&nbsp;&nbsp;<%=rowCount%>
			Records &nbsp;&nbsp;&nbsp;&nbsp; Page: <%=currentPage%> of <%=totalPage%></nobr>
				&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	<%
	if (currentPage == 1)
	{
	%>
		$("#btFistPage,#btPreviousPage").css("color","lightgrey");
	<%
	}
	else
	{
	%>
	$("#btFistPage").click(function()
	{
		searchCreditReport(1);
	});
	$("#btPreviousPage").click(function()
	{
		searchCreditReport(<%=(currentPage - 1)%>);
	});
	<%
	}
	if (currentPage == totalPage)
	{
	%>
		$("#btNextPage,#btLastPage").css("color","lightgrey");
	<%
	}
	else
	{
	%>
	$("#btNextPage").click(function()
	{
		searchCreditReport(<%=(currentPage + 1)%>);
	});
	$("#btLastPage").click(function()
	{
		searchCreditReport(<%=(totalPage)%>);
	});
	<%
	}
	%>
</script>   
    
<% 
}
%>

<table width="3000" cellpadding="1" cellspacing="1" bgcolor="#CCCCCC">

		<tr>
			<td colspan="11" height="20px" valign="middle">
			<div style="float: right;">
				<A HREF="order_db.do?method=exportExcelCreditReport&datepickerFrom=<%=datepickerFrom %>&datepickerTo=<%=datepickerTo %>"><font color="red"><u>EXPORT TO EXCEL</u></font></A>
			</div>
			<div style="float: left;">
				<font color="#990000"><b>Total Credit: <%out.print(Constants.FormatCurrency(totalResult));%></b></font>
			</div>
			</td>
			
		</tr>
		<tr valign="middle">
			<th ALIGN="center" style="min-width: 30px" BGCOLOR="#DBDBDB"><font size = 2>#</font></th>
			<th ALIGN="LEFT" WIDTH="12%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Name</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Order Number</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Service Tag</font></th>
			<th ALIGN="LEFT" WIDTH="7%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Channel</font></th>
			<th ALIGN="LEFT" WIDTH="3%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Ship_to_State</font></th>
			<th ALIGN="LEFT" WIDTH="10%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Transaction Type</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Credit Date</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Ordered Date</font></th>
			<th ALIGN="right" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Amount Credit</font></th>
			<th ALIGN="right" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Sales Price</font></th>
			<th ALIGN="right" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Mfg Part Number</font></th>
			<th ALIGN="right" WIDTH="3%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Cosmetic</font></th>
			<th ALIGN="right" WIDTH="30%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Short Description</font></th>
			<th ALIGN="LEFT" WIDTH="3%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Category ID</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Tax</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Enviro</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Freight</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Discount</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Total Order</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Bill Email</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Ship Email</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Credit Type</font></th>
			<th ALIGN="right" WIDTH="2%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Total Item</font></th>
			
		</tr>
<%if(orders != null && !orders.isEmpty()){    
			 for(CreditReportOrder order : orders) {
			   		String createdate =order.getCredit_date().substring(0,10);
					createdate=createdate.replaceAll("-","/");
					String year=createdate.substring(0,4);
					String month=createdate.substring(5,7);
					String day=createdate.substring(8,10);
					String dateView=month+"/"+day+"/"+year;
					
					String ordereddate = order.getOrdered_date().substring(0,10);
					ordereddate = ordereddate.replaceAll("-","/");
					String or_year = ordereddate.substring(0,4);
					String or_month = ordereddate.substring(5,7);
					String or_day = ordereddate.substring(8,10);
					String or_dateView = or_month+"/"+or_day+"/"+or_year;
					
%>
		<tr valign="top" onMouseOver="mouse_event(this, 'hlt');" onMouseOut="mouse_event(this, '');">
			<td bgcolor=#99cccc><font size = "2px" style="text-align: center;"><%=order.getId()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getContact_name()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getOrdernumber()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getItem_sku()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getAccount()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getShip_to_state()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getReason()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=dateView%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=or_dateView%></font></td>	
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getAmount())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getSales_price())/100 ));%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getMfg_part_number1()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getCosmetic1()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getShort_description1()%></font></td>
			<%	
			String statusCat2=""+order.getCat2();
			if(statusCat2.equals("")) {%>
			<td bgcolor=#99cccc><font size = 2><%=order.getCat2()%></font></td>
			<%} 						
			 else{ %>
			<td bgcolor=#99cccc><font size = 2><%=order.getCat1()%></font></td>
			<% }%>
			
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getTax_amt())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getRF_amt())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getShip_amt())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getDiscount_amt())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getTotal_amt())));%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getBill_email()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getShip_email()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getCC_type()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getTotal_item()%></font></td>
			
		</tr>
<%} %>	
	</table>

<%
} 

%>

























