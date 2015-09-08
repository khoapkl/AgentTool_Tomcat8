<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
	int rowOnPage = 0 ;
	if(session.getAttribute(Constants.ATTR_ROW_ON_PAGE) != null)
	{
	    rowOnPage = Integer.parseInt(session.getAttribute(Constants.ATTR_ROW_ON_PAGE).toString());
	}else
	{
	    rowOnPage = Constants.RECORDS_ON_PAGE ; 
	}
%>
<script type="text/javascript">

	$(document).ready(function(){
		var selected = $("#only_available option:selected");    
	    if(selected.val() != 0){
			var status = selected.text();
			if(status == "All")
			{
				$("#sort_by").val("status");
			}
	    }
	});

	$("#only_available").change(function(){
		var selected = $("#only_available option:selected");    
	    if(selected.val() != 0){
			var status = selected.text();
			if(status == "All")
			{
				$("#sort_by").val("status");
			}else if(status != "All"){
			
				$("#sort_by").val("list_price");
			}
	    }
	});
	//checkMutiple
	$(".check-multiple").click(function(){
			id = $(this).attr('rel');
			sel = $("#"+id);
			
			if(!sel.attr('multiple'))
			{
				id = $(this).attr('rel');
				sel.attr('multiple','true');
				$("#checkbox_" + id).hide();
				$("#reorder_" + id).attr('checked',false);
				$(this).text('[ - ]');
			}
			else
				{
					sel.removeAttr('multiple');
					$("#checkbox_" + id).show();
					$(this).text('[ + ]');
				}
		
	});
	//mutiple-Cosmetic
	$(".mutiple-Cosmetic").click(function(){
			id = $(this).attr('rel');
			sel = $("#"+id);
			
			if(sel.attr('multiple'))
			{
				sel.removeAttr('multiple');
				$(this).text('[ + ]');
			}
			else
			{
				sel.attr('multiple','true');
				$(this).text('[ - ]');
			}
		
	});
	$("#btFilterProduct").click(function() 
	{
		var rowOnPage = 0 ;
		if(document.getElementById("slRowOnpage"))
		{
			rowOnPage = $("#slRowOnpage option:selected").val();
		}else
		{
			rowOnPage = <%= rowOnPage %>;
		}
		
		$("#SearchItemSku").val(0);
		FilterProduct("",1,rowOnPage);
	});

	var params  = "";
	function FilterProduct(item_sku,page,rowOnpage)
	{
		dialogOpen();
		
		params = setParamSearch();

		var newParams  = params ;
		
		item_sku = jQuery.trim(item_sku);
		if(item_sku != null || item_sku != "")
		{
			newParams = newParams + "&item_sku="+ item_sku;			
		}

		if(page==0 || page == null || page =="")
		{
			page = 1 ;
		}
		newParams = newParams + "&page="+page; 
		newParams = newParams + "&slRowOnpage="+rowOnpage;
		//alert(newParams);
		
		$.ajax({
		type: "POST",
		url: 'searchProduct.do?method=category',
		data: newParams,
		success: function(data) 
		{
			if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	
			{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else 
			if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer)
			{ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else
			{
				$("#divSearchProductResults").html(""); 			
				$("#divSearchProductResults").html(data);
			}	
			dialogClose();
		},
		error: function()
		{
			//alert("Processing Error, Please Try Again !");
			dialogClose();
		}		 
		});
	}

	function FilterProductPaging(page,rowOnpage)
	{
		dialogOpen();
		var newParams  = params ;
		if(page==0 || page == null || page ==""){
			page = 1 ;
		}
		newParams = newParams + "&page="+page;
		newParams = newParams + "&slRowOnpage="+rowOnpage; 
		$.ajax({
			type: "POST",
			url: 'searchProduct.do?method=category',
			data: newParams,
			success: function(data) 
			{
				if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	
				{
					document.location = "<%=request.getContextPath()%>/authenticate.do";
				}
				else 
				if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer)
				{ 
					document.location = "<%=request.getContextPath()%>/login.do";
				}
				else
				{
					$("#divSearchProductResults").html(""); 			
					$("#divSearchProductResults").html(data);
				}	
				dialogClose();
			},
			error: function(){
				//alert("Processing Error, Please Try Again !");
				dialogClose();
			}		 
			});
	}
	
	$("#btServiceTagSearch").click(function()
	{
			var itemSku = $("#item_sku").val(); 
			var categoryId =  $("#category_id").val();
			window.location = "searchProduct.do?method=serviceTagSearch&item_sku="+itemSku + "&category_id="+categoryId;
	});

	function setParamSearch()
	{
		var params = ""; 
		$("#divSearchProductToolbar input, #divSearchProductToolbar select").each(function(i) 
		{
			if($(this).attr("type")=="checkbox")
			{
				if($(this).attr("checked")== true)
				{
					params += $(this).attr("id") + "=" 	+ $(this).val() + "&";
				}
			}else
			{
				if($(this).val() != null && $(this).val() != "")
				{
					var value = encodeURIComponent($(this).val());
					params += $(this).attr("id") + "=" 	+ value + "&";
				}
			}
		});
		return params ;
	}
</script>