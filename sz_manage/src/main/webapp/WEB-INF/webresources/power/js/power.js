//删除提示
function del(){
	return confirm("确定要删除吗?");
}

//树型页面 form 提交
function treeFormSubmit(obj) {
	var l_childs = obj.elements;
	var l_child;
	var flag = true;
	for(var i = 0; i < l_childs.length; i++){
		l_child = l_childs[i];
		var nodename = l_child.nodeName;
		if(nodename.toLowerCase() == "input"){
			var type = l_child.type;
			var isNull = l_child.isnull;
			var l_label = l_child.label;
			if(isNull != null && isNull == "false"){
				if(type.toLowerCase() == "text"){
					var l_value = l_child.value.replace(/(^\s*)|(\s*$)/g, "");
					if(l_value.length == 0){
						alert(l_label + "项,必须填写！");
						l_child.select();
						return false;
					}
				}
			}else{
				if(type.toLowerCase() == "checkbox"){
					var name = l_child.name;
					var value = l_child.value;
					var arr = name.split("_");
					/*if((arr.length == 3) && (l_child.checked == true)){
						if(value.indexOf("#music#1") != -1){
							flag = true;
						}
					}*/
					if((arr.length == 5) && (l_child.checked == true)){
						flag = true;

						//ѡ�и��ڵ�
						var index = name.lastIndexOf("_");
						var name1 = name.substring(0, index);
						document.getElementById(name1).checked = true;
						index = name1.lastIndexOf("_");
						var name2 = name1.substring(0, index);
						document.getElementById(name2).checked = true;
					}
				}
			}
		}
	}
	if(!flag){
		alert("权限项,必须选择！");
		return false;
	}
	return true;
}

/**
* 树型页面初始
*/
function treeFormInit() {
	var l_childs = document.forms[0].elements;
	var l_child;
	var flag = false;
	var nameTreeArray=new Array();
	for(var i = 0; i < l_childs.length; i++){
		l_child = l_childs[i];
		var nodename = l_child.nodeName;
		if(nodename.toLowerCase() == "input"){
			var type = l_child.type;
			if(type.toLowerCase() == "checkbox"){
				if(l_child.checked){
					findParentName(l_child.id,nameTreeArray)
				}
			}
		}
	}


	//alert(nameTreeArray);
	for(var j=0;j<nameTreeArray.length;j++){
		if(nameTreeArray[j]!='root'){
			document.getElementById(nameTreeArray[j]).checked=true;
		}
	}
	
	//查询父类的ID
	function findParentName(objId,nameTreeArray){
		var nameP;
		if(objId.lastIndexOf("_")!=-1){
			nameP=objId.substring(0,objId.lastIndexOf("_"));
			nameTreeArray[nameTreeArray.length]=nameP;
			findParentName(nameP,nameTreeArray)
		}
	}
	return true;
}

//form 提交
function formSubmit(obj) {
	var l_childs = obj.elements;
	var l_child;
	for(var i = 0; i < l_childs.length; i++){
		l_child = l_childs[i];
		var nodename = l_child.nodeName;
		if(nodename.toLowerCase() == "input"){
			var type = l_child.type;
			var isNull = l_child.isnull;
			var l_label = l_child.label;
			if(isNull != null && isNull == "false"){
				if(type.toLowerCase() == "text"){
					var l_value = l_child.value.replace(/(^\s*)|(\s*$)/g, "");
					if(l_value.length == 0){
						alert(l_label + "项,必须填写！");
						l_child.select();
						return false;
					}
				}
			}
		}
	}
	return true;
}

//重置
function formReset(){
	if(confirm("确定要重置吗?")){
		document.forms[0].reset();
	}
	return false;
}


