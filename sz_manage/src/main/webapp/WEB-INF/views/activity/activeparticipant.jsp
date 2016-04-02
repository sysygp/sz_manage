<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/activeparticipant">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="activityId" value="${activityId }">

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>


<div class="well">
	<table>
		<tr>
			<td>产品名称：<input type="text" name="appName" value="${appName }" style="height: 25px;"></td>
			<td>渠道：
				<select name="channelId" id="channelId">
    				<option value="">请选择</option>
    				<c:forEach items="${channelList }" var = "channel">
    					<option value="${channel.id }" <c:if test="${channelId eq channel.id}">selected="selected"</c:if>>${channel.cnName}</option>
    				</c:forEach>
    			</select>
			</td>
			<td><input type="button" value="查询" onclick="javascript:find();"></td>
    		<td><input type="button" value="重置" onclick="javascript:clean();"></td>
		</tr>
	</table>

    <table class="table">
      <thead>
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>User_ID</th>
          <th>产品名称</th>
          <th>产品版本</th>
          <th>渠道名称</th>
          <th>参与时间</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td>${entity.userId }</td>
          <td>${entity.appName }</td>
          <td>${entity.verNumber }</td>
          <td>${entity.cnName }</td>
          <td><fmt:formatDate value="${entity.joinTime }" type="both"/></td>
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