<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/datapack.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/datapackinfo">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="appId" value="${dataPackInfo.appId }">

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>
  <div class="btn-group"> </div>
</div>


<div class="well">
	<table class="table">
		<tr>
			<td>数据文件版本ID：
				<input type="text" name="dataFileId" id="dataFileId" value="${dataPackInfo.dataFileId }">
			</td>
			<td>状态：
				<select name="status" id="status">
					<option value="">--全部--</option>
					<option value="1" <c:if test="${dataPackInfo.status eq 1 }">selected</c:if>>上线</option>
					<option value="2" <c:if test="${dataPackInfo.status eq 2 }">selected</c:if>>下线</option>
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
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>数据文件ID</th>
          <th>数据文件</th>
          <th>数据文件状态</th>
          <th>支持游戏版本</th>
          <th>备注</th>
          <th>更新时间</th>
          <th style="width: 140px;text-align: center;">操作</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.dataFileId }</td>
          <td><a href="/manager/zhrt/datapackinfo/download?id=${entity.id }">${fn:substring(entity.dataFile,13,fn:length(entity.dataFile)) }</a></td>
          <td>
          	<c:if test="${entity.status eq 1}">上线</c:if>
          	<c:if test="${entity.status eq 2}">下线</c:if>
          </td>
          <td>${entity.supportVersion }</td>
          <td>${entity.remark }</td>
          <td><fmt:formatDate value="${entity.updateTime}" type="both"/></td>
          <td>
              <a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
              <a href="javascript:delDataFile('${entity.id }');" >删除</a>
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