<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/propinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/propinfo">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>>保存 </a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<div class="btn-group"></div>
	</div>


	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		        <label>应用产品信息</label>
		        <label>
		        	<select id="appId" name="appId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${appInfoList }"  var="app">
		        			<option value="${app.id }" <c:if test="${entity.appId eq app.id}">selected="selected"</c:if>>${app.appName }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="appId_tip" style="color:red"></lable>
		        </label>
		        
		       <%--  <label>道具次序</label>
		        <label>
		        	<input type="text" id="propCodeInApp" name="propCodeInApp" value="${entity.propCodeInApp }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="propCodeInApp_tip" style="color:red"></lable>
		        </label> --%>
		        
		        <label>道具名称</label>
		        <label>
		        	<input type="text" id="propName" name="propName" value="${entity.propName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="propName_tip" style="color:red"></lable>
		        </label>
		        
		        <label>道具价格(分)</label>
		        <label>
		        	<input type="text" id="propFee" name="propFee" value="${entity.propFee }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="propFee_tip" style="color:red"></lable>
		        </label>
		        
		        
		        <label>状态</label>
		        <label>
		        	<%-- <input type="text" id="status" name="status" value="${entity.status }" class="input-xlarge" style="height: 40px;"> --%>
		        	<select id="status" name="status" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<option value="1" <c:if test="${entity.status eq 1}">selected="selected"</c:if>>正常</option>
		        		<option value="2" <c:if test="${entity.status eq 2}">selected="selected"</c:if>>已删除</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="status_tip" style="color:red"></lable>
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

</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>