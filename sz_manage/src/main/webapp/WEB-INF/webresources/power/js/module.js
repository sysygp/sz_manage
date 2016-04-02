/**
 * 清空表单
 */
function clear(){
	$("#moduleName").val("");
}

function validate(){
	var flag = true;
	var moduleName = $.trim($("#moduleName").val());
	if(moduleName==null || moduleName==''){
		$("#moduleName_tip").html("不能为空");flag = false;
	}else{
		$("#moduleName_tip").html("");
	}
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("#moduleName").attr("readonly","readonly");
	}
});