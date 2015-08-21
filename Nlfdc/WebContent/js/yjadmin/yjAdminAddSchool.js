function doAddNewSchool() {
	var schoolName = $("#schoolName").val();
	var provinceIn = $("#provincesIn").val();
	var cityIn = $("#citiesIn").val();
	var category = $("input[name='add_school_category']:checked").val();

	//幼教管理员添加学校的时候没有提供选择学校category的功能，默认为6-》对应幼教
	if (category == null || category == "")
	{
		category = 6;
	}
	
	if (schoolName == "") {
		$("#schoolName").focus();
		return;
	}
	if (provinceIn == '-1') {
		$("#provincesIn").focus();
		return;
	}
	if (cityIn == '-1') {
		$("#citiesIn").focus();
		return;
	}

	ConfirmDialog("确认添加新学校 ?<br> 学校名称：<strong>" + schoolName + "</strong>", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addnewschool=",
			data : {
				schoolName : schoolName,
				provinceId : provinceIn,
				cityId : cityIn,
				category : category,
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					window.location.href = window.location.href;
				}
				else if (result == "lengthException") {
					AlertDialog("学校名称长度超出限制,添加学校失败  ！");
				}
				else if (result == "error") {
					AlertDialog("出现错误，学校添加失败 ！");
				}
			}
		});
	}, function() {
	});

}

function check1() {
	var value = $("#schoolName").val();
	if (value.length > 29) {
		$("#msg1").show();
	}
	else {
		$("#msg1").hide();
		if (value == "")
			$("#button").hide();
		else if ($("#citiesIn").val() != -1)
			$("#button").show();
	}
}

function getSchoolCityIn(provinceId) {
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
			var cityList = "<option value='-1'>选择城市</option>";
			for (var i = 0; i < jsonArray.length; i++) {
				cityList += "<option value=" + jsonArray[i].cityId + ">" + jsonArray[i].cityName + "</option>";
			}
			$("#citiesIn").html(cityList);
			$("#button").hide();
		}
	});
}