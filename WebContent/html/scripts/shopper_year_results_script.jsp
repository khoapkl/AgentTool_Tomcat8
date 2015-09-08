<script>
$(document).ready(function() {
	$('a.month').click(function() {
		$('#year').val($('#yearPicker').val());
		$('#month').val($(this).attr('id'));
		$('form').submit();
	});
});
</script>
