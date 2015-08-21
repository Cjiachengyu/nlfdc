function doCorrectBatchStuAsm()
{
	var feedback = $("#feedback_words").val();
	var star = $("#difficulty").html();
	
	if ( feedback.length > 512)
	{
		AlertDialogWithCallback("任务反馈长度超出限制，请将字数控制在512以内！", "", function(){
			$("#feedback_words").focus();
		});
		return false;
	}
	
	var correctedStuIds = "";
	var $batch_corrected_stuid = $(".batch_corrected_stuid");
	for (var i = 0; i < $batch_corrected_stuid.size(); i++) {
		if ($batch_corrected_stuid[i].checked) {
			correctedStuIds += $batch_corrected_stuid[i].value + ",";
		}
	}
	if (correctedStuIds == "") {
		correctedStuIds = "no_checked";
	}
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?docorrectbatchstuasm=",
		data: { feedback: feedback, star: star, correctedStuIds: correctedStuIds },
		success : function(result) {
			isTimeOut(result);

			if (result == "ok")
			{
				window.location.href = window.location.href; 
			}
			else if (result == "long_param")
			{
				AlertDialogWithCallback("任务反馈长度超出限制，请将字数控制在512以内！", "", function(){
					$("#feedback_words").focus();
				});
			}
			else
			{
				AlertDialogWithCallback("服务器出现异常，批改任务失败！", "", function(){
					window.location.href = window.location.href;
				});
			}
		}
	});
}

function doCorrectOneStuAsm(gotoNext)
{
	var feedback = $("#feedback_words").val();
	var star = $("#difficulty").html();

	if ( feedback.length > 256)
	{
		AlertDialogWithCallback("任务反馈长度超出限制，请将字数控制在256以内！", "", function(){
			$("#feedback_words").focus();
		});
		return false;
	}
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?docorrectonestuasm=",
		data: { feedback: feedback, star: star, },
		success : function(result) {
			isTimeOut(result);

			if (result == "ok")
			{
				if (gotoNext == 0)
				{
					window.location.href = window.location.href; 
				}
				else
				{
					$.ajax({
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						type : "post",
						url : htmlVal.htmlUrl + "?getcorrectonestuasmview=",
						success : function(result) {
							isTimeOut(result);
							
							if (result == "no_next")
							{
								window.location.href = window.location.href;
							}
							
							$("#yjtea_correct_one_stu_asm").html(result);
						}
					});
				}
			}
			else if (result == "long_param")
			{
				AlertDialogWithCallback("任务反馈长度超出限制，请将字数控制在256以内！", "", function(){
					$("#feedback_words").focus();
				});
			}
			else
			{
				AlertDialogWithCallback("服务器出现异常，批改任务失败！", "", function(){
					window.location.href = window.location.href;
				});
			}
		}
	});
}

function  doCorrectOneStuAsm0()
{
	doCorrectOneStuAsm(0);
}

function doCorrectOneStuAsm1()
{
	doCorrectOneStuAsm(1);
}

$(function(){
	$("#star_score").starScore({
		messageArray: ["|",	"|", "|", "|", "|" ],
		difficultySign: "奖励",
		initIndex: 4,
	});
	
	replaceAudioHtml();
	
//	$("#do_correct_one_stu_asm").bind("click",doCorrectOneStuAsm0);
	$("#do_correct_one_stu_asm_next").bind("click",doCorrectOneStuAsm1);
	$("#do_correct_batch_stu_asm").bind("click",doCorrectBatchStuAsm);
	
	// 设置弹出层的标题（单独批改和查看已完成批改时用到）
	var stuName = $("#stu_name").val();
	$("#border_masker_id").html(stuName);
});