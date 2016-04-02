<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/view.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/subcode">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="subcode" id="subcode" value="${entity.subcode }" >
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
		    	<label>万号段</label>
		        <label>
		        	<input type="text" id="subcode" name="subcode" value="${entity.subcode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="subcode_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>所属省份</label>
		        <label>
		        	<input type="text" id="latinProvince" name="latinProvince" value="${entity.latinProvince }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="latinProvince_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>所属市</label>
		        <label>
		        	<input type="text" id="latinCity" name="latinCity" value="${entity.latinCity }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="latinCity_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>用户类型编号</label>
		        <label>
		        	<input type="text" id="userType" name="userType" value="${entity.userType }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="userType_tip" style="color:red"></lable>
		        </label>
		    
		    	<label>运营商ID</label>
		        <label>
		        	<input type="text" id="operator" name="operator" value="${entity.operator }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="operator_tip" style="color:red"></lable>
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