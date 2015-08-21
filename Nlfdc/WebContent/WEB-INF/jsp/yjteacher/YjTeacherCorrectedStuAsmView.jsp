<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="css/component/chatDialogStyle.css" media="screen" type="text/css" />

<style>
.corrected_stu_asm { width: 100%; height: 500px; } 
.corrected_stu_asm_res_list {  width: 100%; height: 90%;  }
.stu_answer_item { width: 95%; margin: 5px 10px; }
.stu_answer_item_text { font-size: 17px; }
.head_box {position: relative; width: 100%; height: 10%; border-bottom: solid 2px #999999;}
.score_box { display: inline-block; margin: 10px 0 10px 0; width: 50%; float: left; text-align:center; }
.score_star_box { display: inline-block;}
.corrected_stu_score_flower { width: 25px; }
.submit_time_box {display: inline-block; margin: 10px 0 10px 0; width: 50%; float: left; text-align:center; }
.operation_box { width: 99%; height: 50px; }
</style>

<div class="corrected_stu_asm">
	<div class="head_box">
		<div class="submit_time_box">
			<label  style="font-size: 14px; margin-top: 10px; display: inline-block; ">提交时间：${actionBean.correctedStuAsm.beginSubmitTimeString } </label>
		</div>
		<div class="score_box">
			<label style="font-size: 14px; margin-top: 10px; display: inline-block; ">奖励：</label>
			<div class="score_star_box">
				<!-- 幼教只有1-5分 -->
				<c:if test="${actionBean.correctedStuAsm.stuAsmScore gt 0}"><img src="image/yj/flower-on.png" class="corrected_stu_score_flower"></c:if>
				<c:if test="${actionBean.correctedStuAsm.stuAsmScore gt 1}"><img src="image/yj/flower-on.png" class="corrected_stu_score_flower"></c:if>
				<c:if test="${actionBean.correctedStuAsm.stuAsmScore gt 2}"><img src="image/yj/flower-on.png" class="corrected_stu_score_flower"></c:if>
				<c:if test="${actionBean.correctedStuAsm.stuAsmScore gt 3}"><img src="image/yj/flower-on.png" class="corrected_stu_score_flower"></c:if>
				<c:if test="${actionBean.correctedStuAsm.stuAsmScore gt 4}"><img src="image/yj/flower-on.png" class="corrected_stu_score_flower"></c:if>
			</div>
		</div>
		
		<div class="clear"></div>
	</div>

	<div class="corrected_stu_asm_res_list">
		<ul class="chat-thread">
			<c:forEach var="stuAnswerItem" items="${actionBean.stuAnswerItemList }" >
			<li 
				<c:if test="${stuAnswerItem.isPiGai == 0 }">class="left"</c:if>
				<c:if test="${stuAnswerItem.isPiGai == 1 }">class="right"</c:if>
				>
			<div class="stu_answer_item">
				<!-- 文本 -->
				<c:if test="${stuAnswerItem.answerType == 1 }">
					<span class="stu_answer_item_text">${stuAnswerItem.answerItem }</span>
				</c:if>
				
				<!-- 图片 -->
				<c:if test="${stuAnswerItem.answerType == 2 }">
					<img title="查看图片" style="width: 100px;" src="${stuAnswerItem.fileUrl }" onclick="checkBigImg('${stuAnswerItem.fileUrl }')">
				</c:if>

				<!-- 音频 -->
				<c:if test="${stuAnswerItem.answerType == 3 }">
					<span class="fake_audio" audio_path="${stuAnswerItem.fileUrl }"></span>
				</c:if>
				
				<!-- 视频 -->
				<c:if test="${stuAnswerItem.answerType == 4 }">
					<img src="image/yj/vedio_black.png" title="播放视频" style="width: 100px; " onclick="checkVideo('${stuAnswerItem.fileUrl }')" >
				</c:if>
			</div>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="operation_box">
	<a class="right mar20 pink_button" href="javascript:getNextCorrectedStuAsmView(${actionBean.correctedStuAsm.stuId })" id="do_view_next_stu_asm">下一个</a>
</div>

<input type="hidden" id="stu_name" value="${actionBean.correctedStuAsm.studentName }">

<script>
$(function(){
	replaceAudioHtml();
	
	// 设置弹出层的标题（单独批改和查看已完成批改时用到）
	var stuName = $("#stu_name").val();
	$("#border_masker_id").html(stuName);
});

function getNextCorrectedStuAsmView(stuId)
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getcorrectedstuasmview=",
		data : {
			stuId : stuId, getNext : 1
		},
		success : function(result) {
			isTimeOut(result);
				
			if (result == "no_next")
			{
				closeAllLayers();	
			}
			
			$("#yjtea_corrected_stu_asm").html(result);
		}
	});
}
</script>
