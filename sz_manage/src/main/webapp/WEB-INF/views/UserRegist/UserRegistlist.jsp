<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/My97DatePicker/WdatePicker.js" ></script>
	
	<script type="text/javascript">
		function findByTime(){
			$("#mainForm").attr("action",$("#mainForm").attr("action")+"/index?flag=1");
			$("#mainForm").submit();
		}
		function findByChan(){
			$("#mainForm").attr("action",$("#mainForm").attr("action")+"/index?flag=2");
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
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/UserRegist">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >
<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="well">

	<span>
		开始时间：<input type="text" name="beginTime" id="beginTime" value="${beginTime }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
		结束时间：<input type="text" name="endTime" id="endTime" value="${endTime }" class="input-xlarge Wdate" onFocus="WdatePicker({lang:'zh-cn'})" style="height: 30px;width:120px;"/>
	</span>
	<span>
		<input type="button" value="按时间" onclick="javascript:findByTime();">
    	<input type="button" value="按渠道" onclick="javascript:findByChan();">			
	</span>


    <table class="table">
    
      <thead>
      	<tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <c:if test="${flag=='1' }">
          	<th>日期</th>
          </c:if>
          <c:if test="${flag=='2' }">
          	<th>渠道</th>
          </c:if>
          <th>新增用户数</th>
          <th>渠道结算用户</th>
          <th>结算比例</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
         <c:if test="${flag=='1' }">
          <td>${entity.userRegistDate }</td>
         </c:if>
         <c:if test="${flag==2 }">
          <td>${channelMap[entity.channelId].cnName }</td>
         </c:if>
          <td>${entity.userCount }</td>
          <td>${entity.channelUserCount }</td>
          <td>
          	<fmt:formatNumber value="${entity.channelUserCount/entity.userCount }"
          	type="percent" maxFractionDigits="2"/>
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