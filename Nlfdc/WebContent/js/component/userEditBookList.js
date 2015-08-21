var userEditBooklistFn = {
	deleteUserTextbook : function(bookId) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?deleteuserbook=",
			data : {
				bookId : bookId
			},
			success : function(result) {
				if (result == "ok") {
					$.ajax({
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						type : "post",
						url : htmlVal.htmlUrl + "?getuserbooklistview=",
						success : function(page) {
							$("#textbook_div").html(page);
						}
					});
				}
				else if (result == "error") {
					AlertDialog("删除课本失败！");
				}
			}
		});

	},

	getAddTextbookView : function() {
		createBorderMaskLayer("add_textbook_form", "添加课本", getLoading(), 850, 560);
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getuserbooklisttoaddview=",
			success : function(result) {
				isTimeOut(result);

				$("#add_textbook_form").html(result);

			}
		});
	},

	showDeleteImg : function(id) {
		$("#" + id).show();
	},

	hideDeleteImg : function(id) {
		$("#" + id).hide();
	},
};