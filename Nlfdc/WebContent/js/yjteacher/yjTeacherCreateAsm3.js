$(function(){
	var date = new Date();
	var assignmentName = $("#assignment_name").val();
	if(assignmentName == "" || assignmentName == null){
		$("#assignment_name").val((date.getMonth() + 1) + "月" + date.getDate() + "日" + "的任务");
		$("#assignment_name").focus();
		$("#assignment_name").select();
	};
	
	var hour = date.getHours();
    var minute = date.getMinutes();
    	
    var isMobile = $("#is_in_mobile_browser").val();
    if (isMobile == 1)
    {
    	$("#assignment_start_time").date({theme:"datetime"});
    	$("#assignment_finish_time").date({theme:"datetime"});
    }
    else
    {
    	$("#assignment_start_time").datetimepicker({
 		   hour: hour,
 		   minute: minute  });
    	$("#assignment_finish_time").datetimepicker({
 		   hour: hour,
 		   minute: minute  });
    }
});

function doCreateAssignment(isMobile) {
	
	// 防止重复点击，暂时把确定按钮屏蔽
	if (isMobile)
	{
		$("#create_assignment_btn").attr("href", "javascript:void(0)");
		$("#create_assignment_btn").attr("class", "col-xs-4 step_block step_btn_disabled")
		$("#back_step").attr("href", "javascript:void(0)");
		$("#back_step").attr("class", "col-xs-4 step_block step_btn_disabled")
	}
	else
	{
		$("#create_assignment_btn").attr("href", "javascript:void(0)");
		$("#create_assignment_btn").attr("class", "disable_look");
		$("#web_back_step").attr("class", "create_step_btn create_step_btn_prev_disable left");
		$("#web_back_step").attr("href", "javascript: void(0)");
	}
	
	$("#asm_detail").hide();
	$("#creating_tip").show();
	
	var asmName = $("#assignment_name").val();

	if ($.trim(asmName) == "" || asmName == null) {
		if (isMobile == 0)
		{
			AlertDialogWithCallback("请输入任务名称！", null, function(){
				$("#assignment_name").focus();
			});
		}
		else if (isMobile == 1)
		{
			$("#limit_msg").html("请输入任务名称！");
			$("#assignment_name").focus();
		}
		return;
	}

	if (asmName.length > 48) {
		if (isMobile == 0)
		{
			AlertDialogWithCallback("任务名称长度超出限制，请重新输入！", null, function(){
				$("#assignment_name").focus();
			});
		}
		else if (isMobile == 1)
		{
			$("#limit_msg").html("任务名称长度超出限制，请重新输入！");
			$("#assignment_name").focus();
		}
		return;
	}
	
	var asmQueContent = $("#asm_que_content").val();
	if (asmQueContent == "" || asmQueContent == null)
	{
		asmQueContent = "";
		// AlertDialogWithCallback("请输入任务说明", null, function(){
			// $("#asm_que_content").focus();
		// });
		// return;
	}

	if (asmQueContent.length > 1000)
	{
		if (isMobile == 0)
		{
			AlertDialogWithCallback("任务说明长度超出限制，请重新输入！", null, function(){
				$("#asm_que_content").focus();
			});
		}
		else if (isMobile == 1)
		{
			$("#limit_msg").html("任务说明长度超出限制，请重新输入！");
			$("#asm_que_content").focus();
		}
		return;
	}


	var now = new Date().getTime();
	var startTime = $("#assignment_start_time").val();
	var finishTime = $("#assignment_finish_time").val();
	var sTime = 0;
	var fTime = 0;
	
	if (startTime != null && startTime != "") {
		startTime = startTime + ":00";
		sTime = new Date(startTime.replace(/-/g, "/")).getTime();
		if (sTime < now) {
			startTime = "";
		}
	}
	
	if (finishTime != null && finishTime != "") {
		finishTime = finishTime + ":00";
		fTime = new Date(finishTime.replace(/-/g, "/")).getTime();
		if (fTime < now) {
			if (isMobile == 0)
			{
				AlertDialogWithCallback("作业结束时间不能早于当前时间！", null, function() {
					$("#assignment_finish_time").focus();
				});
			}
			else if (isMobile == 1)
			{
				$("#limit_msg").html("作业结束时间不能早于当前时间！");
				$("#assignment_finish_time").focus();
			}
			return;
		}
	}
	
	if (startTime != null && startTime != "" && finishTime != null && finishTime != "") {
		if (fTime < sTime) {
			if (isMobile == 0)
			{
				AlertDialogWithCallback("作业结束时间不能早于开始时间！", null, function() {
					$("#assignment_start_time").focus();
				});
			}
			else if (isMobile == 1)
			{
				$("#limit_msg").html("作业结束时间不能早于开始时间！");
				$("#assignment_start_time").focus();
			}
			return;
		}
	}
	
	if (finishTime == "" || finishTime == null)
	{
		if (startTime == "" || startTime == null)
		{
			sTime = new Date().getTime();
		}

		// 默认一周后
		// 7*24*60*60*1000 = 86400000;
		var finishDate = new Date(sTime + 604800000);
		
		var year = finishDate.getFullYear();
		var month = finishDate.getMonth()+1;
		var day = finishDate.getDate();
		var hour = finishDate.getHours();
		var minute = finishDate.getMinutes();
		finishTime = year +"-"+month+"-"+day+" "+hour+":"+minute+":00";
	}
	
	// 收集创建任务需要的数据
	var asmType = $("input[name='asmType']:checked").val();
	var classIdsStr = ""; // 发布班级的id拼接成字符串传到后台，后台在解析
	var checkClasses = $(".class_ids");
	for (var i = 0; i < checkClasses.length; i++) {
		if (checkClasses[i].checked) {
			classIdsStr += checkClasses[i].value + ",";
		}
	}
	
	var createAsmToken = $("#create_asm_token").val();
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?docreateyjassignment=",
		data : {
			asmName : asmName,
			asmQueContent: asmQueContent,
			asmType : asmType,
			classIdsStr : classIdsStr,
			startTime : startTime,
			finishTime : finishTime,
			createAsmToken: createAsmToken,
		},
		success : function(result) {
			isTimeOut(result);
			
			if (isMobile == 1)
			{
				//移动端
				window.location = htmlVal.htmlUrl + "?getcreateasmmsgpage";
			}
			else if (isMobile == 0)
			{
				//电脑网页端
				if (result == "ok") {
					window.location = "yjteaasmaction";
				}
				else if (result == "exception") {
					AlertDialogWithCallback("服务器出现异常，发布任务失败！", "", function() {
						window.location = htmlVal.htmlUrl;
					});
				}
				else if (result == "already_created")
				{
					AlertDialogWithCallback("不能重复发布任务！", "", function() {
						window.location = htmlVal.htmlUrl;
					});
				}
				else if (result == "asmName_empty") {
					AlertDialogWithCallback("任务名称不能为空！", null, function(){
						$("#assignment_name").focus();
						$("#create_assignment_btn").attr("href","javascript:doCreateAssignment("+isMobile+");");
					});
				}
				else if (result == "asmQueContent_and_resList_empty") {
					AlertDialogWithCallback("任务说明和任务资源不能同时为空！", null, function(){
						$("#asm_que_content").focus();
						$("#create_assignment_btn").attr("href","javascript:doCreateAssignment("+isMobile+");");
					});
				}
				else if (result == "asmName_too_long") {
					AlertDialogWithCallback("任务名称长度超出限制！", null, function(){
						$("#assignment_name").focus();
						$("#create_assignment_btn").attr("href","javascript:doCreateAssignment("+isMobile+");");
					});
				}
			}
		}
	});
}
