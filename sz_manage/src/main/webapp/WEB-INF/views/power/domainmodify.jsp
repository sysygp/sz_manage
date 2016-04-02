<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/power/js/domain.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/power/domain">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn">保存</a>
			<a href="javascript:clear();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<c:if test="${m == 'v' }">
			<a href="javascript:back();" data-toggle="modal" class="btn">返回</a>
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
		        <label>域名</label>
		        <label>
		        	<input type="text" id="domainName" name="name" value="${entity.name }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="domainName_tip" style="color:red"></lable>
		        </label>
		        
		        <c:if test="${operUser.platId == 0 }">
		        	<label>所属平台</label>
			        <label>
			        	<select name="platid" id="platid">
			        		<option value="">--请选择--</option>
				        	<c:forEach items="${platList }" var="plat">
				        		<option value="${plat.platid }" <c:if test="${plat.platid == entity.platId }"> selected </c:if> >${plat.platname }</option>
				        	</c:forEach>
			        	</select>
			        	<lable id="platid_tip" style="color:red"></lable>
			        </label>
		        </c:if>
		        <c:if test="${operUser.platId != 0 }">
		        	<input type="hidden" id="platid" name="platid" value="${operUser.platId }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="platid_tip" style="color:red"></lable>
		        </c:if>
		        
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

</form>
                    
</body>
</html>