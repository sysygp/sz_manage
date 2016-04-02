<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/My97DatePicker/WdatePicker.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/operlog">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
	<table class="table">
		<tr>
    		<td>查询时间：<input type="text" id="startTime" name="startTime" value="${startTime }" style="height: 27px;width: 100px;" class="Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',minData:'#F{$dp.$D(\'endTime\',{m:-2})}'})">至<input type="text" id="endTime" name="endTime" value="${endTime }" style="height: 27px;width: 100px;" class="Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',minData:'#F{$dp.$D(\'start\',{m:+2})}'})"></td>
    		<td>业务名称：
    			<select name="bussName" style="height: 27px;width: 100px;" id="status">
    				<option value="">--全部--</option>
    			</select>
    		</td>
    		<td>操作类型：
    			<select name="operType" style="height: 27px;width: 100px;" id="status">
    				<c:set var="operTypeMap" value="<%=Constant.OPERTYPE_MAP %>"></c:set>
    				<option value="">--全部--</option>
    				<c:forEach items="${operTypeMap }" var="oper">
    					<option value="${oper.key }" <c:if test="${operType eq oper.key }">selected</c:if>>${oper.value }</option>
    				</c:forEach>
    			</select>
    		</td>
    		<td><input type="button" value="查询" onclick="javascript:find();"></td>
    		<td><input type="button" value="重置" onclick="javascript:clean();"></td>
   		</tr>
	</table>
    <table class="table">
      <thead>
      	<tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th>业务名称</th>
		  <th>操作类型</th>          
		  <th>操作描述</th>  
		  <th>操作人</th>        
		  <th>操作人角色</th>        
		  <th>操作时间</th>        
          <th>操作结果</th>
          <th>操作IP</th>  
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${entity.bussName }</td>
          <td>
          	 <c:set var="operMap" value="<%=Constant.OPERTYPE_MAP %>"></c:set>
          	 ${operMap[entity.operType] }
          </td>
          <td>${entity.operDesc }</td>
          <td>${entity.operUser }</td>
          <td>${entity.operRole }</td>
          <td><fmt:formatDate value="${entity.operTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td>
          	<c:if test="${entity.operResult eq 1}">成功</c:if>
          	<c:if test="${entity.operResult eq 2}"><span style="color: red;">失败</span></c:if>
          </td>
          <td>${entity.ip }</td> 
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