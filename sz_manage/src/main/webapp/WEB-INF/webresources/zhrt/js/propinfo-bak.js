/**
 * 清空表单
 */
function myclear() {
	$("#appId").empty();
	$("#appId").append("<option value=''>请选择</option>");
	clean();
}

function validate() {
	var flag = true;
	return flag;
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
/**
 * 新增道具按钮
 */
var propId = 0;
function propAdd() {
	
	var appId = $("#appId").val();
	//通过ajax访问后台，生成uuid值
	$.ajax({
	      type: "GET",
	      url: "/manager/zhrt/propinfo/geneUUIDkey?appId="+appId,
	      async: false,
	      dataType: "json",
	      success: function(data){
	    	 
	    	  if(data.result >= 10){
	    		  propId = data.result + 1;  
	    		  $("#propCode").val(propId);
	    	  }else if(data.result == 0){
	    		  //表示第一次给产品加道具，默认是10
	    		  propId = 10;
	    		  $("#propCode").val(propId);
	    	  }else{	    		  
	    		  $("#resultStr").text("系统错误");
	    	  }
	      }	
	});		
		
	var propHtml = "<tr id='propTr"+propId+"'>+" 
			+ "<td><span>"+propId+"</span></td>"
			+ "<td><input type='text' name='propName' id='propName" + propId + "' style='height: 25px;width:100px;'></td>"
			+ "<td><input type='text' name='propFee' id='propFee" + propId + "' style='height: 25px;width:100px;'></td>" 
			+ "<td><a href='javascript:propSave(" + propId + ");'>保存</a>&nbsp;&nbsp;<a href='javascript:propCancelAdd(" + propId + ")'>取消</a></td>"
			+ "</tr>";
	$("tbody").append(propHtml);
}

/**
 * 保存道具信息
 * @param propId
 */
function propSave(propId) {
	var propCode = $("#propCode").val();
	var propName = $("#propName" + propId).val();
	var virtualCurrency = $("#virtualCurrency" + propId).val();
	var propFee = $("#propFee" + propId).val();
	var propType = $("#propType" + propId).val();
	var payType = $("#payType"+propId).val();
	var feeTipMode = $("#feeTipMode"+propId).val();
	var appId = $("#appId").val();
	location.href = encodeURI(encodeURI($("#mainForm").attr("action") + "/modify/a?propCode=" + propCode
					+ "&propName=" + propName+ "&appId=" + appId + "&virtualCurrency=" + virtualCurrency+"&propFee="+propFee+"&propType="+propType+"&payType="+payType+"&feeTipMode="+feeTipMode));
}
/**
 * 取消新增按钮
 * @param propId
 * @param method a 新增取消 m 修改取消
 */
function propCancelAdd(propId){
	$("tr[id=propTr"+propId+"]").remove();
}
/**
 * 修改按钮事件
 * @param id
 */
function propModify(id){
	$(".show"+id).hide();
	$(".modify"+id).show();
	$("#but01"+id).hide();
	$("#but02"+id).show();
	$("#but03"+id).show();
}

/**
 * 修改道具信息
 * @param propId
 */
function propSaveModify(propId) {
	
	var propName = $("#propName" + propId).val();
	var propFee = $("#propFee" + propId).val();
	var virtualCurrency = $("#virtualCurrency" + propId).val();
	var propType = $("#propType" + propId).val();
	var payType = $("#payType"+propId).val();
	var feeTipMode = $("#feeTipMode"+propId).val();
	var platId = $("#platId").val();
	var appId = $("#appId").val();
	location.href = encodeURI(encodeURI($("#mainForm").attr("action") + "/modify/m?propName=" + propName
					+ "&virtualCurrency=" + virtualCurrency + "&propFee=" + propFee + "&propType=" + propType+"&appId="+appId+"&payType="+payType+"&feeTipMode="+feeTipMode+"&platId="+platId+"&id="+propId));
}
/**
 * 取消道具修改
 * @param id
 */
function propCancelModify(id){
	$(".show"+id).show();
	$(".modify"+id).hide();
	$("#but01"+id).show();
	$("#but02"+id).hide();
	$("#but03"+id).hide();
}