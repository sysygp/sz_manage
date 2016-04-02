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
<form id="mainForm" method="post" action="/manager/power/domain">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>
  <div class="btn-group"> </div>
</div>

<div style="color: red;text-align:center;">${errorInfo }</div>
<div class="well">
    <table class="table">
    
      <thead>
        <tr>
          <th style="width: 10%;text-align: center;"><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th style="width: 30%;text-align: center;">域名</th>
          <th style="width: 60%;text-align: center;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td style="width: 10%;text-align: center;"><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td style="width: 30%;text-align: center;">${entity.name }</td>
          <td style="width: 60%;text-align: center;">
              <a href="/manager/power/role/index.do?domainId=${entity.id }">角色管理</a>&nbsp;&nbsp;
              <a href="/manager/power/user/index.do?domainId=${entity.id }">用户管理</a>&nbsp;&nbsp;
              <a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:del('${entity.id }');" >删除</a>
          </td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
</div>

<jsp:include page="../common/page.jsp" />

</form>

</body>
</html>