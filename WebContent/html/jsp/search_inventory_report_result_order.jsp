<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.SummaryInventoryReport"%>
<%@include file="/html/scripts/search_inventory_report_result_orderScript.jsp"%>
<%@page import="java.util.List"%><%
List<SummaryInventoryReport> orders =(List<SummaryInventoryReport>) request.getAttribute(Constants.LIST_SEARCH_SUMMARY_INVENTORY_REPORT);
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

<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#CCCCCC">

		<tr>
			<td colspan="11" height="20px" valign="middle">
			<div style="float: right;">
				<A HREF="order_db.do?method=exportExcelInventoryReport"><font color="red"><u>EXPORT TO EXCEL</u></font></A>
			</div>
			
			</td>
			
		</tr>
		<tr valign="middle">
			<th ALIGN="center" style="min-width: 2px" BGCOLOR="#DBDBDB"><font size = 2>#</font></th>
			<th ALIGN="LEFT" WIDTH="45%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Short Description</font></th>
			<th ALIGN="LEFT" WIDTH="5%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Quantity</font></th>
			<th ALIGN="LEFT" WIDTH="8%" height="25" BGCOLOR="#DBDBDB"><font size = 2>List Price</font></th>
			<th ALIGN="LEFT" WIDTH="8%" height="25" BGCOLOR="#DBDBDB"><font size = 2>40% of List</font></th>
			<th ALIGN="LEFT" WIDTH="8%" height="25" BGCOLOR="#DBDBDB"><font size = 2>50% of List</font></th>
			<th ALIGN="LEFT" WIDTH="8%" height="25" BGCOLOR="#DBDBDB"><font size = 2>60% of List</font></th>
			<th ALIGN="LEFT" WIDTH="15%" height="25" BGCOLOR="#DBDBDB"><font size = 2>Status</font></th>
		</tr>
<%if(orders != null && !orders.isEmpty()){    
			 for(SummaryInventoryReport order : orders) {
			   		//String createdate =order.getCredit_date().substring(0,10);
					//createdate=createdate.replaceAll("-","/");
					//String year=createdate.substring(0,4);
					//String month=createdate.substring(5,7);
					//String day=createdate.substring(8,10);
					//String dateView=month+"/"+day+"/"+year;
					
%>
		<tr valign="top" onMouseOver="mouse_event(this, 'hlt');" onMouseOut="mouse_event(this, '');">
			<td bgcolor=#99cccc><font size = "2px" style="text-align: center;"><%=order.getId()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getshort_description()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getitem_count()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getList_price()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getList_price40()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getList_price50()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getList_price60()%></font></td>
			<td bgcolor=#99cccc><font size = "2px"><%=order.getitem_status()%></font></td>
		</tr>
<%} %>	
	</table>

<%
} 

%>

























