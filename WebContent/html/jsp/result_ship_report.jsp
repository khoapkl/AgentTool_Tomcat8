
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Reports"%>

<%
List<Reports> reports =(List<Reports>) request.getAttribute(Constants.SEARCH_RESULT_BY_REPORT_SHIPPING);
String datepickerFrom =(String) request.getAttribute(Constants.DATAPICKER_FROM);
String datepickerTo = (String)request.getParameter(Constants.DATAPICKER_TO);
String valueCheck=(String)request.getAttribute("valueCheck");
if(reports != null && !reports.isEmpty()){ 
	if(valueCheck.equals("html")){	
%>

<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<TABLE border=0>	
	<tr>
		<td bgcolor=#A4A4A4>item_SKU</td>
		<td bgcolor=#A4A4A4>category_id</td>
		<td bgcolor=#A4A4A4>manufacturer_id</td>
		<td bgcolor=#A4A4A4>mfg_part_number</td>
		<td bgcolor=#A4A4A4>name</td>
		<td bgcolor=#A4A4A4>image_url</td>
		<td bgcolor=#A4A4A4>short_description</td>
		<td bgcolor=#A4A4A4>long_description</td>
		<td bgcolor=#A4A4A4>weight</td>
		<td bgcolor=#A4A4A4>download_filename</td>
		<td bgcolor=#A4A4A4>received_by</td>
		<td bgcolor=#A4A4A4>received_date</td>
		<td bgcolor=#A4A4A4>warehouse_location</td>
		<td bgcolor=#A4A4A4>status</td>
		<td bgcolor=#A4A4A4>status_date</td>
		<td bgcolor=#A4A4A4>list_price</td>
		<td bgcolor=#A4A4A4>modified_price</td>
		<td bgcolor=#A4A4A4>modified_date</td>
		<td bgcolor=#A4A4A4>warranty_date</td>
		<td bgcolor=#A4A4A4>flagType</td>
		<td bgcolor=#A4A4A4>lease_number</td>
		<td bgcolor=#A4A4A4>contract_number</td>
		<td bgcolor=#A4A4A4>mfg_SKU</td>
		<td bgcolor=#A4A4A4>notes</td>
		<td bgcolor=#A4A4A4>ship_via</td>
		<td bgcolor=#A4A4A4>attribute01</td>
		<td bgcolor=#A4A4A4>attribute02</td>
		<td bgcolor=#A4A4A4>attribute03</td>
		<td bgcolor=#A4A4A4>attribute04</td>
		<td bgcolor=#A4A4A4>attribute05</td>
		<td bgcolor=#A4A4A4>attribute06</td>
		<td bgcolor=#A4A4A4>attribute07</td>
		<td bgcolor=#A4A4A4>attribute08</td>
		<td bgcolor=#A4A4A4>attribute09</td>
		<td bgcolor=#A4A4A4>attribute10</td>
		<td bgcolor=#A4A4A4>attribute11</td>
		<td bgcolor=#A4A4A4>attribute12</td>
		<td bgcolor=#A4A4A4>attribute13</td>
		<td bgcolor=#A4A4A4>attribute14</td>
		<td bgcolor=#A4A4A4>attribute15</td>
		<td bgcolor=#A4A4A4>attribute16</td>
		<td bgcolor=#A4A4A4>attribute17</td>
		<td bgcolor=#A4A4A4>attribute18</td>
		<td bgcolor=#A4A4A4>attribute19</td>
		<td bgcolor=#A4A4A4>attribute20</td>
		<td bgcolor=#A4A4A4>attribute21</td>
		<td bgcolor=#A4A4A4>attribute22</td>
		<td bgcolor=#A4A4A4>attribute23</td>
		<td bgcolor=#A4A4A4>attribute24</td>
		<td bgcolor=#A4A4A4>attribute25</td>
		<td bgcolor=#A4A4A4>attribute26</td>
		<td bgcolor=#A4A4A4>attribute27</td>
		<td bgcolor=#A4A4A4>attribute28</td>
		<td bgcolor=#A4A4A4>attribute29</td>
		<td bgcolor=#A4A4A4>attribute30</td>
		<td bgcolor=#A4A4A4>attribute31</td>
		<td bgcolor=#A4A4A4>attribute32</td>
		<td bgcolor=#A4A4A4>attribute33</td>
		<td bgcolor=#A4A4A4>attribute34</td>
		<td bgcolor=#A4A4A4>attribute35</td>
		<td bgcolor=#A4A4A4>attribute36</td>
		<td bgcolor=#A4A4A4>attribute37</td>
		<td bgcolor=#A4A4A4>attribute38</td>
		<td bgcolor=#A4A4A4>attribute39</td>
		<td bgcolor=#A4A4A4>attribute40</td>
		<td bgcolor=#A4A4A4>order_number</td>
		<td bgcolor=#A4A4A4>tracking_number</td>
		<td bgcolor=#A4A4A4>ship_date</td>
		<td bgcolor=#A4A4A4>origin</td>		
	</tr>   
	<%	
	 for(Reports report : reports) {
	%>
	 <tr>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getItem_SKU() %></font></td>	
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getCategory_id() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getManufacturer_id() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getMfg_part_number() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getName() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getImage_url() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getShort_description() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getLong_description() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getWeight() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getDownload_filename() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getReceived_by() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getReceived_date() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getWarehouse_location() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getStatus() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getStatus_date() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getList_price() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getModified_price() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getModified_date() %></font></td>	
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getWarranty_date() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getFlagType() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getLease_number() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getContract_number() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getMfg_SKU() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getNotes() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getShip_via() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute01() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute02() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute03() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute04() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute05() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute06() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute07() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute08() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute09() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute10() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute11() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute12() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute13() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute14() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute15() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute16() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute17() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute18() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute19() %></font></td>	
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute20() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute21() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute22() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute23() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute24() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute25() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute26() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute27() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute28() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute29() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute30() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute31() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute32() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute33() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute34() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute35() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute36() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute37() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute38() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute39() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getAttribute40() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getOrder_number() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getTracking_number() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getShip_date() %></font></td>
	<td align=justify nowrap bgcolor=#CAD6DF><font face="Verdana" size = 2><%=report.getOrigin() %></font></td>
	 </tr>    
  
<%	 
 }
 %>
  </TABLE> 
  <%}else{ %>  
	<%		
	 for(Reports report : reports) {
	%>	
<pre>"<%=report.getItem_SKU() %>","<%=report.getCategory_id() %>","<%=report.getManufacturer_id() %>","<%=report.getMfg_part_number() %>","<%=report.getName() %>","<%=report.getImage_url() %>","<%=report.getShort_description()%>","<%=report.getLong_description().trim()%>","<%=report.getWeight()%>","<%=report.getDownload_filename() %>","<%=report.getReceived_by() %>","<%=report.getReceived_date() %>","<%=report.getWarehouse_location() %>","<%=report.getStatus() %>","<%=report.getStatus_date() %>","<%=report.getList_price() %>","<%=report.getModified_price() %>","<%=report.getModified_date() %>","<%=report.getWarranty_date() %>","<%=report.getFlagType() %>","<%=report.getLease_number() %>","<%=report.getContract_number() %>","<%=report.getMfg_SKU() %>","<%=report.getNotes().toString() %>","<%=report.getShip_via() %>","<%=report.getAttribute01() %>","<%=report.getAttribute02() %>","<%=report.getAttribute03() %>","<%=report.getAttribute04() %>","<%=report.getAttribute05() %>","<%=report.getAttribute06() %>","<%=report.getAttribute07() %>","<%=report.getAttribute08() %>","<%=report.getAttribute09() %>","<%=report.getAttribute10() %>","<%=report.getAttribute11() %>","<%=report.getAttribute12() %>","<%=report.getAttribute13() %>","<%=report.getAttribute14() %>","<%=report.getAttribute15() %>","<%=report.getAttribute16() %>","<%=report.getAttribute17() %>","<%=report.getAttribute18()%>","<%=report.getAttribute19() %>","<%=report.getAttribute21() %>","<%=report.getAttribute22() %>","<%=report.getAttribute23() %>","<%=report.getAttribute24() %>","<%=report.getAttribute25() %>","<%=report.getAttribute26() %>","<%=report.getAttribute27() %>","<%=report.getAttribute28() %>","<%=report.getAttribute29() %>","<%=report.getAttribute30() %>","<%=report.getAttribute31() %>","<%=report.getAttribute32() %>","<%=report.getAttribute33() %>","<%=report.getAttribute34() %>","<%=report.getAttribute35() %>","<%=report.getAttribute36() %>","<%=report.getAttribute37() %>","<%=report.getAttribute38() %>","<%=report.getAttribute39() %>","<%=report.getAttribute40() %>","<%=report.getOrder_number() %>","<%=report.getTracking_number() %>","<%=report.getShip_date() %>","<%=report.getOrigin() %>", </pre>
  
<%	 
 }}
 %>  																	

<%
} else {
	out.println("<b>No data for selected date range</b>" );
}
%>


