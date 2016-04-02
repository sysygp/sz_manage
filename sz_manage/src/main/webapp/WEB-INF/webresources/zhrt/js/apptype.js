/**
 * 清空表单
 */
function clear(){
	$("input[type='text']").val("");
}

function validate(){
	var flag = true;
	var appTypeId = $("#appTypeId").val();
	if(appTypeId == null || appTypeId == ""){
		$("#appTypeId_tip").html('产品类型id不能为空');
		flag = false;
	}else{
		$("#appTypeId_tip").html('');
	}
	var appTypeName = $("#appTypeName").val();
	if(appTypeName == null || appTypeName == ""){
		$("#appTypeName_tip").html('产品类型名不能为空');
		flag = false;
	}else{
		$("#appTypeName_tip").html('');
	}
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