<!--[if IE 9]>
	 	<script src="<%=request.getContextPath() %>/jscripts/jquery-1.8.3.js" language="javascript"></script>
	 	<script src="<%=request.getContextPath() %>/jscripts/jquery.placeholder.js" language="javascript"></script>	
	  		 		
  		<script> 	
		$(document).ready(function() {
			$('.input_text').placeholder();
			$("#clean").on('click', function(e){
				e.preventDefault();
				$("#userName").val('');
				$("#password").val('');
				$("#userName").trigger('change');
				$("#password").trigger('change');
				return false;
			});
		});
		</script>
<![endif]-->
<div id="wrapper_login">
        <div id="logo_change"><img alt="Agent Tool" id="imgDefault" name="imgDefault" src="images/welcome_to_dfs_direct_sale.GIF"></div> 
		<div id="container_login">
			
			<form action="<%=request.getContextPath()%>/authenticate.do?method=login" method="POST">
				<p>
					<label>User Name:</label><br>
					<input class="input_text" type="text" id="userName" name="userName" placeholder="Username" >
				</p>	
				<p>
					<label>Password:</label><br>
					<input class="input_text" type="password" id="password" name="password" placeholder="Password" >
				</p>
				<p class="bt_login">
					<input class="input_bt" type="submit" id="login" value="LOGIN"> &nbsp;&nbsp;&nbsp;
					<input class="input_bt" type="reset" id="clean" value="CLEAR">
				</p>
				<p class="mid"></p>
				<div id="loginerrorMessage"></div>
			</form>
			
		</div><!-- End #container -->
	</div><!-- End #wrapper -->
	<%@include file="/html/scripts/login_script.jsp"%>