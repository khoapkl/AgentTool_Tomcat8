<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);
%>
<div>
<table width="600" cellpadding="10">
	<tr>
		<td valign="top">
		<table width="100%">
			<tr>
				<th colspan="2" align="left" bgcolor="#E0E0E0">General Tools</th>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<th align="left"><A HREF="shopper_list.do"> All Shoppers </A>
				</th>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<th align="left"><A HREF="new_shoppers.do?method=getNewShoppersByYear"> New Shoppers
				by Date </A></th>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<script>
$('#pageTitle').text("Shopper Manager");
$('#topTitle').text("Shopper Manager");
</script>