/**
 * 清空表单
 */
function myclear(){
	$("#appId").empty();
	$("#appId").append("<option value=''>请选择</option>");
	clean();
}

function validate(){
	var flag = true;
	var verPackName = $("#verPackName").val();
	if(verPackName==null||verPackName==""){
		flag = false;
		$("#verPackName_tip").html("请选择产品版本文件");
	}else{
		$("#verPackName_tip").html("");
	}
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("input[type='text'],input[type='password'],textarea").attr("readonly","readonly");
		$("select").attr("disabled","disable");
	}
});
