
<script >
$('#pageTitle').text("Settings ");
$('#topTitle').text("Settings ");

 	$(document).ready(function(){
		$('#textExpirationdate option[value=<%=orders.getExpirationdate()%>]').attr('selected','selected');
	
		$("#updateDiscount").click(function()
		{
			$('#messageResult').html("");	
			$('#textMaxdisvalue').val(jQuery.trim($('#textMaxdisvalue').val())) ;
			var textDiscount= $('#textMaxdisvalue').val();		
			var expirationdate= $('#textExpirationdate').val();
			if(!checkNumber(textDiscount) || (textDiscount < 0 || textDiscount > 100))
			{
				alert('Max Discount Percentage is invalid.');
				$('#textMaxdisvalue').focus();
			}else			
			if(expirationdate != '')
			{
				updateDiscountOrder(textDiscount,expirationdate);
			}
			else
			{
				alert('Please set Customer - Held Order Expiration.');
				$('#textExpirationdate').focus();
			}
		});
 	});

	function updateDiscountOrder(percdiscount,expirationdate){		
		$.ajax( {
			url : "order_db.do",
			type : 'post',
			cache : false,
			data : {
				 method : "updateDiscountAdjustment",
				 percdiscount : percdiscount,
				 expirationdate:expirationdate				 
			},
			dataType : 'html',
			async : true,
			success : function(html) {
				var data = html.replace(/[\r\n]+/g, "");
				if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
					document.location = "<%=request.getContextPath()%>/authenticate.do";
				}
				else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
					document.location = "<%=request.getContextPath()%>/login.do";
				}
				else{
					$('#messageResult').html('<font color="green"><b>Update successful.</b></font>');		
					}							
					
			},
			error : function() {				
				$('#messageResult').html('<font color="red"><b>Update failed.</b></font>');
			}
		}); 

	};
	

</script>


