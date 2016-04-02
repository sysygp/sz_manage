<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<div class="content">
	<div class="container-fluid">
		<div class="row-fluid">
			<c:if test="${toPage != null}">
				<jsp:include page="${toPage }" />
			</c:if>
			<c:if test="${toPage == null}">
				 <jsp:include page="right_default.jsp" /> 
			</c:if>
		</div>
	</div>
</div>

</body>
</html>