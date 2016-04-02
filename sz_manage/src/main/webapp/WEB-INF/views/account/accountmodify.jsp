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
<form id="mainForm" method="post" action="/manager/zhrt/account">
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
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		    	<label>账户ID</label>
		        <label>
		        	<input type="text" id="accountId" name="accountId" value="${entity.accountId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="accountId_tip" style="color:red"></lable>
		        </label>
		        
		    	<label>渠道产品序号</label>
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
		    
		        <label>账户密码等级</label>
		        <label>
		        	<input type="text" id="accountPwdLevel" name="accountPwdLevel" value="${entity.accountPwdLevel }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="accountPwdLevel_tip" style="color:red"></lable>
		        </label>
		        
		        <label>账户余额(元)</label>
		        <label>
		        	<input type="text" id="accountMoney" name="accountMoney" value="${entity.accountMoney }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="accountMoney_tip" style="color:red"></lable>
		        </label>
		        
		         <label>虚拟币</label>
		        <label>
		        	<input type="text" id="virtualCurrency" name="virtualCurrency" value="${entity.virtualCurrency }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="virtualCurrency_tip" style="color:red"></lable>
		        </label>
		        
		        <label>账户积分</label>
		        <label>
		        	<input type="text" id="accountCore" name="accountCore" value="${entity.accountCore }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="accountCore_tip" style="color:red"></lable>
		        </label>
		        
		        <label>手机号</label>
		        <label>
		        	<input type="text" id="phone" name="phone" value="${entity.phone }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="phone_tip" style="color:red"></lable>
		        </label>
		        
		        <label>免密支付额度(元)</label>
		        <label>
		        	<input type="text" id="payPwdLimit" name="payPwdLimit" value="${entity.payPwdLimit }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="payPwdLimit_tip" style="color:red"></lable>
		        </label>
		        
		        <label>账户状态</label>
		        <label>
		        	<c:set var="statusMap" value="<%=Constant.STATUS_MAP %>"></c:set>
		        	<input type="text" id="accountStatus" name="accountStatus" value="${statusMap[fn:trim(entity.accountStatus)]}" class="input-xlarge" style="height: 40px;">
		        	&nbsp;&nbsp;
		        	<lable id="accountStatus_tip" style="color:red"></lable>
		        </label>
		        
		        <label>创建时间</label>
		        <label>
		        	<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value='${entity.createTime}' type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="createTime_tip" style="color:red"></lable>
		        </label>
		        
		        <label>更改时间</label>
		        <label>
		        	<input type="text" id="updateTime" name="updateTime" value="<fmt:formatDate value='${entity.updateTime}' type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
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