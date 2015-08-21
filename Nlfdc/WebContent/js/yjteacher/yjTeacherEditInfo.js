function doChangeUserSubject(check, subjectId) {
	var checked = check ? 1 : 0;

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?changesubject",
		data : {
			subjectId : subjectId,
			checked : checked
		},
		success : function(result) {
			isTimeOut(result);

			if (result === "ok") {
				window.location.href = htmlVal.htmlUrl;
			}
			else {
				AlertDialog("修改科目失败");
			}
		}
	});
}
