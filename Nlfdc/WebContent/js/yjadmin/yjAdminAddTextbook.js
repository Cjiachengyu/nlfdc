
function doAddNewTextBook() {
	var bookName = $("#bookName").val();
	var publisher = $("#publisher").val();
	var subject = $("#subject").val();
	
	if (bookName == "") {
		$("#bookName").focus();
		return;
	}

	if (subject == "-1") {
		$("#subject").focus();
		return;
	}
	
	// 校验textArea内容
	var content = $("#c2").val();
	if (content == "") {
		$("#c2").focus();
		return;
	}
	var strs = content.split("\n");
	for (var i = strs.length - 1; i >= 0; i--) {
		if (strs[i] == "" || strs[i].length == 1) {
			AlertDialogWithCallback("第  " + (i + 1) + " 行数据为空 ！", null, function() {
				$("#c2").focus();
			});
			return;
		}
		if (strs[i].charAt(0) != "#") {
			AlertDialogWithCallback("第  " + (i + 1) + " 行数据没有以   # 开头！", null, function() {
				$("#c2").focus();
			});
			return;
		}
	}

	ConfirmDialog("确认添加新课本  ? ", function() {
		$("#add_textbook_button").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?doaddtextbook=",
			data : {
				bookName : bookName,
				publisher : publisher,
				subject : subject,
				content : content,
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("添加成功！", "", function() {
						window.location = htmlVal.htmlUrl + "?turntoaddtextbookpage=";
					});
				}
				else if (result == "dataError") {
					AlertDialog("数据校验失败！");
				}
				else if (result == "error") {
					AlertDialog("出现错误，课本添加失败 ！");
				}
			}
		});
	}, function() {
	});
}

function checkValidData(value,id)
{
	if (isNaN(value))
	{
		$("#"+id).val("");
	}
}


function checkBookCover(filePath) {
	$("#file_text_field").val(filePath);
	var bookCover = $("#bookCover").val();

	if (bookCover !== "") {
		var array = bookCover.split(".");
		var type = array[array.length - 1];
		type = type.toLowerCase();
		if (type != "jpg" && type != "png" && type != "gif") {
			AlertDialog("文件只能是 .jpg/.png/.gif");
			$("#file_text_field").val("");
		}
		else
		{
			var form = $("#submitBookCover");

			var options = {
				url : htmlVal.htmlUrl + '?uploadbookcover=',
				type : 'post',
				success : function(result) {
					isTimeOut(result);

					if (result == "ok")
					{
						updateBookCover();
					}
					else if (result == "error")
					{
						AlertDialogWithCallback("图片上传出现错误，请重新选择文件！","",function(){
							form[0].reset();
						});
					}
				}
			};
			form.ajaxSubmit(options);
		}
	}
}

function updateBookCover()
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getbookcoverurl=",
		success : function(result) {
			isTimeOut(result);

			if (result == "") {
				AlertDialogWithCallback("添加封面出现错误，请重新选择文件！","",function(){
					form[0].reset();
				});
			}
			else
			{
				$("#add_textbook_cover").attr("src",result);
			}
		}
	});
}

function refreshGrade(category) {
	$("#grade").html($("#options_c" + category).html());
}

$(function(){
	$(".price_box").hide();
	
	$("#add_textbook_button").bind("click",doAddNewTextBook);
});
