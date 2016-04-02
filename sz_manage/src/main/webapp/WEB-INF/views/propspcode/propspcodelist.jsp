<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/propspcode.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/propspcode">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="propId" id="propId" value="${propInfo.id }" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:editSelf('a','0','${propInfo.id }');" class="btn"> 新增 </a>
    <a href="javascript:delmulSelf('${propInfo.id }');" class="btn"> 批量删除 </a>
  <div class="btn-group"> </div>
</div>

<div class="well">
    <table class="table">

    <thead>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
		  <!-- <th>道具名称</th> -->          
		  <th>道具价格</th>          
		  <th>通道名称</th>          
		  <th>通道所属运营商</th>          
		  <th>通道顺序</th>          
		  <th>修改人</th>          
		  <th>修改时间</th>  
          <th style="width: 66px;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${propSpcodeList}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id}"/></td>
          <%-- <td>${propInfo.propName }</td> --%>
          <td>${propInfo.propFee }</td>
          <td>${spCodeMap[entity.spcodeId].name }</td>
          <td>
          	<c:if test="${spCodeMap[entity.spcodeId].operatorType eq 1 }">中国移动</c:if>
          	<c:if test="${spCodeMap[entity.spcodeId].operatorType eq 2 }">中国联通</c:if>
          	<c:if test="${spCodeMap[entity.spcodeId].operatorType eq 3 }">中国电信</c:if>
          </td>
          <td>${entity.spcodeSeq }</td>
          <td>${entity.lastUpdatePerson }</td>
          <td><fmt:formatDate value="${entity.lastUpdateTime }" pattern="yyyy-MM-dd"/></td>
          <td>
              <a href="javascript:editSelf('e','${entity.id }','${propInfo.id }');"><i class="icon-pencil"></i></a>&nbsp;&nbsp;
              <a href="javascript:editSelf('v','${entity.id }','${propInfo.id }');"><i class="icon-share-alt"></i></a>&nbsp;&nbsp;
              <a href="javascript:delSelf('${entity.id }','${propInfo.id }');" ><i class="icon-remove"></i></a>
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