<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="css/yjteacher/yjTeacherCorrectStuAsm.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div class="correct_stu_asm">
	<div class="uncorrected_stu_asm_list">
		<div >
		<label style="margin-left: 10px; font-size: 22px; ">选择要批改的同学：</label>
		<br>
			<c:forEach var="assign" items="${actionBean.currentClassAssignment.uncorrectedList }">
				<div class="batch_student_name_box">
					 <input type="checkbox" class="batch_corrected_stuid"  id="student_asm_${assign.stuId }" value="${assign.stuId }" checked>
               		 <label class="class_title_name" for="student_asm_${assign.stuId }">${assign.studentName }</label>
				</div>		
			</c:forEach>		
		</div>
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
			</div>
		</div>
		<div>
			<a class="right mar10" id="do_correct_batch_stu_asm">确定</a>
			<div class="clear"></div>
		</div>
	</div>
</div>

<script src="jqueryplugin/starscore/jquery.k.starScore.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src='js/yjteacher/yjTeacherCorrectedAsm.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>