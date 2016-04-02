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
<form id="mainForm" method="post" action="/manager/power/role">
<input type="hidden" name="domainId" id="domainId" value="${domainId}" >

<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>
    <a href="javascript:editByDomain('a','0', '${domainId}');" class="btn">添加角色</a>
    <a href="/manager/power/user/index.do?domainId=${domainId}" class="btn"> 用户管理 </a>
  <div class="btn-group"> </div>
</div>


<div style="color: red;text-align:center;">${errorInfo }</div>
<div class="well">
    <table class="table">
    
      <thead>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>角色名称</th>
          <th style="width: 160px;text-align: center;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.name }</td>
          <td>
          	  <a href="/manager/power/user/list.do?roleId=${entity.id }&domainId=${entity.domainId }">浏览用户</a>&nbsp;&nbsp;
              <a href="javascript:editByDomain('e','${entity.id }', '${entity.domainId}');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:del('${entity.id }');" >删除</a>
          </td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
</div>

<jsp:include page="../common/page.jsp" />
</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>