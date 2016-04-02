/**
 * 常用的分页js
 * @author 杨功平(SYSYGP) 2015/6/17
 */


/**
 * 换页
 * @param toPageNo 需要跳转到的页数
 */
function turn(toPageNo) {
	var totalPageNo = $("#totalPages").val();
	
	if (toPageNo > totalPageNo) {
		$("#pageNo").val(totalPageNo);
	}else{
		$("#pageNo").val(toPageNo);
	}
	
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/index");
	$("#mainForm").submit();
}