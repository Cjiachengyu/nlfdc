var loginNameExist = true;

function doAddNewSystemUser() {
	var loginName = $("#login_name").val();
	var password = $("#password").val();
	var userName = $("#editor_name").val();
	var userRole = $("#add_user_role").val();

	if (loginName == "") {
		$("#login_name").focus();
		return;
	}
	if (password == "") {
		$("#password").focus();
		return;
	}
	if (userName == "") {
		$("#editor_name").focus();
		return;
	}
	if (userRole == 0) {
		AlertDialogWithCallback("请选择角色！", "", function() {
			$("#add_user_role").focus();
		});
		return;
	}

	if (loginNameExist) {
		AlertDialogWithCallback("登录名已经被占用，请重新输入！", "", function() {
			$("#login_name").focus();
		});
		return false;
	}

	var userRoleStr = "";
	if (userRole == 11) {
		userRoleStr = "编辑员";
	}

	var tab = "&nbsp;&nbsp;&nbsp;&nbsp;";

	ConfirmDialog("确认添加系统用户 ？<br><br>" + tab + "角色：<strong class='red' >" + userRoleStr + "</strong><br>" + tab + "登录名：<strong>" + loginName + "</strong><br>" + tab + "密码：<strong>" + password + "</strong><br>" + tab + "姓名：<strong>" + userName + "</strong>", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addnewsystemuser=",
			data : {
				loginName : loginName,
				password : password,
				userName : userName,
				userRole : userRole,
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("添加用户成功！", "", function() {
						$("#login_name").val("");
						$("#password").val("");
						$("#editor_name").val("");
						loginNameExist = true;
						$("#check_loginname_exist").html("");
						$("#login_name").attr("class", "input_text");
					});
				}
				else if (result == "dupkey") {
					AlertDialogWithCallback("登录名已经被占用，请重新输入！", "", function() {
						$("#login_name").focus();
					});
				}
				else if (result == "lengthException") {
					AlertDialog("数据长度超出限制,添加学校失败  ！");
				}
				else if (result == "error") {
					AlertDialog("出现错误，编辑添加失败 ！");
				}
			}
		});
	}, function() {
	});
}

function check(id, index) {
	var value = $("#" + id).val();
	if (value.length > 31) {
		$("#msg" + index).show();
	}
	else {
		$("#msg" + index).hide();
	}

	// 检查loginName是否已经存在
	if (index == 1) {
		if (value == "") {
			$("#check_loginname_exist").html("");
			$("#login_name").attr("class", "input_text");
			return false;
		}

		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?checkloginnameunique=",
			data : {
				loginName : value
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					$("#check_loginname_exist").html("<font size='1' color='green' >*登录名可以使用</font>");
					$("#login_name").attr("class", "input_text loginname_check_passed");
					loginNameExist = false;
				}
				else if (result == "exist") {
					$("#check_loginname_exist").html("<font size='1' color='red' >*登录名已经被占用</font>");
					$("#login_name").attr("class", "input_text loginname_check_failed");
					loginNameExist = true;
				}
			}
		});
	}

}
