function findPassword() {
	var email = $("#email").val();
	
	if (email== null || email == "")
	{
		$("#email").focus();
		return false;
	}
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?findpassword=",
		dataType : "json",
		data : {
			email : email
		},
		success : function(result) {
			if (result == null) {
				isTimeOut("timeOut");
			}

			if (result.role == null) {
				$("#tip").html("没有此帐号！");
				$(".userInfo").hide();
			}
			else {
				$("#tip").html("");
				setValue(result);
				$(".userInfo").show();
			}
		}
		});
}

function setValue(result) {
	var role = result.role;
	var userRoleString = [ "", "", "", "", "", "", "" ,"幼教家长" ,"幼教老师" ,"幼教园长" ,"幼教编辑", "幼教管理员" ];

	$("#user_roleString").html(userRoleString[role - 1]);
	$("#user_name").html(result.name);
	$("#user_email").html(result.email);
	$("#user_loginId").html(result.loginId);
	$("#user_loginName").html(result.loginName);
	$("#user_password").val(result.password);

	$("#user_password").attr("disabled", "disabled");
	$("#reset_password").html("重置密码");
	$("#reset_password").attr("href", "javascript:resetPassword()");
}

function resetPassword() {
	$("#user_password").removeAttr("disabled");
	$("#user_password").focus();
	$("#reset_password").html("确定");
	$("#reset_password").attr("href", "javascript:doResetPassword()");
}

function doResetPassword() {
	var newPassword = $("#user_password").val();
	
	if (newPassword == null || newPassword == "")
	{
		$("#user_password").focus();
		return false;
	}
	
	if (newPassword.length > 32) {
		AlertDialogWithCallback("密码长度超出限制！", null, function() {
			$("#user_password").focus();
		});
		return false;
	}
		
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?resetpassword=",
		data : {
			newPassword : newPassword
		},
		success : function(result) {
			isTimeOut(result);

			$("#user_password").attr("disabled", "disabled");
			$("#reset_password").html("重置密码");
			$("#reset_password").attr("href", "javascript:resetPassword()");
			if (result == "ok") {
				AlertDialog("重置密码成功！新密码为： " + newPassword, "");
			}
			else {
				AlertDialog("重置密码失败！");
			}
		}
	});
}

function enterkey(e) {
	var ev = window.event || e;
	if (ev.keyCode == 13) {
		findPassword();
	}
}
