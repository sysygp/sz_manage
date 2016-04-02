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
     <li>渠道产品管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
  
</div>
<form id="mainForm" method="post" action="/manager/zhrt/channelappinfo">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >


<div class="btn-toolbar">
    <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
   <!--  <a href="javascript:delmul();" class="btn"> 批量删除 </a> -->
  <div class="btn-group"> </div>
</div>

<div class="well">

<table class="table">
		<tr>
			<td>渠道：
				<select name="channelId" id="channelId">
    				<option value="">请选择</option>
    				<c:forEach items="${channelList }" var = "channel">
    					<option value="${channel.id }" <c:if test="${channelId eq channel.id}">selected="selected"</c:if>>${channel.cnName }</option>
    				</c:forEach>
    			</select>
			</td> 
			<td>CP：
    			<select id="cpId" name="cpId" onchange="javascript:getAppListByCp('')">
    				<option value="">请选择</option>
    				<c:forEach items="${cpList }" var = "cp">
    					<option value="${cp.id }" <c:if test="${cp.id eq cpId }">selected="selected"</c:if>>${cp.cpName}</option>
    				</c:forEach>
    			</select>
    		</td>
    		<td>产品：
    			<select name="appId" id="appId" onchange="javascript:getAppVersionByApp('')">
    				<option value="">请选择</option>
    				<c:forEach items="${appList }" var = "app">
    					<option value="${app.id }" <c:if test="${app.id eq channelAppInfo.appId }">selected="selected"</c:if>>${app.appName}</option>
    				</c:forEach>
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
          <th>渠道名称</th>
		  <th>产品名称</th>          
		  <th>channelapp编号</th>          
		  <!-- <th>文件名称</th>  -->         
		  <th>状态</th>          
		  <th>修改人</th>          
          <th>更新时间</th>          
          <th style="text-align: center;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id}"/></td>
          <td>
          	${channelMap[entity.channelId].cnName }
          </td>
          <td>
          		<c:set var="appMap" value="<%=Constant.appMap %>"/>
			    ${appMap[entity.appId].appName }
          </td>
           <td>${entity.chanAppVerSeq }</td>
          <%-- <td><c:if test="${entity.downUrl !=null }"><a href="/manager/zhrt/channelappinfo/download?id=${entity.id }">${entity.fileName }</a></c:if></td> --%>
          <td>
          	<c:if test="${entity.status eq 1}">上线</c:if>
          	<c:if test="${entity.status eq 2}">下线</c:if>
          </td>
          <td>${entity.lastUpdatePerson }</td>
          <td><fmt:formatDate value="${entity.lastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>  
               
          <td>
              <a href="javascript:edit('e','${entity.id }');">修改</a>&nbsp;&nbsp;
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
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>