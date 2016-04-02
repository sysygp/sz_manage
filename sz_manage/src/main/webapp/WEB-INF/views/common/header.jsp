<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <script type="text/javascript">
   
   function getCurDate()
   {
    var d = new Date();
    var week;
    switch (d.getDay()){
    case 1: week="星期一"; break;
    case 2: week="星期二"; break;
    case 3: week="星期三"; break;
    case 4: week="星期四"; break;
    case 5: week="星期五"; break;
    case 6: week="星期六"; break;
    default: week="星期天";
    }
    var years = d.getFullYear();
    var month = add_zero(d.getMonth()+1);
    var days = add_zero(d.getDate());
    var hours = add_zero(d.getHours());
    var minutes = add_zero(d.getMinutes());
    var seconds=add_zero(d.getSeconds());
    var ndate = years+"-"+month+"-"+days+"　"+hours+":"+minutes+":"+seconds+"　"+week;
    divT.innerHTML= ndate;
   }

   function add_zero(temp)
   {
    if(temp<10) return "0"+temp;
    else return temp;
   }

   setInterval("getCurDate()",100);
   
   
   </script>
   
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<!-- <ul class="nav pull-right">

				<li>
					a href="#" class="hidden-phone visible-tablet visible-desktop" role="button">Settings</a>
				</li>
				<li id="fat-menu" class="dropdown">
				  <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> 
				        <i class="icon-user"></i> ${operUser.loginName }
                            <i class="icon-caret-down"></i>
                        
                         </a>

					<ul class="dropdown-menu">
						<li><a tabindex="-1" href="#">My Account</a></li>
						<li class="divider"></li>
						<li><a tabindex="-1" class="visible-phone" href="#">Settings</a></li>
						<li class="divider visible-phone"></li>
						<li><a tabindex="-1" href="sign-in.html">Logout</a></li>
					</ul>
				</li>

			</ul> -->
			
			
			<br /><a class="brand" href="#"><span class="first" style="font-size: 28;">平台管理系统</span></a>
			
			<!-- 如果session中由用户的信息才显示 -->
			  
			  <c:if test="${operUser.loginId !=null }">
				<div style="float: right;">
				  <span id="divT" style="color: black;float: right;margin-bottom: 1px;"></span>
				  <br /><span style="color: black;float: right;"> 您好，${operUser.loginName }，欢迎使用本系统 <a href="${ctx }/manager/zhrt/platInfo/info?platid=${operUser.platId}">首页</a> |<a href="${ctx }/login/out">退出 </a></span><br/>
				  <span>&nbsp;</span>
				</div>
			 </c:if>
			 
			 
		</div>		
	</div>
</body>
</html>