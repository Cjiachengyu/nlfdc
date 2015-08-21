function check1(value) {
	if (value.length > 15) {
		$("#msg1").show();
	}
	else {
		$("#msg1").hide();
		if (value.length > 0) {
			$("#button").show();
		}
		else {
			$("#button").hide();
		}
	}
}

function getSchoolCityIn(provinceId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getcity=",
		data : {
			provinceId : provinceId,
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result.result);

			var jsonArray = result;
			var cityList = "<option value='-1'>选择城市</option>";
			for (var i = 0; i < jsonArray.length; i++) {
				cityList += "<option value=" + jsonArray[i].cityId + ">" + jsonArray[i].cityName + "</option>";
			}
			$("#citiesIn").html(cityList);
		}
	});
}

function doUpdateSchool() {
	var newSchoolName = $("#schoolName").val();
	var provinceId = $("#provincesIn").val();
	var cityId = $("#citiesIn").val();
	var category = $("input[name='add_school_category']:checked").val();

	if (provinceId == -1) {
		AlertDialogWithCallback("请选择省份!", "", function(){
			$("#provincesIn").focus();
		});
		return false;
	}
	
	if (cityId == -1) {
		AlertDialogWithCallback("请选择城市!", "", function(){
			$("#citiesIn").focus();
		});
		return false;
	}
	if (category == null || category == "")
	{
		category = 6; //幼教学校的category
	}
	
	ConfirmDialog("确认修改？", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?updateschool=",
			data : {
				newSchoolName : newSchoolName,
				provinceId : provinceId,
				cityId : cityId,
				category : category
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialog("修改成功！", "");
				}
				else if (result == "error") {
					AlertDialog("出现错误,修改失败 ！");
				}
			}
		});
	}, function() {
	});
}

function addSchema(scmId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?addschematoeditingschool=",
		data : {
			scmId : scmId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				refreshSchema();
			}
			else {
				AlertDialog("添加大纲失败!");
			}
		}
	});
}

function deleteSchema(scmId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?deleteschemafromeditingschool=",
		data : {
			scmId : scmId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				refreshSchema();
			}
			else {
				AlertDialog("删除大纲失败!");
			}
		}
	});
}

function refreshSchema() {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getschemaouteditingschoollistjson=",
		dataType : "json",
		success : function(result) {
			var jsonArray = result;
			var schemaOut = "";
			for (var i = 0; i < jsonArray.length; i++) {
				schemaOut += "<li class='scm_item' ><a href='javascript:addSchema(" + jsonArray[i].scmId + ")' title='添加大纲'>" + jsonArray[i].scmName + "</a></li>";
			}
			$("#schemaOut").html(schemaOut);
		}
	});

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getschemaineditingschoollistjson=",
		dataType : "json",
		success : function(result2) {
			var jsonArray2 = result2;
			var schemaIn = "";
			for (var i = 0; i < jsonArray2.length; i++) {
				schemaIn += "<li class='scm_item' ><a href='javascript:deleteSchema(" + jsonArray2[i].scmId + ")' title='删除大纲'>" + jsonArray2[i].scmName + "</a></li>";
			}
			$("#schemaIn").html(schemaIn);
		}
	});
}

function deleteMaster(userId) {
	ConfirmDialog("确认删除该校长？", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?deleteuser=",
			data : {
				userId : userId
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("删除校长成功！", "", function(){
						$.ajax({
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							type : "post",
							url : "admineditschoolaction?getmastersinfoview=",
							success : function(result) {
								$("#school_masters_info").html(result);
							}
						});
					});
				}
			}
		});
	}, function() {
	});
}

function deleteTeacher(userId) {
	ConfirmDialog("确认删除该老师？", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?deleteuser=",
			data : {
				userId : userId
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("删除老师成功！", "", function(){
						$.ajax({
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							type : "post",
							url : htmlVal.htmlUrl + "?getteachersinfoview=",
							success : function(result) {
								$("#school_teachers_info").html(result);
							}
						});
					});
				}
			}
		});
	}, function() {
	});
}

function deleteStudent(userId) {
	ConfirmDialog("确认删除该家长？", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?deleteuser=",
			data : {
				userId : userId
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("删除家长成功！", "", function(){
						$.ajax({
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							type : "post",
							url : htmlVal.htmlUrl + "?getstudentsinfoview=",
							success : function(result) {
								$("#school_students_info").html(result);
							}
						});
					});
				}
			}
		});
	}, function() {
	});
}

function addNewMaster() {
	var title = $("#add_new_master").html();
	if (title == null || title == "")
	{
		title = "添加校长";
	}
	
	createBorderMaskLayer("add_master_form", title, getLoading(), 500, 300);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddmasterview=",
		success : function(result) {
			isTimeOut(result);

			$("#add_master_form").html(result);
		}
	});
}

$(document).ready(function() {
	var provinceId = $("#provincesIn")[0].value;
	if (provinceId != -1) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getcity=",
			data : {
				provinceId : provinceId,
				ignore : 1
			},
			dataType : "json",
			success : function(result) {
				isTimeOut(result.result);

				var jsonArray = result;
				var cityList = "<option value='-1' selected>选择城市</option>";
				for (var i = 0; i < jsonArray.length; i++) {
					cityList += "<option value=" + jsonArray[i].cityId + ">" + jsonArray[i].cityName + "</option>";
				}

				$("#citiesIn").html(cityList);
				$("#citiesIn")[0].value = $("#hide_cityId").val();
			}
		});
	}

	var category = $("#hide_category").val();

	var obj = document.getElementsByName("add_school_category");
	for (var i = obj.length - 1; i >= 0; i--) {
		if (obj[i].value == category) {
			obj[i].checked = true;
		}
	}
	;
});
