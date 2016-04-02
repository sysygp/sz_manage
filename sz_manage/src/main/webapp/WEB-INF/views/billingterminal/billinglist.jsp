<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/billingterminal">
<input type="hidden" name="pageNo" id="pageNo" value="${pageNo }" >
<input type="hidden" name="orderBy" id="orderBy" value="${orderBy }" >
<input type="hidden" name="order" id="order" value="${order }" >
<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="well">
    <table class="table">
    
      <thead>
      	<tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th>uuid</th>
          <th>手机号</th>
          <th>渠道</th>
          <th>通道</th>
          <th>价格</th>
          <th>cp</th>
          <th>业务</th>
          <th>时间</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input value="${entity.userUuid }" style="width: 80px;"/></td>
          <td><input value="${entity.tel }" style="width: 100px;"/></td>
          <td><input value="${entity.channelName }" style="width: 80px;"/></td>
          <td><input value="${entity.spCodeName }" style="width: 300px;"/></td>
          <td><input value="${entity.price }" style="width: 35px;"/></td>
          <td><input value="${entity.cpName }" style="width: 50px;"/></td>
          <td><input value="${entity.serviceName }" style="width: 90px;"/></td>
          <td><input value="<fmt:formatDate value="${entity.cTime}" type="both"/>" style="width: 120px;"/></td>
        </tr>
        </c:forEach>
      </tbody>
      
      
    </table>
</div>


<jsp:include page="../common/page.jsp" />

</form>
</body>
</html>