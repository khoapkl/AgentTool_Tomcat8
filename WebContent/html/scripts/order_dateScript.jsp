<script>
var month = new Array("Error","January","February","March","April","May","June","July","August","September","October","November","December");
function getOrderByYear(iyear)
{
	dialogOpen();
		$.ajax( {
			url : "order_db.do",
			type : 'post',
			cache : false,
			data : {
				 method : "yearOrder",
				 iyear : iyear
			},
			dataType : 'html',
			async : true,
			success : function(html) {
				var data = html.replace(/[\r\n]+/g, "");
				$('#pageTitle').text("New Orders by Year: "+iyear);
				$('#topTitle').text("New Orders by Year: "+iyear);
				$('#yearUpdate').removeAttr("style");
				if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
					document.location = "<%=request.getContextPath()%>/authenticate.do";
				}
				else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
					document.location = "<%=request.getContextPath()%>/login.do";
				}
				else{
					$('#year_result').html(data);
					}	
				dialogClose();	
			},
			error : function() {
				//alert("Error execute in search process!");
		}
		});
	
}
function getOrderByMonth(iyear,imonth)
{
	dialogOpen();
	$.ajax( {
		url : "order_db.do",
		type : 'post',
		cache : false,
		data : {
			 method : "monthOrder",
			 iyear : iyear,
			 imonth : imonth
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			var data = html.replace(/[\r\n]+/g, "");
			$('#pageTitle').text("New Orders by Month: "+changeRequest(imonth)+" / "+iyear);
			$('#topTitle').text("New Orders by Month: "+changeRequest(imonth)+" / "+iyear);
			$('#yearUpdate').attr("style","display:none");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#year_result').html(data);
				}	
			$('#timePeriod').html("Time Period: <a href=\"#\" onclick='getOrderByYear("+iyear+");' ><span id=\"iyear\"> "+iyear+"</span></a> / "+month[imonth]+"<span id=\"imonth\" style =\"display:none\">"+imonth+"</span>");
			dialogClose();	
		}
		
	});	
}

function getOrderByDay(iyear,imonth,iday)
{
	dialogOpen();
	$.ajax( {
		url : "order_db.do",
		type : 'post',
		cache : false,
		data : {
			 method : "dayOrder",
			 iyear : iyear,
			 imonth : imonth,
			 iday : iday
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			//var data = html.replace(/[\r\n]+/g, "");
			var data = html;
			$('#pageTitle').text("New Orders by Day: "+changeRequest(imonth)+" / "+changeRequest(iday)+" / "+iyear);
			$('#topTitle').text("New Orders by Day: "+changeRequest(imonth)+" / "+changeRequest(iday)+" / "+iyear);
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#year_result').html(data);
				}	
			$('#timePeriod').html("Time Period: <a href=\"#\" onclick='getOrderByYear("+iyear+");' ><span id=\"iyear\"> "+iyear+"</span></a> / <a href=\"#\" onclick='getOrderByMonth("+iyear+","+imonth+");' >"+month[imonth]+"</a><span id=\"imonth\" style =\"display:none\">"+imonth+"</span> / <span id=\"iday\"> "+iday+"</span>");
			dialogClose(); 	
		}
	});
		
}
 function changeRequest(dateVal){
	 var val = dateVal;
	 if(parseInt(dateVal)<10){
		 val = "0"+dateVal;
	 }
	return val; 
 }
</script>