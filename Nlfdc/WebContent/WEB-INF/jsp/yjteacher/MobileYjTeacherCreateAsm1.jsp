<%@ page pageEncoding="utf-8" %>
<%@ include file="../component/CommonTopMobile.jsp" %>

<link href="css/yjteacher/mobileYjTeacherCreateAsm.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet"/>

<div class="selector_container">
    <select class="selector" id="book_selector" onchange="selectBook(this.value);">
        <c:forEach var="book" items="${actionBean.tsCatalogueModule.textbookList}">
            <option value="${book.bookId}" <c:if test="${book.isSelected == true}"> selected="selected" </c:if> >
                    ${book.bookName}
            </option>
        </c:forEach>
    </select>
    <select class="selector" id="chapter_selector" onchange="selectChapter(this.value);">
        <c:forEach var="chapter" items="${actionBean.tsCatalogueModule.chapterList}">
            <option value="${chapter.chapterId}" <c:if test="${chapter.isSelected == true}"> selected="selected" </c:if> 
            								     <c:if test="${chapter.chapterId == 00}"> id="chapter_all"</c:if> 
            >
                    ${chapter.chapterName}
            </option>
        </c:forEach>
    </select>
    <div class="clear"></div>
</div>

<div class="res_from_type_container">
	<a href="javascript:selectSource(3)"
		<c:if test="${actionBean.resFromType == 3}">class="res_from_type_selector_item res_from_type_selector_item_selected"</c:if> 
		<c:if test="${actionBean.resFromType != 3}">class="res_from_type_selector_item"</c:if>
	>系统</a>
	<a href="javascript:selectSource(2)"
		<c:if test="${actionBean.resFromType == 2}">class="res_from_type_selector_item res_from_type_selector_item_selected"</c:if> 
		<c:if test="${actionBean.resFromType != 2}">class="res_from_type_selector_item"</c:if>
	>园本</a>
	<a href="javascript:selectSource(1)"
		<c:if test="${actionBean.resFromType == 1}">class="res_from_type_selector_item res_from_type_selector_item_selected"</c:if> 
		<c:if test="${actionBean.resFromType != 1}">class="res_from_type_selector_item"</c:if>
	>个人</a>
	<a href="yjteacreateasmaction?getmobileuploadresourcepage" id="add_res_btn" class="hide" ><img src="image/yj/addQuePlus.png" ></a>
</div>
<input type="hidden" id="resFromType" value="${actionBean.resFromType}">

<div>
    <div id="resource_list">
        <%@ include file="MobileResourceListView.jsp" %>
    </div>
    <div class="clear"></div>
</div>

<div>
    <div class="container create_step">
        <div class="row">
            <a class="col-xs-4 step_block step_btn_disabled">上一步</a>
            <span class="col-xs-4 step_block step_info">第一步：选择资源</span>
            <a class="col-xs-4 step_block step_btn" href="yjteacreateasmaction?gotostep=&step=2">下一步</a>
        </div>
    </div>
</div>


<script>
    var htmlVal = {htmlUrl: "yjteacreateasmaction"};

    function selectSource(source) {
        window.location.href = htmlVal.htmlUrl + "?switchresfromtype&resfromtype=" + source;
    }

    function selectBook(bookId) {
        window.location.href = htmlVal.htmlUrl + "?mobileselecttext&bookId=" + bookId;
    }

    function selectChapter(chapterId) {
        window.location.href = htmlVal.htmlUrl + "?mobileselectchapter&chapterId=" + chapterId;
    }
    
    $(function(){
    	if($("#resFromType").val() == 1)
    	{
    		$("#add_res_btn").attr("class","");
    	}
    });
</script>
<script src="js/yjteacher/yjTeacherCreateAsm1.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

