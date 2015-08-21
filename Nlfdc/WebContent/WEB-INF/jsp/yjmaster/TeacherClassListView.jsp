<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<style>
.teacher_classes { height: 275px; padding: 30px 40px 10px 40px; overflow-y: auto; } 
.enter_year_header { padding: 10px 0 5px 0; margin: 5px 0; font-weight: bold; }
.check_class { float: left; width: 140px; margin: 5px 10px 5px 40px; position: relative; }
.buttons { padding: 10px; margin: 15px 50px 0px 50px; float: right; }
.big_button { width: 150px; padding: 8px; }
.clear {clear: both; }
</style>

<div class="teacher_classes">
	<c:forEach var="grade" items="${actionBean.schoolGradesList }" varStatus="var">
		<div class="enter_year_header">${grade.gradeString}：</div>

       	<c:forEach var="classInfo" items="${actionBean.classes[var.index] }" varStatus="var2">
    		<div class="check_class">
    			<input id="check_class_${classInfo.clsId }" class="check_class_input" type="checkbox" value="${classInfo.clsId }"
    				<c:if test="${classInfo.isSelected }"> checked </c:if>
    			/>
    			<label for="check_class_${classInfo.clsId }">${classInfo.clsName }</label>
    		</div>
			<c:if test="${(var2.index+1) == fn:length(actionBean.classes[var.index])  }">
				<div class="clear">
				</div>	
			</c:if>			
    	</c:forEach>
	</c:forEach>
</div>

<div class="buttons">
    <a class="pink_button big_button" href="javascript:save_classes_changes()">确定</a>
</div>
