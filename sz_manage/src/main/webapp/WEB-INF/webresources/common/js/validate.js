//正整数
function checkInteger(integer){
	var reg =/^[1-9]*[1-9][0-9]*$/;
	return reg.test(integer);
}
//验证手机号
function checkPhone(phone){
	var reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
	return reg.test(phone);
}
//整数或小数
function checkNumber(number){ 
	var reg = /^[0-9]+([.]{1}){0,1}[0-9]*$/;
	return reg.test(number);
}