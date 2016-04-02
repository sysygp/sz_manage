/**
 * jquery常用表单操作：input,password,textarea,radio,checkbox,select的取值，赋值，清空。
 * 需要注意的就是checkbox的全选，反选，设置选中值,select赋值可以根据value值，也可以根据文本text属性值
 * @author 杨功平(SYSYGP) 2015/6/17
 */

$(document).ready(function(){
	
	//列表也全选/反选
	$("#selectAll").click(function(){
		$("input[name='ids']").each(function(i) {
			var isSelect = $("#selectAll").attr("checked");
			if("checked"==isSelect){
				$(this).attr("checked","checked");
			}else{
				$(this).removeAttr("checked");
			}
		});
	});
	
});

/**
 * 编辑
 * @param id 要编辑数据的主键，如果是新增则参数为0
 */
function edit(method,id){
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/get/"+id+"/"+method);
	$("#mainForm").submit();
}

function editByDomain(method,id){
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/get/"+ id +"/"+ method);
	$("#mainForm").submit();
}

/**
 * 删除
 * @param id 要删除的数据主键
 */
function del(id){
	if(confirm("确定要删除?")){
		$("#mainForm").attr("action",$("#mainForm").attr("action")+"/del/"+id);
		$("#mainForm").submit();
	}else{
		return;
	}
}


/**
 * 批量删除
 */
function delmul(){
	var checkValue = '';
	$("input[name='ids']:checked").each(function(i) {
		checkValue += $(this).val() + ",";
	});
	if(checkValue.length==0){
		alert("请选择要操作的数据");return;
	}
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/delmul");
	$("#mainForm").submit();
}

/**
 * 提交查询
 */
function find(){
	$("#mainForm").attr("action",$("#mainForm").attr("action")+"/index");
	$("#mainForm").submit();
}

/**
 * 重置
 */
function clean(){
	$("select,input[type='text']").val('');
}

function exportToExcel(){
	var oldAction = $("#mainForm").attr("action");
	$("#mainForm").attr("action",oldAction+"/exportToExcel");
	$("#mainForm").submit();
	$("#mainForm").attr("action",oldAction);
}
