function doAddNewSchema() {
	var scmName = $("#scmName").val();
	if (scmName == "") {
		$("#textBookName").focus();
		return;
	}

	ConfirmDialog("确认添加新大纲 ?<br><strong>" + scmName + "</strong>", function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addoneschema=",
			data : {
				scmName: scmName,
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					window.location.href = window.location.href;
				}
				else if (result == "lengthException") {
					AlertDialogWithCallback("大纲名称长度超出限制,添加大纲失败  ！", null, function() {
						$("#bookName").focus();
					});
				}
				else if (result == "error") {
					AlertDialog("出现错误，大纲添加失败 ！");
				}
			}
		});
	}, function() {
	});

}

function check1(value) {
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