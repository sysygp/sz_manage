/**
 * 清空表单
 */


function validate(){
	var flag = true;
	var chargeTimes = $("#chargeTimes").val();
	if(chargeTimes == null || chargeTimes ==""){
		flag = false;
		$("#chargeTimes_tip").html("计费次数不能为空");
	}else{
		$("#chargeTimes_tip").html("");
	}
	var interceptWord = $("#interceptWord").val();
	if(interceptWord == null || interceptWord ==""){
		flag = false;
		$("#interceptWord_tip").html("不能为空");
	}else{
		$("#interceptWord_tip").html("");
	}
	var limitDayNum = $("#limitDayNum").val();
	if(limitDayNum == null || limitDayNum ==""){
		flag = false;
		$("#limitDayNum_tip").html("不能为空");
	}else{
		$("#limitDayNum_tip").html("");
	}
	var limitMonthNum = $("#limitMonthNum").val();
	if(limitMonthNum == null || limitMonthNum ==""){
		flag = false;
		$("#limitMonthNum_tip").html("不能为空");
	}else{
		$("#limitMonthNum_tip").html("");
	}
	return flag;
}
