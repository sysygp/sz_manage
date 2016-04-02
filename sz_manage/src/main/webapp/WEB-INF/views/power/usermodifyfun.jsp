<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/power/css/dtree.css"/>
	<script type="text/javascript" src="${ctx }/webresources/power/js/user.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/power.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/dtree.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/power/user">
<input type="hidden" name="domainId" id="domainId" value="${domainId }" />

<input type="hidden" name="id" id="id" value="${entity.id }" />
<input type="hidden" name="m" id="m" value="${m}" />

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:saveExtend();" data-toggle="modal" class="btn">保存</a>
		</c:if>
		<a href="javascript:back();" data-toggle="modal" class="btn">返回</a>
		<div class="btn-group"></div>
	</div>

<div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		        <label>用户名称:&nbsp;&nbsp;${entity.loginName }</label>
		        
		        <label>用户权限</label>
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