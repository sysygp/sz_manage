<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/activitycontent.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/ajax.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/My97DatePicker/WdatePicker.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/activitycontent">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<div class="btn-group"></div>
	</div>


	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		    	<label>*活动标题</label>
		        <label>
		        	<input type="text" id="activityTitle" name="activityTitle" value="${entity.activityTitle }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="activityTitle_tip" style="color:red"></lable>
		        </label>
		    	<label>*活动类型</label>
		        <label>
		        	<%-- <input type="text" id="remark" name="remark" value="${entity.remark }" class="input-xlarge" style="height: 40px;"> --%>
		        	<select id="activityType" name="activityType" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${activityTypeList }" var="activityType">
		        			<option value="${activityType.id }" <c:if test="${activityType.id eq entity.activityType }">selected</c:if>>${activityType.activityTypeName }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="remark_tip" style="color:red"></lable>
		        </label>
		        <label>活动内容</label>
		        <label>
		        	<input type="text" id="activityContent" name="activityContent" value="${entity.activityContent }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="activityContent_tip" style="color:red"></lable>
		        </label>
		        <label>所属产品</label>
		        <label>
		        	<select name="appId" id="appId" onchange="javascript:getAppVersionByApp('')" class="input-xlarge" style="height: 40px;">
	    				<option value="">请选择</option>
	    				<c:forEach items="${appList }" var = "app">
	    					<option value="${app.id }" <c:if test="${app.id eq entity.appId }">selected="selected"</c:if>>${app.appName}</option>
	    				</c:forEach>
    				</select>
		        	&nbsp;&nbsp;
		        	<lable id="appId_tip" style="color:red"></lable>
		        </label>
		        <label>版本</label>
		        <label>
		        	<select id="verId" name="verId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${appVersionList }" var="ver">
		        			<option value="${ver.id }" <c:if test="${ver.id eq entity.verId }">selected="selected"</c:if>>${ver.verNumber }</option>
		        		</c:forEach>
		       		</select>
		        	&nbsp;&nbsp;
		        	<lable id="verId_tip" style="color:red"></lable>
		        </label>
		        <label>开始时间</label>
		        <label>
		        	<input type="text" id="startTime" name="startTime" value="${entity.startTimeStr }" class="input-xlarge Wdate" style="height: 40px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})" >&nbsp;&nbsp;
		        	<lable id="startTime_tip" style="color:red"></lable>
		        </label>
		        <label>截止时间</label>
		        <label>
		        	<input type="text" id="endTime" name="endTime" value="${entity.endTimeStr }" class="input-xlarge Wdate" style="height: 40px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})" >&nbsp;&nbsp;
		        	<lable id="endTime_tip" style="color:red"></lable>
		        </label>
		        <c:if test="${m eq 'v' }">
			        <label>创建时间</label>
			        <label>
			        	<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${entity.createTime }" type="both"/>" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
			        	<lable id="endTime_tip" style="color:red"></lable>
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