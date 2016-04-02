<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/common/css/style.css"/>
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
<form id="mainForm" method="post" action="/manager/zhrt/signlog">
<input type="hidden" name="pageNo" id="pageNo" value=""/>
<input type="hidden" name="orderBy" id="orderBy" value="signDate" />
<input type="hidden" name="order" id="order" value="desc" />

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
	用户ID：<input type="text" id="userId" name="userId" style="height: 27px;" value="${userId }">
	产品名称：
	<select name="appId" id="appId">
		<option value="">-- 全部 --</option>
		<c:forEach items="${appList }" var="app">
			<option value="${app.id }" <c:if test="${appId eq app.id }">selected</c:if>>${app.appName }</option>
		</c:forEach>
	</select>
	渠道名称：
	<select name="channelId" id="channelId">
		<option value="">-- 全部 --</option>
		<c:forEach items="${channelList }" var="channel">
			<option value="${channel.id }" <c:if test="${channelId eq channel.id }">selected</c:if>>${channel.cnName }</option>
		</c:forEach>
	</select>
	<input type="button" value="查询" onclick="javascript:find()">
</div>
<div class="well">
    <table class="table">
      <thead>
        <tr><th colspan="5" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>     
        <tr>
          <th>用户ID</th>
          <th>产品</th>
          <th>产品版本</th>
          <th>渠道</th>
          <th>签到日期</th>
<!--           <th style="width: 180px;text-align: center;">操作</th> -->
        </tr>
      </thead>
	      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${entity.userId }</td>
          
          <c:set value="<%=Constant.appMap %>" var="appMap"></c:set>
          <td>${appMap[entity.appId].appName }</td>
          <c:set value="<%=Constant.appVersionMap %>" var="verMap"></c:set>
          <td>${verMap[entity.appVerId].verNumber}</td>
          <c:set value="<%=Constant.channelMap %>" var="channelMap"></c:set>
          <td>${channelMap[entity.channelId].cnName }</td>
          <td><fmt:formatDate value="${entity.signDate }" pattern="yyyy-MM-dd"/></td>
<%--           <td>
              <a href="javascript:edit('v','${entity.id }_${entity.userId}');">详情</a>&nbsp;&nbsp;
          </td> --%>
        </tr>
        </c:forEach>
      </tbody>
      
    </table>
</div>

<jsp:include page="../common/page.jsp" />

</form>
                    
</body>
</html>