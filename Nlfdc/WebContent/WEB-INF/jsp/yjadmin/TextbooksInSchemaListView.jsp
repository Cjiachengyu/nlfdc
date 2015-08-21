<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="textbook_in_list" class="book_list">
    <table class="small_table">
        <tbody>
            <c:forEach var="textbook" items="${actionBean.textbookInSchemaList }" varStatus="var">
                <tr>
                    <td width="50%" >
                        <a id="${textbook.bookId }name" title="从大纲中删除" class="highlight"
                            href="javascript:deleteTextbookFromSchema(${textbook.bookId })" 
                        >${textbook.bookName }</a>
                    </td>
                    <td width="20%">${textbook.subjectName }</td>
                    <td width="30%">${textbook.publisher }</td>
                 </tr>
            </c:forEach>
        </tbody>
    </table>
</div>