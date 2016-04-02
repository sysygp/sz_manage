<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/datapack.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/datapackinfo" enctype="multipart/form-data">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >
<input type="hidden" name="appId" value="${appId }">

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
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
		    	<label><span style="color: red;">*</span>数据文件ID</label>
		        <label>
		        	<input type="text" id="dataFileId" name="dataFileId" value="${entity.dataFileId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="dataFileId_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>数据文件</label>
		        <label>
		        	<input type="file" id="file" name="file" value="${entity.dataFile }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="dataFile_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>状态</label>
		        <label>
		        	<select id="status" name="status" class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.status eq 1}">selected</c:if>>上线</option>
		        		<option value="2" <c:if test="${entity.status eq 2}">selected</c:if>>下线</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="status_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>支持游戏版本</label>
		        <label>
		        	<%-- <input type="text" id="remark" name="remark" value="${entity.remark }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp; --%>
		        	<c:forEach items="${appVerList }" var="appVer">
		        		<input type="checkbox" value="${appVer.id }" name="supportVersion" <c:if test="${supportVersionMap[appVer.id] eq '1'}">checked</c:if>>${appVer.verNumber }
		        	</c:forEach>
		        	<lable id="supportVersion_tip" style="color:red"></lable>
		        </label>
		        
		        <label>备注</label>
		        <label>
		        	<input type="text" id="remark" name="remark" value="${entity.remark }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="remark_tip" style="color:red"></lable>
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