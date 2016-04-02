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
<form id="mainForm" method="post" action="/manager/zhrt/propinfo">
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
          <th>所属产品</th>
		  <th>道具名称</th>          
		  <th>道具价格(分)</th>          
		  <th>配置通道</th>          
		  <!-- <th>创建人</th>          
		  <th>创建时间</th> -->          
		  <th>修改人</th>          
		  <th>修改时间</th>  
          <th style="width: 66px;">操作</th>
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id}"/></td>
          <td>
          	<c:set var="appMap" value="<%=Constant.appMap %>"/>
			    ${appMap[entity.appId].appName }
          </td>
          <td>${entity.propName }</td>
          <td>${entity.propFee }</td>
          <td><a href="${ctx }/manager/zhrt/propspcode/index.do?propId=${entity.id }">设置</a> </td>
          <%-- <td>${entity.createPerson }</td>
          <td><fmt:formatDate value="${entity.createTime }" pattern="yyyy-MM-dd"/></td> --%>
          <td>${entity.lastUpdatePerson }</td>
          <td><fmt:formatDate value="${entity.lastUpdateTime }" pattern="yyyy-MM-dd"/></td>
          <td>
              <a href="javascript:edit('e','${entity.id }');"><i class="icon-pencil"></i></a>&nbsp;&nbsp;
              <a href="javascript:edit('v','${entity.id }');"><i class="icon-share-alt"></i></a>&nbsp;&nbsp;
              <a href="javascript:del('${entity.id }');" ><i class="icon-remove"></i></a>
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