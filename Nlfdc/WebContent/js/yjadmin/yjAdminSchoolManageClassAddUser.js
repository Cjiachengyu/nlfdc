function check1(value) {
	if (value == "") {
		$("#add_user_button").hide();
	}
	else if ($("#password").val() != "") {
		$("#add_user_button").show();
	}
}
function check2(value) {
	if (value.length > 31) {
		$("#msg2").show();
	}
	else {
		$("#msg2").hide();
		if (value == "") {
			$("#add_user_button").hide();
		}
		else if ($("#userName").val() != "") {
			$("#add_user_button").show();
		}
	}
}
function doAddNewUser() {
	var userName = $("#userName").val();
	var password = $("#password").val();
	var userRole = $("input[name='add_user_role']:checked").val();
	if (password == "") {
		$("#password").focus();
		return;
	}
	if (userName == "") {
		$("#userName").focus();
		return;
	}
	var shortName = "";
	var strs = userName.split("\n");
	for (var i = strs.length - 1; i >= 0; i--) {
		if (strs[i] == "") {
			AlertDialogWithCallback("姓名输入框中包含空行！", null, function() {
				$("#userName").focus();
			});
			return;
		}
		else {
			shortName += strs[i] + ", ";
		}
	}
	;
	shortName = shortName.substring(0, shortName.length - 2);
	ConfirmDialog("确认添加用户? <br> 用户名：<strong>" + shortName + "</strong>", function() {
		$("#add_user_button").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?classadduser=",
			data : {
				userName : userName,
				password : password,
				userRole : userRole
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "error") {
					AlertDialog("出现错误，添加用户失败！");
				}
				else if (result == "ok") {
					AlertDialogWithCallback("添加用户成功 ！", "", function() {
						window.location.href = window.location.href;
					});
				}
			}
		});
	}, function() {
	});
}