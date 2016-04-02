/**
 * 清空表单
 */
function clear(){
	$("#behavId").val("");
	$("#behavName").val("");
	$("#remark").val("");
}

function validate(){
	var flag = true, mark = true;
	
	var behavId = $.trim($("#behavId").val());
	var behavName = $.trim($("#behavName").val());
	
	if(behavId==null || behavId==''){
		$("#behavId_tip").html("不能为空");
		flag = false;
		
	}else if(isNaN(behavId)){
		$("#behavId_tip").html("格式不对");
		flag = false;
		
	}else{
		$("#behavId_tip").html("");
	}
	
	if(behavName==null || behavName==''){
		$("#behavName_tip").html("不能为空");
		mark = false;
	}else{
		$("#behavName_tip").html("");
	}
	return (flag && mark);
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("#behavId").attr("readonly","readonly");
		$("#behavName").attr("readonly","readonly");
		$("#remark").attr("readonly","readonly");
	}
});