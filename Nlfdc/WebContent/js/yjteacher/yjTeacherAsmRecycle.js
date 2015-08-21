var htmlFn = {
	restoreAssignment : function(asmId) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?restoreassignment",
			data : {
				asmId : asmId
			},
			success : function(result) {
				isTimeOut(result);

				$("#assignment_list").html(result);
			}
		});
	},

	gotoPageCallback : function(result) {
		$("#assignment_list").html(result);
	},

};
