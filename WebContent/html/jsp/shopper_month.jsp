<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Map"%>
<%
	Map<Integer, Integer> newShoppersByMonth = (Map<Integer, Integer>) request.getAttribute("new_shoppers_by_month");
	TreeSet<Integer> keys = new TreeSet<Integer>(newShoppersByMonth.keySet());
%>

<%@page import="com.dell.enterprise.agenttool.util.Converter"%><br/>
Time Period: <a href="new_shoppers.do?method=getNewShoppersByYear&year=<%=request.getParameter("year") %>&type=0"><%=request.getParameter("year") %></a> / <%=Constants.MONTHS[Integer.valueOf(request.getParameter("month")) - 1] %>

<table border="0" cellpadding="2" width="600">
	<tr>
		<td align="right" width="50%" valign="bottom"><b>Day</b></td>
		<td align="right" width="50%" valign="bottom"><b>New Shoppers</b></td>
	</tr>
<%
	for (Integer key: keys) {
%>
	<tr>
		<td align="right"><a href="new_shoppers.do?method=getNewShoppersByDay&year=<%=request.getParameter("year") %>&month=<%=request.getParameter("month") %>&day=<%=key %>"><%=key %></a></td>
		<td align="right"><%=newShoppersByMonth.get(key) %></td>
	</tr>
<%
	}
%>
</table>
<script>
$('#pageTitle').html("New Shoppers by Month: "+"<%=Converter.changeRequest(request.getParameter("month")) %>"+" / "+"<%=request.getParameter("year") %>");
$('#topTitle').html("New Shoppers by Month: "+"<%=Converter.changeRequest(request.getParameter("month")) %>"+" / "+"<%=request.getParameter("year") %>");
</script>