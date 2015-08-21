<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="css/component/schemaCatalogue.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<style>
    .subject_title { background: #FE7472; margin: 0 0 -1px 0; border-radius: 5px 5px 0 0; padding: 5px; color: white; text-align: center; font-weight: bold; }
    .subject_grouper { border: solid 2px #FE7472; border-radius: 8px; margin: 10px 0 15px 0; }
    .book_list_item { border-top: dotted 1px #FE7472; cursor: pointer; padding: 3px 0 3px 10px; }
    .return_all_book_btn { background: #FE7472; padding: 5px; color: white; text-align: center; margin: 10px 0; border-radius: 5px; }
    .textbook_name { width: 115px; margin-top: 10px; color: #FAB2B0; }
</style>

<div id="hidden_textbook_info_list" class="hidden" >
    <c:forEach var="book" items="${actionBean.tsCatalogueModule.allTextbookList}">
       <div id="hidden_textbook_${book.bookId }" >
            <div class="textbook_whole"
                onclick="javascript:catalogue_select_text(0);" 
            >
            	<div class="return_all_book_btn">
            		<span>返回所有课本</span>
            	</div>
                <div class="left" >
                    <img class="textbook_cover" 
                        <c:if test="${book.coverUrl == ''}"> src="image/common/defaultCover.png" </c:if>
                        <c:if test="${book.coverUrl != ''}"> src="${book.coverUrl}" </c:if>
                    >
                </div>
                <div class="textbook_info left" >
                    <div class="textbook_name">
                        <span class="font14"> ${book.bookName} </span>
                    </div>
                    <div class="textbook_publisher">
                        <span class="font_small gray"> ${book.publisher} </span>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </c:forEach>
</div>

<div id="textbook_info_list" <c:if test="${actionBean.tsCatalogueModule.currentTextbook.bookId != 0 }"> class="hidden" </c:if>>
    <c:forEach var="subject" items="${actionBean.tsCatalogueModule.subjectList}">
        <div class="subject_grouper">
            <div class="subject_title">
                <span>${subject.subjectName }</span>
            </div>
            <c:forEach var="book" items="${subject.bookList}">
                <div id="textbook_${book.bookId }" class="book_list_item" onclick="javascript:catalogue_select_text(${book.bookId});" >
                    <span class="font16"> ${book.bookName} </span>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
</div>

<div id="current_textbook_info" <c:if test="${actionBean.tsCatalogueModule.currentTextbook.bookId == 0 }"> class="hidden" </c:if> >
    <div id="schema_catalogue">
        <div id="textbook_current" >
        </div>
    </div>

    <div id="chapter_section">
        <%@ include file="ChapterAndSection.jsp"%>
    </div>
</div>

<input type="hidden" id="current_textbookId" value="${actionBean.tsCatalogueModule.currentTextbook.bookId }"> 

<c:if test="${actionBean.tsCatalogueModule.currentTextbook.bookId != 0 }">

<script>
$(function (){
	var bookId = $("#current_textbookId").val();
    $("#textbook_current").html($("#hidden_textbook_" + bookId).html());
});
</script>
</c:if>

<script src='js/component/schemaCatalogue.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
