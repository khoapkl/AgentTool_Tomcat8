<script>
if(getCookie("logged_out") && getCookie("logged_out") != ""){
	console.log(getCookie("logged_out"));
	var stateObj = { foo: "bar" };
	try
	  {
	  	if(window.history.pushState){
		  for(var i=0;i<30; i++){
				  window.history.pushState( null, "" , "");
				  console.log("asda");
			}
			deleteCookie("logged_out");
		}else{

			(function(window, undefined) {
		    // Define variables
		   		 var History = window.History,
		       	 State = History.getState();

		    // Bind statechange event
			    History.Adapter.bind(window, 'statechange', function() {
			        var State = History.getState();
			    });
			})(window)
			for(var i=30; i>0; i--){
				window.location.hash = ""+ Math.random();
			}
			deleteCookie("logged_out");
		}
	  }
	catch(err)
	  {			console.log("errors");
			  //Handle errors here
			  console.log(err);
	  }

}

function getCookie( name ) {	
	var start = document.cookie.indexOf( name + "=" );
	var len = start + name.length + 1;
	if ( ( !start ) && ( name != document.cookie.substring( 0, name.length ) ) ) {
		return null;
	}

	if ( start == -1 ) return null;
	var end = document.cookie.indexOf( ';', len );
	if ( end == -1 ) end = document.cookie.length;
	return unescape( document.cookie.substring( len, end ) );

}

function setCookie( name, value, expires, path, domain, secure ) {
	var today = new Date();
	today.setTime( today.getTime() );
	if ( expires ) {
		expires = expires * 1000 * 60 * 60 * 24;
	}
	var expires_date = new Date( today.getTime() + (expires) );
	document.cookie = name+'='+escape( value ) +
		( ( expires ) ? ';expires='+expires_date.toGMTString() : '' ) +
		( ( path ) ? ';path=' + path : '' ) + 
		( ( domain ) ? ';domain=' + domain : '' ) +
		( ( secure ) ? ';secure' : '' );

}

function deleteCookie( name, path, domain ) {
	if ( getCookie( name ) ) document.cookie = name + '=' +
			( ( path ) ? ';path=' + path : '') +
			( ( domain ) ? ';domain=' + domain : '' ) +
			';expires=Thu, 01-Jan-1970 00:00:01 GMT';
}

$(document).ready(function() {
	if (<%=request.getAttribute("message") != null %>) {
		$('#loginerrorMessage').html('<%=request.getAttribute("message")%>');
	}
	
	$('#loginerrorMessage').css({"color":"red","text-align":"center","font-weight":"bold"});
	$('#loginContainer').css('margin-top', '-' + $('#loginContainer').height()/2);
	$('#loginContainer').css('margin-left', '-' + $('#loginContainer').width()/2);
	$('#userName').focus();
});
</script>