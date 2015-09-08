<script type="text/javascript">
	$("#btUpdateCard").click(function() 
	{
		var params = "";
		var paramsHidden = "";
		$("#divSearchProductResults input[type='checkbox']").each(function(i)
		{
			if ($(this).attr("checked") == true) 
			{
				if (params != null && params != "") 
				{
					params += "," + $(this).val();
				}else 
				{
					params += $(this).val();
				}
			}
		});
		params = jQuery.trim(params);

		$("#divSearchProductResults input[type='hidden']").each(function(i) 
		{
			if (paramsHidden != null && paramsHidden != "") 
			{
				paramsHidden += "," + $(this).val();
			} else 
			{
				paramsHidden += $(this).val();
			}
		});
		
		paramsHidden = jQuery.trim(paramsHidden);
		var valSearchItemSku = $("#SearchItemSku").val();
		var itemSku = $("#item_sku").val();
		var rowOnPage = $("#slRowOnpage option:selected").val();
		
		$.ajax({
			type : "POST",
			url : 'searchProduct.do?method=updateBasketItem',
			data : 
			{
				chk : params,
				taken : paramsHidden,
				update_card : 1
			},
			success : function(data) 
			{
				//add success here
				var page = $("#currentPage").val();
				data = jQuery.trim(data);
				if (data == 'another') 
				{
					if(valSearchItemSku == 1)
					{
						FilterProduct(itemSku,1,rowOnPage);
					}else
					{
						FilterProductPaging(page,rowOnPage);
					}
					//alert('Product is used by another Agent!');
				} else 
				if (data == 'ok') 
				{
					if(valSearchItemSku == 1)
					{
						FilterProduct(itemSku,1,rowOnPage);
					}else
					{
						FilterProductPaging(page,rowOnPage);
					}
				}else 
				{
					//alert('Update cart failed!');
				}
			}
		});
	});

	$("#btClearItem").click(function() 
	{
		//currentPage
		var page = $("#currentPage").val();
		var valSearchItemSku = $("#SearchItemSku").val();
		var itemSku = $("#item_sku").val();

		var rowOnPage = $("#slRowOnpage option:selected").val();
		
		$.ajax( {
			type : "POST",
			url : 'searchProduct.do?method=updateBasketItem',
			data : {
				clear_card : 1
			},
			success : function(data) 
			{
				//add success here
				data = jQuery.trim(data);
				if (data == "ok") 
				{
					if(valSearchItemSku == 1)
					{
						FilterProduct(itemSku,1,rowOnPage);
					}else
					{
						FilterProductPaging(page,rowOnPage);
					}
					//alert('Clear cart successfully!');
				} else 
				{
					//alert('Clear cart failed!');
				}
			}
		});
	});
	
	$("#btSelectAll").click(function() 
	{
		$("#divSearchProductResults input[type='checkbox']").each(function(i) 
		{
			$(this).attr("checked", true);
		});
	});
	
	$("#btDeSelectAll").click(function() 
	{
		$("#divSearchProductResults input[type='checkbox']").each(function(i) 
		{
			$(this).attr("checked", false);
		});
	});

	$("#slRowOnpage").change(function() 
	{
		var rowOnPage = $("#slRowOnpage option:selected").val();
		FilterProductPaging(1,rowOnPage);
	});
	
</script>