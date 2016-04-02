<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/appversioninfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/ajax.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/appversioninfo" enctype="multipart/form-data">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >
<input type="hidden" name="appId" id="appId" value="${appId }">
	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>>保存 </a>
			<a href="javascript:myclear();" data-toggle="modal" class="btn">清空</a>
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
		    <form id="tab">
		        
		       <%--  <label>CP</label>
		        <label>
		        	<select id="cpId" name="cpId" class="input-xlarge" style="height: 40px;" onchange="javascript:getAppListByCp(1)">
    				<option value="">请选择</option>
    				<c:forEach items="${cpList }" var = "cp">
    					<option value="${cp.id }" <c:if test="${cp.id eq cpId }">selected="selected"</c:if>>${cp.cpName}</option>
    				</c:forEach>
    				</select>
		        	&nbsp;&nbsp;
		        	<lable id="cpId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>产品信息</label>
		        <label>
		        	<select id="appId" name="appId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:if test="${appInfo!=null }">
		        			<option value="${appInfo.id }" selected="selected">${appInfo.appName }</option>
		        		</c:if>
		        		<c:forEach items="${appInfoList }" var="appInfo" varStatus="st">
		        			
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="appId_tip" style="color:red"></lable>
		        </label> --%>
		        
		        <label>产品名称</label>
		        <label>
		        	<input type="text" id="appName" value="${appInfo.appName}" class="input-xlarge" style="height: 40px;" disabled="disabled">&nbsp;&nbsp;
		        	<lable id="appName_tip" style="color:red"></lable>
		        </label>
		        
		         <label><span style="color: red;">*</span>版本文件</label>
		        <label>
		        	<c:if test="${m eq 'a' }">
		        		<input type="file" id="verPackName" name="file" value="${entity.verPackName }" class="input-xlarge" style="height: 40px;">
		        	</c:if>
		        	<c:if test="${m eq 'v' or m eq 'e' }">
		        		<c:if test="${entity.verDownUrl !=null }"><a href="/manager/zhrt/appversioninfo/download?id=${entity.id }">${entity.verPackName }</a></c:if>
		        	</c:if>
		        	&nbsp;&nbsp;
		        	<lable id="verPackName_tip" style="color:red"></lable>
		        </label>
		        
		        <label>版本号</label>
		        <label>
		        	<input type="text" id="verNumber" name="verNumber" value="${entity.verNumber }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="verNumber_tip" style="color:red"></lable>
		        </label>
		        
		       <%--  <label>版本名称</label>
		        <label>
		        	<input type="text" id="verName" name="verName" value="${entity.verName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="verName_tip" style="color:red"></lable>
		        </label> --%>
		        
		         <label>SDK版本号</label>
		        <label>
		        	<input type="text" id="sdkVer" name="sdkVer" value="${appInfo.myepaySdkVer }" class="input-xlarge" style="height: 40px;" readonly="readonly">&nbsp;&nbsp;
		        	<lable id="sdkVer_tip" style="color:red"></lable>
		        </label>
		        
		        <label>状态</label>
		        <label>
		        	<select id="status" name="status" class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.status eq 1 }">selected</c:if>>上线</option>
		        		<option value="2" <c:if test="${entity.status eq 2 }">selected</c:if>>下线</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="verNumber_tip" style="color:red"></lable>
		        </label>
		        
		        
		        <label>推广渠道</label>
		        <label>
		        	<c:forEach items="${channelList }" var="channel" varStatus="status">
		        		<input name="channels" type="checkbox" value="${channel.id }" <c:if test="${verChanMap[channel.id] eq 1}">checked</c:if>>${channel.cnName }
		        	</c:forEach>
		        </label>
		        <%-- <label>产品下载地址</label>
		        <label>
		        	<input type="text" id="verDownUrl" name="verDownUrl" value="${entity.verDownUrl }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="verDownUrl_tip" style="color:red"></lable>
		        </label> --%>
		        
		        <label>版本更新</label>
		        <label>
		        	<%-- <input type="text" id="descript" name="descript" value="${entity.descript }" class="input-xlarge" style="height: 40px;"> --%>
		        	<textarea rows="8" cols="150" id="descript" name="descript" class="input-xlarge">${entity.descript }</textarea>
		        	&nbsp;&nbsp;
		        	<lable id="descript_tip" style="color:red"></lable>
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

</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>