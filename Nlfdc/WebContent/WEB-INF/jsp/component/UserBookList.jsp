<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
.textbook { width: 90px; margin: 10px; padding: 5px; display: inline-block; position: relative; }
.textbook_cover { width: 90px; height: 120px; }
.textbook_name { height: 44px; overflow: auto;}
.textbook_publisher {height: 22px; color:#AAA; font-style: italic; }
.delete_div { position: absolute; right: 0; top: 0; border-radius: 50%;}
.delete_img { width: 30px; height: 30px; border-radius: 50%; display: none; }
</style>

<!-- 被包含页面，不需要设置title -->
<div class="textbookList">
	<c:forEach var="textbook" items="${actionBean.userBookList }" >
        <div class="textbook" onmouseover="userEditBooklistFn.showDeleteImg(${textbook.bookId})" onmouseout="userEditBooklistFn.hideDeleteImg(${textbook.bookId})" >
            <img class="textbook_cover" 
                <c:if test="${textbook.coverUrl == ''}"> src="image/common/defaultCover.png" </c:if>
                <c:if test="${textbook.coverUrl != ''}"> src="${textbook.coverUrl}" </c:if>
            >
            <div class="textbook_name">
                <span class="font12">${textbook.bookName }</span>
            </div>
            <div class="textbook_publisher">
                <span class="font12" >${textbook.publisher }</span>
            </div>
            <div class="delete_div">
                <a href="javascript:userEditBooklistFn.deleteUserTextbook(${textbook.bookId })">
                    <img title="删除课本" src="image/yj/delete_button.png" class="delete_img" id="${textbook.bookId}" >
                </a>
            </div>
            <div class="clear"></div>
        </div>
	</c:forEach>
    <div class="textbook hand" onclick="javascript:userEditBooklistFn.getAddTextbookView()" >
        <img class="textbook_cover" src="image/yj/add_textbook2.png">
        <div class="textbook_name" style="overflow: initial; text-align: center;">
            <span class="font12" style="font-size: 17px; position: relative; top: 18px; color: red;">添加课本</span>
        </div>
        <div class="textbook_publisher">
            <span class="font12">&nbsp;</span>
        </div>
        <div class="clear"></div>
    </div>
</div>

