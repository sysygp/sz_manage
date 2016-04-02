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
<form id="mainForm" method="post" action="/manager/zhrt/behavior">
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
		    
		    	<label>用户类型</label>
		        <label>
		        	<input type="text" id="userType" name="userType" value="${entity.userType==1? '注册用户':(entity.userType==2? '临时用户':'未知') }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="userType_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>手机号</label>
		        <label>
		        	<input type="text" id="phone" name="phone" value="${entity.phone }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="phone_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>省份</label>
		        <label>
		        	<input type="text" id="province" name="province" value="${entity.province }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="province_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>省份名称</label>
		        <label>
		        	<input type="text" id="provinceName" name="provinceName" value="${entity.provinceName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="provinceName_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>账户ID</label>
		        <label>
		        	<input type="text" id="accountid" name="accountid" value="${entity.accountid }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="accountid_tip" style="color:red"></lable>
		        </label>
		    	
		    	<label>用户行为类型编号</label>
		        <label>
		        	<input type="text" id="behavId" name="behavId" value="${entity.behavId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="behavId_tip" style="color:red"></lable>
		        </label>
		    
		        <label>用户行为类型描述</label>
		        <label>
		        	<input type="text" id="behavName" name="behavName" value="${entity.behavName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="behavName_tip" style="color:red"></lable>
		        </label>
		        
		    	<label>平台名称</label>
		        <label>
		        	<input type="text" id="platname" name="platname" value="${entity.platname }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="platname_tip" style="color:red"></lable>
		        </label>
		        
		    	 <label>渠道产品序列号</label>
		        <label>
		        	<input type="text" id="chanAppVerSeq" name="chanAppVerSeq" value="${entity.chanAppVerSeq }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="chanAppVerSeq_tip" style="color:red"></lable>
		        </label>
		        
		    	 <label>CP名称</label>
		        <label>
		        	<c:set var="cpMap" value="<%=Constant.cpMap %>"></c:set>
		        	<input type="text" id="cpId" name="cpId" value="${cpMap[entity.cpId].cpName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="cpId_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>产品名称</label>
		        <label>
		        	<c:set var="appMap" value="<%=Constant.appMap %>"></c:set>
		        	<input type="text" id="appId" name="appId" value="${appMap[entity.appId].appName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="appId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>产品版本名称</label>
		        <label>
		        	<c:set var="verMap" value="<%=Constant.appVersionMap %>"></c:set>
		        	<input type="text" id="verId" name="verId" value="${verMap[entity.verId].verNumber }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="verId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>sdk版本号</label>
		        <label>
		        	<input type="text" id="sdkVer" name="sdkVer" value="${entity.sdkVer }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="sdkVer_tip" style="color:red"></lable>
		        </label>
		        
		       <label>渠道名称</label>
		        <label>
		        	<c:set var="channelMap" value="<%=Constant.channelMap %>"></c:set>
		        	<input type="text" id="channelId" name="channelId" value="${channelMap[entity.channelId].cnName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="channelId_tip" style="color:red"></lable>
		        </label>
		        
		    	<label>IMEI</label>
		        <label>
		        	<input type="text" id="imei" name="imei" value="${entity.imei }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="imei_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>IMSI</label>
		        <label>
		        	<input type="text" id="imsi" name="imsi" value="${entity.imsi }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="imsi_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>MAC</label>
		        <label>
		        	<input type="text" id="mac" name="mac" value="${entity.mac }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="mac_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>终端类型</label>
		        <label>
		        	<input type="text" id="termType" name="termType" value="${entity.termType==1 ? '手机':(entity.termType==2 ? '平板':'未知')  }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="termType_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>终端型号</label>
		        <label>
		        	<input type="text" id="termModel" name="termModel" value="${entity.termModel }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="termModel_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>版本号</label>
		        <label>
		        	<input type="text" id="version" name="version" value="${entity.version }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="version_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>经度</label>
		        <label>
		        	<input type="text" id="longitude" name="longitude" value="${entity.longitude }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="longitude_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>纬度</label>
		        <label>
		        	<input type="text" id="latitude" name="latitude" value="${entity.latitude }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="latitude_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>手机时间</label>
		        <label>
		        	<input type="text" id="mobileTime" name="mobileTime" value="<fmt:formatDate value="${entity.mobileTime}" type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="mobileTime_tip" style="color:red"></lable>
		        </label>
		    
		        <label>创建时间</label>
		        <label>
		        	<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime}" type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="createTime_tip" style="color:red"></lable>
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