<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/jquery-1.7.2.min.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/appinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
</head>

<body onload="javascript:checkInput();">
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>内容资源管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>&nbsp;&nbsp;/&nbsp;&nbsp;
     <c:if test="${ m == 'a' }">
              添加产品
     </c:if>
     <c:if test="${ m == 'e' }">
             修改产品
     </c:if>
     <c:if test="${ m == 'v' }">
      产品详情
     </c:if>
      &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
      <li><a class="btn"  onclick="javascript:window.history.back(-1);"> 返回</a></li>
       &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        <c:if test="${ m == 'v' }">
          <li style="float: right;">
           <a class="btn"  href="javascript:edit('e','${entity.id }');">修改</a>
          </li>
        </c:if>
   </ul>
  
</div>
<form id="mainForm" method="post" action="/manager/zhrt/appinfo">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >
<input type="hidden" name="myepayAppId" value="${entity.myepayAppId }" id="myepayAppId">
	
<div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">产品详情</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		    <label><span style="color: red;">*</span>产品名称</label>
		        <label>
		        	<input type="text" id="appName" name="appName" value="${entity.appName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="appName_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>所属CP</label>
		        <label>
		        	<select id="cpId" name="cpId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${cpInfoList }"  var="cp">
		        			<option value="${cp.id }" <c:if test="${entity.cpId eq cp.id}">selected="selected"</c:if>>${cp.cpName }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="cpId_tip" style="color:red"></lable>
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
		        
		        
		        <label>描述</label>
		        <label>
		        	<%-- <input type="text" id="descript" name="descript" value="${entity.descript }" class="input-xlarge" style="height: 40px;"> --%>
		        	<textarea rows="5" cols="100" id="descript" name="descript" class="input-xlarge" onkeydown="javascript:checkInput();" onkeyup="javascript:checkInput();"><c:out value="${entity.descript }"></c:out></textarea>
		        	<span id="used">0</span> / 200
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
    <div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>>保存 </a>
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