<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/user.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/power/user">
<input type="hidden" name="domainId" id="domainId" value="${domainId }" />

<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m}" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn">保存</a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<c:if test="${m == 'v' }">
			<a href="javascript:back();" data-toggle="modal" class="btn">返回</a>
		</c:if>
		<div class="btn-group"></div>
	</div>
<div style="color: red;text-align:center;">${errorInfo }</div>


	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		        <label>用户帐号</label>
		        <label>
		        	<input type="text" id="loginId" name="loginId" value="${entity.loginId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="loginId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>用户名称</label>
		        <label>
		        	<input type="text" id="loginName" name="loginName" value="${entity.loginName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="loginName_tip" style="color:red"></lable>
		        </label>
		        
		        <label>密码</label>
		        <label>
		        	<input type="text" id="loginPwd" name="loginPwd" value="${entity.loginPwd }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        </label>
		        
		        <label>所属渠道</label>
		        <label>
		        	<c:forEach items="${channelInfoList }" var="channelInfo">
		        		<input type="radio" name="channelId" value="${channelInfo.id }" <c:if test='${channelInfo.id==entity.domainId }'>checked="checked"</c:if>/>${channelInfo.cnName } &nbsp;&nbsp;
		        	</c:forEach>
		        </label>
	        </div>
		</div>
	</div>

</form>
                    
</body>
</html>