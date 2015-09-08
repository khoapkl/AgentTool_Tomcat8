<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
List<String> listVis = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_VIS);
List<String> listDocking = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_DOCKING);
List<String> listServer = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandServer);
List<String> listLaptop = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandLaptop);
List<String> listTablet = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandTablet);
List<String> listDesktop = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandDesktop);
List<String> listWorkstation = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandWorkstation);

List<String> listPart = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandPart);
List<String> listWarranty = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_BrandWarranty);
List<String> listCosmetic = (List<String>)request.getAttribute(Constants.ORDER_AGENT_REPORT_COSMETIC);
%>


<html>
<form name="report">
<table cellpadding="0" cellspacing="0" >
	<tr>
		<td>
		<table align="left" height="30" bgcolor="white"
			cellspacing="5" cellpadding="5" border="0">
			<tr valign="top">
				<td align="left">Date Range:</td>
				<td ><b>From Date:  </b><input type="text" id="datepickerFrom" readonly="readonly" name="datebox1" value="<%=Constants.getCurrentDate() %>" ></input>(MM/DD/YYYY)  <b>To Date:  </b>
				<input type="text" id="datepickerTo" name="datebox2" readonly="readonly" value="<%=Constants.getCurrentDate() %>"></input>(MM/DD/YYYY)</td>
			</tr>
			<tr>
				<td align="left" valign="middle" width="140">Operating
				System:&nbsp;</td>
				<td align="left"><input type="radio" name="ostype" value="%win"
					name="Yes" onclick="selectOS(this.value);" />Yes <input
					type="radio" name="ostype" value="no" name="No"
					onclick="selectOS(this.value);" />No <input
					type="radio" name="ostype" value="" checked="checked"
					onclick="selectOS(this.value);" />All</td>
			</tr>
			<tr>
				<td align="left" valign="middle" width="140">Cosmetic Grade :</td>
				<td><select name="cosmetic" id="cosmetic">
						<option value="all">ALL</option>
						<%if(null != listCosmetic && !listCosmetic.isEmpty()) for(String str : listCosmetic){ %>
							<option value="<%=str %>"><%=str %></option>
							<%} %>
					</select>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="20"></td>
	</tr>
	<tr>
		<td>
		<table align="left" bgcolor="white" border="0" cellpadding="5"
			cellspacing="0" >
			<tr>
				<td bgcolor="#6600FF"><font color="#FFFFFF"><u><b>Category:</b></u></font>
				<table cellpadding="0" cellspacing="5" border="0">
					<tr>
						<td>Available:&nbsp;<br />
						<select size="10" name="catList" multiple id="catList">
							<option value="11946">Notebooks</option>
							<option value="11949">Desktops</option>
							<option value="11947">Workstations</option>
							<option value="11964">Tablets</option>
							<option value="11950">Servers</option>
							<option value="11955">Monitors</option>
							<option value="11958">Docking Stations</option>
							<option value="11956">Projectors</option>
							<option value="11957">Printers</option>
							<option value="11959">Memories</option>
							<option value="11960">Batteries</option>
							<option value="11961">Storage</option>
							<option value="11962">Networking</option>
							<option value="11963">Parts</option>
							<option value="11940">Warranty</option>
						</select></td>
						<td><input type="button" id="idAddSrcToDestList" value=" >> "
							onClick="addSrcToDestList(report.catList,report.catselList)"><br />
						<br />
						<input type="button" id="iddeleteFromDestList" value=" << "
							onClick="removeCatelogy(report.catselList);">
						</td>
						<td>Selected:<br /><div id="catlDiv">
						<select size="10" name="catselList" multiple>
						</select></div></td>
					</tr>
				</table>
				</td>
				<td bgcolor="#3399FF"><font color="#FFFFFF"><u><b>Brand
				Type:</b></u></font>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>Available:&nbsp;<br />
						<select size="10" name="brandList" multiple>
							<option value="">--NoteBooks--</option>
							<%if(null != listLaptop&& !listLaptop.isEmpty()) for(String str4 : listLaptop){%>
							<option value="<%=str4 %>"><%=str4 %></option>
							<%} %>
							<option value="">--Desktops--</option>							
							<%if(null != listDesktop&& !listDesktop.isEmpty()) for(String str5 : listDesktop){%>
							<option value="<%=str5 %>"><%=str5 %></option>
							<%} %>
							<option value="">--Workstations--</option>							
							<%if(null != listWorkstation&& !listWorkstation.isEmpty()) for(String str6 : listWorkstation){%>
							<option value="<%=str6 %>"><%=str6 %></option>
							<%} %>
							<option value="">--Tablets--</option>							
							<%if(null != listTablet&& !listTablet.isEmpty()) for(String str8 : listTablet){%>
							<option value="<%=str8 %>"><%=str8 %></option>
							<%} %>
							<option value="">--Monitors--</option>
							<%if(null != listVis&& !listVis.isEmpty()) for(String str : listVis){ %>
							<option value="<%=str %>"><%=str %></option>
							<%} %>
			                <option value="">--Dockings--</option>
							<%if(null != listDocking&& !listDocking.isEmpty()) for(String str : listDocking){ %>
							<option value="<%="DOCKING"+str %>"><%=str %></option>
							<%} %>
							<option value="">--Servers--</option>
							<%if(null != listServer&& !listServer.isEmpty()) for(String str2 : listServer){ %>
							<option value="<%=str2 %>"><%=str2 %></option>
							<%} %>
							<option value="">--Parts--</option>
							<%if(null != listPart&& !listPart.isEmpty()) for(String str3 : listPart){ %>
							<option value="<%=str3 %>"><%=str3 %></option>
							<%} %>
							<option value="">--Warranty--</option>
							<%if(null != listWarranty&& !listWarranty.isEmpty()) for(String str7 : listWarranty){ %>
							<option value="<%=str7 %>"><%=str7 %></option>
							<%} %>
						</select></td>
						<td><input type="button" value=" >> "
							onClick="addProcessor(report.brandList, report.brandselList, report.catselList, report.datebox1.value, report.datebox2.value);"><br />
						<br />
						<input type="button" value=" << "
							onClick="removeProcessor(report.brandselList, report.catselList, report.datebox1.value, report.datebox2.value);">
						</td>
						<td>Selected:<br /><div id="brandselDiv">
						<select size="10" name="brandselList" multiple>
						</select></div></td>
					</tr>
				</table>
				</td>
				<td bgcolor="#6666FF"><font color="#FFFFFF"><u><b>Processor
				Type:</b></u></font>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>Available:&nbsp;<br />
						<div id="processorList"><select size="10"
							name="processorList" multiple>
						</select></div>
						</td>
						<td><input type="button" value=" >> "
							onClick="addSrcToDestList(report.processorList, report.processorselList); addModel(report.brandList, report.brandselList, report.catselList, report.datebox1.value, report.datebox2.value, report.processorselList);"><br />
						<br />
						<input type="button" value=" << "
							onClick="deleteFromDestList(report.processorselList); removeModel(report.brandselList, report.catselList, report.processorselList, report.datebox1.value, report.datebox2.value);">
						</td>
						<td>Selected:<br /><div id="processorselDiv">
						<select size="10" name="processorselList" multiple>
						</select></div></td>
					</tr>
				</table>
				</td>
				<td bgcolor="#6666FF"><font color="#FFFFFF"><u><b>Model
				Number:</b></u></font>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>Available:&nbsp;<br />
						<div id="modelLoc"><select size="10" name="modelList"
							multiple>
						</select></div>
						</td>
						<td><input type="button" value=" >> "
							onClick="addSrcToDestList(report.modelList, report.modelselList);"><br />
						<br />
						<input type="button" value=" << "
							onClick="deleteFromDestList(report.modelselList);">
						</td>
						<td>Selected:<br /><div id="modelselDiv">
						<select size="10" name="modelselList" multiple>
						</select></div></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
			<td colspan="3"><input type="reset" name="clear"
					value="Clear Selected" onclick="clearSelected();"></td>
				<td ><input type="button" name="search"
					value="Show Report" onClick="runReport();"></td>
					
			</tr>
			<tr valign="top">
				<td colspan="4">
				<div id="report_result"></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%@include file="/html/scripts/agent_reportScript.jsp"%>
<%@include file="/html/scripts/calendarScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>