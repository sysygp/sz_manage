function viewHistory(platid,userid){
	$("#mainForm").attr("action","/manager/zhrt/uinfohistory/total.do?platid="+platid+"&userid="+userid);
	$("#mainForm").submit();
}