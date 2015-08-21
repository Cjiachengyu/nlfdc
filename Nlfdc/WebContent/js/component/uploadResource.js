var fileCount;
var limitFileSize = 0;
var limitFileSizeString = "";

$(function(){
	if (htmlVal.limitFileSize == null)
	{
		// 1073741824 = 1 * 1024 * 1024 * 1024;  1G
		limitFileSize = 1073741824;
	}
	else
	{
		limitFileSize = htmlVal.limitFileSize;
	}
	
	if (htmlVal.limitFileSizeString == null)
	{
		limitFileSizeString = "1G";
	}
	else
	{
		limitFileSizeString = htmlVal.limitFileSizeString;
	}
});

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'add_file_btn', // you can pass in id...
	url : '../uploadtresourceaction',
	flash_swf_url : 'js/plupload/js/Moxie.swf',
	silverlight_xap_url : 'js/plupload/js/Moxie.xap',
	filters : {
		prevent_duplicates : true
	// 不允许选取重复文件
	},
	multi_selection : true,
	// chunk_size: '20Mb',

	init : {

		FilesAdded : function(up, files) {
			if (files.length > 1)
			{
				// 上传多个文件时，显示文件个数和文件总大小
				var fileSizeSum = 0;
				fileCount = 0;
				
				for (var i = 0, len = files.length; i < len; i++)
				{
					var file_size = files[i].size;
					if (file_size > limitFileSize)
					{
						// 当文件大小超出限制时清空选择的文件
						AlertDialog("请选择不超过" + limitFileSizeString + "的文件上传！", "");
						files = null;
						return;
					}
					else 
					{
						fileSizeSum+= file_size;
						fileCount++;
					}
				}
				
				fileSizeSum = fileSizeSum / 1024 / 1024;
				$("#file_name_value").html("共  "+ fileCount + " 个文件");
				$("#file_size_value").html("共  "+ fileSizeSum.toFixed(2) + " M");
				
				$("#file_name").attr("class", "col_1");
				$("#file_size").attr("class", "col_1");
				$("#upload_file_btn").attr("class", "pink_button");
			}
			else if (files.length == 1)
			{
				// 上传单个文件时，显示文件名和文件大小
					var file_name = files[0].name; // 文件名
					var file_size = files[0].size;
					if (file_size > limitFileSize) 
					{
						// 当文件大小超出限制时清空选择的文件
						AlertDialog("请选择不超过" + limitFileSizeString + "的文件上传！", "");
						files = null;
						return;
					}
					else 
					{
						fileCount = 1;
						
						file_size = file_size / 1024 / 1024;
						if (file_name.length > 80)
						{
							// 限制资源名称长度为80
							file_name = file_name.substr(0, 80);
						}
						$("#file_name_value").html(file_name)
						$("#file_size_value").html(file_size.toFixed(2) + " M")
						
						$("#file_name").attr("class", "col_1");
						$("#file_size").attr("class", "col_1");
						$("#upload_file_btn").attr("class", "pink_button");
					}
			}
		},

		UploadProgress : function(up, file) {
			$("#progress_bar").css('width', file.percent + '%');
			$("#progress_tip").html(file.percent + '%');
		},
		UploadComplete : function(up, files, res) {
			$("#cancle_upload_file_btn").hide();
			
			AlertDialogWithCallback("资源上传完成！", "", function() {
				window.location.href = window.location.href;
			});
		},
		Error : function(up, err) {
			if (err.code == -601) {
				AlertDialog("文件后缀错误");
			}

			if (err.code == -600) {
				AlertDialog("文件过大");
			}
		}
	}
});

uploader.init();

$("#upload_file_btn").bind("click", function() {
	
	if(uploader.files.length == 0)
	{
		AlertDialogWithCallback("请选择文件!", "", function() {
		});
		return;
	}
	
	if ($("#schema_classify_textbook_selector").val() == null || $("#schema_classify_textbook_selector").val() == 0 ) {
		AlertDialogWithCallback("请选择课本!", "", function() {
			$("#schema_classify_textbook_selector").focus();
		});
		return;
	}
	
	$("#main_content").slideUp(100);
	$("#file_upload_progress").show(100);
	$("#cancle_upload_file_btn").bind("click", function() {
	});

	var len;
	while (len = uploader.files.length > fileCount) {
		uploader.removeFile(uploader.files[len - 1])
	}

	var resTag = $("input[name='resource_tag']:checked").val();
	var bookId = $("#schema_classify_textbook_selector").val();
	var chapterId = $("#schema_classify_chapter_selector").val();
	var sectionId = $("#schema_classify_section_selector").val();
	var shareToSchool;
	
	if (resTag == null || resTag == "")
	{
		// 幼教老师、幼教编辑 上传资源时不显示resTag
		resTag = 1;
	}
	
	var shareToSchoolObj = $("#share_to_school")[0];
	if( shareToSchoolObj  == null)
	{
		shareToSchool = 0;
	}
	else
	{
		shareToSchool = shareToSchoolObj.checked ? 1 : 0;
	}
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : "uploadtresourceaction?receiveparam",
		data : {
			resTag : resTag,
			bookId : bookId,
			chapterId : chapterId,
			sectionId : sectionId,
			shareToSchool : shareToSchool,
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "invalid_params") {
				AlertDialogWithCallback("参数错误，终止上传！", "", function(){
					window.location.href = window.location.href;
				});
			}
			else
			{
				try {
					uploader.start(); // 开始上传
				}
				catch (e) {
					AlertDialog("上传资源出现异常，请尝试升级浏览器或使用其他浏览器！");
				}
			}
		}
	});
});

$("#cancle_upload_file_btn").bind("click", function() {

	uploader.stop();
	ConfirmDialog("确定终止该资源的上传？", function() {
		uploader.destroy();
		window.location.href = window.location.href;
	}, function() {
		uploader.start();
	})
});
