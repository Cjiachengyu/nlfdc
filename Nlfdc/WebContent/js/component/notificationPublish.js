var umContent = UM.getEditor("content_edit_div");

var htmlFn = {
		
		// 检查并上传图片
		uploadImage : function(id) {
			var $image = $("#" + id + "_image");
			var imageName = $image.val();
			if (imageName !== "") {
				var imageNameArray = imageName.split(".");
				var type = imageNameArray[imageNameArray.length - 1];
				type = type.toLowerCase();
				if (!(type === "jpg" || type === "png" || type === "gif")) {
					AlertDialog("请选择jpg、png、gif类型的图片");
					$image.val("");
					return;
				}
				else {
					this.doUploadImage(id);
				}
			}
		},

		doUploadImage : function(id) {
			var $image = $("#" + id + "_image");
			var imageName = $image.val();
			if (imageName === "") {
				AlertDialog("请选择图片", "");
				return;
			}
			else {
				var imageNameArray = imageName.split(".");
				var type = imageNameArray[imageNameArray.length - 1];
				type = type.toLowerCase();
				if (!(type === "jpg" || type === "png" || type === "gif")) {
					AlertDialog("请选择jpg、png、gif类型的图片");
					$image.val("");
					return;
				}
				var form = $("#" + id + "_image_form");

				var options = {
					url : htmlVal.htmlUrl + '?uploadimage=',
					type : 'post',
					success : function(result) {
						isTimeOut(result);
						
						if (result == "upload_image_exception")
						{
							AlertDialog("上传图片出现异常！");
							return false;
						}
						
						htmlFn.insertImage(id, result);
						form[0].reset();
					}
				};
				form.ajaxSubmit(options);
			}
		},

		insertImage : function(id, imagePath) {
			var img = "<img style=\"max-width:680px;width:expression(width>680?'680px':width+'px');\" class=\"inserted_image\" src=\"" + imagePath + "\" ></img>";
			var doc = document.getElementById(id + "_edit_div");
			this.insertHTML(img + "<br>", doc);
		},
		
		// 在div框中的光标处插入图片或者音频
		insertHTML : function(html, doc) {
			// html 是要插入的内容
			// doc 是要插入内容的某个div,在标准浏览器中用不到它
			doc.focus(); 
			
			var sel, range;
			if (window.getSelection) {
				
				// IE9 and non-IE
				sel = window.getSelection();
				if (sel.getRangeAt && sel.rangeCount) {
					range = sel.getRangeAt(0);
					range.deleteContents();
					var el = document.createElement('div');
					el.innerHTML = html;
					var frag = document.createDocumentFragment(), node, lastNode;
					while ((node = el.firstChild)) {
						lastNode = frag.appendChild(node);
					}

					range.insertNode(frag);
					if (lastNode) {
						range = range.cloneRange();
						range.setStartAfter(lastNode);
						range.collapse(true);
						sel.removeAllRanges();
						sel.addRange(range);
					}
				}
			}
			else if (document.selection && document.selection.type != 'Control') {
//				
				if(ierange == null || focusDivId != doc.id)
				{
					ierange = document.selection.createRange();// 获取光标位置
				}
					
				ierange.pasteHTML("<br>"+html); // 在光标位置插入html 如果只是插入text
			}
		},
		
		setChoosed: function(index)
		{
			$("#notify_target_"+index).attr("checked", "checked");
			
			$("#check_type_1").attr("class", "check_type");	
			$("#check_type_2").attr("class", "check_type");	
			$("#check_type_3").attr("class", "check_type");	
			$("#check_type_4").attr("class", "check_type");	
			$("#check_type_"+index).attr("class", "check_type_choosed");
			if (index == 4)
			{
				$("#class_list").show();
			}
			else
			{
				$("#class_list").hide();
			}
		},
	
		doPublishNotification: function()
		{
			var notifyTargetType = $("input[name='notify_target_type']:checked").val();
			
			var classIdsStr = ""; // 发布班级的id拼接成字符串传到后台，后台在解析
			if (notifyTargetType == 4)
			{
				var checkClasses = $(".class_ids");
				for (var i = 0; i < checkClasses.length; i++) {
					if (checkClasses[i].checked) {
						classIdsStr += checkClasses[i].value + ",";
					}
				}
				
				if (classIdsStr == "")
				{
					AlertDialog("请选择班级", "");
					return false;
				}
			}
			
			var title = $("#notification_title").val();
			var content = $("#content_edit_div").html();
			
			if (title == "")
			{
				AlertDialogWithCallback("请输入标题", "", function(){
					$("#notification_title").focus();
				});
				return false;
			}
			else if (title.length > 128)
			{
				AlertDialogWithCallback("请将标题控制在128字符以内", "", function(){
					$("#notification_title").focus();
				});
				return false;
			}
			
			if (content == "")
			{
				AlertDialogWithCallback("请输入内容", "", function(){
					$("#content_edit_div").focus();
				});
				return false;
			}
			
			$.ajax({
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				type : "post",
				url : htmlVal.htmlUrl + "?dopublishnotification=",
				data : {
					notifyTargetType: notifyTargetType,
					classIdsStr: classIdsStr,
					title: title,
					content: content,
				},
				success : function(result) {
					isTimeOut(result);
					
					if (result == "ok")
					{
						AlertDialogWithCallback("通知发布成功！", "", function(){
							window.location.href = htmlVal.htmlUrl;
						});
					}
					else if (result == "paramInValid")
					{
						AlertDialogWithCallback("参数不合法！", "", function(){
							$("#notification_title").focus();
						});
						return false;
					}
					else if (result == "exception")
					{
						AlertDialogWithCallback("出现异常，通知发布失败！", "", function(){
							window.location.href = window.location.href;
						});
					}
				}
			});
			
			
		},
}

$(function(){
	
	umContent.ready(function() {
		var content_str = $("#content_editor_bar_append").html();
		$("#notification_content .edui-btn-toolbar").append(content_str);
		$("#content_editor_bar_append").html("");
	});
	
	
});

