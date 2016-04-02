<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/power/css/dtree.css"/>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/role.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/power.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/dtree.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/power/role">
<input type="hidden" name="domainId" id="domainId" value="${domainId }" />

<input type="hidden" name="id" id="id" value="${entity.id }" />
<input type="hidden" name="m" id="m" value="${m}" />

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn">保存</a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<c:if test="${m == 'v' }">
			<a href="javascript:back();" data-toggle="modal" class="btn">返回</a>
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
		        <label>角色名称</label>
		        <label>
		        	<input type="text" id="roleName" name="name" value="${entity.name }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="roleName_tip" style="color:red"></lable>
		        </label>
		        
		        <label>角色权限</label>
		        <div class="dtree">
		        		<script type="text/javascript">
		        			${dtreeBo.dtree}
							treeFormInit(); 
							mytree.oAll(true);
						</script>
						 <c:forEach items="${dtreeBo.hidePowerFunIds}" var="hideFunId"> 
						 	<input name="powerCode" id="powerCode_${hideFunId}" type="hidden" value="${hideFunId}">
						 </c:forEach>
		        	</div>
	        </div>
		</div>
	</div>

</form>
                    
</body>
</html>