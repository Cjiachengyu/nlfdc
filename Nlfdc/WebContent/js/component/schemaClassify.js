function schema_classify_select_subject(subjectId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?classifyselectsubject",
		data : {
			subjectId : subjectId
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result.result);
			
//			alert("textArray: " + result.testArray);
//			alert("chapterArray: " + result.testArray);
			
			schema_classify_fill_text(result.textArray);
			schema_classify_fill_chapter(result.chapterArray);
			//schema_classify_fill_section(result.sectionArray);
		}
	});
}

function schema_classify_select_textbook(bookId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?classifyselecttextbook",
		data : {
			bookId : bookId
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result.result);

			schema_classify_fill_chapter(result.chapterArray);
			//schema_classify_fill_section(result.sectionArray);
		}
	});
}

function schema_classify_select_chapter(chapterId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?classifyselectchapter",
		data : {
			chapterId : chapterId
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result.result);

			//schema_classify_fill_section(result.sectionArray);
		}
	});
}

function schema_classify_select_section(sectionId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?classifyselectsection",
		data : {
			sectionId : sectionId
		},
		dataType : "json",
		success : function(result) {
			isTimeOut(result);
		}
	});
}

function schema_classify_fill_text(textArray) {
	var jsonArray = textArray;
	var textList = "";
	for (var i = 0; i < jsonArray.length; i++) {
		textList += "<option value='" + jsonArray[i].bookId + "' >" + jsonArray[i].bookName + "</option>";
	}
	$("#schema_classify_textbook_selector").html(textList);
}

function schema_classify_fill_chapter(chapterArray) {
	var jsonArray = chapterArray;
	var chapterList = "";
	for (var i = 0; i < jsonArray.length; i++) {
		chapterList += "<option value='" + jsonArray[i].chapterId + "' >" + jsonArray[i].chapterName + "</option>";
	}
	$("#schema_classify_chapter_selector").html(chapterList);
}

function schema_classify_fill_section(sectionArray) {
	var jsonArray = sectionArray;
	var sectionList = "";
	for (var i = 0; i < jsonArray.length; i++) {
		sectionList += "<option value='" + jsonArray[i].sectionId + "' >" + jsonArray[i].sectionName + "</option>";
	}
	$("#schema_classify_section_selector").html(sectionList);
}

function do_classify_schema() {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?dotextbookclassify",
		success : function(result) {
			isTimeOut(result);

			if (result == "atLeastTextbook") {
				AlertDialog("请至少选择一本课本");
				return;
			}
			else if (result == "exception")
			{
				AlertDialog("出现错误，分类失败！");
			}

			closeAllLayers();

			htmlFn.classifySchemaCallback(result);
		}
	});
}
