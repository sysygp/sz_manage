$(document).ready(function(){
	$("#search").click(function(){
		var url = $(this).attr("data-url");
		var platid = $.trim($("#platid").val()); 
		var userid = $.trim($("#userid").val()); 
		var productName = $.trim($("#productName").val()); 
		
		location.href = url+ "?" +encodeURI(encodeURI("platid="+ platid+"&userid="+ userid +"&productName="+ productName));
	});
});