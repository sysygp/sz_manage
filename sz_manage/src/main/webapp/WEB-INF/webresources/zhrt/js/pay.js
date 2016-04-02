/**
 * 清空表单
 */
function clear(){
	$("#paytype").val("");
	$("#remark").val("");
}

function validate(){
	var flag = true;
	var paytype = $.trim($("#paytype").val());
	if(paytype==null || paytype==''){
		$("#paytype_tip").html("不能为空");
		flag = false;
	}else{
		$("#paytype_tip").html("");
	}
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("#paytype").attr("readonly","readonly");
		$("#remark").attr("readonly","readonly");
	}
});