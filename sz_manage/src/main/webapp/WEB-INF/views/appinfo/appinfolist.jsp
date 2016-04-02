<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.zhrt.util.Constant" %>
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
     <li>内容资源管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
     <li style="float: right;">
     <a href="javascript:edit('a','0');" class="btn"> 创建产品 </a>
     <a href="javascript:exportToExcel();" class="btn"> 导出excel</a>
     </li>
   </ul>
  
</div>
<form id="mainForm" method="post" action="/manager/zhrt/appinfo">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >
<div class="well">
	<table class="table">
		<tr>
<%--     		<td>CP：
    			<select name="cpId" id="cpId">
    				<option value="">请选择</option>
    				<c:forEach items="${cpList }" var = "cp">
    					<option value="${cp.id }" <c:if test="${appInfo.cpId eq cp.id}">selected="selected"</c:if>>${cp.cpName}</option>
    				</c:forEach>
    			</select>
    		</td> --%>
    		<td>产品名称：<input type="text" name="appName" value="${appInfo.appName }" style="height: 25px;"></td>
    		<%-- <td>微信wxAppId：<input type="text" name="wxAppId" value="${appInfo.wxAppId }" style="height: 25px;"></td>
    		<td>微信wxAppSecret：<input type="text" name="wxAppSecret" value="${appInfo.wxAppSecret }" style="height: 25px;"></td>
    		 --%>
    		 <td>产品类型：
    			<select name="appSort" id="appSort">
    				<option value="">请选择</option>
    				<c:forEach items="${appSortList }" var = "sort">
    					<option value="${sort.id }" <c:if test="${appInfo.appSort eq sort.id}">selected="selected"</c:if>>${sort.appTypeName}</option>
    				</c:forEach>
    			</select>
    		</td>
    		<td>状态：
    			<select name="status" style="height: 27px;" id="status">
    				<option value="">--全部--</option>
    				<option value="1" <c:if test="${appInfo.status eq 1}">selected</c:if>>上线</option>
    				<option value="2" <c:if test="${appInfo.status eq 2}">selected</c:if>>下线</option>
    			</select>
    		</td>
    		<td><input type="button" value="查询" onclick="javascript:find();"></td>
   		</tr>
	</table>
    <table class="table">
    
      <thead>
      	<tr><th colspan="9" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
		  <th>产品名称</th>          
		 <!--  <th>icon</th>           -->
		  <th>所属CP</th>          
		  <th>道具数量</th>          
		  <th>道具管理</th>          
		  <th>状态</th>          
		  <!-- <th>修改人</th>          
		  <th>修改时间</th>   -->
          <th style="width: 140px;text-align: center;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id}"/></td>
          <td>${entity.appName }</td>
          <!-- <td><img alt="" src=""></td> -->
          <td>
			    <c:set var="cpMap" value="<%=Constant.cpMap %>"/>
			    ${cpMap[entity.cpId].cpName }
          </td>
          <td>${entity.propCount }</td>
          <td><a href="/manager/zhrt/propinfo/index?appId=${entity.id }&cpId=${entity.cpId}">修改</a></td>
          <td>
          	<c:set var="statusMap" value="<%=Constant.STATUS_MAP %>"></c:set>
          	${statusMap[entity.status] }
          </td>
          <%-- <td>${entity.lastUpdatePerson }</td>
          <td><fmt:formatDate value="${entity.lastUpdateTime }" type="both"/></td> --%>
          <td>
              <c:if test="${entity.status ne 3 }">
              	<a href="javascript:edit('e','${entity.id }');">修改</a>&nbsp;&nbsp;
              	<a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
              	<a href="javascript:del('${entity.id }');" >删除</a>
              </c:if>
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