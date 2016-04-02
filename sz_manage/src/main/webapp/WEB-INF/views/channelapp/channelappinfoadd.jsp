<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.zhrt.util.Constant" %>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/channelappinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/ajax.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>渠道管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>&nbsp;&nbsp;/&nbsp;&nbsp;运营产品&nbsp;
     <li style="float: right;"><a href="javascript:pak('1');" class="btn">打包</a> </li>
     &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
     <li><a href="#" class="btn"  onclick="javascript:window.history.back(-1);"> 返回</a></li>
   </ul>  
</div>
<form id="mainForm" method="post" action="/manager/zhrt/channelappinfo">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="channelId" id="channelId"  value="${channelId }"/>
<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="well">
<table class="table">
		<tr>
			<td>产品id：<input type="text" name="appId" id="appId" value="${appId }" style="height: 27px;"></td>
			<td>产品名称：<input type="text" name="appName" id="appName" value="${appName }" style="height: 27px;"></td>
			<td>产品类别：
			<select id="appSort" name="appSort" style="height: 27px;">
				<option value="">--全部--</option>
				<c:forEach items="${appTypeList }" var="type">
					<option value="${type.id }" <c:if test="${appSort eq type.id }">selected</c:if>>${type.appTypeName }</option>
				</c:forEach>
			</select>
			</td>
    		<td><input type="button" value="查询" onclick="javascript:edit('e','0');"></td>
   		</tr>
	</table>
    <table class="table">
      <thead>
        <tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>产品ID</th>
		  <th>产品名称</th>          
		  <th>版本号</th>          
		  <th>产品类别</th>          
		  <th>所属CP</th>          
		  <th>SDK版本号</th>          
		  <th>产品详情</th>          
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id}"/></td>
          <td>
          	${entity.appId }
          </td>
          <td>
			    ${entity.appName}
          </td>
          <td>${entity.verNumber }</td>
          <td>${entity.appTypeName }</td>
          <td>${entity.cpName }</td>
          <td>${entity.sdkVer }</td>
          <td><a href="javascript:getDetail('${entity.id }')">详情</a></td>
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