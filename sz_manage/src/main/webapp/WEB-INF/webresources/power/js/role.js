/**
 * 清空表单
 */
function clear(){
	$("#roleName").val("");
}

function validate(){
	var flag = true;
	var roleName = $.trim($("#roleName").val());
	
	if(roleName==null || roleName==''){
		$("#roleName_tip").html("不能为空");flag = false;
	}else{
		$("#roleName_tip").html("");
	}
	
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("#roleName").attr("readonly","readonly");
		$("input[type='checkbox']").attr("disabled","true");
	}
});