/**
 * 清空表单
 */
function clear(){
	$("input[type='text']").val("");
}

function validate(){
	var flag = true;
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("input[type='text']").attr("readonly","readonly");
	}
});