var htmlFn = {
	selectSubjectCallback : function(subjectId) {
		htmlFn.getAssignListView();
	},

	selectTextCallback : function(bookId) {
		htmlFn.getAssignListView();
	},

	selectChapterCallback : function(chapterId, result) {
		$("#assignment_list").html(result);
	},

	selectSectionCallback : function(sectionId, result) {
		$("#assignment_list").html(result);
	},

	gotoPageCallback : function(result) {
		$("#assignment_list").html(result);
	},

	getAssignListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getassignmentlistview",
			success : function(result) {
				isTimeOut(result);
				$("#assignment_list").html(result);
			}
		});
	},

	deleteAssignment : function(asmId) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?deleteassignment",
			data : {
				asmId : asmId
			},
			success : function(result) {
				isTimeOut(result);
				$("#assignment_list").html(result);
			}
		});
	},

	setAsmFree : function(asmId) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?setasmfree",
			data : {
				asmId : asmId
			},
			success : function(result) {
				isTimeOut(result);
				$("#assignment_list").html(result);
			}
		});
	},

	setAllToFree : function(setToValue) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?setalltofree",
			data : {
				setToValue : setToValue
			},
			success : function(result) {
				isTimeOut(result);
				$("#assignment_list").html(result);
			}
		});
	},

};