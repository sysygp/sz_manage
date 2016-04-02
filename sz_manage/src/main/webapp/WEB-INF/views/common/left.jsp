<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<div class="sidebar-nav">
	<c:forEach items="${menuList }" var="menu" varStatus="num" step="1" begin="0">
		<a href="#${menu.moduleId }" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>${menu.moduleName }</a>
        
		<c:if test="${moduleIdSession==menu.moduleId }">
        	<ul id="${menu.moduleId }" class="nav nav-list collapse in">
      	</c:if>
      	<c:if test="${moduleIdSession!=menu.moduleId }">
        	<ul id="${menu.moduleId }" class="nav nav-list collapse">
      	</c:if>
        <%-- <ul id="${menu.moduleId }" class="nav nav-list collapse"> --%>
        	<c:forEach items="${menu.functionList }" var="func">
        		<li><a href="${func.functionUrl }?moduleIdSession=${menu.moduleId }&funcId=${func.id}">${func.functionName }</a></li>
        	</c:forEach>
        </ul>
	</c:forEach>


        <a href="/login/out" class="nav-header" ><i class="icon-comment"></i>退出</a>
    </div>
</body>
</html>