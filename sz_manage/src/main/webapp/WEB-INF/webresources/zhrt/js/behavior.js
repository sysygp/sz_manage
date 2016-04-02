/**
 * 清空表单
 */
function clear(){
	$("input[type='text']").val("");
}

function validate(){
	var flag = true;
	var behavId = $("#behavId").val();
	if(behavId == null || behavId == ""){
		$("#behavId_tip").html('用户行为类型编号不能为空');
		flag = false;
	}else{
		$("#behavId_tip").html('');
	}
	var behavName = $("#behavName").val();
	if(behavName == null || behavName == ""){
		$("#behavName_tip").html('用户行为类型描述不能为空');
		flag = false;
	}else{
		$("#behavName_tip").html('');
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