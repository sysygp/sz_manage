<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<html>
<head>
<meta charset="utf-8">
<title>平台管理系统</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">


<link rel="stylesheet" type="text/css"
	href="${ctx }/webresources/common/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/webresources/common/stylesheets/theme.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/webresources/common/lib/font-awesome/css/font-awesome.css">

<link rel="stylesheet" type="text/css"
	href="${ctx }/webresources/system/css/login.css">
<script type="text/javascript"
	src="${ctx }/webresources/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/webresources/system/js/login.js"></script>
<body class="firefox_no_select" oncontextmenu="return false;"
	onselectstart="return false;">

	<jsp:include page="../common/header.jsp" />

	<div class="row-fluid">
		<div class="dialog">
			<div class="block" style="width: 450px; height: 350px;">
				<p class="block-heading" style="text-align: center;">用户登录</p>
				<div class="block-body">
					<form id="loginForm" method="post">
						<center>
							<table>&nbsp;
							</table>
							<table border="0" style="text-align: center;">
								<tr>
									<td colspan="4"><span id="checkResult">&nbsp;</span></td>
								</tr>
								<tr>
									<td><label>用户名:</label></td>
									<td><input
										style="height: 20px; width: 140px; margin-left: 15px;"
										type="text" name="loginName" id="loginName" class="span9"><br />
									</td>
									<td colspan="2"><span id="loginNameSpan">&nbsp;</span></td>
								</tr>

								<tr>
									<td><label>密&nbsp;&nbsp;&nbsp;&nbsp;码:</label></td>
									<td><input
										style="height: 25px; width: 140px; margin-left: 15px;"
										type="password" name="loginPwd" id="loginPwd" class="span9"></td>
									<td colspan="2"><span id="loginPwdSpan">&nbsp;</span></td>
								</tr>

								<%-- <tr>
									<td><label>验证码:</label></td>
									<td><input
										style="height: 25px; width: 80px; margin-left: 19px;"
										type="text" maxlength="4" name="checkCode" id="checkCode"
										class="span9"> <img id="image"
										src="${ctx }/util/image.jsp" onclick="changeImage()"
										title="点击换验证码" /></td>
									<td colspan="2"><span id="checkCodeSpan">&nbsp;</span></td>
								</tr> --%>

								<tr>
									<td colspan="4"><a id="logBtn" href="#"
										class="btn btn-primary pull-center">登录</a></td>
								</tr>

							</table>
						</center>
						<div class="clearfix"></div>
					</form>
				</div>
			</div>
			<!-- p style="text-align: center;"><a  href="#">忘记密码?发邮件给管理员吧</a></p> -->
		</div>
	</div>
</body>
</html>
