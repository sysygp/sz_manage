/**
 * 清空表单
 */
function myclear() {
	$("#appId").empty();
	$("#appId").append("<option value=''>请选择</option>");
	$("#verId").empty();
	$("#verId").append("<option value=''>请选择</option>");
	clean();
}

function validate() {
	var flag = true;
	return flag;
}

/**
 * 打包
 * 
 * @param pakType
 *            1:正式包 2:测试包
 * @param id
 */
function pak(pakType) {
	$('input:checkbox[name=ids]:checked').each(function(i) {
		var id = $(this).val();
		$("#mainForm").attr("action",$("#mainForm").attr("action") + "/pak/" + id + "/" + pakType);
		$("#mainForm").submit();
	});
}

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function() {
	var m = $("#m").val();
	if (m == 'v') {
		$("input[type='text']").attr("readonly", "readonly");
		$("select").attr("disabled", "disable");
	}
});

function getDetail(id) {
	var url = "/manager/zhrt/appversioninfo";
	window.location = url + "/get/" + id + "/v";
}


function updateBatch(){
	var checkValue = '';
	$("input[name='ids']:checked").each(function(i) {
		checkValue += $(this).val() + ",";
	});
	if(checkValue.length==0){
		alert("请选择要操作的数据");return;
	}
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/updateBatch");
	$("#mainForm").submit();
}