function doAddNewAdmin() {
	var password = $("#password").val();
	var loginName = $("#loginName").val();
	var userName = $("#userName").val();

	if (password == "") {
		$("#password").focus();
		return;
	}
	if (userName == "") {
		$("#userName").focus();
		return;
	}
	if (loginName == ""){
		$("#loginName").focus();
		return;
	}

	ConfirmDialog("确认添加管理员 ?<br> 用户名：<strong>" + userName + "</strong>", function() {
		$("#add_admin_button").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addadmin=",
			data : {
				password : password,
				userName : userName,
				loginName : loginName,
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "error") {
					AlertDialog("出现错误，管理员添加失败 ！");
				}
				else {
					AlertDialogWithCallback("添加成功！", "", function(){
						window.location.href = window.location.href;
					});
				}
			}
		});
	}, function() {
	});
}

function check2(value) {
	if (value.length > 15) {
		$("#add_admin_msg2").show();
	}
	else {
		$("#add_admin_msg2").hide();
		if (value == '')
			$("#add_admin_button").hide();
		else if ($("#userName").val() != "")
			$("#add_admin_button").show();
	}
}

function check1(value) {
	if (value.length > 15) {
		$("#add_admin_msg1").show();
	}
	else {
		$("#add_admin_msg1").hide();
		if (value == '')
			$("#add_admin_button").hide();
		else if ($("#userName").val() != "")
			$("#add_admin_button").show();
	}
}

function check3(value) {
	if (value.length > 15) {
		$("#add_admin_msg3").show();
	}
	else {
		$("#add_admin_msg3").hide();
		if (value == '')
			$("#add_admin_button").hide();
		else if ($("#password").val() != "")
			$("#add_admin_button").show();
	}
}
