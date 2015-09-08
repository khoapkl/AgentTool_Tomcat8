<script type="text/javascript">

function openWarrenty(URL) 
{
    var newWIN;
    day = new Date();
    id = day.getTime();
    eval("page"+id+"=window.open(URL,'"+id+"','status=1,toolbar=0,scrollbars=1,location=0,statusbars=1,menubar=0,resizable=1,width=540,height=440');");
}
$(document).ready(function() {
	$("#clear").click(function(){
		
		$.ajax( {
			url : "order_db.do",
			type : 'post',
			cache : false,
			data : {
				 method : "updateOrderStatus",
				 orderNumber:'<%=orderViewPending.getOrder_id()%>'					 
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
				
					}							
							
			},
			error : function() {			
			}
		}); 
	
		});

	//$("#spanShowBasket").hide();
	//$("#spanShowCheckout").hide();
    // put all your jQuery goodness in here.
});

</script>