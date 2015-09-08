<script>
if (<%=request.getAttribute("messageSuces") != null %>) {
	$('#security_errorMessage').html('<p style="font-weight: bold; color: green;"><%=request.getAttribute("messageSuces") %></p>');
}
if (<%=request.getAttribute("messageError") != null %>) {
	$('#security_errorMessage').html('<p style="font-weight: bold; color: red;"><%=request.getAttribute("messageError") %></p>');
}
	var keypressHex = function(e) {
		var ch = String.fromCharCode(e.charCode || e.keyCode);
		if (((ch >= '0') && (ch <= '9'))|| e.keyCode == 8 || e.keyCode == 9|| (( e.keyCode >= 37)&&( e.keyCode <= 40)))
		    return true;
		return false;
	};
	$(function() {
	$(".spinner").each(function(item){
		$(this).spinner(item,{
		      spin: function( event, ui ) {
		        if ( ui.value > item.attr('max') ) {
		          $( this ).spinner( "value", item.attr('max') );
		          return false;
		        } else if ( ui.value < item.attr('min') ) {
		          $( this ).spinner( "value", item.attr('min') );
		          return false;
		        }
		      }
		    });
		});
	});
	jQuery('.spinner').unbind('keypress.uispinner');
	jQuery('.spinner').bind('keypress.uispinner', keypressHex);
	$(".spinner").width(40);

    function TextChange(item) {
       if(item.value>180){
    	   item.value = 180;
           }   
       if(item.value<30){
    	   item.value=30;
           }      
    }
    
    function charNumberChange(item) {
        if(item.value>15){
     	   item.value=15;
            }   
        if(item.value<8){
     	   item.value=8;
            }      
        
     }
    function countChange(item) {
        if(item.value>20){
     	   item.value=20;
            }   
        if(item.value<1){
     	   item.value=1;
            }      
        
     }
    function resetChange(item) {
        if(item.value>24){
     	   item.value=24;
            }   
        if(item.value<1){
     	   item.value=1;
            }      
        
     }
    jQuery('.spinner1').unbind('keypress.uispinner');
	jQuery('.spinner1').bind('keypress.uispinner', keypressHex);
	$(".spinner1").width(55);

</script>
