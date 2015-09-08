<script>
$(document).ready(function() {
	if (<%=request.getAttribute("invalid") != null %>) {
		$('#loginMessage').html('<p style="font-weight: bold; color: red;">Invalid user name/password.</p>');
	} else {
		$('#loginMessage').html('<p style="font-weight: bold;">Please enter your user name and password to log in.</p>');
	}
	
	$('#loginContainer').css('margin-top', '-' + $('#loginContainer').height()/2);
	$('#loginContainer').css('margin-left', '-' + $('#loginContainer').width()/2);
	$('#userName').focus();
});
</script>