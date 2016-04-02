<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/rank.js" ></script>	
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
        <c:if test="${m == 'a' }">创建排行榜</c:if>
        <c:if test="${m == 'e' }">修改排行榜</c:if>
        <c:if test="${m == 'v' }">查看帮内玩家</c:if>
   </li>
   
   </ul>
</div>
<div style="color: red;text-align:center;">${errorInfo }</div>
<form id="mainForm" method="post" action="/manager/zhrt/ranking">
  <input type="hidden" name="m" id="m" value="${m }" >
  <input type="hidden" name="id" id="id" value="${entity.id }" >
	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">排行榜详情</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		       <c:if test="${m == 'a' }">
		    	<label><span style="color: red;">*</span>排行榜数量:&nbsp;</label>
		        <label>
		        	<input type="text" id="amountStr" name="amountStr"
		        	  <c:if test="${entity.amountStr} == null">value=""</c:if>
		        	  <c:if test="${entity.amountStr} != null">value="${entity.amountStr}"</c:if>
		        	 class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="amountStr_tip" style="color:red"></lable>
		        </label>
		       </c:if>   
		       <c:if test="${m == 'e' }">
		          <label><span style="color: red;">*</span>排行榜ID:&nbsp;</label>
		          <label>
		            <input type="text" id="rankCode" name="rankCode" value="${entity.rankCode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="rankCode_tip" style="color:red"></lable>
		          </label>
		       </c:if>
		        
		        <label><span style="color: red;">*</span>排行榜名称:&nbsp;</label>
		        <label>
		        	<input type="text" id="rankName" name="rankName" value="${entity.rankName }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="rankName_tip" style="color:red"></lable>
		        </label>
		        
		        <label><span style="color: red;">*</span>排行内容</label>
		        <label>
		        	<select id="rankTypeId" name="rankTypeId" class="input-xlarge" style="height: 40px;">
		        		<option value="">--请选择--</option>	        			        		
		        		<c:if test="${rankTypeInfoList!= null }">	  
	       	    		 <c:forEach items="${rankTypeInfoList }"  var="rankTypeInfo">      		        		        			        		
	        	 		   <option value="${rankTypeInfo.rankTypeId}" 
	        		   		<c:if test="${rankTypeInfo.rankTypeId == entity.rankTypeId }">selected</c:if> 
	        				>${rankTypeInfo.rankTypeName}</option>     				        				        
       	      		     </c:forEach>
       	   		        </c:if>       				        		
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="rankTypeId_tip" style="color:red"></lable>
		        </label>
		        <label><span style="color: red;">*</span>排行顺序</label>
		        <label>
		        	<select id="sortWay" name="sortWay" class="input-xlarge" style="height: 40px;">
		        		<option value="">--请选择--</option>	        			        		
		        		<option value="0" 
		        		   <c:if test="${entity.sortWay==0 }">selected</c:if>
		        		>升序</option>
		        		<option value="1" 
		        		   <c:if test="${entity.sortWay==1 }">selected</c:if>
		        		>降序</option>		        				        		
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="sortWay_tip" style="color:red"></lable>
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
        	  <select id="verId" name="verId" class="input-xlarge" onchange="javascript:getChannelByAppVer('1')" style="height: 40px;">
        	   <option value="">--全部--</option>
        	   <c:if test="${appVersionInfoList!= null }">
        	    <c:forEach items="${appVersionInfoList}" var="appVersion">
	        		<option value="${appVersion.id}" 
	        		   <c:if test="${appVersion.id == entity.verId }">selected</c:if>
	        		>${appVersion.verNumber}</option>       				        			         
        	    </c:forEach>
        	   </c:if>
        	  </select>
			    <lable id="verId_tip" style="color:red"></lable>
			    <br>
			  </label>
		    </form>
	        </div>						
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
			<a href="javascript:clean();" data-toggle="modal" class="btn">取消</a>
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