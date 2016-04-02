/**
 * 编辑权限等扩展功能
 * @param method
 * @param id
 * @param domainid
 */
function editExtend(method,id,domainid){
	if(method=='getfun'){
		$("#mainForm").attr("action",$("#mainForm").attr("action")+"/getfun/"+ id +"/"+ domainid +"/"+ method);
	}
	$("#mainForm").submit();
}

/**
 * 保存用户权限等扩展功能
 */
function saveExtend(){
	//if(method=='getfun'){
		$("#mainForm").attr("action",$("#mainForm").attr("action")+"/savefun");
		$("#mainForm").submit();
	//}
}

/**
 * 清空表单
 */
function clear(){
	$("#loginId").val("");
	$("#loginName").val("");
}

function validate(){
	var flag = true; 
	var loginId = $.trim($("#loginId").val());
	var loginName = $.trim($("#loginName").val());
	
	if(loginId==null || loginId==''){
		$("#loginId_tip").html("不能为空");
		flag = false;
	}else{
		$("#loginId_tip").html("");
	}
	
	if(loginName==null || loginName==''){
		$("#loginName_tip").html("不能为空");
		flag = false;
	}else{
		$("#loginName_tip").html("");
	}
	
	return flag;
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("input[type='text']").attr("readonly","readonly");
	}
});