function doEditTextBook() {
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
	
	ConfirmDialog("确认修改课本信息  ? ", function() {
		$("#edit_textbook_button").hide();
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?doedittextbook=",
			data : {
				bookName : bookName,
				publisher : publisher,
				subject : subject,
			},
			success : function(result) {
				isTimeOut(result);

				if (result == "ok") {
					AlertDialogWithCallback("修改成功！", "", function() {
						window.location = htmlVal.htmlBackUrl ;
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
		url : htmlVal.htmlUrl + "?updatebookcover=",
		success : function(result) {
			isTimeOut(result);

			if (result == "error") {
				AlertDialogWithCallback("更新封面出现错误，请重新选择文件！","",function(){
					form[0].reset();
				});
			}
			else
			{
				$("#edit_textbook_cover").attr("src",result);
			}
		}
	});
}

function refreshGrade(category) {
	$("#grade").html($("#options_c" + category).html());
}

$(function(){
	$(".price_box").hide();
	
	$("#edit_textbook_button").bind("click",doEditTextBook);
	
	refreshGrade($("#category").val());
});
