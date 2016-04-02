<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/sdkver.js" ></script>
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
     <li style="float: right;"><a href="javascript:edit('a','0');" class="btn">新增</a></li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/sdkver">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >
<div class="btn-toolbarDiv">
   <!-- <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
    <a href="javascript:delmul();" class="btn"> 批量删除 </a>  -->
      	
      	 版本号:&nbsp;<input class="condition" id="sdkVerCode" type="text" name="sdkVerCode" value="${sdkVerCode}"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       	  
       	是否更新:&nbsp;
       	<select style="height: 28px;" class="condition" id="isUpdate" name="isUpdate" class="input-xlarge" style="height: 40px;">
       		<option value="">--全部--</option>	        			        		  
       		<option value="1" <c:if test="${isUpdate == 1}">selected</c:if>>是</option>	        			        		  
       		<option value="2" <c:if test="${isUpdate == 2}">selected</c:if>>否</option>
       	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	 状态:&nbsp;
       	<select style="height: 28px;" class="condition" id="status" name="status" class="input-xlarge" style="height: 40px;">
       		<option value="">--全部--</option>	        			        		  
       		<option value="1" <c:if test="${status == 1}">selected</c:if>>上线</option>	        			        		  
       		<option value="2" <c:if test="${status == 2}">selected</c:if>>下线</option>
       	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	 <input type="button" name="find" value="查询" onclick="tofind()"/>
 		
  <div class="btn-group"> </div>
</div>

<div class="well">
    <table class="table">
    
      <thead>
        <tr><th colspan="7" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>      
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>版本号</th>
          <th>更新文件</th>
          <th>是否更新</th>
          <th>状态</th>
          <th style="width: 140px;text-align: center;">操作</th>
          <th>更新时间</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.sdkVerCode }</td>
          <td><a href="/manager/zhrt/sdkver/download?id=${entity.id }">${fn:substring(entity.updateFile,13,fn:length(entity.updateFile)) }</a></td>
          <td>
          	<c:if test="${entity.isUpdate eq 1}">
  				是
          	</c:if>
          	<c:if test="${entity.isUpdate eq 2}">否</c:if>
          </td>
          <td>
          	<c:set var="statusMap" value="<%=Constant.STATUS_MAP%>"></c:set>
          ${statusMap[entity.status] }</td>
          <td>
              <a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
              <a href="javascript:del('${entity.id }');" >删除</a>
          </td>
          <td><fmt:formatDate value="${entity.updateTime}" type="both"/></td>
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