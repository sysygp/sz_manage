/**
 * 登录js
 */
$(document).ready(function(){
		
	document.oncontextmenu=new Function("return false"); //禁止右键 ie,chrome,firefox
	document.onselectstart=new Function("return false"); //禁止左键选择 ie,chrome
	
	$("#logBtn").click(function(){
		var loginName=$("#loginName").val();
		var loginPwd=$("#loginPwd").val();
		var checkCode = $("#checkCode").val();
		
		//判断输入的参数是否为空
		if(isEmpty(loginName)){
			$("#loginNameSpan").text("请输入用户名!").css("color","red");
			return;
		}
		if(isEmpty(loginPwd)){
			$("#loginPwdSpan").text("请输入登陆密码!").css("color","red");
			return;
		}
//		if(isEmpty(checkCode)){
//			$("#checkCodeSpan").text("请输入验证码!").css("color","red");
//			return;
//		}
		//判断输入的参数是否为合法参数
		//用户名：6-18位数字，字母(区分大小写)。
		if(!checkUserAndPsw(loginName)){
			$("#checkResult").text("用户名不存在!").css("color","red");
			return;
		}
		//密码：6-18位数字，字母(区分大小写)。
		if(!checkUserAndPsw(loginPwd)){
			$("#checkResult").text("登录密码错误!").css("color","red");
			return;
		}
		
	    //验证码：由平台生生成4位的随机数字，点击后重新生成验证码。
//        if(checkCode.length < 4){
//          $("#checkResult").text("验证码错误!").css("color","red");
//          changeImage();
//       	  return;
//        }
		
		
		
		//通过ajax校验用户名和密码是否正确  同步方式
		$.ajax({
		      type: "GET",
		      url: "/login/check?loginName="+loginName+"&loginPwd="+loginPwd+"&checkCode="+checkCode,
		      async: false,
		      dataType: "text",
		      success: function(value){
		         // 0 表示成功   1表示验证码有问题   2表示用户名有误  3表示密码有误 
		    	  if(value == "0"){
		    		 // 检验成果  放行
	    		     //提交表单
	    			 $("#loginForm").attr("action","/login/in");
	    			 $("#loginForm").submit();
		    		
		          }else if(value == "1"){
		        	  $("#checkResult").text("验证码错误!").css("color","red");	
		        	  changeImage();
		        	  return;
		    	  }else if(value == "2"){
		    		  $("#checkResult").text("用户名不存在!").css("color","red");				    	   
		    		  return;
		    	  }else if(value == "3"){
		    		  $("#checkResult").text("登陆密码错误!").css("color","red");				    	   
		    		  return;
		    	  }else{
		    		  $("#checkResult").text("系统错误!").css("color","red");
		    		  return;
		    	  }
		    	  
		      }
		     });		
	});
	
	//给用户名框、密码框、验证码框加上  change事件,发生改变时 隐藏下边的文字
	$("#loginName").change(function(){
		$("#loginNameSpan").css("display", "none"); 
		
	});
	$("#loginPwd").change(function(){
		$("#loginPwdSpan").css("display", "none"); 
		
	});
	$("#checkCode").change(function(){
		$("#checkCodeSpan").css("display", "none"); 
		
	}).keydown(function(e){
		if(e.keyCode == 13){ //回车键  13	
		  $("#logBtn").click();
		}
	});
	
	
});
// 点击换图片 
function changeImage(){
	  var src = $("#image").attr("src")+"?date="+new Date();
	  $("#image").attr("src",src);
	  //将验证码输入框清空
	  $("#checkCode").val("");
}

//判断是否为null
function isEmpty(value){
	if(value=="" || value=="undefined" || value == null){
		return true;
	}
	return false;
}
//6-18位数字，字母(区分大小写)
function checkUserAndPsw(str){
  var s = /^[a-zA-Z0-9]{6,18}$/;
  return s.test(str);
}

