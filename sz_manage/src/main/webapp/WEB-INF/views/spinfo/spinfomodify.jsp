<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/spinfo.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/spinfo">
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
		    	<label>sp名称</label>
		        <label>
		        	<input type="text" id="name" name="name" value="${entity.name }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="name_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步地址(查看时自动生成，不要手动编辑)</label>
		        <label>
		        	<input type="text" id="syncUrl" name="syncUrl" value="${syncUrl }" readonly="readonly" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        </label>
		        
		        <label>流水同步类型(1:get形式; 2:xml形式)</label>
		        <label>
		        	<input type="text" id="synType" name="synType" value="${entity.synType }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="synType_tip" style="color:red"></lable>
		        </label>
		        
		        <label>通道指令长度(该sp分配给我们的未扩展的指令长度)</label>
		        <label>
		        	<input type="text" id="codeLen" name="codeLen" value="${entity.codeLen }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="codeLen_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步字段订单号(sp流水同步中存放订单号的字段，可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_linkid" name="f_linkid" value="${entity.f_linkid }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_linkid_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步字段计费代码(可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_msg" name="f_msg" value="${entity.f_msg }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_msg_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步字段spNumber(可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_spnumber" name="f_spnumber" value="${entity.f_spnumber }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_spnumber_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步字段手机号(可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_mobile" name="f_mobile" value="${entity.f_mobile }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_mobile_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步字段状态(可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_status" name="f_status" value="${entity.f_status }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_status_tip" style="color:red"></lable>
		        </label>
		        
		        <label>同步字段扩展(可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_ext" name="f_ext" value="${entity.f_ext }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_ext_tip" style="color:red"></lable>
		        </label>
		        
		        <label>回复字段(可以从sp技术对接文档中获取)</label>
		        <label>
		        	<input type="text" id="f_answer" name="f_answer" value="${entity.f_answer }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="f_answer_tip" style="color:red"></lable>
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

</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>