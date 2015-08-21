function doChangeUserPassword() {
	var oldPwd = $("#old_pwd").val();
	var newPwd = $("#new_pwd").val();
	var newPwdAgain = $("#new_pwd_again").val();

	if (oldPwd == null || oldPwd == "") {
		AlertDialogWithCallback("请输入原密码", null, function() {
			$("#old_pwd").focus();
		});
		return;
	}

	if (newPwd != newPwdAgain) {
		AlertDialogWithCallback("两次输入新密码不一致", null, function() {
			$("#new_pwd").focus();
		});
		return;
	}

	if (newPwd.length < 6) {
		AlertDialogWithCallback("您的密码过短，请输入至少6位", null, function() {
			$("#new_pwd").focus();
		});
		return;
	}
	else if (newPwd.length > 32) {
		AlertDialogWithCallback("您的新密码超出限制，最长为32位！", null, function() {
			$("#new_pwd").focus();
		});
		return;
	}

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?dochangepwd",
		data : {
			oldPwd : oldPwd,
			newPwd : newPwd
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				if (htmlVal.changePwdSuccessUrl) {
					window.location.href = htmlVal.changePwdSuccessUrl;
				}
				else {
					AlertDialog("修改密码成功", "");
					$("#old_pwd").val("");
					$("#new_pwd").val("");
					$("#new_pwd_again").val("");
				}
			}
			else if (result == "wrongPwd") {
				AlertDialogWithCallback("原密码不正确", "", function() {
					$("#old_pwd").focus();
				});
			}
			else if (result == "length_exception") {
				AlertDialogWithCallback("您的新密码超出限制，最长为32位！", null, function() {
					$("#new_pwd").focus();
				});
			}
			else if (result == "error") {
				AlertDialog("出现错误，添加密码失败！");
			}
		}
	});
}