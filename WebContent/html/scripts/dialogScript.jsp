<link type="text/css" href="<%=request.getContextPath() %>/styles/jquery.ui.all.css" rel="stylesheet" />
<link type="text/css" href="<%=request.getContextPath() %>/styles/demos.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.position.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.dialog.js"></script>

<div id="divLoading" style="padding:0px;text-align:center;vertical-align: middle; display: none;">
	<i>Loading now ...</i>
	<img src="images/processing.gif">
</div>

<script type="text/javascript">
<!--
$(document).ready(function() {	

	/*
	var alr = "";
	jQuery.each(jQuery.browser, function(i, val) {
		alr += i + "--" + val + "\n";
	});
	alert( alr );
	*/
	
	var ua = $.browser;
	var height = 40;
	
	if(ua.mozilla)
	{
		height = 35;
	}else if(ua.msie)
	{
		height = 70;
	}	
	$("#divLoading" ).dialog({
		modal: true,
		resizable: false,
		autoOpen: false,
		height : height
	}).prev().hide();
});

function dialogOpen()
{
	$(".ui-widget-overlay ").css('height','100%');
	var popup = $("#divLoading");
	popup.dialog('open');
}

function dialogClose()
{
	$(".ui-widget-overlay ").css('height','100%');
	var popup = $("#divLoading");
	popup.dialog('close');
}

//-->
</script>

<style>
<!--
.ui-widget-overlay 
{
    background: url("images/phpThumb_generated_thumbnailpng.png") repeat scroll 50% 50% #666666;
    opacity: 0.5;
}
-->
</style>




