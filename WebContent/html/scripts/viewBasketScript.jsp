<script type="text/javascript">
	$("#btRemove").click(function() {
		var params = "";
		var paramsHidden = "";
		$("#table1 input[type='checkbox']").each(function(i) {
			if ($(this).attr("checked") == true) {
				if (params != null && params != "") {
					params += "," + $(this).val();
				} else {
					params += $(this).val();
				}
			}
		});
		params = jQuery.trim(params);

		$("#table1 input[type='hidden']").each(function(i) {
			if (paramsHidden != null && paramsHidden != "") {
				paramsHidden += "," + $(this).val();
			} else {
				paramsHidden += $(this).val();
			}

		});
		$("#chks").val(params);
		$("#takens").val(paramsHidden);
		form1.submit();
	});

</script>