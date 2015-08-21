$(document).ready(function() {

	$("#class_role_type").on("click", function(event) {
		event = event || window.event;
		var target = event.target || event.srcElement;
		var roleType = $("#class_role_type_value");
		if (target.id === "class_role_type1") {
			if (roleType.val() === "1") {
				return;
			}
			$(this).find("a").removeClass("class_role_type_selected");
			$(target).addClass("class_role_type_selected");
			roleType.val("1");
			$("#main1").show();
			$("#main2").hide();
		}
		else if (target.id === "class_role_type2") {
			if (roleType.val() === "2") {
				return;
			}
			$(this).find("a").removeClass("class_role_type_selected");
			$(target).addClass("class_role_type_selected");
			roleType.val("2");
			$("#main1").hide();
			$("#main2").show();
		}
	});
});

function changeClassInfo() {
	var clsName = $("#classNameText").val();
	var enterYear = $("#classEnteryear").val();

	if (clsName == "") {
		$("#classNameText").focus();
		return;
	}
	if (clsName.length > 16) {
		AlertDialogWithCallback("班级名称长度超出限制！", null, function() {
			$("#classNameText").focus();
		});
		return;
	}

	ConfirmDialog("确定修改班级信息？", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?changeclassinfo=",
			data : {
				clsName : clsName,
				enterYear : enterYear
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialog("修改班级信息成功", "information");
				}
				else if (result == "clsNameLength") {
					AlertDialogWithCallback("班级名称长度错误！", null, function() {
						window.location.href = window.location.href;
					});
				}
				else if (result == "clsEnterYear") {
					AlertDialogWithCallback("入学年份错误！", null, function() {
						window.location.href = window.location.href;
					});
				}
				else if (result == "error") {
					AlertDialogWithCallback("班级信息修改失败！", null, function() {
						window.location.href = window.location.href;
					});
				}
			}
		});
	}, function() {
	});
}

function searchStudents() {
	var name = $("#searchText2").val();
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?searchusersbyname=",
		data : {
			userName : name,
			type : 2
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				$.ajax({
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					type : "post",
					url : htmlVal.htmlUrl + "?getstudentslistview=",
					success : function(result2) {
						$("#main_content2").html(result2);
					}
				});
			}
			else {
				$("#searchText2").focus();
			}
		}
	});
}

function searchTeachers() {
	var name = $("#searchText").val();
	if (name != null) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?searchusersbyname=",
			data : {
				userName : name,
				type : 1
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					$.ajax({
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						type : "post",
						url : htmlVal.htmlUrl + "?getteacherslistview=",
						success : function(result2) {
							$("#main_content1").html(result2);
						}
					});
				}
				else {
					$("#searchText").focus();
				}
			}
		});
	}
	else {
		$("#searchText").focus();
	}
}

function judgeKey(e, v) {
	if (v == 1) {
		searchTeachers();
	}
	else if (v == 2) {
		searchStudents();
	}
}

// 给用户快速重置默认的密码
function resetPass(userId) {
	ConfirmDialog("确定该用户的密码重置为: <strong>123456</strong> ?", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?resetuserpassword=",
			data : {
				userId : userId
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialog("密码重置成功！", "");
				}
				else {
					AlertDialog("密码重置失败！");
				}
			}
		});
	}, function() {
	});
}

function moveIn(userId2, isTeacher) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?addusertouserclass=",
		data : {
			userId : userId2,
			isTeacher : isTeacher
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				var classRoleType = $("#class_role_type_value").val();
				var url;
				if (classRoleType == 1)
				{
					url = htmlVal.htmlUrl + "?getteacherslistview=";
				}
				else
				{
					url = htmlVal.htmlUrl + "?getstudentslistview=";
				}

				$.ajax({
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					type : "post",
					url : url,
					success : function(result) {
						if (classRoleType == 1)
						{
							$("#main_content1").html(result);
						}
						else
						{
							$("#main_content2").html(result);
						}
					}
				});
			}
			else if (result == "failed") {
				AlertDialog("用户加入失败！");
			}
		}
	});
}

function moveOut(userId) {
	ConfirmDialog("确定移出该用户 ？", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?removeuserfromuserclass=",
			data : {
				userId : userId
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					var classRoleType = $("#class_role_type_value").val();
					var url;
					if (classRoleType == 1)
					{
						url = htmlVal.htmlUrl + "?getteacherslistview=";
					}
					else
					{
						url = htmlVal.htmlUrl + "?getstudentslistview=";
					}
					
					$.ajax({
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						type : "post",
						url : url,
						success : function(result) {
							if (classRoleType == 1)
							{
								$("#main_content1").html(result);
							}
							else
							{
								$("#main_content2").html(result);
							}
						}
					});
				}
				else if (result == "failed") {
					AlertDialog("用户移出失败！");
				}
			}
		});
	}, function() {
	});
}

function reselectClass1(clsId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?reselectclass=",
		data : {
			selectedClassId : clsId,
			type : 1
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				$.ajax({
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					type : "post",
					url : htmlVal.htmlUrl + "?getteacherslistview=",
					success : function(result2) {
						$("#main_content1").html(result2);
					}
				});
			}
			else {
				AlertDialog("出现错误！");
			}
		}
	});
}

function reselectClass2(clsId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?reselectclass=",
		data : {
			selectedClassId : clsId,
			type : 2
		},
		success : function(result) {
			isTimeOut(result);
			
			if (result == "ok") {
				$.ajax({
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					type : "post",
					url : htmlVal.htmlUrl + "?getstudentslistview=",
					success : function(result2) {
						$("#main_content2").html(result2);
					}
				});
			}
			else {
				AlertDialog("出现错误！");
			}
		}
	});
}

function deleteClass() {
	ConfirmDialog("确认删除该班级 ?", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?deleteclass=",
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					window.location.href = htmlVal.htmlOutUrl;
				}
				else {
					AlertDialog("删除班级失败！");
				}
			}
		});
	}, function() {
	});
}

function addUser(clsId) {
	createBorderMaskLayer("add_class_user_form", "添加用户", getLoading(), 580, 640);
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getclassadduserview=",
		data : {
			clsId : clsId
		},
		success : function(result) {
			isTimeOut(result);

			$("#add_class_user_form").html(result);
		}
	});
}