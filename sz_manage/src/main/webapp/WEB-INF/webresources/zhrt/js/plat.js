/**
 * 清空表单
 */
function clear(){
	/*$("#platname").val("");
	$("#remark").val("");*/
	$("input[type='text']").val('');
}

function validate(){
	var flag = true;
	var platname = $.trim($("#platname").val());
	if(platname==null || platname==''){
		$("#platName_tip").html("不能为空");
		flag = false;
	}else{
		$("#platName_tip").html("");
	}
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		/*$("#platname").attr("readonly","readonly");
		$("#remark").attr("readonly","readonly");*/
		$("input[type='text']").attr("readonly","readonly");
	}
});