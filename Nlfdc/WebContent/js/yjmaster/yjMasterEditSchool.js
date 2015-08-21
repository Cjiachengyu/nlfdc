var htmlVal = {
	htmlUrl : "yjmastersettingaction",
};

// 提交修改SchoolName
function changeSchoolName() {
	var newName = $("#school_name").val();
	var oldName = $("#old_school_name").val();
	if (newName == "") {
		$("#school_name").focus();
		return;
	}
	if (newName.length > 30) {
		AlertDialogWithCallback("新学校名长度超出范围！", null, function() {
			$("#school_name").focus();
		});
		return;
	}
	if (newName == oldName) {
		$("#school_name").focus();
		return;
	}
	ConfirmDialog("请确认将学校名称 改为: <strong>" + newName + "</strong>  ?", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?changeschoolname",
			data : {
				newSchoolName : newName
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("学校名修改成功！", "info", function() {
						window.location.href = window.location.href;
					});
				}
				else {
					AlertDialog("学校名修改失败！");
				}
			}
		});

	}, function() {
	});

}

// 提交修改SchoolMotto
function changeSchoolMotto() {
	var newMotto = $("#school_motto").val();
	var oldMotto = $("#old_school_motto").val();

	if (newMotto.length > 32) {
		AlertDialogWithCallback("新校训长度超出范围！", null, function() {
			$("#school_motto").focus();
		});
		return;
	}
	if (oldMotto == newMotto) {
		$("#school_motto").focus();
		return;
	}
	if (newMotto == "") {
		newMotto = "null";
	}
	ConfirmDialog("确认将校训改为: <strong> " + newMotto + "</strong>  ?", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?changeschoolmotto",
			data : {
				newSchoolMotto : newMotto
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("校训修改成功！", "info", function() {
						window.location.href = window.location.href;
					});
				}
				else {
					AlertDialog("校训修改失败！");
				}
			}
		});
	}, function() {
	});
}

function checkSchoolIconImg(filePath) {
	$("#file_text_field").val(filePath);
	var schoolIconSrc = $("#schoolIconSrc").val();

	if (schoolIconSrc !== "") {
		var array = schoolIconSrc.split(".");
		var type = array[array.length - 1];
		type = type.toLowerCase();
		if (type != "jpg" && type != "png" && type != "gif") {
			AlertDialog("文件只能是 .jpg/.png/.gif");
			$("#file_text_field").val("");
		}
		else
		{
			var form = $("#submitSchoolIconForm");

			var options = {
				url : htmlVal.htmlUrl + '?uploadschoolicon=',
				type : 'post',
				success : function(result) {
					isTimeOut(result);

					if (result == "error")
					{
						AlertDialogWithCallback("图片上传出现错误，请重新选择文件！","",function(){
							form[0].reset();
						});
					}
				}
			};
			form.ajaxSubmit(options);
		}
	}
}

function changeSchoolIcon() {
	var schoolIconSrc = $("#schoolIconSrc").val();

	if (schoolIconSrc !== "") {
		
		ConfirmDialog("确定更换校徽  ?", function() {
			$.ajax({
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				type : "post",
				url : htmlVal.htmlUrl + "?changeschoolicon",
				success : function(result) {
					isTimeOut(result);

					if (result == "ok") {
						AlertDialogWithCallback("校徽修改成功！", "info", function() {
							window.location.href = window.location.href;
						});
					}
					else {
						AlertDialog("校徽修改失败！");
					}
				}
			});

		}, function() {
		});
	}
	else {
		AlertDialog("未选择文件！");
	}
}