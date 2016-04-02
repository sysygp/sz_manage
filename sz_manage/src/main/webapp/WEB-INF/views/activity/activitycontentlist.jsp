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
<form id="mainForm" method="post" action="/manager/zhrt/activitycontent">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>
  <div class="btn-group"> </div>
</div>


<div class="well">
    <table class="table">
    
      <thead>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>ID</th>
          <th>活动内容</th>
          <th>活动类型</th>
          <th>参与人数</th>
          <th>展示次数</th>
          <th>开始日期</th>
          <th>截止日期</th>
          <th>产品ID</th>
          <th>产品名称</th>
          <th>更新时间</th>
          <th style="width: 140px;text-align: center;">操作</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.id }</td>
          <td>${entity.activityContent }</td>
          <td>${entity.activityType }</td>
          <td>${entity.joinCount }</td>
          <td>${entity.showCount }</td>
          <td>${entity.startTimeStr }</td>
          <td>${entity.endTimeStr }</td>
          <td>${entity.appId }</td>
          <td>
          	<c:set var="appMap" value="<%=Constant.appMap %>"></c:set>
          	${appMap[entity.appId].appName }
          </td>
          <td><fmt:formatDate value="${entity.updateTime }" type="both"/></td>
          <td>
              <a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
              <a href="javascript:del('${entity.id }');" >删除</a>
              <a href="/manager/zhrt/activeparticipant/index?activityId=${entity.id }" >查看参与人员</a>
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