<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%
	Map<Integer, String> newShoppersByYear = (Map<Integer, String>) request.getAttribute("new_shoppers_by_year");
	TreeSet<Integer> keys = new TreeSet<Integer>(newShoppersByYear.keySet());
%>

<form action="new_shoppers.do?method=getNewShoppersByMonth" method="post">
<table border="0" cellpadding="2" width="600">
	<tr>
		<td align="right" width="50%" valign="bottom"><b>Month</b></td>
		<td align="right" width="50%" valign="bottom"><b>New Shoppers</b></td>
	</tr>
	
<%
	for (Integer key: keys) {
%>
    <tr>
    	<td align="right"><a id="<%=key %>" class="month" href="javascript:void(0)"><%=Constants.MONTHS[key - 1] %></a></td>
    	<td align="right"><%=newShoppersByYear.get(key) %></td>
    </tr>
<%
	}
%>
</table>
<input type="hidden" id="year" name="year" />
<input type="hidden" id="month" name="month" />
</form>
<script>
var iyear = '<%=request.getAttribute("iyear")%>';
$('#pageTitle').html("New Shoppers by Year: "+iyear);
$('#topTitle').html("New Shoppers by Year: "+iyear);
</script>
<%@include file="/html/scripts/shopper_year_results_script.jsp" %>