/**
 * 清空表单
 */
function clear(){
	$("input[type='text']").val("");
}

function validate(){
	//校验必填字段不能为空
	var sdkVerCode = $("#sdkVerCode").val();
	var file = $("#file").val();
	if(sdkVerCode == "" || sdkVerCode == undefined || file == "" || file == undefined ){
		$("#sdkCodeCheck").html("必选字段未填值！");
		return false;
	}
		
	var m = $("[name='m']").val();
	if(m == "e"){
		return true;
	}
	
	var flag = false;
	
	var sdkVerCode = $("#sdkVerCode").val();
	$.ajax({
		type:"post",
		async: false,
		url : "/manager/zhrt/sdkver/checkSdkVerCode",
		data : {
			"sdkVerCode" : sdkVerCode
		},
		success:function(data){
			if(data.result == "0"){
				flag = true;
			}else if(data.result == "1"){
				$("#sdkCodeCheck").html("版本号每一位必须是在0-99范围内");
				
			}else if(data.result == "2"){
				$("#sdkCodeCheck").html("新增版本号不能比之前版本号低");
				
			}else{
				$("#sdkCodeCheck").html("系统错误");
				
			}	
		}
	});
	
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