<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/sdkver.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}&nbsp;/&nbsp;
        <c:if test="${m == 'a' }">新增sdk版本管理</c:if>
        <c:if test="${m == 'e' }">编辑sdk版本管理</c:if>
        <c:if test="${m == 'v' }">sdk版本管理详情</c:if>
   </li>
   
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/sdkver" enctype="multipart/form-data">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<%-- <div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clean();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<div class="btn-group"></div>
	</div>
    --%>
    <div id="sdkCodeCheck" style="color: red;text-align:center;">${errorInfo }</div>
	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		    	<label><span style="color: red;">*</span>版本号</label>
		        <label>
		            <c:if test="${m == 'v' || m == 'a' }">
		        	<input type="text" id="sdkVerCode" name="sdkVerCode" value="${entity.sdkVerCode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	</c:if>
		        	<c:if test="${m == 'e' }">
		        	  <input type="text" readonly="readonly" id="sdkVerCode" name="sdkVerCode" value="${entity.sdkVerCode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	</c:if>
		        	<span>4个数字,0-99,以.间隔</span>
		        	<lable id="sdkVerCode_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>更新文件</label>
		        <label>
		            <c:if test="${m == 'v' }">
		            	<input type="text" id="updateFile" name="updateFile" value="${entity.updateFile }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;		            
		            </c:if>
		            <c:if test="${m == 'a' }">
		               <input type="file" id="file" name="file" value="${entity.file }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		            </c:if>
		            <c:if test="${m == 'e' }">
		               <c:if test="${entity.updateFile != null}">
		        		<a href="/manager/zhrt/sdkver/download?id=${entity.id }">${fn:substring(entity.updateFile,13,fn:length(entity.updateFile)) }</a>
		           	   </c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		               <input type="file" id="file" name="file" value="${entity.file }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	   
		            </c:if>
		        	<lable id="file_tip" style="color:red"></lable>
		        </label>
		        
		         <label><span style="color: red;">*</span>是否更新</label>
		        <label>
		        	<select id="isUpdate" name="isUpdate" class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.isUpdate eq 1 }">selected</c:if>>是</option>
		        		<option value="2" <c:if test="${entity.isUpdate eq 2 }">selected</c:if>>否</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="isUpdate_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>状态</label>
		        <label>
		        	<select id="status" name="status" class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.status eq 1 }">selected</c:if>>上线</option>
		        		<option value="2" <c:if test="${entity.status eq 2 }">selected</c:if>>下线</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="status_tip" style="color:red"></lable>
		        </label>
		        
		         <label>升级描述</label>
		        <label>
		        	<textarea rows="5" cols="100" id="description" name="description" class="input-xlarge" onkeydown="javascript:checkInput();" onkeyup="javascript:checkInput();"><c:out value="${entity.description }"></c:out></textarea>
		        	<lable id="description_tip" style="color:red"></lable>
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
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clean();" data-toggle="modal" class="btn">取消</a>
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