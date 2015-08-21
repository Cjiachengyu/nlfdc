<!doctype html>
<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<style>
.textbook_cover { width: 90px; height: 120px; }
.textbook_name { height: 44px; overflow: auto;}
.textbook_publisher {height: 22px; color:#AAA; font-style: italic; }

.add_textbook { width: 96%;  height: 490px;  margin-left: 4%; }
.textbook { width: 90px; margin: 10px; padding: 5px; display: inline-block; position: relative; }
.textbookList { width: 100%; }
.add_textbook_button { margin: 15px; padding: 8px; margin-left: 66%; width: 191px; }
.textbookList2 { width: 100%;  height: 440px; overflow-y: auto; }
</style>
<div class="add_textbook">
    <a class="pink_button add_textbook_button" href="javascript:doAddUserBook()" >确定添加</a>
    <div class="textbookList2" id="textbookList2">
	    <c:forEach var="textbook" items="${actionBean.userBookListToAdd }" varStatus="var">
            <div class="textbook hand" style="border: solid 2px #FFFFFF" 
            		onclick="chooseTextbook(${var.index },${textbook.bookId })" 
            		id="tb_${textbook.bookId }">
                <img class="textbook_cover" 
                    <c:if test="${textbook.coverUrl == ''}"> src="image/common/defaultCover.png" </c:if>
                    <c:if test="${textbook.coverUrl != ''}"> src="${textbook.coverUrl}" </c:if>
                >
                <div class="textbook_name">
                    <span class="font12 ">${textbook.bookName }</span>
                    <div class="clear"></div>
                </div>
                <div class="textbook_publisher">
                    <span class="font12 ">${textbook.publisher }</span>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
		</c:forEach>
    </div>
	<input type="hidden" id="textbook_size" value="${fn:length(actionBean.userBookListToAdd) }">
</div>

<script src="js/yjteacher/yjTeacherEditInfoAddTextbook.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
