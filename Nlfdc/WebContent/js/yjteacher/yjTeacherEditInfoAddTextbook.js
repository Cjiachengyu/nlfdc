var chooseStateArray;

$(function() {
	var size = $("#textbook_size").val();

	// 记录选中状态； 1为选中，0为反
	chooseStateArray = new Array(size);

	for (var i = 0; i < size; i++) {
		chooseStateArray[i] = 0;
	}
});

function chooseTextbook(index, bookId) {
	if (chooseStateArray[index] == 0) {
		chooseStateArray[index] = bookId;
		$("#tb_" + bookId).css("border", "solid 2px #CC0000");
	}
	else {
		chooseStateArray[index] = 0;
		$("#tb_" + bookId).css("border", "solid 2px #FFFFFF");
	}
}

function doAddUserBook() {
	var textbookIds = "";
	for (var i = 0; i < chooseStateArray.length; i++) {
		if (chooseStateArray[i] != 0) {
			textbookIds += chooseStateArray[i] + "-";
		}
	}

	if (textbookIds == "")
		return;

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?adduserbooklist=",
		data : {
			textbookIds : textbookIds
		},
		success : function(result) {
			if (result == "ok") {
				window.location.href = htmlVal.htmlUrl;
			}
			else if (result == "error") {
				AlertDialog("添加课本出现错误！");
			}
		}
	});
}