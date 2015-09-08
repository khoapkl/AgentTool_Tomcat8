<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Product"%>
<%@page import="com.dell.enterprise.agenttool.model.ProductAttribute"%>

<div align="center" style="padding-top: 10px; padding-bottom: 10px">
<table width="485" cellspacing="0" cellpadding="0" border="0"
	align="center">
	<tbody>
		<tr>
			<th bgcolor="#663399" align="left" colspan="3">
			<table cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td width="10"><img width="10" height="1"
							src="/images/+___.gif"></td>
						<td width="485"><img width="1" height="5"
							src="/images/+___.gif"><br>
						<b style="font-size: 8.5pt; color: rgb(255, 255, 255);">Product
						Detail</b><br>
						<img width="1" height="5" src="/images/+___.gif"><br>
						</td>
					</tr>
				</tbody>
			</table>
			</th>
		</tr>
		<tr>
			<td width="1" bgcolor="#663399"><img width="1" height="1"
				src="/images/+___.gif"></td>
			<td width="483" valign="TOP">
			<table width="483" cellspacing="0" cellpadding="10" border="0">
				<tbody>
					<tr>
						<td>
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tbody>
								<tr>
									<td width="60%"></td>
									<td width="40%"></td>
								</tr>

								<%
								    if (request.getAttribute(Constants.ATTR_PRODUCT_DETAIL) != null && request.getAttribute(Constants.ATTR_PRODUCT_DETAIL_ATTRIBUTE) != null)
								    {
								        Product product = (Product) request.getAttribute(Constants.ATTR_PRODUCT_DETAIL);
								        List<ProductAttribute> listProductAttribute = (List<ProductAttribute>) request.getAttribute(Constants.ATTR_PRODUCT_DETAIL_ATTRIBUTE);

								        Map<String, String> proAttributes = product.getAttributes();

								        for (int i = 0; i < listProductAttribute.size(); i++)
								        {
								            ProductAttribute productAttribute = (ProductAttribute) listProductAttribute.get(i);
								%>
										<tr>
											<td><font size="2" face="Arial, Helvetica"><b><%=productAttribute.getAttribute_name()%></b></font>
											</td>
											<td><font size="2" face="Arial, Helvetica">
															<%
															if(productAttribute.getAttribute_name().toLowerCase().equals("operating system"))
															{
																out.println("<b>"+proAttributes.get(productAttribute.getAttribute_number())+"</b>");    
															}else{
															    out.println(proAttributes.get(productAttribute.getAttribute_number()));
															}
															%>
														     
												</font>
											</td>
										</tr>
										<tr>
											<td height="1" bgcolor="#f5f5f5" colspan="3"><img
												width="1" height="1" src="/images/+___.gif"></td>
										</tr>
								<%
								    	}
								    }
								%>



							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
			<td width="1" bgcolor="#663399"><img width="1" height="1"
				src="/images/+___.gif"></td>
		</tr>
		<tr>
			<td width="485" height="1" bgcolor="#663399" colspan="3"><img
				width="1" height="1" src="/images/+___.gif"></td>
		</tr>
	</tbody>
</table>

<br>

<table width="485" cellspacing="0" cellpadding="0" border="0"
	align="center">
	<tbody>
		<tr>
			<th bgcolor="#663399" align="left" colspan="3">
			<table cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td width="10"><img width="10" height="1"
							src="/images/+___.gif"></td>
						<td width="485"><img width="1" height="5"
							src="/images/+___.gif"><br>
						<b style="font-size: 8.5pt; color: rgb(255, 255, 255);">Warranty
						Information</b><br>
						<img width="1" height="5" src="/images/+___.gif"><br>
						</td>
					</tr>
				</tbody>
			</table>
			</th>
		</tr>
		<tr>
			<td width="1" bgcolor="#663399"><img width="1" height="1"
				src="/images/+___.gif"></td>
			<td width="483" valign="TOP">
			<table width="483" cellspacing="0" cellpadding="10" border="0">
				<tbody>
					<tr>
						<td><font size="2" face="Arial, Helvetica"> To view
						remaining warranty (Systems only, not monitors), follow these
						steps:<br>
						<br>
						</font>
						<ol>
							<font size="2" face="Arial, Helvetica">
							<li>Note the item's Product # (5-8 characters in blue)
							located on the<br>
							Product listings page (you will need to enter this in Step 5).<br>
							</li>
							<li>Go to <a href="http://www.dell.com/support"
								target="_blank">www.dell.com/support</a>.<br>
							</li>
							<li>If this is your first visit to Dell Support, select your
							customer type on the Dell Support Welcome page and go to Step 5,
							otherwise continue with Step 4.<br>
							</li>
							<li>Click "Select another system" link in the left hand
							navigation.<br>
							</li>
							<li>Enter the 5-8 character Product # into the "Enter a
							service tag" box and click Go.<br>
							</li>
							<li>Click the "Warranty" link in the left hand navigation.<br>
							</li>
							</font>
						</ol>
						<font size="2" face="Arial, Helvetica"> Note: Not all
						systems will have remaining warranty. </font>
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tbody>
								<tr>
									<td width="60%"></td>
									<td width="40%"></td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
			<td width="1" bgcolor="#663399"><img width="1" height="1"
				src="/images/+___.gif"></td>
		</tr>
		<tr>
			<td width="485" height="1" bgcolor="#663399" colspan="3"><img
				width="1" height="1" src="/images/+___.gif"></td>
		</tr>
	</tbody>
</table>
<font size="2" face="Arial, Helvetica"><br>

</font>
</div>