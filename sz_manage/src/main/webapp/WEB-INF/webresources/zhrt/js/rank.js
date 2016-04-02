/**
 * 清空表单
 */
function clear(){
	$("input[type='text']").val("");
}

function validate(){
    var flag = true;
	var amountStr = $("#amountStr").val();
	if(amountStr==null||amountStr==""){
		flag = false;
		$("#amountStr_tip").html('排行榜数量不能为空');
	}else if(!checkInteger(amountStr)){
		flag = false;
		$("#amountStr_tip").html('排行榜数量只能是数字');
	}else{
		$("#amountStr_tip").html('');
	}
	
	var rankName = $("#rankName").val();
	if(rankName==null||rankName==""){
		flag = false;
		$("#rankName_tip").html('排行榜名称不能为空');
	}else{
		$("#rankName_tip").html('');
	}
	
	var rankTypeId = $("#rankTypeId").val();
	if(rankTypeId==null||rankTypeId==""){
		flag = false;
		$("#rankTypeId_tip").html('请选择排行榜内容');
	}else{
		$("#rankTypeId_tip").html('');
	}
	var sortWay = $("#sortWay").val();
	if(sortWay==null||sortWay==""){
		flag = false;
		$("#sortWay_tip").html('请选择排行顺序');
	}else{
		$("#sortWay_tip").html('');
	}
	var cpId = $("#cpId").val();
	if(cpId==null||cpId==""){
		flag = false;
		$("#cpId_tip").html('请选择cp');
	}else{
		$("#cpId_tip").html('');
	}
	var appId = $("#appId").val();
	if(appId==null||appId==""){
		flag = false;
		$("#appId_tip").html('请选择产品');
	}else{
		$("#appId_tip").html('');
	}
	var verId = $("#verId").val();
	if(verId==null||verId==""){
		flag = false;
		$("#verId_tip").html('请选择产品版本');
	}else{
		$("#verId_tip").html('');
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

function findUserAndRankInfo(rankCode){
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/getUserAndRankInfo/"+ rankCode);
	$("#mainForm").submit();
}

