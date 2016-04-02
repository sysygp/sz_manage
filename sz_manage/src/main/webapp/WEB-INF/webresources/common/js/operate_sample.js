/**
 * jquery常用表单操作：input,password,textarea,radio,checkbox,select的取值，赋值，清空。
 * 需要注意的就是checkbox的全选，反选，设置选中值,select赋值可以根据value值，也可以根据文本text属性值
 * @author 杨功平(SYSYGP) 2015/6/17
 */

/**
 * 获取数据
 */
function getDate() {

	/*
	 * 获取值
	 */
	var textValue = $("#text1").val();
	var passValue = $("#password1").val();
	var textAreaValue = $("#textarea1").val();
	var radioValue = $("input[name='radioGroup']:checked").val();
	var checkBoxValue = $("input[name='checkbox1']:checked").val();
	var selectValue = $("#select1").val();

	alert("textValue:  " + textValue);
	alert("passValue:  " + passValue);
	alert("textAreaValue:  " + textAreaValue);
	alert("radioValue:  " + radioValue);
	var checkValue = '';
	$("input[name='checkbox1']:checked").each(function(i) {
		checkValue += $(this).val() + ",";
	})
	alert(checkValue);
	alert("selectValue:  " + selectValue);
}


/**
 * 赋值
 */
function setDate() {
	$("#text1").val("aaaa");
	$("#password1").val("bbbb");
	$("#textarea1").val("cccc");

	//设置单选按钮选中
	// $("input[name='radioGroup']").attr("value","radio2");
	$(":radio[value='radio1']").attr("checked", true);

	//设置复选框选中
	$("input[name='checkbox1']").each(function(i) {
		if ("checkbox2" == $(this).val()) {
			$(this).attr("checked", true)
		} else {
			$(this).attr("checked", "")
		}
	})

	//设置下拉选中
	$("#select1").val("2");
}

/*
 * 清空数据
 */
function clearDate() {
	$("#text1").val("");
	$("#password1").val("");
	$("#textarea1").val("");

	//取消单选按钮选中  //注意这里会检索页面中所有的radio。
	$(":radio[checked='true']").attr("checked", "");

	//如果只想操作指定的单选按钮组，则可以使用name属性。

	$(":radio[name='filter_zcfl']").attr("checked", "");

	//取消复选框选中,这样就不用each方法遍历了。
	$(":checkbox[checked='true']").attr("checked", "");

	//设置下拉选中
	$("#select1").val("");
}

/*
 * 设置多选框反选
 */
function reverse() {
	$("input[name='checkbox1']").each(function(i) {
		if ($(this).attr("checked") == true) {
			$(this).attr("checked", "")
		} else {
			$(this).attr("checked", true)
		}
	})
}

/*
 * 获取选中的下拉文本
 */
function getSelectedText() {
	var selectedText = $("#select1").find("option:selected").text();
	alert(selectedText);
}

/*
 * 通过文本来设置选中的下拉
 */
function setSelectedByText() {
	//让用户输入想要选中的下拉的text值
	var inputText = prompt("Please input selected text:", "0");
	//遍历该下拉下的option
	$("#select1 > option").each(function(i) {
		//如果option的text属性和用户输入的值相等，则让他选中
		//否则给一个默认值，该默认值是“--请选择--”的value值。
		if ($(this).text() == inputText) {
			$(this).attr("selected", true);
		} else {
			$(this).val("");
		}
	})
}