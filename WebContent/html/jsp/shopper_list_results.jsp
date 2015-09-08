<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<%@page import="java.util.Map"%>
<%
	List<Shopper> shopperList = (List<Shopper>) session.getAttribute("shopper_list");
    int shopperCount = (Integer) session.getAttribute("shopper_count");
    int pageCount = (Integer) session.getAttribute("shopper_page_count");
    int currentPage = (Integer) session.getAttribute("shopper_current_page");
%>

<div id="navigation" align="left">
<table cellspacing="3" border="0">
	<tr>
		<td bgcolor="lightgrey" align="center">
			<b><%=shopperCount %>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp;Page: <%=currentPage %> of <%=pageCount %></b>
		</td>
	</tr>
	<tr>
		<td>
			<div id="pagingDiv">
				<input type="Button" value="   &lt;&lt;   " id="firstPage" />
				<input type="Button" value="   &lt;    " id="prevPage" />
	
				<input type="Button" value="    &gt;   " id="nextPage" />
				<input type="Button" value="   &gt;&gt;   " id="lastPage" />
	
				<input type="Button" value=" Requery " id="requery" />
			</div>
		</td>
	</tr>
</table>
</div>
<table style="border: 0; padding: 2; width: 100%;">
	<tr>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Customer#</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB" colspan="3">Shipping</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB" colspan="3">Billing</TH>
	</tr>
	<tr>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">&nbsp;</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Name</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Company</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Phone</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Name</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Company</TH>
		<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">Phone</TH>
	</tr>
<%
    for (Iterator<Shopper> iterator = shopperList.iterator(); iterator.hasNext();)
    {
        Shopper shopper = iterator.next();
%>
	<tr>
		<td valign=top align=left><%=shopper.getShopperNumber()%></td>
		<td valign=top align=left>
			<a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=shopper.getShopperId() %>&from=shopper_result" class="shopper"><%=shopper.getShipToFName() + "&nbsp;" + shopper.getShipToLName()%></a>
		</td>
		<td valign=top align=left><%=shopper.getShipToCompany()%></td>
		<td valign=top align=left><%=shopper.getShipToPhone()%></td>
		<td valign=top align=left><%=shopper.getBillToFName() + "&nbsp;" + shopper.getBillToLName()%></td>
		<td valign=top align=left><%=shopper.getBillToCompany()%></td>
		<td valign=top align=left><%=shopper.getBillToPhone()%></td>
	</tr>
<%
    }
%>
</table>

<%@include file="/html/scripts/shopper_list_results_script.jsp" %> 