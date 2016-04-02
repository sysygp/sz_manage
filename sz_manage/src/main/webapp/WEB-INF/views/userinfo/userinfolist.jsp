<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<%-- <script type="text/javascript" src="${ctx }/webresources/zhrt/js/userinfo.js" ></script> --%>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>用户管理<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
  
</div>

<form id="mainForm" method="post" action="/manager/zhrt/userinfo">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="${orderBy }" >
<input type="hidden" name="order" id="order" value="${order }" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

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
    		<td><input type="button" value="查询" onclick="javascript:find();"></td>
    		<!-- <td><input type="button" value="重置" onclick="javascript:clean();"></td> -->
   		</tr>
	</table>
    <table class="table">
      <thead>
      	<tr><th colspan="9" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th>渠道</th>
          <th>渠道产品编号</th>
		  <th>手机号</th>          
		  <th>省份</th>  
		  <th>imsi</th>        
		  <th>创建时间</th>        
        </tr>
      </thead>
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${channelMap[entity.channelId].cnName }</td>
          <td>${entity.chanAppSeq }</td>
          <td>${entity.tel }</td>
          <td>
          	<%-- <fmt:formatNumber value="${entity.province }" pattern="#,#00" var="provinceId"/> --%>
          	${latnMap[entity.province] }
          </td>
          <td>${entity.imsi }</td>
          <td><fmt:formatDate value="${entity.cTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
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