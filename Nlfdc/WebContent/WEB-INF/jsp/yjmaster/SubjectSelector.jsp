<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
.master_subject_item { display: inline-block; padding: 5px 5px 2px 5px; }
.master_subject_item_selected { display: inline-block; color: white; background: #FE7472; padding: 5px;}
.master_subject_selector {float: left; }
</style>

<div class="master_subject_selector">
    <c:forEach var="subject" items="${actionBean.subjects }">
        <a id="master_subject_item_${subject.subjectId}" 
            href="javascript:masterSubjectSelectorSelectSubject(${subject.subjectId})"
            <c:if test="${subject.subjectId == actionBean.subjectId }">class="master_subject_item master_subject_item_selected"</c:if>
            <c:if test="${subject.subjectId != actionBean.subjectId }">class="master_subject_item"</c:if>
        >${subject.subjectName }</a>
    </c:forEach>
    <div class="clear"></div>
</div>

<script>
	function masterSubjectSelectorSelectSubject(subjectId) {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?selectsubject",
			data : {
				subjectId : subjectId
			},
			success : function(result) {
				isTimeOut(result);

				$(".master_subject_item").removeClass("master_subject_item_selected");
				$("#master_subject_item_" + subjectId).addClass("master_subject_item_selected");

				$("#main_content").html(result);
			}
		});
	}
</script>
