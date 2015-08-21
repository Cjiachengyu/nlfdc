function refreshCity(provinceId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getcity=",
		data : {
			provinceId : provinceId
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result.result);

			var jsonArray = result;

			var cityList = "<option value='-1'>选择城市</option>";
			for (var i = 0; i < jsonArray.length; i++) {
				cityList += "<option value=" + jsonArray[i].cityId + ">" + jsonArray[i].cityName + "</option>";
			}
			$("#cities").html(cityList);

			$.ajax({
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				type : "post",
				url : htmlVal.htmlUrl + "?getschoollistview=",
				success : function(jsp) {
					$("#main_content").html(jsp);
				}
			});
		}
	});
}

function changeCity(cityId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?changecity=",
		data : {
			cityId : cityId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				$.ajax({
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					type : "post",
					url : htmlVal.htmlUrl + "?getschoollistview=",
					success : function(jsp) {
						$("#main_content").html(jsp);
					}
				});
			}
		}
	});
}

function addSchool() {
	var title = $("#add_new_school").html();
	if (title == null || title == "")
	{
		title = "添加学校";
	}
	createBorderMaskLayer("add_school_form", title, getLoading(), 520, 410);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddschoolview=",
		success : function(result) {
			isTimeOut(result);

			$("#add_school_form").html(result);
		}
	});
}

$(document).ready(function() {
	var provinceId = $("#provinces")[0].value;
	if (provinceId != -1) {
		refreshCity(provinceId);
	}
});
