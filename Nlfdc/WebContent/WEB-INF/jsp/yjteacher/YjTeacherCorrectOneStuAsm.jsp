<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<link rel="stylesheet" href="css/component/chatDialogStyle.css" media="screen" type="text/css" />

<link href="css/yjteacher/yjTeacherCorrectStuAsm.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div class="correct_stu_asm">
	<label  style="margin: 5px 0px; text-align: center; display: block; width: 100%; font-size: 14px; ">提交时间：${actionBean.correctingStuAsm.beginSubmitTimeString } </label>
	<hr>
	<div class="stu_asm_res_list">
		<ul class="chat-thread">
		<c:forEach var="stuAnswerItem" items="${actionBean.stuAnswerItemList }" >
		<li <c:if test="${stuAnswerItem.isPiGai == 0 }">class="left"</c:if>
			<c:if test="${stuAnswerItem.isPiGai == 1 }">class="right"</c:if>	>
			<div class="stu_answer_item">
				<!-- 文本 -->
				<c:if test="${stuAnswerItem.answerType == 1 }">
					<span class="stu_answer_item_text">${stuAnswerItem.answerItem }</span>
				</c:if>
				
				<!-- 图片 -->
				<c:if test="${stuAnswerItem.answerType == 2 }">
					<img class="stu_answer_item_img" title="查看图片" style="width: 100px;" src="${stuAnswerItem.fileUrl }" onclick="checkBigImg('${stuAnswerItem.fileUrl }')">
				</c:if>

				<!-- 音频 -->
				<c:if test="${stuAnswerItem.answerType == 3 }">
					<span class="fake_audio" audio_path="${stuAnswerItem.fileUrl }"></span>
				</c:if>
				
				<!-- 视频 -->
				<c:if test="${stuAnswerItem.answerType == 4 }">
					<img src="image/yj/vedio_black.png" title="播放视频" style="width: 100px; " onclick="checkVideo('${stuAnswerItem.fileUrl }')">
				</c:if>
			</div>
		</li>
		</c:forEach>
		</ul>
	</div>
	
	<div class="tea_grade_box">
		<div class="student_asm_feedback">
			<div class="feedback_content_box">
				<label style="font-size: 14px; float: left; margin-right: 10px; ">评语：</label>
				<textarea rows="3" cols="100" class="feedback_words_textarea" id="feedback_words"></textarea>
				<div class="clear"></div>
			</div>
			<div class="feedback_content_box">
				<div id="star_score" ></div>
				<div class="clear"></div>	
			</div>
	
			<!-- 改为取消确定，只使用‘下一个’ -->			
			<!-- 
			<c:if test="${actionBean.currentClassAssignment.uncorrectedList != null && fn:length(actionBean.currentClassAssignment.uncorrectedList) != 0}">
		    </c:if>
			<a class="right mar10 do_correct_btn" id="do_correct_one_stu_asm">确定</a>
			 -->
			<a class="right mar10 do_correct_btn" id="do_correct_one_stu_asm_next">确定</a>
		</div>

	</div>
</div>
<input type="hidden" id="stu_name" value="${actionBean.correctingStuAsm.studentName }">

<script src="jqueryplugin/starscore/jquery.k.starScore.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src='js/yjteacher/yjTeacherCorrectedAsm.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
