<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style>
.textbook_list { width: 100%; }
a { text-decoration: none; color: #2b8ccd; }
</style>

<div class="textbook_list">
  <table class="light_pink_table">
      <thead >
         <tr>
             <th width="40%">课本名称</th>
             <th width="40%">出版社</th>
             <th width="20%">操作</th>
         </tr>
      </thead>
  
      <tbody>
         <c:forEach var="book" items="${actionBean.bookList }" varStatus="var">
           <tr>
           		<td class="font_bold"> ${book.bookName }</td>
           		<td> ${book.publisher }</td>
           		<td><a href="javascript:editTextbook(${book.bookId })" >编辑书本</a></td>
           </tr>
         </c:forEach>
      </tbody>
     </table>
 </div>

<div>
    <%@ include file="../component/PagingBar.jsp"%>
</div>
</fmt:bundle>