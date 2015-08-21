var htmlVal = {
	htmlUrl : "yjmasterclassaction",
};

var htmlFn = {

	gotoPageCallback : function(result) {
		$("#class_list").html(result);
	},

	getClassListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getclasslistview",
			success : function(result) {
				isTimeOut(result);
				$("#class_list").html(result);
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

function addClass() {
	createBorderMaskLayer("add_class_form", "新添加班级", getLoading(), 530, 350);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddclassview=",
		success : function(result) {
			isTimeOut(result);

			$("#add_class_form").html(result);
		}
	});
}

function doAddClass() {
	var clsName = $("#cls_name").val();
	var classEnterYear = $("#classEnterYear").val();
	
	if (clsName == "")
	{
		$("#cls_name").focus();
		return ;
	}
	else if (clsName.length > 16)
	{
		AlertDialogWithCallback("班级名称长度超出限制！请重新输入班级名称！", null, function() {
			$("#cls_name").focus();
		});
		return ;
	}
	
	ConfirmDialog("确认添加班级?<br>  名称：<strong>" + clsName + "</strong><br> 入学年份：<strong> " + classEnterYear + "</strong>", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addclass=",
			data : {
				clsName : clsName,
				classEnterYear : classEnterYear
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					window.location = htmlVal.htmlUrl;
				}
				else if (result == "dataError") {
					$("#cls_name").val("");
					$("#cls_name").focus();
					AlertDialog("参数不合法！");
				}
				else if (result == "error") {
					AlertDialog("出现错误，班级添加失败 ！");
				}
			}
		});
	}, function() {
	});		

}
