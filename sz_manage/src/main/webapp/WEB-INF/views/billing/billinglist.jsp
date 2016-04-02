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
<form id="mainForm" method="post" action="/manager/zhrt/billing">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="well">
    <table class="table">
    
      <thead>
      	<tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th>订单号</th>
          <th>计费指令</th>
          <th>计费手机号</th>
          <th>价格</th>
          <th>计费结果</th>
          <th>sp</th>
          <th>spcode</th>
          <th>cp</th>
          <th>业务</th>
          <th>道具</th>
          <th>计费时间</th>
          <th>修改时间</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${entity.linkId }</td>
          <td>${entity.msg }</td>
          <td>${entity.tel }</td>
          <td>${entity.price }</td>
          <td>${entity.status }</td>
          <td>${entity.spId }</td>
          <td>${entity.spCodeId }</td>
          <td>${entity.cpId }</td>
          <td>${entity.serviceId }</td>
          <td>${entity.propId }</td>
          <td><fmt:formatDate value="${entity.chargeTime}" type="both"/></td>
          <td><fmt:formatDate value="${entity.cTime}" type="both"/></td>
        </tr>
        </c:forEach>
      </tbody>
      
      
    </table>
</div>


<jsp:include page="../common/page.jsp" />

</form>
</body>
</html>