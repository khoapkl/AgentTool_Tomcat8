<font size="2" face="Arial, Helvetica">
				
				<img alt="Agent Tool" id="imgDefault" name="imgDefault" src="images/welcome_to_dfs_direct_sale.GIF">
				<br>
				<table width="800" cellspacing="0" cellpadding="0" border="0" align="center">
					<tbody><tr>
					  <td width="25%" align="center"><a href="searchProduct.do?method=category&amp;category_id=11946"><img width="110" hspace="2" border="0" src="images/Latitude_new.jpg"></a><br>					    
				      <font size="2" face="Arial, Helvetica"><a href="searchProduct.do?method=category&amp;category_id=11946">Notebooks</a></font><a href="searchProduct.do?method=category&amp;category_id=11946"></a></td>
					  <td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11949"><img width="130" hspace="2" border="0" src="images/dimension_desktop_new.jpg"><br>
				      <font size="2" face="Arial, Helvetica">Desktops</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11947"><img width="130" hspace="2" border="0" src="images/workstation_new.jpg"><br>
				        <font size="2" face="Arial, Helvetica">Workstations</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11955"><img width="100" hspace="2" border="0" src="images/monitor_new.jpg"><br>
				          <font size="2" face="Arial, Helvetica">Monitors</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11950"><img width="80" hspace="2" border="0" src="images/dept_servers.jpg"><br>
				            <font size="2" face="Arial, Helvetica">Servers</font></a><font size="2" face="Arial, Helvetica"></font></td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						
						<td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11958"><img width="100" hspace="2" border="0" src="images/Dockingstation.jpg"><br>
					  <font size="2" face="Arial, Helvetica">Docking Station</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11956"><img width="90" hspace="2" border="0" src="images/dept_projector.jpg"><br>
					    <font size="2" face="Arial, Helvetica">Projectors</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11957"><img width="80" hspace="2" border="0" src="images/dept_printer.jpg"><br>
					      <font size="2" face="Arial, Helvetica">Printers</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11959"><img width="100" hspace="2" border="0" src="images/memory.jpg"><br>
					        <font size="2" face="Arial, Helvetica">Memory</font></a><font size="2" face="Arial, Helvetica"></font></td><td width="25%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11960"><img width="100" hspace="2" border="0" src="images/dept_battery.jpg"><br>
					          <font size="2" face="Arial, Helvetica">Batteries</font></a><font size="2" face="Arial, Helvetica"></font></td>
						
					</tr>
					<tr>
						<td width="20%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11964"><img  width=120 hspace="2" border="0" src="images/Ipad_Image2015.jpg"><br>
					          <font size="2" face="Arial, Helvetica">Tablets</font></a><font size="2" face="Arial, Helvetica"></font></td>
						<td width="20%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11961"><img  width=120 hspace="2" border="0" src="images/ng_storage.jpg"><br>
					          <font size="2" face="Arial, Helvetica">Storage</font></a><font size="2" face="Arial, Helvetica"></font></td>
					    <td width="20%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11962"><img  width=120 hspace="2" border="0" src="images/ng_networking_sub_cat.jpg"><br>
					          <font size="2" face="Arial, Helvetica">Networking</font></a><font size="2" face="Arial, Helvetica"></font></td>
					    <td width="20%" align="center"><a class="nounderline" href="searchProduct.do?method=category&amp;category_id=11963"><img   width=120 hspace="2" border="0" src="images/ng_parts_sub_cat.jpg"><br>
					          <font size="2" face="Arial, Helvetica">Parts</font></a><font size="2" face="Arial, Helvetica"></font></td>	
					    <td width="10%" align="center"></td>				                    
					</tr>
					</tbody>
				</table>
				<table width="800" border="0" align="center">
					<tbody><tr>
						<td width="16%">&nbsp;</td>
					</tr>
				</tbody></table>				
				
				<br> 
				<table width="800" border="0" align="center">
					<tbody><tr>
						<td width="16%">&nbsp;</td>
						<td width="33%" align="center">
						<a class="nounderline" href="order_db.do" target="_parent"><img hspace="2" border="0" src="images/dept_orders.gif">
								<br>
								<font size="2" face="Arial, Helvetica">Orders</font></a><font size="2" face="Arial, Helvetica"></font>							
						</td>
						<%
						Object isCustomer = session.getAttribute(com.dell.enterprise.agenttool.util.Constants.IS_CUSTOMER);
						if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){
						%>
						<td width="33%" align="center">
							<a class="nounderline" href="shopper_manager.do" target="_parent"><img hspace="2" border="0" src="images/dept_shoppers.gif">
								<br>
								<font size="2" face="Arial, Helvetica">Shoppers</font></a><font size="2" face="Arial, Helvetica"></font>
						</td>
						<%
						}
						%>
						<td width="16%">&nbsp;</td>
					</tr>
				</tbody></table>
				
</font>