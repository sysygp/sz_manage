<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/noticeinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/ajax.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
    <script type="text/javascript" src="${ctx }/webresources/common/My97DatePicker/WdatePicker.js" ></script>
	
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}&nbsp;/&nbsp;
        <c:if test="${m == 'a' }">创建通知</c:if>
        <c:if test="${m == 'e' }">修改通知</c:if>
        <c:if test="${m == 'v' }">通知详情</c:if>
   </li>
   
   </ul>
   <input type="hidden" name="m" id="m" value="${m }" >
</div>

<form id="mainForm" method="post" action="/manager/zhrt/notice">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<div style="color: red;text-align:center;">${errorInfo }</div>
	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">通知详情</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		        
		    	<label><span style="color: red;">*</span>通知标题:&nbsp;</label>
		        <label>
		        	<input type="text" id="noticeTitle" name="noticeTitle" value="${entity.noticeTitle }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="noticeTitle_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>通知类型:&nbsp;</label>
		        <label>
		         <select id="noticeType" name="noticeType" class="input-xlarge" onchange="javascript:getAppListByCp('1')" style="height: 40px;">
       	            <option value="">--请选择--</option>	
       	    		<c:if test="${noticeTypeInfoList!= null }">	  
       	    		 <c:forEach items="${noticeTypeInfoList }"  var="noticeTypeInfo">      		        		        			        		
        	 		   <option value="${noticeTypeInfo.noticeTypeId}" 
        		   		<c:if test="${noticeTypeInfo.noticeTypeId == entity.noticeType }">selected</c:if> 
        				>${noticeTypeInfo.noticeTypeName}</option>     				        				        
       	      		 </c:forEach>
       	   		   </c:if>
       	          </select>
		        	&nbsp;&nbsp;
		        	<lable id="noticeType_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>通知内容:&nbsp;</label>
		        <label>		 
		        	<textarea rows="5" cols="100" id="noticeContent" name="noticeContent" class="input-xlarge" onkeydown="javascript:checkInput();" onkeyup="javascript:checkInput();"><c:out value="${entity.noticeContent }"></c:out></textarea>
		        	<span id="used">0</span> / 100
		        	&nbsp;&nbsp;
		        	<lable id="noticeContent_tip" style="color:red"></lable>
		        </label>
		    </form>
	        </div>
	        
	        <label>链接地址:&nbsp;</label>
	        <label>
	        	<input type="text" id="noticeLinkAddr" name="noticeLinkAddr" value="${entity.noticeLinkAddr }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
	        	<lable id="noticeLinkAddr_tip" style="color:red"></lable>
	        </label>
				
		  <label><span style="color: red;">*</span>cp名称:&nbsp;</label>
       	  <label>
       	  <select id="cpId" name="cpId" class="input-xlarge" onchange="javascript:getAppListByCp('1')" style="height: 40px;">
       	     <option value="">--请选择--</option>	
       	    <c:if test="${cpInfoList!= null }">	  
       	     <c:forEach items="${cpInfoList }"  var="cpInfo">      		        		        			        		
        	  <option value="${cpInfo.id}" 
        		   <c:if test="${cpInfo.id == entity.cpId }">selected</c:if>
        		>${cpInfo.cpName}</option>     				        				        
       	      </c:forEach>
       	    </c:if>
       	   </select>
		   <lable id="cpId_tip" style="color:red"></lable>
		    <br>
		  </label>
		  
		  <label><span style="color: red;">*</span>产品名称:&nbsp;</label>
       	  <label>
       	  <select id="appId" name="appId" class="input-xlarge" onchange="javascript:getAppVersionByApp('1')" style="height: 40px;">
       	  <option value="">--请选择--</option>	 
       	   <c:if test="${appInfoList!= null }">
       	    <c:forEach items="${appInfoList }"  var="appInfo">
        	 <option value="${appInfo.id}" 
        		<c:if test="${appInfo.id == entity.appId }">selected</c:if>
        	  >${appInfo.appName}</option>       				        		      
       	    </c:forEach>
       	   </c:if>
       	  </select>
		    <lable id="appId_tip" style="color:red"></lable>
		    <br>
		  </label>
			 	
			  <label><span style="color: red;">*</span>产品版本:&nbsp;</label>
        	  <label>
        	  <select id="verId" name="appVerId" class="input-xlarge" onchange="javascript:getChannelByAppVer('1')" style="height: 40px;">
        	   <option value="">--全部--</option>
        	   <c:if test="${appVersionInfoList!= null }">
        	    <c:forEach items="${appVersionInfoList}" var="appVersion">
	        		<option value="${appVersion.id}" 
	        		   <c:if test="${appVersion.id == entity.appVerId }">selected</c:if>
	        		>${appVersion.verNumber}</option>       				        			         
        	    </c:forEach>
        	   </c:if>
        	  </select>
			    <lable id="verId_tip" style="color:red"></lable>
			    <br>
			  </label>
			 	
			 
			  <label><span style="color: red;">*</span>渠道名称:&nbsp;</label>
        	  <label>
        	    <select id="channelId" name="channelId" class="input-xlarge" style="height: 40px;">       	  
        	    <option value="">--全部--</option>	
        	    <c:if test="${channelInfoList!= null }">	
        	      <c:forEach items="${channelInfoList }"  var="channelInfo">	        		        			        		
	        		<option value="${channelInfo.id}" 
	        		   <c:if test="${channelInfo.id == entity.channelId }">selected</c:if>
	        		>${channelInfo.cnName}</option>        				        				    
        	      </c:forEach>
        	    </c:if>
        	     </select>
			    <lable id="channelId_tip" style="color:red"></lable>
			    <br>
			  </label>
			 
			 
	        <label>起始时间:&nbsp;</label>
	        <label>
			    <input type="text" id="noticeBegin" name="noticeBeginStr" value="<fmt:formatDate value="${entity.noticeBegin }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-xlarge Wdate" style="height: 40px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'noticeEnd\')}'})" >	        	
			    <lable id="noticeBegin_tip" style="color:red"></lable>
	        </label>
	        <label>截止时间:&nbsp;</label>
	        <label>
		        <input type="text" id="noticeEnd" name="noticeEndStr" value="<fmt:formatDate value="${entity.noticeEnd }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-xlarge Wdate" style="height: 40px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'noticeBegin\')}'})" >&nbsp;&nbsp;
	        	<lable id="noticeEnd_tip" style="color:red"></lable>
	        </label>
	        <c:if test="${m == 'v' }">	
	           <label>更新时间:&nbsp;</label>
	           <label>
		        <input type="text" id="utime" name="utime" value="<fmt:formatDate value="${entity.utime }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-xlarge Wdate" style="height: 40px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'noticeBegin\')}'})" > &nbsp;&nbsp;
	        	<lable id="utime_tip" style="color:red"></lable>
	        </c:if> 
						
			<div class="tab-pane fade" id="profile">
				<form id="tab2">
					<label>New Password</label> 
					<input type="password" class="input-xlarge">
					<div>
						<button class="btn btn-primary">Update</button>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clean();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<div class="btn-group"></div>
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