/**
 * 清空表单
 */
function editSelf(method,id,propId){
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/get/"+id+"/"+method+"/"+propId);
	$("#mainForm").submit();
}

/**
 * 删除
 * @param id 要删除的数据主键
 */
function delSelf(id,propId){
	if(confirm("确定要删除?")){
		$("#mainForm").attr("action",$("#mainForm").attr("action")+"/del/"+id+"/"+propId);
		$("#mainForm").submit();
	}else{
		return;
	}
}


/**
 * 批量删除
 */
function delmulSelf(propId){
	var checkValue = '';
	$("input[name='ids']:checked").each(function(i) {
		checkValue += $(this).val() + ",";
	});
	if(checkValue.length==0){
		alert("请选择要操作的数据");return;
	}
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/delmul"+"/"+propId);
	$("#mainForm").submit();
}

function validate(){
	var flag = true;
	return flag;
}
