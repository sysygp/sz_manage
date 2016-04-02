/**
 * 如果是查看页面，禁用页面表单元素
 */
$(document).ready(function(){
	var m = $("#m").val();
	if(m=='v'){
		$("input").each(function(i) {
			$(this).attr("readonly","readonly");
		});
	}
});