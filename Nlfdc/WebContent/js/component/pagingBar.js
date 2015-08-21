var pagingBarFn = {
	getDestPageNum : function() {
		var pageCount = $.trim(document.getElementById("page_count").innerHTML);
		var destPage = document.getElementById("dest_page").value;

		var positiveInteger = /^[1-9]\d*$/;
		if (!positiveInteger.test(destPage)) {
			AlertDialog(jsMessage.pageSkipError);
			return false;
		}

		if (parseInt(destPage) > parseInt(pageCount)) {
			destPage = pageCount;
		}

		return destPage;
	},

	gotoPage : function(dstPage) {
		var pageNum = dstPage;
		if (dstPage == null) {
			pageNum = pagingBarFn.getDestPageNum();
		}

		if (pageNum != false) {
			$.ajax({
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				type : "post",
				url : htmlVal.htmlUrl + "?gotopage",
				data : {
					pageNum : pageNum
				},
				success : function(result) {
					isTimeOut(result);

					htmlFn.gotoPageCallback(result);
				}
			});
		}
	}
};