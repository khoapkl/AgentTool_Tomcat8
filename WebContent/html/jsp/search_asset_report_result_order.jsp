<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.SoldAssetReport"%>
<%@include file="/html/scripts/search_asset_report_result_orderScript.jsp"%>
<%@page import="java.util.List"%><%
List<SoldAssetReport> orders =(List<SoldAssetReport>) request.getAttribute(Constants.LIST_SEARCH_ASSET_REPORT);
String total =(String) request.getAttribute(Constants.VIEW_TOTAL_CREDIT_REPORT_ORDER);
String datepickerFrom=(String)request.getAttribute(Constants.DATAPICKER_FROM);
String datepickerTo= (String)request.getAttribute(Constants.DATAPICKER_TO);
String assetDateType = (String)request.getAttribute(Constants.ASSETDATETYPE);
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
		searchAssetReport(1);
	});
	$("#btPreviousPage").click(function()
	{
		searchAssetReport(<%=(currentPage - 1)%>);
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
		searchAssetReport(<%=(currentPage + 1)%>);
	});
	$("#btLastPage").click(function()
	{
		searchAssetReport(<%=(totalPage)%>);
	});
	<%
	}
	%>
</script>   
    
<% 
}
%>

<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#CCCCCC">

		<tr>
			<td colspan="11" height="20px" valign="middle">
			<div style="float: right;">
				<A HREF="order_db.do?method=exportExcelAssetReport&datepickerFrom=<%=datepickerFrom %>&datepickerTo=<%=datepickerTo %>&datetype=<%=assetDateType %>"><font color="red"><u>EXPORT TO EXCEL</u></font></A>
			</div>
			
			</td>
			
		</tr>
		<tr valign="middle">
			<th ALIGN="center" style="min-width: 30px" BGCOLOR="#DBDBDB"><font size = 2>#</font></th>
			<th ALIGN="LEFT" WIDTH="10%" height="25" BGCOLOR="#DBDBDB"><font size = 2>OOR Date</font></th>
			<th ALIGN="LEFT" WIDTH="10%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Serial Number</font></th>
			<th ALIGN="LEFT" WIDTH="10%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Item Status</font></th>
			<th ALIGN="LEFT" WIDTH="10%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Status Change Date</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Order Number</font></th>
			<th ALIGN="LEFT" WIDTH="10%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Order Date</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Order Total</font></th>
			<th ALIGN="right" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Order Tax</font></th>
			<th ALIGN="right" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Order Shipping Cost</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Sold Price</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Modified Price</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Category ID</font></th>
			<th ALIGN="LEFT" WIDTH="15%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Description</font></th>
		</tr>
<%if(orders != null && !orders.isEmpty()){    
			 for(SoldAssetReport order : orders) {
			   		String exporteddate =order.getExported_date().substring(0,10);
			   		exporteddate=exporteddate.replaceAll("-","/");
					String year=exporteddate.substring(0,4);
					String month=exporteddate.substring(5,7);
					String day=exporteddate.substring(8,10);
					String exportdateView=month+"/"+day+"/"+year;
					
					String orderdate =order.getOrder_date().substring(0,10);
					orderdate=orderdate.replaceAll("-","/");
					String ordyear=orderdate.substring(0,4);
					String ordmonth=orderdate.substring(5,7);
					String ordday=orderdate.substring(8,10);
					String orderdateView=ordmonth+"/"+ordday+"/"+ordyear;
					
%>
		<tr valign="top" onMouseOver="mouse_event(this, 'hlt');" onMouseOut="mouse_event(this, '');">
			<td bgcolor=#99cccc><font size = "2px" style="text-align: center;"><%=order.getId()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getExported_date()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getItem_sku()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getItem_status()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getItem_status_date()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getOrder_number()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getOrder_date()%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getOrder_total())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getTax_total())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getShip_total())));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getSold_price()) / 100));%></font></td>
			<td bgcolor=#99cccc align="right"><font size = "2px"><%out.print(Constants.FormatCurrency(new Float(order.getModified_price())));%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getCategory_id()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getDescription()%></font></td>
		</tr>
<%} %>	
	</table>

<%
} 

%>

























