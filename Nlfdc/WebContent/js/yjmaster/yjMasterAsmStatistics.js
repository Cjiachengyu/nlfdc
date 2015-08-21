var htmlVal = {
	htmlUrl : "yjmastercheckassignmentaction",
};

var htmlFn = {

	gotoPageCallback : function(result) {
		$("#main_content").html(result);
	},

	getTeacherListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getteacherassignmentlistview",
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
