var dateString;

var htmlVal = {
	htmlUrl : "yjmasterresourceaction",
};

var htmlFn = {
	getResourceListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getresourcelistview",
			data : {
				dateString : dateString
			},
			success : function(result) {
				$("#resource_list").html(result);
			}
		});
	},

	selectSubjectCallback : function(subjectId) {
		htmlFn.getResourceListView();
	},

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

	selectDate : function(dateString) {
		dateString = dateString.substr(0,10);
		$("#time").val(dateString);
		
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?selectdate",
			data : {
				dateString : dateString
			},
			success : function(result) {
				isTimeOut(result);

				$("#resource_list").html(result);
			}
		});
	},
};

$(function(){
	$("#time").datetimepicker({
	       showHour: false,
	       showMinute: false,
	       showTime: false,
	    });
	
});