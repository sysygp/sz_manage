$(document).ready(function(){

});


/**
 * 编辑账户密码
 */
function editAccountPwd(platid, phone){
	var url = $("#mainForm").attr("action") + "/modifyaccountpaypwd";
	$.post(url, {"platid":platid, "phone":phone}, function(data){
		if(data.code=="10000"){
			if(data.info.status=="1"){
				alert("修改成功\n支付密码为："+ data.info.payPwdNew);
			}else{
				alert("修改失败");
			}
		}else{
			alert("修改失败");
		}
		
	}, "json");
}
