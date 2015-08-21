$(function() {
	$("#loginName").on("focus", function() {
		$(".tip1").show(500);
	});

	$("#email").on("focus", function() {
		$(".tip2").show(500);
	});

	$("#loginName").on("blur", function() {
		$(".tip1").hide();
	});

	$("#email").on("blur", function() {
		$(".tip2").hide();
	});
});

function changeLoginName() {
	var newName = $("#loginName").val();
	var oldName = $("#old_loginName").val();
	if (newName == "") {
		$("#loginName").focus();
		return;
	}
	if (newName.length > 32) {
		AlertDialogWithCallback("新登录名长度超出范围！", null, function() {
			$("#loginName").focus();
		});
		return;
	}

	if (newName == oldName) {
		$("#loginName").focus();
		return;
	}

	var p = /^[^0-9][A-Za-z0-9_*&^%$#@!-]+$/;
	if (!p.exec(newName)) {
		AlertDialogWithCallback("登录名格式错误！", null, function() {
			$("#loginName").focus();
		});
		return;
	}

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?dochangeloginname=",
		data : {
			newLoginName : newName
		},
		success : function(result) {
			if (result == "ok") {
				AlertDialog("修改登录名成功！", "");
				$("#old_loginName").val(newName);
			}
			else if (result == "error") {
				AlertDialogWithCallback("出现错误，修改失败！", null, function() {
					$("#loginName").focus();
				});
			}
			else if (result == "dupkey") {
				AlertDialogWithCallback("该登录名已被使用！", null, function() {
					$("#loginName").focus();
				});
			}
			else if (result == "length_exception") {
				AlertDialogWithCallback("修改失败！新登录名长度超出限制！", null, function() {
					$("#loginName").focus();
				});
			}

		}
	});
}

function changeEmail() {
	var newEmail = $("#email").val();
	var oldEmail = $("#old_email").val();

	if (newEmail == "") {
		$("#email").focus();
		return;
	}
	if (newEmail.length > 32) {
		AlertDialogWithCallback("新Email长度超出范围！", null, function() {
			$("#email").focus();
		});
		return;
	}
	if (newEmail == oldEmail) {
		$("#email").focus();
		return;
	}
	var p = /^([a-z0-9A-Z]+[_-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/;
	if (!p.exec(newEmail)) {
		AlertDialogWithCallback("新Email不能以数字开头 ！", null, function() {
			$("#email").focus();
		});
		return;
	}

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?dochangeemail=",
		data : {
			newEmail : newEmail
		},
		success : function(result) {
			if (result == "ok") {
				AlertDialog("修改Email成功！", "");
			}
			else if (result == "dupkey") {
				AlertDialogWithCallback("该邮箱已被使用！", null, function() {
					$("#email").focus();
				});
			}
			else if (result == "error") {
				AlertDialogWithCallback("出现错误，修改失败！", null, function() {
					$("#email").focus();
				});
			}
			else if (result == "length_exception") {
				AlertDialogWithCallback("修改失败！新Email长度超出限制！", null, function() {
					$("#email").focus();
				});
			}
		}
	});
}