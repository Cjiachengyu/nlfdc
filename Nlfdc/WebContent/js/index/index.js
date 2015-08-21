$(function() {
	enablePlaceHolder();

});

$(function() {

	var lowVersion = false;
	
	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		var version = $.browser.version - 0;
		
		if (version < 10) {
			$("#update_broswer_tip").show();
			
			if (version < 8)
			{
				lowVersion = true;
			}
		}
	}
	
	judgeBrowserVersion();

	var inputEmail = $("#input_email");
	var inputPwd = $("#input_password");
	var tipEmail = $("#tip_email");
	var tipPwd = $("#tip_pwd");

	var submitForm = function() {
		if (lowVersion) {
			alert("很抱歉，您的浏览器版本过低，请安装最新的浏览器再来尝试，谢谢！");
			return;
		}

		tipEmail.hide();
		tipPwd.hide();

		var email = inputEmail.val();
		if (email === "") {
			tipEmail.html("请输入用户名").show();
			inputEmail.focus();
			return;
		}

		var password = inputPwd.val();
		if (password === "") {
			tipPwd.html("请输入密码").show();
			inputPwd.focus();
			return;
		}

		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : "loginaction?yjlogincheck=",
			data : {
				email : email,
				password : password,
			},
			success : function(result) {
				if (result == "ok") {
					window.location.href = "loginaction?gotoadminmainpage";
				}
				else {
					tipEmail.html("用户名或密码错误").show();
				}
			}
		});
	}

	inputEmail.on("keydown", function(e) {
		if (e.which == 13) {
			submitForm();
		}
	});

	inputPwd.on("keydown", function(e) {
		if (e.which == 13) {
			submitForm();
		}
	});

	$("#login_btn").on("click", function(event) {
		submitForm();
	});
});
