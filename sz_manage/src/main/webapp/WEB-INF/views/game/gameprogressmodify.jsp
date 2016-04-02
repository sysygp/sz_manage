<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/view.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/gameprogress">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
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
		    	<label>用户ID</label>
		        <label>
		        	<input type="text" id="userid" name="userid" value="${entity.userid }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="userid_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>产品ID</label>
		        <label>
		        	<input type="text" id="appId" name="appId" value="${entity.appId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="appId_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>游戏关卡信息</label>
		        <label>
		        	<input type="text" id="gamePointInfo" name="gamePointInfo" value="${entity.gamePointInfo }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="gamePointInfo_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>游戏关卡其他信息</label>
		        <label>
		        	<input type="text" id="gameOtherInfo" name="gameOtherInfo" value="${entity.gameOtherInfo }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="gameOtherInfo_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>平台名称</label>
		        <label>
		        	<c:set var="platMap" value="<%=Constant.platMap %>"></c:set>
		        	<input type="text" id="platid" name="platid" value="${platMap[entity.platid].platName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="platid_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>终端类型</label>
		        <label>
		        	<c:set var="termTypeMap" value="<%=Constant.TERMTYPE_MAP %>"></c:set>
		        	<input type="text" id="termType" name="termType" value="${termTypeMap[entity.termType] }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="termType_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>终端型号</label>
		        <label>
		        	<input type="text" id="termModel" name="termModel" value="${entity.termModel }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="termModel_tip" style="color:red"></lable>
		        </label>
		    
		        <label>创建时间</label>
		        <label>
		        	<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="createTime_tip" style="color:red"></lable>
		        </label>
		        
		    	<label>修改时间</label>
		        <label>
		        	<input type="text" id="updateTime" name="updateTime" value="<fmt:formatDate value="${entity.updateTime}" type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="updateTime_tip" style="color:red"></lable>
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