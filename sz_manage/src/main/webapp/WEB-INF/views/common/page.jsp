<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<html>
	<body>
		<div class="pagination">
		    <ul>
		    	<c:if test="${page.totalCount > 0}">
		    		<li><a>当前 第 <c:out value="${page.pageNo}" /> 页 / 共 ${page.totalPages} 页 (共 <c:out 	value="${page.totalCount}" /> 条) </a></li>
		    		<c:if test="${page.pageNo!=1}">
						<li><a href="javascript:turn(1);">首页</a></li>
			   			<li><a href="javascript:turn(<c:out value="${page.pageNo-1}"/>);">上一页</a></li>
		   			</c:if>
		    		<c:if test="${page.pageNo!=page.totalPages}">
						<li><a href="javascript:turn(<c:out value="${page.pageNo+1}"/>);">下一页</a></li>
			   			<li><a href="javascript:turn(<c:out value="${page.totalPages}"/>);">末      页</a></li>
		   			</c:if>
		   			<li>
		   				<a>电梯直达 <input type="text" name="goPageNumber" id="goPageNumber" style="width:50px;" value="<c:out value="${page.pageNo+1}"/>" > 页</a>
		   				<a href="javascript:turn($('#goPageNumber').val());">GO</a>
		   			</li>
		    	</c:if>
		    </ul>
		</div>
	</body>
</html>
