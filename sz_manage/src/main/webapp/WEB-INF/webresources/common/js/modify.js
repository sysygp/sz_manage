/**
 * jquery常用表单操作：input,password,textarea,radio,checkbox,select的取值，赋值，清空。
 * 需要注意的就是checkbox的全选，反选，设置选中值,select赋值可以根据value值，也可以根据文本text属性值
 * @author 杨功平(SYSYGP) 2015/6/17
 */

/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("input[type='text'],input[type='password'],textarea").attr("readonly","readonly");
		$("select").attr("disabled","disable");
		$("input[type='radio']").attr("disabled","disable");
		$("input[type='checkbox']").attr("disabled","disabled");
	}
});


function save(){
	if(validate()){	
		$("#mainForm").attr("action",$("#mainForm").attr("action")+"/modify");
		$("#mainForm").submit();
	}else{
		return;
	}
}

function back(){
	window.history.back(-1);
}
/**
 * 清空
 */
function clear(){
	$("input[type='text'],select").val('');
}


//增加js验证方法
document.write("<script type=\"text/javascript\" src=\"/webresources/common/js/validate.js\" ></script>");
