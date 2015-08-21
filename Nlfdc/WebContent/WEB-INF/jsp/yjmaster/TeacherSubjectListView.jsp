<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.teacher_subjects { height: 225px; padding: 30px 40px 10px 40px; overflow-y: auto; } 
.check_subject { float: left; width: 140px; margin: 5px 10px 5px 40px; }
.buttons { padding: 10px; margin: 15px 50px 0px 50px; float: right; }
.big_button { width: 150px; padding: 8px; }
.clear {clear: both; }
</style>

<div class="teacher_subjects">
	<c:forEach var="subject" items="${actionBean.subjects }" >
		<div class="check_subject" >
            <input id='check_subject_${subject.subjectId }' class="check_subject_input" 
                type='checkbox' value='${subject.subjectId }' 
                <c:if test="${subject.isSelected }"> checked='checked' </c:if> 
            />
            <label for='check_subject_${subject.subjectId }'> ${subject.subjectName } </label>
		</div>
	</c:forEach>
	<div class="clear"></div>
</div>

<div class="buttons">
	<a class="pink_button big_button" href="javascript:save_subjects_changes()">确定</a>
</div>