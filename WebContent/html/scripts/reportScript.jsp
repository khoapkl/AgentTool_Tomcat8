
<script>

$(document).ready(function() {
	$('#runShippingReport').click(function() {		
		validateDate();
	});
});
function validateDate(){
	
	var datepickerFrom= $('#datepickerFrom').val();
	var datepickerTo= $('#datepickerTo').val();		
	var checkdayFrom=new Date(datepickerFrom)	;
	var checkdayTo=new Date(datepickerTo)	;
			
	if(checkdayFrom<=checkdayTo){
		$('#formShippingReport').submit();
		
	}
	else{
	alert('From Date should be less than or equal To Date.');
			}	
}
$('#pageTitle').text("Shipping Reports");
$('#topTitle').text("Shipping Reports");

</script>

