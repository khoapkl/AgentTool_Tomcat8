<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<%@page import="java.util.List"%>
<%
	List<Shopper> newShoppersByDay = (List<Shopper>) request.getAttribute("new_shoppers_by_day");
	DateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
%>
<br/>
Time Period: 
<a href="new_shoppers.do?method=getNewShoppersByYear&year=<%=request.getParameter("year") %>&type=0"><%=request.getParameter("year") %></a> 
/ <a href="new_shoppers.do?method=getNewShoppersByMonth&year=<%=request.getParameter("year") %>&month=<%=request.getParameter("month") %>"><%=Constants.MONTHS[Integer.valueOf(request.getParameter("month")) - 1] %></a> 
/ <%=request.getParameter("day") %>

<div id="day_result">
<%
	int totalRecord = (Integer) request.getAttribute(Constants.ORDER_TOTAL);
	int pageRecord = Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
	
	if (totalRecord > pageRecord) {
		int numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
		int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>

<table cellspacing="3" border="0" class="main">
        <tbody><tr>
            <td bgcolor="lightgrey" align="center"><b>
                <nobr><span id="totalRecord"><%=totalRecord %></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp; Page: <span id="noPage"><%=noPage %></span> of <span id="maxPage"><%=numOfPage %></span></nobr>
            </b></td>
        </tr>
        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingNewShoppersByDay","day_result");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingNewShoppersByDay","day_result");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingNewShoppersByDay","day_result");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingNewShoppersByDay","day_result");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingNewShoppersByDay","day_result");' />
                 </div>
            </td>
        </tr>
  </tbody></table>
<%
	}
%>

<form id="frmViewShopper" action="shopper_manager.do?method=getShopperDetails" method="post">
<table border="0" cellpadding="2" width="600">
	<tr>
		<td align="right" width="50%" valign="bottom"><b>Shopper</b></td>
		<td align="right" width="50%" valign="bottom"><b>Time Registered</b></td>
	</tr>
<%
	for (Iterator<Shopper> iterator = newShoppersByDay.iterator(); iterator.hasNext();) {
	    Shopper shopper = iterator.next();
%>
	<tr>
		<td align="right"><a href="javascript:void(0)" class="shopper" id="<%=shopper.getShopperId() %>"><%=shopper.getShipToFName() %> <%=shopper.getShipToLName() %></a></td>
		<td align="right"><%=df.format(shopper.getCreatedDate()) %></td>
	</tr>
<%
	}
%>
</table>
<input type="hidden" id="shopperId" name="shopperId" />
</form>
</div>

<%@include file="/html/scripts/shopper_day_script.jsp" %>