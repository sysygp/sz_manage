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
<form id="mainForm" method="post" action="/manager/zhrt/account">
<input type="hidden" name="pageNo" id="pageNo" value=""/>
<input type="hidden" name="orderBy" id="orderBy" value="createTime" />
<input type="hidden" name="order" id="order" value="desc" />

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
	<table>
		<tr>
			<td>查询时间：<input type="text" name="createTimeStart" id="createTimeStart" value="${createTimeStart }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/> 至 <input type="text" name="createTimeEnd" id="createTimeEnd" value="${createTimeEnd }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>账户id：<input type="text" name="accountId" id="accountId" value="${accountId }" style="height: 30px;width:160px;"></td>
			<td>用户id：<input type="text" name="userid" id="userid" value="${userid }" style="height: 30px;width:160px;"></td>
			<td>
			</td>
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
          <th nowrap="nowrap">账户ID</th>
          <!-- <th nowrap="nowrap">账户密码等级</th> -->
          <th nowrap="nowrap">账户积分</th>
          <th nowrap="nowrap">账户余额(元)</th>
          <th nowrap="nowrap">虚拟币</th>
          <th nowrap="nowrap">免密支付额度(元)</th>
          <th nowrap="nowrap">账户状态</th>
          <th nowrap="nowrap">创建时间</th>
          <th style="width: 180px;text-align: center;">操作</th>
        </tr>
      </thead>
	      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td>${entity.userid }</td>
          <td>${entity.accountId }</td>
          <td>${entity.accountCore }</td>
          <td>￥${entity.accountMoney }</td>
          <td>${entity.virtualCurrency }</td>
          <td>￥${entity.payPwdLimit }</td>
          <td>
          		<c:set var="statusMap" value="<%=Constant.STATUS_MAP %>"></c:set>
          		${statusMap[fn:trim(entity.accountStatus)]}
          </td>
          <td><fmt:formatDate value="${entity.createTime}" type="both" /></td>
          <td>
              <a href="javascript:edit('v','${entity.id }_${entity.platid}');">详情</a>&nbsp;&nbsp;
              <a href="javascript:editAccountPwd('${entity.platid}', '${entity.phone }');">重置支付密码</a>&nbsp;&nbsp;
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