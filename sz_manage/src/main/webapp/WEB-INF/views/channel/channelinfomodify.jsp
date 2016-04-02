<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/channelinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>
<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>渠道管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
     <c:if test="${m == 'v' || m == 'e'}">&nbsp;/&nbsp;</c:if>
        ${entity.cnName }&nbsp;&nbsp;
      <li>
      	<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"><i class="icon-save"></i>>保存 </a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>
      	<a class="btn"  onclick="javascript:window.history.back(-1);"> 返回</a></li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/channelinfo">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >
<input type="hidden" id="parentId" name="parentId" value="${parentId }">
	
<div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">渠道基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		        <%-- <label>渠道父ID</label>
		        <label>
		        	<input type="text" id="parentId" name="parentId" value="${entity.parentId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="parentId_tip" style="color:red"></lable>
		        </label> --%>
		        <%-- 
		        <label>渠道编号</label>
		        <label>
		        	<input type="text" id="channelCode" name="channelCode" value="${entity.channelCode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="channelCode_tip" style="color:red"></lable>
		        </label> --%>
		        <label><span style="color: red;">*</span>渠道标志</label>
		        <label>
		        	<input type="text" id="channelId" name="channelId" value="${entity.channelId }" class="input-xlarge" style="height: 40px;" disabled="disabled">&nbsp;&nbsp;
		        	<lable id="channelId_tip" style="color:red"></lable>
		        </label>
		        <label><span style="color: red;">*</span>渠道简称</label>
		        <label>
		        	<input type="text" id="cnName" name="cnName" value="${entity.cnName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="cnName_tip" style="color:red"></lable>
		        </label>
		        
		        <label>渠道全称</label>
		        <label>
		        	<input type="text" id="fullName" name="fullName" value="${entity.fullName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="fullName_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>用户名</label>
		        <label>
		        	<input type="text" id="userName" name="userName" value="${entity.userName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="userName_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>状态</label>
		        <label>
		        	<select  id="status" name="status"  class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.status eq 1 }">selected</c:if>>上线</option>
		        		<option value="2" <c:if test="${entity.status eq 2 }">selected</c:if>>下线</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="status_tip" style="color:red"></lable>
		        </label>		
		        
		        <label>联系电话</label>
		        <label>
		        	<input type="text" id="phone" name="phone" value="${entity.phone }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="phone_tip" style="color:red"></lable>
		        </label>        
		        <%-- <label>平台</label>
		        <label>
		        	<input type="text" id="platId" name="platId" value="${entity.platId }" class="input-xlarge" style="height: 40px;">
		        	<select id="platId" name="platId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		  				 <c:forEach items="${platList }" var="plat">
		  				 	<option value="${plat.id }" <c:if test="${plat.id eq entity.platId }">selected="selected"</c:if>>${plat.platname }</option>
		  				 </c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="platId_tip" style="color:red"></lable>
		        </label> --%>
		        
		        
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
</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>