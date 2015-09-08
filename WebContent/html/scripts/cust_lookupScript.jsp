<script>

function resetForm() {
	  $('#linknumber').val("");
	  $('#bill_to_fname').val("");
	  $('#bill_to_lname').val("");
	  $('#bill_to_company').val("");
	  $('#bill_to_phone').val("");
	  $('#ship_to_fname').val("");
	  $('#ship_to_lname').val("");
	  $('#ship_to_company').val("");
	  $('#ship_to_phone').val("");	  
}

function newCustomer(addnew) {	  
	if(addnew!=null && addnew!='null' && addnew!='')
		redirect = 'shopper.do?method=new_checkout&manage=true';
	else
		redirect = 'shopper.do?method=new_checkout';
	  document.location = redirect;	  
}

function buildPath(action, shopperID, name, agent_level,addnew)
{
	//checkout
	if(addnew!=null && addnew!='null' && addnew!='')
		redirect = 'shopper.do?method=prepareCheckout&manage=true&shopper_new=' + shopperID + '&shopas=shopas&lg=lg&section=' + action +'&shopper_name=' + name + '&agent_level=' + agent_level;
	else
		redirect = 'shopper.do?method=prepareCheckout&shopper_new=' + shopperID + '&shopas=shopas&lg=lg&section=' + action +'&shopper_name=' + name + '&agent_level=' + agent_level;
	
	document.location = redirect;	
}

function getListCustomer(){	

		if (isNaN($('#linknumber').val()))
		{
			alert("Customer # must be numeric.");
			return;
		}
		dialogOpen();
		
		var linknumber = $('#linknumber').val();		
		var ship_to_fname = $('input#ship_to_fname').val();		
		var ship_to_lname = $('#ship_to_lname').val();
		var ship_to_company = $('#ship_to_company').val();
		var ship_to_phone = $('#ship_to_phone').val();
		var bill_to_fname = $('#bill_to_fname').val();
		var bill_to_lname = $('#bill_to_lname').val();
		var bill_to_company = $('#bill_to_company').val();
		var bill_to_phone = $('#bill_to_phone').val();		
		
		$.ajax({
			url : "cust_lookup.do",
			type : 'post',
			cache : false,
			data : {
				 method : "search",		
				 results: "true",		 
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
					$('#search_results').html(data);
					}
			},
			error : function() {
				dialogClose();
				$('#search_results').html("<b>Error execute in search process!</b>");				
		}
		}); 
};

//Number Validation
function isNumberKey(obj ,evt,allowDecimal)
{							
	 var key;
	 var isCtrl = false;
	 var keychar;
	 var reg;
	  
	 if (window.event) {
	  key = evt.keyCode;
	  isCtrl = window.event.ctrlKey;
	 }
	 else if (evt.which) {
	  key = evt.which;
	  isCtrl = evt.ctrlKey;
	 }
	 
	 if (isNaN(key)) {
	  return true;
	 }
	 
	 keychar = String.fromCharCode(key);
	 
	 //check for backspace or delete, or if Ctrl was pressed
	 if (key == 8 || isCtrl) {
	  return true;
	 }

	 reg = /\d/;					 
	 var isFirstD = allowDecimal ? keychar == '.' && obj.value.indexOf('.') == -1 : false;
	 
	 return isFirstD || reg.test(keychar);
}
</script>