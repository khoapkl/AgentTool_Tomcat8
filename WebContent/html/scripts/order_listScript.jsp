<script>
function validOrderCriteria(){
	if(	validateInputNumber(document.getElementById('ordernumber'),'Order Number')
	 && validateInputNumber(document.getElementById('listing'),'Listing Number')
 	&& validateInputNumber(document.getElementById('linknumber'),'Customer Number')
	&& validateInputNumber(document.getElementById('ship_to_phone'),'Ship Phone Number')
	&& validateInputNumber(document.getElementById('bill_to_phone'),'Bill Phone Number')
	&& validateEmail(document.getElementById('ship_to_email')))
		return true;
	else return false;
}
function getListOrder(){
	if(validOrderCriteria()){
		var ordernumber = $('#ordernumber').val();
		var sort_order = $('#sort_order').val(); //select
		var itemsku = $('#itemsku').val();
		var listing = $('#listing').val();
		var linknumber = $('#linknumber').val();
		var filter_type = $('#filter_type').val(); //select
		var ship_to_email = $('#ship_to_email').val();
		var ship_to_fname = $('#ship_to_fname').val();
		var ship_to_lname = $('#ship_to_lname').val();
		var ship_to_company = $('#ship_to_company').val();
		var ship_to_phone = $('#ship_to_phone').val();
		var bill_to_fname = $('#bill_to_fname').val();
		var bill_to_lname = $('#bill_to_lname').val();
		var bill_to_company = $('#bill_to_company').val();
		var bill_to_phone = $('#bill_to_phone').val();
		dialogOpen();
			$.ajax( {
				url : 'order_db.do',
				type : 'post',
				cache : false,
				data : {
					 method : 'searchOrder',
					 ordernumber : ordernumber,
					 sort_order : sort_order,
					 itemsku : itemsku,
					 listing : listing,
					 linknumber : linknumber,
					 filter_type : filter_type ,
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
				success : function(html) {
					dialogClose();
					var data = html.replace(/[\r\n]+/g, "");
					if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
						document.location = "<%=request.getContextPath()%>/authenticate.do";
					}
					else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
						document.location = "<%=request.getContextPath()%>/login.do";
					}
					else{
						$('#order_results').html(data);
						}		
				},
				error : function() {
					alert("Error execute in search process!");
				}
			}); 
		}		
	}

function getBackListOrder(){	
	$.ajax( {
		url : 'order_db.do',
		type : 'post',
		cache : false,
		data : {
			 method : 'backOrderPage'
		},
		dataType : 'html',
		success : function(html) {
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#order_results').html(data);
				}		
		},
		error : function() {
			alert("Error execute in search process!");
		}
	}); 
	
}

function validShopperCriteria(){
	if(	validateInputNumber(document.getElementById('ordernumber'),'Order Number')
 	&& validateInputNumber(document.getElementById('linknumber'),'Customer Number')
	&& validateInputNumber(document.getElementById('ship_to_phone'),'Ship Phone Number')
	&& validateInputNumber(document.getElementById('bill_to_phone'),'Bill Phone Number'))
		return true;
	else return false;
}
function getShopOrder(){
	if(validShopperCriteria()){
	var ordernumber = $('#ordernumber').val();
	var sort_order = $('#sort_order').val(); //select
	var itemsku = $('#itemsku').val();
	var linknumber = $('#linknumber').val();
	var ship_to_fname = $('#ship_to_fname').val();
	var ship_to_lname = $('#ship_to_lname').val();
	var ship_to_company = $('#ship_to_company').val();
	var ship_to_phone = $('#ship_to_phone').val();
	
	var bill_to_fname = $('#bill_to_fname').val();
	var bill_to_lname = $('#bill_to_lname').val();
	var bill_to_company = $('#bill_to_company').val();
	var bill_to_phone = $('#bill_to_phone').val();
	dialogOpen();
	$.ajax( {
		url : "order_db.do",
		type : 'post',
		cache : false,
		data : {
			 method : "searchShopOrder",
			 ordernumber : ordernumber,
			 sort_order : sort_order,
			 itemsku : itemsku,
			 linknumber : linknumber,
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
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#order_shopper_results').html(data);
				}
		},
		error : function() {
			alert("Error execute in search process!");
	}
	});
	}
}

function getBackShopOrder(){
	$.ajax( {
		url : "order_db.do",
		type : 'post',
		cache : false,
		data : {
			 method : "backShopperPage"
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#order_shopper_results').html(data);
				}
		},
		error : function() {
			alert("Error execute in search process!");
		}
	});
}
</script>