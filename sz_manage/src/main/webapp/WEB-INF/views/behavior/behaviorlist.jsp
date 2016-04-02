<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/common/css/style.css"/>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/behavior.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/My97DatePicker/WdatePicker.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/behavior">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="createTime" />
<input type="hidden" name="order" id="order" value="desc" />

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
	<%-- <c:if test="${platidSession==0}">
		<span>
			平台ID：<input type="text" name="platid" id="platid" value="${platid }" class="input-xlarge" style="height: 30px;width:160px;"/>
		</span>
	</c:if> --%>
	<table>
		<tr>
			<td>查询时间：<input type="text" name="mobileTimeStart" id="mobileTimeStart" value="${mobileTimeStart }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/> 至 <input type="text" name="mobileTimeEnd" id="mobileTimeEnd" value="${mobileTimeEnd }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/></td>
			<td>用户类型：
				<select style="height: 30px;width:160px;" name="userType" id="userType">
					<option value="">-- 全部 --</option>
					<option value="1" <c:if test="${userType eq 1 }">selected</c:if>>注册用户</option>
					<option value="2" <c:if test="${userType eq 2 }">selected</c:if>>临时用户</option>
				</select>
			</td>
			<td>行为类型：
				<select style="height: 30px;width:160px;" name="behavId" id="behavId">
					<option value="">-- 全部 --</option>
					<c:forEach items="${behTypeList }" var="behaver">
						<option value="${behaver.behavId }" <c:if test="${behavId eq behaver.behavId }">selected</c:if>>${behaver.behavName }</option>
					</c:forEach>
				</select>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>手机号：<input type="text" name="phone" id="phone" value="${phone }" style="height: 30px;width:160px;"></td>
			<td>用户id：<input type="text" name="userid" id="userid" value="${userid }" style="height: 30px;width:160px;"></td>
			<td>系统版本：<input type="text" name="" id="" value="" style="height: 30px;width:160px;"></td>
			<td><input type="button" class="btn" value="搜索" style="margin:-8px 0 0 40px;" id="search" onclick="find();"/></td>
		</tr>
	</table>
</div>

<div class="well">
    <table class="table">
      <thead>
      	<tr><th colspan="9" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th nowrap="nowrap">用户ID</th>
          <th nowrap="nowrap">手机号</th>
          <th nowrap="nowrap">用户类型</th>
          <th nowrap="nowrap">渠道名称</th>
          <th nowrap="nowrap">产品名称</th>
          <th nowrap="nowrap">产品版本</th>
          <th nowrap="nowrap">终端型号</th>
          <th nowrap="nowrap">终端创建时间</th>
          <th nowrap="nowrap">行为类型</th>
          <th style="width: 66px;">操作</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${entity.userid }</td>
          <td>${entity.phone }</td>
          <td>
          	<c:choose>
          		<c:when test="${entity.userType==1 }">
          			注册用户
          		</c:when>
          		<c:when test="${entity.userType==2 }">
          			临时用户
          		</c:when>
          	</c:choose>
          </td>
          <c:set var="channelMap" value="<%=Constant.channelMap %>"></c:set>
          <td>${channelMap[entity.channelId].cnName }</td>
          <c:set var="appMap" value="<%=Constant.appMap %>"></c:set>
          <td>${appMap[entity.appId].appName}</td>
          <c:set var="verMap" value="<%=Constant.appVersionMap %>"></c:set>
          <td>${verMap[entity.verId].verNumber }</td>
          <td>${entity.termModel }</td>
          <td><fmt:formatDate value="${entity.mobileTime}" type="both"/></td>
          <td>${entity.behavName }</td>
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