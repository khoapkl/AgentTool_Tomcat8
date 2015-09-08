<script>
	  if ($.browser.msie){
	
	   	$("ul#menu li").live({
	      		mouseenter:
	           function()
	           {
	            $("ul#menu li ul.sub-menu").css({visibility: "visible",display: "block"}).show();
	           },
	        mouseleave:
	           function()
	           {$("ul#menu li ul.sub-menu").hide();
	           }
	       });
	  	}
</script>