
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.EppReport"%>
<%
List<EppReport> reports =(List<EppReport>) request.getAttribute("agenttool.epp_report.result");
String output_type=(String)request.getAttribute("output_type");
String status=(String)request.getAttribute("status");
	if(output_type.equals("html")){
		if(status.equals("promotion")){		
%>


<%@page import="com.dell.enterprise.agenttool.util.Constants"%><TABLE border=1>
	<tr>
		<td bgcolor=#A4A4A4>epp_id</td>
		<td bgcolor=#A4A4A4>description</td>
		<td bgcolor=#A4A4A4>shopper_id</td>
		<td bgcolor=#A4A4A4>createdDate</td>
		<td bgcolor=#A4A4A4>orderNumber</td>
		<td bgcolor=#A4A4A4>est_subtotal</td>
		<td bgcolor=#A4A4A4>volume_discount</td>
		<td bgcolor=#A4A4A4>dfs_discount</td>
		<td bgcolor=#A4A4A4>cor_discount</td>
		<td bgcolor=#A4A4A4>shipping_total</td>
		<td bgcolor=#A4A4A4>total_total</td>
	</tr>
	<%	
	if(reports != null && !reports.isEmpty()){ 
	 for(EppReport report : reports) {
	%>
	<tr>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getEpp_id() %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getDescription() %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getShopper_id()%></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getCreatedDate() %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getOrderNumber() %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=Constants.FloatRound(new Float(report.getEst_subtotal())) %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=Constants.FloatRound(new Float (report.getVolume_discount())) %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=Constants.FloatRound(new Float (report.getDfs_discount())) %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=Constants.FloatRound(new Float (report.getCor_discount())) %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=Constants.FloatRound(new Float (report.getShipping_total()))%></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=Constants.FloatRound(new Float (report.getTotal_total())) %></font></td>
	</tr>

	<%}
	%>
</TABLE>
<%	}}else{
	if(status.equals("asset")){
%>
<TABLE border=1>
	<tr>
		<td bgcolor=#A4A4A4>orderNumber</td>
		<td bgcolor=#A4A4A4>item_sku</td>
		<td bgcolor=#A4A4A4>name</td>
		<td bgcolor=#A4A4A4>product_list_price</td>
	</tr>
	<%	
	if(reports != null && !reports.isEmpty()){ 
	 for(EppReport report : reports) {
	%>
	<tr>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getOrderNumber() %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getItem_sku() %></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getName()%></font></td>
		<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana"
			size=2><%=report.getProduct_list_price() %></font></td>
	</tr>

	<%}}
	%>
</TABLE>
<%}}%>
<%}else{ 
	  if(status.equals("promotion")){	
		  if(reports != null && !reports.isEmpty()){ 
	 for(EppReport report : reports) {%>
<pre>"<%=report.getEpp_id() %>","<%=report.getDescription() %>","<%=report.getShopper_id()%>","<%=report.getCreatedDate() %>","<%=report.getOrderNumber() %>","<%=report.getEst_subtotal() %>","<%=report.getVolume_discount() %>","<%=report.getDfs_discount() %>","<%=report.getCor_discount() %>","<%=report.getShipping_total()%>","<%=report.getTotal_total() %>"</pre>  
<%	 
 }}}else{
	 if(status.equals("asset")){
	if(reports != null && !reports.isEmpty()){ 
	 for(EppReport report : reports) { %>
<pre>"<%=report.getOrderNumber() %>","<%=report.getItem_sku() %>","<%=report.getName()%>","<%=report.getProduct_list_price() %>"</pre> 
<%}}}}}
 %>





