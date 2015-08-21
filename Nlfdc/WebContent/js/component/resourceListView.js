function deleteResource(resId){
		ConfirmDialog("确定删除此资源？", function() {
			$.ajax({
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				type : "post",
				url : htmlVal.htmlUrl + "?deleteresource=",
				data : {
					resId : resId
				},
				success : function(result) {
					isTimeOut(result);
					
					if(result == "exception")
					{
						AlertDialog("出现错误，删除资源失败！");				
					}
					else
					{
						$("#resource_list").html(result);
					}
				}
			});
		}, function() {

		});
}
		
function editResName(resId) {
		createBorderMaskLayer("edit_res_name_form", "编辑资源名称", getLoading(), 450, 250);
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			data: {resId : resId },
			url : htmlVal.htmlUrl + "?geteditresnameview=",
			success : function(result) {
				isTimeOut(result);

				$("#edit_res_name_form").html(result);
			}
		});
}
	
function moveUp(index) {
		if (index < 0) {
			return false;
		}
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?moveup=",
			data : {
				index : index
			},
			success : function(result) {
				isTimeOut(result);
	
				if (result == "error") {
					AlertDialog("数据出现错误，移动失败！", "");
					return false;
				}
				if (result == "frist_cannot_moveup") {
					AlertDialog("该资源已经是第一题，无法向前移动！", "");
					return false;
				}
				if (result == "exception") {
					AlertDialog("服务器出现异常，移动失败！", "");
					return false;
				}
	
				$("#resource_list").html(result);
			}
		});
}
	
function moveDown(index) {
		if (index < 0) {
			return false;
		}
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?movedown=",
			data : {
				index : index
			},
			success : function(result) {
				isTimeOut(result);
	
				if (result == "error") {
					AlertDialog("数据出现错误，移动失败！", "");
					return false;
				}
				if (result == "last_cannot_movedown") {
					AlertDialog("该资源已经是最后一题，无法向后移动！", "");
					return false;
				}
				if (result == "exception") {
					AlertDialog("服务器出现异常，移动失败！", "");
					return false;
				}
	
				$("#resource_list").html(result);
			}
		});
}
