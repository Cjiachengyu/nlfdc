<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="css/component/chapterAndSection.css?jscssimgversion=${actionBean.jsCssImgVersion}">

<style>
    .accordion .selected { border-left: solid 3px; background-color: #F8F8F8; color: #FAB2B0; font-weight: bold;}
</style>

<div class="accordion">
	<ul class="nav">
		<c:forEach var="chapterItem" items="${actionBean.tsCatalogueModule.chapterList}" >
			<li>
				<a href="javaScript:schema_module_select_chapter(${chapterItem.chapterId});" 
						<c:if test="${chapterItem.isSelected == true }"> class="relative chapter_section_a selected" </c:if>
						<c:if test="${chapterItem.isSelected != true }"> class="relative chapter_section_a" </c:if>
						id="chapter_a_${chapterItem.chapterId}" >
					${chapterItem.chapterName}
				</a>
				
				<!-- 
				<ul <c:if test="${chapterItem.chapterId == actionBean.tsCatalogueModule.currentChapter.chapterId }"> style='display: block;' </c:if> >
			  		<c:forEach var="sectionItem" items="${chapterItem.sectionList}">
						<li>
							<a href="javaScript:schema_module_select_section(${sectionItem.sectionId});" 
									<c:if test="${sectionItem.isSelected == true }"> class="chapter_section_a selected" </c:if>
									<c:if test="${sectionItem.isSelected != true }"> class="chapter_section_a" </c:if>
									id="section_a_${sectionItem.sectionId}" >
								${sectionItem.sectionName}
							</a>
						</li>
					</c:forEach>
				</ul>
				 -->
			</li>
		</c:forEach>
	</ul>
</div>

<script src="js/common/accordion.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="js/component/chapterAndSection.js?jscssimgversion=${actionBean.jsCssImgVersion}" ></script>
