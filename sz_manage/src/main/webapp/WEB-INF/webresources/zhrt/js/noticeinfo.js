

function validate(){
	var flag = true;
	var noticeTitle = $("#noticeTitle").val();
	if(noticeTitle==null||noticeTitle==""){
		flag = false;
		$("#noticeTitle_tip").html('通知标题不能为空');
	}else{
		$("#noticeTitle_tip").html('');
	}
	var noticeType = $("#noticeType").val();
	if(noticeType==null||noticeType==""){
		flag = false;
		$("#noticeType_tip").html('请选择通知类型');
	}else{
		$("#noticeType_tip").html('');
	}
	var noticeContent = $("#noticeContent").html();
	if(noticeContent==null||noticeContent==""){
		flag = false;
		$("#noticeContent_tip").html('通知内容不能为空');
	}else{
		$("#noticeContent_tip").html('');
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