(function() {
	$(".accordion").accordion({
		speed : 100,
		closedSign : "",
		openedSign : ""
	});
})();

function schema_module_select_chapter(chapterId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?selectchapter",
		data : {
			chapterId : chapterId
		},
		success : function(result) {
			isTimeOut(result);

			htmlFn.selectChapterCallback(chapterId, result);

			$(".chapter_section_a").removeClass("selected");
			$("#chapter_a_" + chapterId).addClass("selected chapter_section_a");
		}
	});
}

function schema_module_select_section(sectionId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?selectsection",
		data : {
			sectionId : sectionId
		},
		success : function(result) {
			isTimeOut(result);

			htmlFn.selectSectionCallback(sectionId, result);

			$(".chapter_section_a").removeClass("selected");
			$("#section_a_" + sectionId).addClass("selected chapter_section_a");
		}
	});
};