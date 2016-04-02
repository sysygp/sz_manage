$(document).ready(function(){
	/*$("#search").click(function(){
		var url = $(this).attr("data-url");
		var platid = $.trim($("#platid").val()); 
		var userid = $.trim($("#userid").val());
		var createTime = $.trim($("#createTime").val());
		
		location.href = url + "?" +encodeURI(encodeURI("platid="+ platid+"&userid="+ userid + "&createTime="+ createTime));
	});*/
});

function viewHistory(platid,userid){
	$("#mainForm").attr("action","/manager/zhrt/uinfozhistory/index?platid="+platid+"&userid="+userid);
	$("#mainForm").submit();
}