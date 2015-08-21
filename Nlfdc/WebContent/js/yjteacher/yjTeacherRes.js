var htmlFn = {
		
	selectTextCallback : function(bookId) {
		htmlFn.getResourceListView();
	},

	selectChapterCallback : function(chapterId, result) {
		$("#resource_list").html(result);
	},

	selectSectionCallback : function(sectionId, result) {
		$("#resource_list").html(result);
	},

	gotoPageCallback : function(result) {
		$("#resource_list").html(result);
	},
		
	getResourceListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getresourcelistview",
			success : function(result) {
				$("#resource_list").html(result);
			}
		});
	},
	
};
