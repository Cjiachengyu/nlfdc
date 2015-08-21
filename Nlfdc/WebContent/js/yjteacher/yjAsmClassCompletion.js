function correctStudentAsm(stuId) {
	createBorderMaskLayer("yjtea_correct_one_stu_asm", "<label style='font-size: 14px;' id='border_masker_id'></label>", getLoading(), 1000, 620);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getcorrectonestuasmview=",
		data : {
			stuId : stuId
		},
		success : function(result) {
			isTimeOut(result);

			$("#yjtea_correct_one_stu_asm").html(result);
		}
	});
}

function showTip(index) {
	if (index == 1) {
		AlertDialog("该同学<strong>未下载</strong>作业！", "");
	}
}

function correctStudentAsmByBatch() {
	createBorderMaskLayer("yjtea_correct_batch_stu_asm", "<label style='font-size: 14px;'>任务批改-批量</label>", getLoading(), 1000, 500);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getcorrectbatchstuasmview=",
		success : function(result) {
			isTimeOut(result);

			$("#yjtea_correct_batch_stu_asm").html(result);
		}
	});
}

function viewCorrectedStuAsm(stuId) {
	createBorderMaskLayer("yjtea_corrected_stu_asm", "<label style='font-size: 14px;'  id='border_masker_id'></label>", getLoading(), 1000, 620);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getcorrectedstuasmview=",
		data : {
			stuId : stuId
		},
		success : function(result) {
			isTimeOut(result);

			$("#yjtea_corrected_stu_asm").html(result);
		}
	});
}

function checkBigImg(imageUrl) {
	var viewSize = getViewportSize();
	createBorderMaskLayer("yjtea_check_big_image", "<label style='font-size: 14px;'>查看图片</label> <a class='right' href='javascript:addImage()'><img title='放大' src='image/common/add.png' style='width: 30px; margin-left: 20px;'></a> <a class='right' href='javascript:minusImage()'><img title='缩小' src='image/common/minus.png' style='width: 30px;'></a>", getLoading(), viewSize[0], viewSize[1]);
	
	$("#yjtea_check_big_image").html("<div style='text-align: center;'><img id='view_big_image' src='" + imageUrl + "' style='max-width: 95%; max-height: "+(viewSize[1]-100)+"px;'></div>");
}

function checkVideo(fileUrl) {
	createBorderMaskLayer("view_stu_asm_video_box", "查看视频", getLoading(), null, null);

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : "yjteacherassignmentinfoaction?checkansweritemvideo",
		data : {
			fileUrl : fileUrl
		},
		success : function(result) {
			isTimeOut(result);

			$("#view_stu_asm_video_box").html(result);
		}
	});
}