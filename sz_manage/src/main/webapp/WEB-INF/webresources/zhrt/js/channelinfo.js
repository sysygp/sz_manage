
function validate(){
	var flag = true;
	var cnName = $("#cnName").val();
	if(cnName == null||cnName == ''){
		$("#cnName_tip").html('渠道简称不能为空');
		flag = false;
	}else{
		$("#cnName_tip").html('');
	}
	var userName = $("#userName").val();
	if(userName == null||userName == ''){
		$("#userName_tip").html('用户名不能为空');
		flag = false;
	}else{
		$("#userName_tip").html('');
	}	
	return flag;
}
/**
 * 重置渠道密码
 * @param cpId
 */
function resetPassWord(channelId){
	if(confirm("重置后台登陆密码，平台将随机生成新的登陆密码，是否确认？")){
		$.ajax({
			type:"post",
			url : "/manager/zhrt/channelinfo/resetpwd",
			data : {
				"channelId" : channelId
			},
			success:function(data){
				alert("密码重置成功，用户信息如下：\n"+"用户名："+data.userName+"\n"+"新密码："+data.passWord);
				location.reload();
			},
			error:function(){
				alert("系统异常");
			}
		});
	}
}