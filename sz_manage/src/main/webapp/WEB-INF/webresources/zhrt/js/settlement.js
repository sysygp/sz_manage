
function validate(){
	var flag = true;
	var channelId = $("#channelId").val();
	if(channelId == null||channelId == ''){
		$("#channelId_tip").html('渠道不能为空');
		flag = false;
	}else{
		$("#channelId_tip").html('');
	}
	var appId = $("#appId").val();
	if(appId == null||appId == ''){
		$("#appId_tip").html('产品不能为空');
		flag = false;
	}else{
		$("#appId_tip").html('');
	}
	
	
	return flag;
}
