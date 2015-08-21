function catalogue_select_subject(subjectId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?selectsubject",
		data : {
			subjectId : subjectId
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result.result);

			var jsonArray = result;
			var textList = "";
			for (var i = 0; i < jsonArray.length; i++) {
				textList += "<option value=" + jsonArray[i].bookId + ">" + jsonArray[i].bookName + "</option>";
			}
			$("#select_textbook").html(textList);
			$("#chapter_section").html("");

			htmlFn.selectSubjectCallback(subjectId);
		}
	});
}

function catalogue_select_text(bookId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?selecttext",
		data : {
			bookId : bookId
		},
		success : function(result) {
			isTimeOut(result);

			$("#chapter_section").html(result);

			if (bookId === 0) {
				$("#current_textbook_info").hide();
				$("#textbook_info_list").fadeIn();
				$("#current_textbookId").val(0);
			}
			else {
				$("#textbook_current").html($("#hidden_textbook_" + bookId).html());
				$("#textbook_info_list").hide();
				$("#current_textbook_info").fadeIn();
				$("#current_textbookId").val(bookId);
			}

			htmlFn.selectTextCallback(bookId);
		}
	});
}