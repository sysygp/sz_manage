/**
 * 清空表单
 */
function clear(){
	$("#domainName").val("");
}

function validate(){
	var flag = true;
	var domainName = $.trim($("#domainName").val());
	var platid = $.trim($("#platid").val());
	if(domainName==null || domainName==''){
		$("#domainName_tip").html("不能为空");flag = false;
	}else{
		$("#domainName_tip").html("");
	}
	if(platid==null || platid==''){
		$("#platid_tip").html("不能为空");flag = false;
	}else{
		$("#platid_tip").html("");
	}
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("#domainName").attr("readonly","readonly");
	}
});