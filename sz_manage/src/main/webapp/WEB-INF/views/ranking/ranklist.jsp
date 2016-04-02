<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/rank.js" ></script>
	
	<script type="text/javascript">
	   function tofind(){
			  
		   $("#mainForm").attr("action",$("#mainForm").attr("action")+"/index");
			$("#mainForm").submit();
		  
	   }
	 
	</script>
</head>

<body>

<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
      <li style="float: right;"><a href="javascript:edit('a','0');" class="btn">创建排行榜</a></li>
   </ul>
  
</div>
  
<form id="mainForm" method="post" action="/manager/zhrt/ranking">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbarDiv">
   <!-- <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>  -->
      	
      	 排行榜ID:&nbsp;<input class="condition" id="rankId" type="text" name="rankId" value="${rankId}"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	 产品名称:&nbsp;<input class="condition" id="appName" type="text" name="appName" value="${appName}"/>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	 <input type="button" name="find" value="查询" onclick="tofind()"/>
 		
  <div class="btn-group"> </div>
</div>

  
<div class="well">
    <table class="table" border="1">    
      <thead>
        <tr><th colspan="9" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>排行榜ID</th>
          <th>排行榜名称</th>
          <th>产品ID</th>
          <th>产品名称</th>
          <th>排行内容</th>
          <th>排序方式</th>
          <th>创建时间</th>          
          <th style="width: 140px;text-align: center;">操作</th>
        </tr> 
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.rankCode }</td>
          <td>${entity.rankName }</td>
          <td>${entity.appId }</td>
          <td>${entity.appName }</td>
          <td>${entity.rankTypeName }</td>
          <td>
          <c:if test="${entity.sortWay == 0 }">升序</c:if>
          <c:if test="${entity.sortWay == 1 }">降序</c:if>
          </td>          
          <td><fmt:formatDate value="${entity.createTime}" type="both"/></td>
          <td>
              <a href="javascript:edit('e','${entity.id }');">修改</a>&nbsp;&nbsp;
              <a href="javascript:findUserAndRankInfo('${entity.rankCode }');">查看榜内玩家</a>&nbsp;&nbsp;
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