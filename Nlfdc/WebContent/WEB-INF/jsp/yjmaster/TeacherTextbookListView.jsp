<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<style>
.teacher_textbooks { height: 380px; padding: 30px 40px 10px 40px; overflow-y: auto; } 
.subject_name { padding: 10px 0 5px 0; margin: 5px 0; font-weight: bold; }
.check_textbooks { float: left; width: 300px; margin: 5px 10px 5px 40px; position: relative; }
.buttons { padding: 10px; margin: 15px 50px 0px 50px; float: right; }
.big_button { width: 150px; padding: 8px; }
.clear {clear: both; }
</style>

<div class="teacher_textbooks">
	<c:forEach var="subject" items="${actionBean.userSubjectList }" varStatus="var">
		<div class="subject_name">${subject.subjectName } :</div>

       	<c:forEach var="textbook" items="${actionBean.textbooks[var.index] }" varStatus="var2">
    		<div class="check_textbooks">
    			<input id="check_textbook_${textbook.bookId }" class="check_textbook_input" type="checkbox" value="${textbook.bookId }"
    				<c:if test="${textbook.isSelected }"> checked </c:if>
    			/>
    			<label for="check_textbook_${textbook.bookId }">${textbook.bookName }</label>
    		</div>
			<c:if test="${(var2.index+1) == fn:length(actionBean.textbooks[var.index])  }">
				<div class="clear">
				</div>	
			</c:if>			
    	</c:forEach>
	</c:forEach>
</div>

<div class="buttons">
    <a class="pink_button big_button" href="javascript:save_textbooks_changes()">确定</a>
</div>
