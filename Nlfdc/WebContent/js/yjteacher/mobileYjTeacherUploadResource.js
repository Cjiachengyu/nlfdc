var fileCount;

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
					 // 1073741824
					 // 20971520 = 20 * 1024 * 1024
					if (file_size > 20971520)
					{
						// 当文件大小超出限制时清空选择的文件
						Alert("请选择不超过20M的文件上传！");
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
				$("#upload_file_btn").attr("class", "highlight_button");
			}
			else if (files.length == 1)
			{
				// 上传单个文件时，显示文件名和文件大小
					var file_name = files[0].name; // 文件名
					var file_size = files[0].size;
					if (file_size > 20971520) 
					{
						// 当文件大小超出限制时清空选择的文件
						Alert("请选择不超过20M的文件上传！");
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
						$("#upload_file_btn").attr("class", "highlight_button");
					}
			}
		},

		UploadProgress : function(up, file) {
			$("#progress_bar").css('width', file.percent + '%');
			$("#progress_tip").html(file.percent + '%');
		},
		UploadComplete : function(up, files, res) {
			$("#cancle_upload_file_btn").hide();
			
			Alert("资源上传完成！", function() {
				window.location.href = htmlVal.htmlUrl + "?tokenId=1";
			});
		},
		Error : function(up, err) {
			if (err.code == -601) {
				Alert("文件后缀错误");
			}

			if (err.code == -600) {
				Alert("文件过大");
			}
		}
	}
});

uploader.init();

$("#upload_file_btn").bind("click", function() {
	
	if(uploader.files.length == 0)
	{
		Alert("请选择文件!");
		return;
	}
	
	if ($("#schema_classify_textbook_selector").val() == null || $("#schema_classify_textbook_selector").val() == 0 ) {
		Alert("请选择课本!", "", function() {
			$("#schema_classify_textbook_selector").focus();
		});
		return;
	}

	$("#main_content").attr("class", "hide");
	$("#file_upload_progress").attr("class", "");
	$("#cancle_upload_file_btn").bind("click", function() {
	});

	var len;
	while (len = uploader.files.length > fileCount) {
		uploader.removeFile(uploader.files[len - 1])
	}

	// 幼教老师、幼教编辑 上传资源时不显示resTag
	var resTag = 1;
	var bookId = $("#schema_classify_textbook_selector").val();
	var chapterId = $("#schema_classify_chapter_selector").val();
	var sectionId = $("#schema_classify_section_selector").val();
	var shareToSchool;
	
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
			isMobileTimeOut(result);

			if (result == "invalid_params") {
				Alert("参数错误，终止上传！", function(){
					history.back();
				});
			}
			else
			{
				try {
					uploader.start(); // 开始上传
				}
				catch (e) {
					Alert("上传资源出现异常");
				}
			}
		}
	});
});

$("#cancle_upload_file_btn").bind("click", function() {

	uploader.stop();
	Confirm("确定终止该资源的上传？", function() {
		uploader.destroy();
		window.location.href = htmlVal.htmlUrl + "?tokenId=1";
	}, function() {
		uploader.start();
	})
});
