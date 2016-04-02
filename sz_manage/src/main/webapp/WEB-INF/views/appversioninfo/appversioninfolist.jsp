<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  import="com.zhrt.util.Constant" %>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/appversioninfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/ajax.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/appversioninfo">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="appId" id="appId" value="${avi.appId }" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>
  <div class="btn-group"> </div>
</div>

<div class="well">
	<table class="table">
		<tr>
			<%-- <td>CP：
    			<select id="cpId" name="cpId" onchange="javascript:getAppListByCp('')">
    				<option value="">请选择</option>
    				<c:forEach items="${cpList }" var = "cp">
    					<option value="${cp.id }" <c:if test="${cp.id eq cpId }">selected="selected"</c:if>>${cp.cpName}</option>
    				</c:forEach>
    			</select>
    		</td>
    		<td>产品：
    			<select name="appId" id="appId">
    				<option value="">请选择</option>
    				<c:forEach items="${appList }" var = "app">
    					<option value="${app.id }" <c:if test="${app.id eq avi.appId }">selected="selected"</c:if>>${app.appName}</option>
    				</c:forEach>
    			</select>
    		</td>
    		<td>版本名称：<input type="text" name="verNumber" value="${avi.verNumber }" style="height: 25px;"></td> --%>
    		
    		<td>版本号：<input type="text" name="verNumber" value="${avi.verNumber }" style="height: 25px;"></td>
    		<td>状态：
    			<select name="status" style="height: 27px;" id="status">
    				<option value="">--全部--</option>
    				<option value="1" <c:if test="${avi.status eq 1}">selected</c:if>>上线</option>
    				<option value="2" <c:if test="${avi.status eq 2}">selected</c:if>>下线</option>
    			</select>
    		</td>
    		<td><input type="button" value="查询" onclick="javascript:find();"></td>
    		<td><input type="button" value="重置" onclick="javascript:myclear();"></td>
   		</tr>
	</table>
    <table class="table">
    
      <thead>
        <tr><th colspan="9" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>Icon</th>
          <th>产品名称</th>
		  <th>版本号</th>          
		  <th>包体大小</th>          
		  <!-- <th>版本来源</th> -->
		  <th>SDK版本号</th>
		  <th>渠道包</th>          
		  <th>状态</th>          
<!-- 		  <th>创建人</th>          
		  <th>创建时间</th>          
		  <th>修改人</th>          
		  <th>修改时间</th>   -->
		  <th style="width: 140px;text-align: center;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id}"/></td>
          <td><img alt="" src="${entity.icon }"></td>
          <td>
			    ${appMap[entity.appId].appName }
          </td>
          <td>${entity.verNumber }</td>
          <td>${entity.verPackFileLength } B</td>
          <td>${entity.sdkVer }</td>
          <td><c:if test="${entity.verDownUrl !=null }"><a href="/manager/zhrt/appversioninfo/download?id=${entity.id }">${entity.verPackName }</a></c:if></td>
          <td>
          	<c:if test="${entity.status eq 1 }">
          		上线
          	</c:if>
          	<c:if test="${entity.status eq 2 }">
          		下线
          	</c:if>
          </td>
<%--           <td>${entity.createPerson }</td>
          <td><fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd"/></td>
          <td>${entity.lastUpdatePerson }</td>
          <td><fmt:formatDate value="${entity.lastUpdateTime }" pattern="yyyy-MM-dd"/></td> --%>
          <td>
              <c:if test="${entity.status ne 3 }">
              	<a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
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