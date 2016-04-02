
function validate(){
	var flag = true;
	var appName = $("#appName").val();
	if(appName == null || appName ==""){
		flag = false;
		$("#appName_tip").html("产品名称不能为空");
	}else{
		$("#appName_tip").html("");
	}
	
	var cpId = $("#cpId").val();
	if(cpId == null || cpId ==""){
		flag = false;
		$("#cpId_tip").html("请选择所属cp");
	}else{
		$("#cpId_tip").html("");
	}
	
	return flag;
}


var maxLength = 200;
function checkInput(){
	var flag = true;
	var inputVal = $("#descript").val();
	var userLength = inputVal.length;
	$("#used").html(userLength);
	if(userLength>maxLength){
		$("#used").css("color","red");
		flag = false;
	}else{
		$("#used").css("color","black");
		flag = true;
	}
	return flag;
}