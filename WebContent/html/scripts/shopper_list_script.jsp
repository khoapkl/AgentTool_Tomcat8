
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%><script>
$(document).ready(function() {
	$('#pageTitle').text("Shopper Search");
	$('#topTitle').text("Shopper Search");
	
	$('#shopperListFilter').click(function() {
		filterShopperList();
	});
	
	$('#shopperSearchForm').submit(function () {
		return false;
	});

<%
if (request.getAttribute("backPage") != null) {
	Shopper shopperSearchCriteria = (Shopper) session.getAttribute(Constants.SHOPPER_SEARCH_CRITERIA);
%>	
	getBackListShopper();
<%
	if (shopperSearchCriteria != null) {
%>
	$('#linknumber').val('<%=shopperSearchCriteria.getShopperNumber() == 0 ? "" : shopperSearchCriteria.getShopperNumber() %>');
	$('#ship_to_email').val('<%=shopperSearchCriteria.getShipToEmail() %>');
	$('#ship_to_fname').val('<%=shopperSearchCriteria.getShipToFName() %>');
	$('#ship_to_lname').val('<%=shopperSearchCriteria.getShipToLName() %>');
	$('#ship_to_company').val('<%=shopperSearchCriteria.getShipToCompany() %>');
	$('#ship_to_phone').val('<%=shopperSearchCriteria.getShipToPhone() %>');
	$('#bill_to_fname').val('<%=shopperSearchCriteria.getBillToFName() %>');
	$('#bill_to_lname').val('<%=shopperSearchCriteria.getBillToLName() %>');
	$('#bill_to_company').val('<%=shopperSearchCriteria.getBillToCompany() %>');
	$('#bill_to_phone').val('<%=shopperSearchCriteria.getBillToPhone() %>');
<%
	}
}
%>
});

function validOrderCriteria(){
	if (validateInputNumber(document.getElementById('linknumber'),'Customer Number')
		&& validateInputNumber(document.getElementById('ship_to_phone'),'Ship Phone Number')
		&& validateInputNumber(document.getElementById('bill_to_phone'),'Bill Phone Number')
		&& validateEmail(document.getElementById('ship_to_email')))
		return true;
	else
		return false;
}

function filterShopperList() {
	var linknumber = $('#linknumber').val();
	var ship_to_email = $('#ship_to_email').val();
	var ship_to_fname = $('#ship_to_fname').val();
	var ship_to_lname = $('#ship_to_lname').val();
	var ship_to_company = $('#ship_to_company').val();
	var ship_to_phone = $('#ship_to_phone').val();
	var bill_to_fname = $('#bill_to_fname').val();
	var bill_to_lname = $('#bill_to_lname').val();
	var bill_to_company = $('#bill_to_company').val();
	var bill_to_phone = $('#bill_to_phone').val();
	
	if (validOrderCriteria()) {
		$.ajax({
			url : "shopper_list.do",
			type : 'POST',
			cache : false,
			data : {
				 method : "getShoppers",
				 linknumber : linknumber,
				 ship_to_email : ship_to_email,
				 ship_to_fname : ship_to_fname,
				 ship_to_lname : ship_to_lname,
				 ship_to_company : ship_to_company,
				 ship_to_phone : ship_to_phone ,
				 bill_to_fname : bill_to_fname,
				 bill_to_lname : bill_to_lname,
				 bill_to_company : bill_to_company,
				 bill_to_phone : bill_to_phone
			},
			dataType : 'html',
			async : true,
			success : function(html) {
				var data = html.replace(/[\r\n]+/g, "");
				
				if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
					document.location = "<%=request.getContextPath()%>/authenticate.do";
				}
				else if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer) { 
					document.location = "<%=request.getContextPath()%>/login.do";
				}
				else {
					$('#shopper_list_results').html(data);
				}
			},
			error : function() {
				alert("Error occured in ajax call");
			}
		});
	}
}

function getBackListShopper() {
	$.ajax( {
		url : 'shopper_list.do',
		type : 'post',
		cache : false,
		data : {
			 method : 'backShopperPage'
		},
		dataType : 'html',
		success : function(html) {
			var data = html.replace(/[\r\n]+/g, "");
			
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			} else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			} else {
				$('#shopper_list_results').html(data);
			}
		},
		error : function() {
			alert("Error execute in search process!");
		}
	});
}
</script>