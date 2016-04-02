
<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/common/css/style.css"/>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/account.js" ></script>
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
<form id="mainForm" method="post" action="/manager/zhrt/accountrecharge">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="chargeTime" >
<input type="hidden" name="order" id="order" value="desc" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
	<table>
		<tr>
			<td>查询时间：<input type="text" name="chargeTimeStart" id="chargeTimeStart" value="${chargeTimeStart }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/> 至 <input type="text" name="chargeTimeEnd" id="chargeTimeEnd" value="${chargeTimeEnd }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>账户id：<input type="text" name="accountId" id="accountId" value="${accountId }" style="height: 30px;width:160px;"></td>
			<td>充值类型：
				<select id="refeeStatus" name="refeeStatus" style="height: 30px;width:160px;">
					<option value="">-- 全部 --</option>
					<c:forEach items="${payTypeList }" var="type">
						<option value="${type.payTypeId }" <c:if test="${refeeStatus eq type.payTypeId}">selected</c:if>>${type.paytype }</option>
					</c:forEach>
				</select>
			</td>
			<td><input type="button" class="btn" value="搜索" style="margin:-8px 0 0 40px;" id="search" onclick="find();"/></td>
		</tr>
	</table>
</div>

<div class="well">
    <table class="table">
      <thead>
      <tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th>充值ID</th>
          <th>账户ID</th>
          <th>充值金额(元)</th>
          <th>账户余额(元)</th>
          <th>充值类型</th>
          <th>渠道名称</th>
          <th>产品名称</th>
          <th>产品版本</th>
          <th>充值时间</th>
          <th style="width: 66px;">操作</th>
        </tr>
      </thead>
	  
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>{entity.indexId }</td>
          <td>${entity.accountId }</td>
          <td>￥${entity.rechargeMoney }</td>
          <td>￥${entity.spareMoney }</td>
          <td>
          		<c:set var="payTypeMap" value="<%=Constant.payTypeMap %>"></c:set>
          		${payTypeMap[fn:trim(entity.refeeStatus)].payType}
          </td>
        <td>
		  		<c:set var="channelMap" value="<%=Constant.channelMap %>"></c:set>
		  		${channelMap[entity.channelId].cnName }
		  </td>
          <td>
		  		<c:set var="appMap" value="<%=Constant.appMap %>"></c:set>
		  		${appMap[entity.appId].appName }
		  </td>
		  <td>
		  		<c:set var="verMap" value="<%=Constant.appVersionMap %>"></c:set>
		  		${verMap[entity.verId].verNumber }
		  </td>
          <td><fmt:formatDate value="${entity.chargeTime}" type="both"/></td>
          <td>
              <a href="javascript:edit('v','${entity.id }_${entity.platid}');">详情</a>&nbsp;&nbsp;
          </td>
        </tr>
        </c:forEach>
      </tbody>
      
    </table>
</div>


<jsp:include page="../common/page.jsp" />

</form>
                    
</body>
</html>