var htmlVal = {
	htmlUrl : "yjmasterteacheraction",
};

var htmlFn = {

	gotoPageCallback : function(result) {
		$("#main_content").html(result);
	},

	getTeacherListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getteacherlistview",
			success : function(result) {
				isTimeOut(result);

				$("#main_content").html(result);
			}
		});
	},

	selectSubject : function(subjectId) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?selectsubject",
			data : {
				subjectId : subjectId
			},
			success : function(result) {
				isTimeOut(result);

				$("#main_content").html(result);
			}
		});
	}
};

// 取值范围是学校所有老师的userId
var teaId;

function setClass(userId, userName) {
	teaId = userId;
	createBorderMaskLayer("master_set_teacher_classes_form", "<Strong>" + userName + "</Strong>&nbsp设置班级", getLoading(), 680, 440);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getteacherclasseslistview=",
		data : {
			teaId : teaId
		},
		success : function(result) {
			isTimeOut(result);

			$("#master_set_teacher_classes_form").html(result);
		}
	});
}

function save_classes_changes() {
	var classIds = "";
	var checkClasses = $(".check_class_input");
	for (var i = 0; i < checkClasses.length; i++) {
		if (checkClasses[i].checked) {
			classIds += checkClasses[i].value + ",";
		}
	}
	;
	if (classIds == "") {
		classIds = "nochecked";
	}

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?saveteacherclasses=",
		data : {
			classIds : classIds,
			teaId : teaId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				// window.location.href = "yjmasterteacheraction";
				htmlFn.getTeacherListView();
				closeAllLayers();
			}
			else if (result == "error") {
				AlertDialogWithCallback("老师设置班级出现错误！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
			else if (result == "dataError") {
				AlertDialogWithCallback("数据出现错误，老师设置班级失败！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
		}
	});

}

function setSubject(userId, userName) {
	teaId = userId;

	createBorderMaskLayer("master_set_teacher_subjects_form", "<Strong>" + userName + "</Strong>&nbsp设置科目", getLoading(), 680, 400);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getteachersubjectslistview=",
		data : {
			teaId : teaId
		},
		success : function(result) {
			isTimeOut(result);

			$("#master_set_teacher_subjects_form").html(result);
		}
	});
}

function save_subjects_changes() {
	var subjectIds = "";
	var checkSubjects = $(".check_subject_input");
	for (var i = 0; i < checkSubjects.length; i++) {
		if (checkSubjects[i].checked) {
			subjectIds += checkSubjects[i].value + ",";
		}
	}
	;
	if (subjectIds == "") {
		subjectIds = "nochecked";
	}
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?saveteachersubjects=",
		data : {
			subjectIds : subjectIds,
			teaId : teaId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				// window.location.href = "yjmasterteacheraction";
				htmlFn.getTeacherListView();
				closeAllLayers();
			}
			else if (result == "error") {
				AlertDialogWithCallback("老师设置科目出现错误！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
			else if (result == "dataError") {
				AlertDialogWithCallback("数据出现错误，老师设置科目失败！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
		}
	});
}

function setTextbook(userId, userName) {
	teaId = userId;

	createBorderMaskLayer("master_set_teacher_textbooks_form", "<Strong>" + userName + "</Strong>&nbsp设置课本", getLoading(), 850, 550);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddtextbookview=",
		data : {
			teaId : teaId
		},
		success : function(result) {
			isTimeOut(result);

			$("#master_set_teacher_textbooks_form").html(result);
		}
	});
}

function save_textbooks_changes() {
	var textbookIds = "";
	var checkTextbooks = $(".check_textbook_input");
	for (var i = 0; i < checkTextbooks.length; i++) {
		if (checkTextbooks[i].checked) {
			textbookIds += checkTextbooks[i].value + ",";
		}
	}
	;
	if (textbookIds == "") {
		textbookIds = "nochecked";
	}

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?saveteachertextbooks=",
		data : {
			textbookIds : textbookIds,
			teaId : teaId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				// window.location.href = "yjmasterteacheraction";
				htmlFn.getTeacherListView();
				closeAllLayers();
			}
			else if (result == "error") {
				AlertDialogWithCallback("老师设置课本出现错误！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
		}
	});

}
