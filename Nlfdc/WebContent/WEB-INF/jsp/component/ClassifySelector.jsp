<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="schema_classify_wrapper">

<c:if test="${sessionScope.user.userRole != 9}">
	<select id="schema_classify_subject_selector" onchange="schema_classify_select_subject(this.value);">
		<c:forEach var="subject" items="${actionBean.tsClassifyModule.subjectList}">
			<option value="${subject.subjectId}" <c:if test="${subject.isSelected == true}"> selected="selected" </c:if> >
				${subject.subjectName}
			</option>
		</c:forEach>
	</select>
	<br>
</c:if>

	<select id="schema_classify_textbook_selector" onchange="schema_classify_select_textbook(this.value);">
	  	<c:forEach var="textbook" items="${actionBean.tsClassifyModule.textbookList}">
			<option value="${textbook.bookId}" <c:if test="${textbook.isSelected == true}"> selected="selected" </c:if> >
				${textbook.bookName}
			</option>
		</c:forEach>
	</select>

	<br>

	<select id="schema_classify_chapter_selector" onchange="schema_classify_select_chapter(this.value);">
	  	<c:forEach var="chapter" items="${actionBean.tsClassifyModule.chapterList}">
			<option value="${chapter.chapterId}" <c:if test="${chapter.isSelected == true}"> selected="selected" </c:if> >
				${chapter.chapterName}
			</option>
		</c:forEach>
	</select>

	<br>
<!-- 
	<select id="schema_classify_section_selector" onchange="schema_classify_select_section(this.value);">
	  	<c:forEach var="section" items="${actionBean.tsClassifyModule.currentChapter.sectionList}">
			<option value="${section.sectionId}" <c:if test="${section.isSelected == true}"> selected="selected" </c:if> >
				${section.sectionName}
			</option>
		</c:forEach>
	</select>
 -->
</div>


