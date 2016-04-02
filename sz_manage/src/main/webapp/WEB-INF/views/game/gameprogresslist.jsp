<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.zhrt.util.Constant"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/common/css/style.css"/>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/gameprogress.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/gameprogress">
<input type="hidden" name="pageNo" id="pageNo" value="" />
<input type="hidden" name="orderBy" id="orderBy" value="createTime" />
<input type="hidden" name="order" id="order" value="desc" />

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
	<c:if test="${platidSession==0}">
		<span>
			平台ID：<input type="text" name="platid" id="platid" value="${platid }" class="input-xlarge" style="height: 30px;width:120px;"/>
		</span>
	</c:if>
	<span>
		用户id：<input type="text" name="userid" id="userid" value="${userid }" class="input-xlarge" style="height: 30px;width:120px;"/>
	</span>
	<span>
		<input type="button" class="btn" value="搜索" style="margin:-8px 0 0 40px;" id="search" data-url="/manager/zhrt/gameprogress/index.do" />			
	</span>
</div>

<div class="well">
    <table class="table">
    
      <thead>
      	<tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th>用户ID</th>
          <th>进度信息</th>
          <th>其他信息</th>
          <th>平台名称</th>
          <th>终端类型</th>
          <th>创建时间</th>
          <th>更新时间</th>
          <th style="width: 66px;">操作</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${entity.userid }</td>
          <td>${entity.gamePointInfo }</td>
          <td>${entity.gameOtherInfo }</td>
          <td>
          		<c:set var="platMap" value="<%=Constant.platMap %>"></c:set>
          		${platMap[entity.platid].platName }
          </td>
          <td>
          		<c:set var="termTypeMap" value="<%=Constant.TERMTYPE_MAP %>"></c:set>
          		${termTypeMap[entity.termType] }
          </td>
          <td><fmt:formatDate value="${entity.createTime}" type="both"/></td>
          <td><fmt:formatDate value="${entity.updateTime}" type="both"/></td>
          <td>
              <a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
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