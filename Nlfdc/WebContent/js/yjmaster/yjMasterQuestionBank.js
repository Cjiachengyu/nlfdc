var htmlVal = {
	htmlUrl : "masterquestionbankaction",
};

var htmlFn = {

	selectSubjectCallback : function(subjectId) {
		htmlFn.getQuestionBankListView();
	},

	selectTextCallback : function(bookId) {
		htmlFn.getQuestionBankListView();
	},

	selectChapterCallback : function(chapterId, result) {
		$("#question_list_view").html(result);
	},

	selectSectionCallback : function(sectionId, result) {
		$("#question_list_view").html(result);
	},

	gotoPageCallback : function(result) {
		$("#question_list_view").html(result);
	},

	getQuestionBankListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getquestionbanklistview",
			success : function(result) {
				$("#question_list_view").html(result);
			}
		});
	},

	selectQuestionType : function(queType) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?selectquestiontype",
			data : {
				queType : queType
			},
			success : function(result) {
				isTimeOut(result);
				$("#question_list_view").html(result);
			}
		});
	},
};
