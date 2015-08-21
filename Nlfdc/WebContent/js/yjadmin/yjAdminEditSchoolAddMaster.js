function doAddNewMaster() {
	var password = $("#password").val();
	var userName = $("#userName").val();

	if (password == '') {
		$("#password").focus();
		return;
	}
	if (userName == '') {
		$("#userName").focus();
		return;
	}

	ConfirmDialog("确认添加校长 ?<br> 校长名：<strong>" + userName + "</strong>", function() {
		$("#add_master_button").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addmaster=",
			data : {
				password : password,
				userName : userName
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "error") {
					AlertDialog("出现错误，校长添加失败 ！");
				}
				else {
					$("#password").val("");
					$("#userName").val("");
					$.ajax({
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						type : "post",
						url : htmlVal.htmlUrl + "?getmastersinfoview=",
						success : function(result2) {
							$("#school_masters_info").html(result2);
						}
					});
					AlertDialogWithCallback("添加校长成功！", "", function(){
						closeAllLayers();
					});
				}
			}
		});
	}, function() {
	});
}

function check2(value) {
	if (value.length > 31) {
		$("#add_master_msg2").show();
	}
	else {
		$("#add_master_msg2").hide();
		if (value == '')
			$("#add_master_button").hide();
		else if ($("#userName").val() != "")
			$("#add_master_button").show();
	}
}

function check3(value) {
	if (value.length > 15) {
		$("#add_master_msg3").show();
	}
	else {
		$("#add_master_msg3").hide();
		if (value == '')
			$("#add_master_button").hide();
		else if ($("#password").val() != "")
			$("#add_master_button").show();
	}
}
