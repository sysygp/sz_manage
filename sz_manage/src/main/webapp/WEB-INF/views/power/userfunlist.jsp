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
<form id="mainForm" method="post" action="/manager/power/userfun">
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
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>用户</th>
          <th>菜单</th>
          <th style="width: 140px;text-align: center;">操作</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.userId }</td>
          <td>${entity.functionId }</td>
          <td>
              <a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
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