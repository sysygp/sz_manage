/**
 * 清空表单
 */
function clear(){
	$("input[type='text']").val("");
}

function validate(){
	var flag = true;
	var dataFileId = $("#dataFileId").val();
	if(dataFileId==null||dataFileId==""){
		flag = false;
		$("#dataFileId_tip").html("数据文件id不能为空");
	}else{
		$("#dataFileId_tip").html("");
	}
	var dataFile = $("#file").val();
	if(dataFile==null||dataFile==""){
		flag = false;
		$("#dataFile_tip").html("请选择数据文件版本");
	}else{
		$("#dataFile_tip").html("");
	}
	
	var supportVersion = $("input:checkbox[name='supportVersion']:checked").val();
	if(supportVersion==null||supportVersion==""){
		flag = false;
		$("#supportVersion_tip").html("请选择支持的游戏版本");
	}else{
		$("#supportVersion_tip").html("");
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
		$("input[type='checkbox'],select").attr("disabled","disabled");
	}
});

function delDataFile(id){
	if(confirm("删除后游戏的关卡数据将无法正常更新，是否确认？")){
		$("#mainForm").attr("action",$("#mainForm").attr("action")+"/del/"+id);
		$("#mainForm").submit();
	}else{
		return ;
	}
}