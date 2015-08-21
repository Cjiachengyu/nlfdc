function checkResName() {
	var value = $("#new_res_name").val();
	if (value.length > 79) {
		$("#msg1").show();
	}
	else {
		$("#msg1").hide();
		if (value == '')
			$("#do_change_res_name_btn").hide();
		else
			$("#do_change_res_name_btn").show();
	}
}

function doChangeResName()
{
	var oldResName = $("#old_res_name").val();
	var newResName = $("#new_res_name").val();
	
	if (oldResName == newResName)
	{
		closeAllLayers();
		return false;
	}
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		data: { newResName: newResName },
		url : htmlVal.htmlUrl + "?dochangeresname=",
		success : function(result) {
			isTimeOut(result);
			
			if (result == "ok")
			{
				window.location.href = window.location.href;
			}
			else
			{
				AlertDialogWithCallback("出现错误，修改资源名称失败！", null, function(){
					window.location.href = window.location.href;
				});
			}
		}
	});
	
}