function addClass() {
	createBorderMaskLayer("add_class_form", "添加班级", getLoading(), 620, 500);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddclassview=",
		success : function(result) {
			isTimeOut(result);

			$("#add_class_form").html(result);
		}
	});
}

function checkUserInfos(clsId) {
	createBorderMaskLayer("user_info_form", "<span id='border_masker_id' style='font-weight: bold;'></span>-用户信息", getLoading(), 1000, 645);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getclassuserinfoview=",
		data : {
			clsId : clsId
		},
		success : function(result) {
			isTimeOut(result);

			$("#user_info_form").html(result);
		}
	});
}

function adminEditClass(clsId,schoolId)
{
	window.location.href = htmlVal.htmlOutUrl + "?editclass=&clsId="+clsId+"&schoolId="+schoolId;
}