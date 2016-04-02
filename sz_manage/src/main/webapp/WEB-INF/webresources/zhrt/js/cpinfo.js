
function validate(){
	var flag = true;
	var cpCode = $("#cpCode").val();
	if(cpCode == null||cpCode == ''){
		$("#cpCode_tip").html('CP编号不能为空');
		flag = false;
	}else if(!checkInteger(cpCode)){
		$("#cpCode_tip").html('CP编号只能是数字');
		flag = false;
	}else{
		$("#cpCode_tip").html('');
	}
	var cpName = $("#cpName").val();
	if(cpName == null||cpName == ''){
		$("#cpName_tip").html('CP名称不能为空');
		flag = false;
	}else{
		$("#cpName_tip").html('');
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
 * 在cp下创建产品
 */
function addApp(cpId){
	//window.location.href = "/manager/zhrt/appinfo/get//";
}
/**
 * 重置cp密码
 */
function resetPassWord(cpId){
	if(confirm("重置后台登陆密码，平台将随机生成新的登陆密码，是否确认？")){
		$.ajax({
			type:"post",
			url : "/manager/zhrt/cpinfo/resetpwd",
			data : {
				"cpId" : cpId
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
/**
 * 生成随机数
 * @param n
 * @returns {String}
 */
function genRandom(n) {
	var chars = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
     var res = "";
     for(var i = 0; i < n ; i ++) {
         var id = Math.ceil(Math.random()*61);
         res += chars[id];
     }
     return res;
}