<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/settlement.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>渠道管理<span class="divider">/</span></li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/settlement">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >	
   <div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
	       
		    <form id="tab">
		        
		        <label>渠道信息</label>
		        <label>
		        	<select id="channelId" name="channelId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${channelList }"  var="channel">
		        			<option value="${channel.id }" <c:if test="${entity.channelId eq channel.id}">selected="selected"</c:if>>${channel.cnName }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="channelId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>产品信息</label>
		        <label>
		        	<select id="appId" name="appId" class="input-xlarge" style="height: 40px;" >
		        		<option value="">请选择</option>
		        		<c:forEach items="${appList }"  var="app">
		        			<option value="${app.id }" <c:if test="${entity.appId eq app.id}">selected="selected"</c:if>>${app.appName }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="appId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>结算百分比</label>
		        <label>
		        	<input type="text" id="settPercent" name="settPercent" value="${entity.settPercent }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="settPercent_tip" style="color:red"></lable>
		        </label>
		    </form>
	        </div>
			
			
			<div class="tab-pane fade" id="profile">
				<form id="tab2">
					<label>New Password</label> 
					<input type="password" class="input-xlarge">
					<div>
						<button class="btn btn-primary">Update</button>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
		   <a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>>保存 </a>
		   <a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>		 
		<div class="btn-group"></div>
	</div>

</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>