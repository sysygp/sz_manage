<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/cpinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>渠道管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
     <c:if test="${m == 'v' || m == 'e'}">&nbsp;/&nbsp;</c:if>
        ${entity.cpName }
        &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
      <li><a class="btn"  onclick="javascript:window.history.back(-1);"> 返回</a></li>
       &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        <c:if test="${ m == 'v' }">
          <li style="float: right;">
           <a class="btn"  href="javascript:edit('e','${entity.id }');">修改</a>
           <a class="btn"  href="/manager/zhrt/appinfo/get/${entity.id }/10">新增产品</a>
           <!-- 为了防止发生冲突，这里我定义10这个常数 -->
          </li>
        </c:if>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/cpinfo">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >	
   <div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">CP基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
	       
		    <form id="tab">
		        <label><span style="color: red;">*</span>CP编号</label>
		        <label>
		        	<input type="text" id="cpCode" name="cpCode" value="${entity.cpCode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="cpCode_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>CP简称</label>
		        <label>
		        	<input type="text" id="cpName" name="cpName" value="${entity.cpName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="cpName_tip" style="color:red"></lable>
		        </label>
		        
		        <label>CP全称</label>
		        <label>
		        	<input type="text" id="fullName" name="fullName" value="${entity.fullName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="fullName_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>用户名</label>
		        <label>
		        	<input type="text" id="userName" name="userName" value="${entity.userName }" class="input-xlarge" style="height: 40px;" <c:if test="${m eq 'e' }">disabled</c:if>>&nbsp;&nbsp;
		        	<lable id="userName_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>状态</label>
		        <label>
		        	<select id="status" name="status" class="input-xlarge" style="height: 40px;">
		        		<option value="1"<c:if test="${entity.status eq 1 }">selected</c:if>>上线</option>
		        		<option value="2" <c:if test="${entity.status eq 2 }">selected</c:if>>下线</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="status_tip" style="color:red"></lable>
		        </label>
		        <c:if test="${m eq 'v' or (m eq 'e' and fn:length(appList) gt 0)}">
			        <label>产品数量</label>
			        <label>
			        	<input type="text" id="appCount" value="${fn:length(appList) }" class="input-xlarge" style="height: 40px;" <c:if test="${m eq 'e' }">readOnly</c:if>>&nbsp;&nbsp;
			        	<lable id="appCount_tip" style="color:red"></lable>
			        </label>
			        
			        <label>运营产品</label>
			        <label>
			        	<c:forEach items="${appList }" var="app">
			        		<li>${app.appName}<br></li>
			        	</c:forEach>
			        </label>
		        </c:if>
		       <%--  <label>流水同步地址</label>
		        <label>
		        	<input type="text" id="billSyncUrl" name="billSyncUrl" value="${entity.billSyncUrl }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="billSyncUrl_tip" style="color:red"></lable>
		        </label>
		        
		        <label>管理员账号</label>
		        <label>
		        	<input type="text" id="compManager" name="compManager" value="${entity.compManager }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="compManager_tip" style="color:red"></lable>
		        </label>
		        
		        <label>地址</label>
		        <label>
		        	<input type="text" id="address" name="address" value="${entity.address }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="address_tip" style="color:red"></lable>
		        </label>
		        
		        <label>公司描述</label>
		        <label>
		        	<input type="text" id="companyDesc" name="companyDesc" value="${entity.companyDesc }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="companyDesc_tip" style="color:red"></lable>
		        </label> --%>
		        <label>联系电话</label>
		        <label>
		        	<input type="text" id="phone" name="phone" value="${entity.phone }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="phone_tip" style="color:red"></lable>
		        </label>
		        
		        <c:if test="${m eq 'v' }">
		        	<label>最后修改人</label>
			        <label>
			        	<input type="text" value="${entity.lastUpdatePerson }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
			        </label>
			        <label>最后修改时间</label>
			        <label>
			        	<input type="text" value="<fmt:formatDate value="${entity.lastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
			        </label>
		        </c:if>
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