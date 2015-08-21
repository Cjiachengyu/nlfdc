$(document).ready(function() {
	$("#add_class_type").on("click", function(event) {
		event = event || window.event;
		var target = event.target || event.srcElement;
		var $addType = $("#add_class_type_value");
		if (target.id === "add_class_type1") {
			if ($addType.val() === "1") {
				return;
			}
			$(this).find("a").removeClass("add_class_type_selected");
			$(target).addClass("add_class_type_selected");
			$addType.val("1");
			$("#main1").show();
			$("#main2").hide();

		}
		else if (target.id === "add_class_type2") {
			if ($addType.val() === "2") {
				return;
			}
			$(this).find("a").removeClass("add_class_type_selected");
			$(target).addClass("add_class_type_selected");
			$addType.val("2");
			$("#main1").hide();
			$("#main2").show();
		}
	});

	var thisYear = $("#thisYear").val();
	var minYear = $("#minYear").val();

	var yearOptions = "";
	yearOptions += "<option value='" + thisYear + "' selected>" + thisYear + " 年</option>";
	for (var i = thisYear - 1; i >= minYear; i--) {
		yearOptions += "<option value='" + i + "'>" + i + " 年</option>";
	}
	$("#classEnterYear").html(yearOptions);
	$("#classEnterYear2").html(yearOptions);

	var options = "";
	options += "<option value='1' selected >1班 </option>";
	for (var i = 2; i < 30; i++) {
		options += "<option value='" + i + "'>" + i + "班 </option>";
	}
	$("#beginIndex").html(options);

	var start = $("#beginIndex").val();
	options = "";
	options += "<option value='" + start + "' selected>" + start + "班</option>";
	for (var i = parseInt(start) + 1; i < parseInt(start) + 30; i++) {
		options += "<option value='" + i + "'>" + i + "班</option>";
	}
	$("#endIndex").html(options);
});

function check(value) {
	if (value.length > 15) {
		$("#msg1").show();
	}
	else {
		$("#msg1").hide();
		if (value == '')
			$("#button").hide();
		else
			$("#button").show();
	}
}

function check10(value) {
	if (value.length > 9) {
		$("#msg2").show();
	}
	else {
		$("#msg2").hide();
		if (value == '')
			$("#button2").hide();
		else
			$("#button2").show();
	}
}

function initEndIndex(start) {
	var options = "";
	options += "<option value='" + start + "' selected>" + start + "班</option>";
	for (var i = parseInt(start) + 1; i < parseInt(start) + 30; i++) {
		options += "<option value='" + i + "'>" + i + "班 </option>";
	}
	$("#endIndex").html(ptions);
}

function addOneClass() {
	var clsName = $("#cls_name").val();
	var enterYear = $("#classEnterYear").val();

	ConfirmDialog("确认添加班级?<br>名称：<strong>" + clsName + "</strong><br>入学年份：<strong>" + enterYear + "</strong>", function() {
		$("#button").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addoneclass=",
			data : {
				clsName : clsName,
				classEnterYear : enterYear
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					window.location.href = window.location.href;
				}
				else {
					AlertDialog("出现错误，班级添加失败 ！");
				}
			}
		});
	}, function() {
	});
}

function addBatchClass() {
	var baseClassName = $("#className2").val();
	var enterYear = $("#classEnterYear2").val();
	var beginIndex = $("#beginIndex").val();
	var endIndex = $("#endIndex").val();

	ConfirmDialog("确认添加班级?<br> 名称：<strong>" + baseClassName + "(" + beginIndex + ")班 </strong> ..." + "<br>   入学年份：<strong>" + enterYear + "</strong>", function() {
		$("#button2").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addbatchclass=",
			data : {
				baseClassName : baseClassName,
				classEnterYear : enterYear,
				beginIndex : beginIndex,
				endIndex : endIndex
			},
			success : function(result) {

				if (result == "ok") {
					window.location.href = window.location.href;
				}
				else {
					AlertDialog("出现错误，班级添加失败 ！");
				}
			}
		});
	}, function() {
	});
}