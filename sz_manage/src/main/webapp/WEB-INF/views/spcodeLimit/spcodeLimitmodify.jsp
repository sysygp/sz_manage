<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/spcodeLimit.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/spcodeLimit">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clean();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<div class="btn-group"></div>
	</div>
<div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		        <label><span style="color: red;">*</span>spcode(计费指令)</label>
		        <label>
		        	<select id="spcodeId" name="spcodeId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${spcodeList }"  var="spcode">
		        			<option value="${spcode.id }" <c:if test="${entity.spcodeId eq spcode.id}">selected="selected"</c:if>>${spcode.name }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="spcodeId_tip" style="color:red"></lable>
		        </label>
		        
		        <label>日限制条数</label>
		        <label>
		        	<input type="text" id="limitDayNum" name="limitDayNum" value="${entity.limitDayNum }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="limitDayNum_tip" style="color:red"></lable>
		        </label>
		        
		        <%-- <label>日限制金额</label>
		        <label>
		        	<input type="text" id="limitDayMoney" name="limitDayMoney" value="${entity.limitDayMoney }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="limitDayMoney_tip" style="color:red"></lable>
		        </label> --%>
		        
		        <label>月限制条数</label>
		        <label>
		        	<input type="text" id="limitMonthNum" name="limitMonthNum" value="${entity.limitMonthNum }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="limitMonthNum_tip" style="color:red"></lable>
		        </label>
		        
		       <%--  <label>月限制金额</label>
		        <label>
		        	<input type="text" id="limitMonthMoney" name="limitMonthMoney" value="${entity.limitMonthMoney }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="limitMonthMoney_tip" style="color:red"></lable>
		        </label> --%>
		        
		        
		    </form>
	        </div>
			
		</div>
	</div>

</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>