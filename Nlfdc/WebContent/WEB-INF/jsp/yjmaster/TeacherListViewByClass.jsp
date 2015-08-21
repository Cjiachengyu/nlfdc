<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 被包含页面，不需要设置title -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style type="text/css">
#teacher_list { width: 100%; }
</style>

<div id="teacher_list">
    <table class="light_pink_table" >
        <thead>
            <tr>
                <th width="10%">年级</th>
                <th width="10%">班级</th>
                <th width="10%">教师</th>
                <th width="10%">均分</th>
                <th width="10%">作业数</th>
                <th width="10%">批改率</th>
                <th width="10%">资源数</th>
                <th width="10%">教授科目</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="grade" items="${actionBean.schoolGradesList }" varStatus="var">
                <c:forEach var="teacherClassInfo" items="${actionBean.teacherInfoInClassListArray[var.index] }" varStatus="var2">
                    <tr height="40px">
                        <c:if test="${var2.index == 0}">
                            <td style="background: #eee;" rowSpan="${fn:length(actionBean.teacherInfoInClassListArray[var.index]) }" >
                            	${grade.gradeString } 
                            </td>
                        </c:if>

                        <td>
                            ${teacherClassInfo.classInfo.clsName}
                        </td>
                        <td> 
                            <a href="loginaction?switchtouser=&userId=${teacherClassInfo.teacherInfo.userId }" > 
                            	${teacherClassInfo.teacherInfo.teacherName} 
                            </a> 
                        </td>
                        <td>
                            ${teacherClassInfo.teacherInfo.avrClassAssignmentAvrScore}
                        </td>
                        <td>
                            <a title="查看老师作业" href="yjmastercheckassignmentaction?masterteacherassignmentinfo=&teaId=${teacherClassInfo.teacherInfo.userId }&subjectId=${actionBean.subjectId }">
                                ${teacherClassInfo.teacherInfo.numberOfAssignment}
                            </a>
                        </td>
                        <td>
                            <c:if test="${teacherClassInfo.teacherInfo.avrClassAssignmentCorrectedRatio == 0.0}">0%</c:if>
                            <c:if test="${teacherClassInfo.teacherInfo.avrClassAssignmentCorrectedRatio == 100.0}">100%</c:if>
                            <c:if test="${teacherClassInfo.teacherInfo.avrClassAssignmentCorrectedRatio != 0.0 && teacherClassInfo.teacherInfo.avrClassAssignmentCorrectedRatio != 100.0}">
                                ${teacherClassInfo.teacherInfo.avrClassAssignmentCorrectedRatio }%
                            </c:if>
                        </td>
                        <td>
                            <a title="查看老师资源" href="yjmastercheckresourceaction?mastercheckresourceinfo=&teaId=${teacherClassInfo.teacherInfo.userId }&subjectId=${actionBean.subjectId }">
                            	${teacherClassInfo.teacherInfo.numberOfResource}
                            </a>              			
                        </td>
                        <td>${teacherClassInfo.teacherInfo.subject}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</div>

</fmt:bundle>