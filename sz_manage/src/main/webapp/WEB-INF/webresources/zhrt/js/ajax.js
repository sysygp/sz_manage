function getAppListByCp(status){
	/*$("#cpId").change(function() {*/
		var cpId = $("#cpId").val();
		if(cpId==""){
			$("#appId").empty();
			$("#verId").empty();
			$("#appId").append("<option value=''>--请选择--</option>");
			$("#verId").append("<option value=''>--全部--</option>");
			return;
		}
		$.ajax({
			type:"post",
			url : "/manager/zhrt/appinfo/findAppByCp",
			data : {
				"cpId" : cpId,
				"status":status
			},
			success:function(data){
				$("#appId").empty();
				$("#verId").empty();
				$("#appId").append("<option value=''>--请选择--</option>");
				$("#verId").append("<option value=''>--全部--</option>");
				var result = data.result;
				for (var int = 0; int < result.length; int++) {
					var res = result[int];
					$("#appId").append("<option value="+res.id+">"+res.appName+"</option>");
				}
			}
		});
	/*});*/
}

function getAppVersionByApp(status){
/*	$("#appId").change(function() {*/
		var appId = $("#appId").val();
		if(appId==""){
			$("#verId").empty();
			$("#verId").append("<option value=''>--全部--</option>");
			$("#channelId").empty();
			$("#channelId").append("<option value=''>--全部--</option>");
			return;
		}
		$.ajax({
			type:"post",
			url : "/manager/zhrt/channelappinfo/getByAppId",
			data : {
				"appId" : appId,
				"status" : status
			},
			success:function(data){
				$("#verId").empty();
				$("#verId").append("<option value=''>--全部--</option>");
				$("#channelId").empty();
				$("#channelId").append("<option value=''>--全部--</option>");
				var result = data.result;
				for (var int = 0; int < result.length; int++) {
					var res = result[int];
					$("#verId").append("<option value="+res.id+">"+res.verNumber+"</option>");
				}
			}
		});
/*	});*/
}

function getChannelByAppVer(status){
	var verId = $("#verId").val();
	if(verId==""){
		$("#channelId").empty();
		$("#channelId").append("<option value=''>--全部--</option>");
		return;
	}
	$.ajax({
		type:"post",
		url : "/manager/zhrt/channelinfo/getByVerId",
		data : {
			"verId" : verId,
			"status" : status
		},
		success:function(data){
			$("#channelId").empty();
			$("#channelId").append("<option value=''>--全部--</option>");
			var result = data.result;
			for (var int = 0; int < result.length; int++) {
				var res = result[int];
				$("#channelId").append("<option value="+res.id+">"+res.cnName+"</option>");
			}
		}
	});
}