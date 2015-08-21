<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 被包含页面，不需要添加title  -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style type="text/css">
#class_list { width: 100%; } 
.teacher_name {display: inline-block; padding: 0 2px;}
</style>

<div id="class_list">
    <table class="light_pink_table" >
        <thead>
            <tr>
                <th width="8%" >年级</th>
                <th width="15%" >班级</th>
                <th width="10%" >家长人数</th>
                <th width="10%" >作业数</th>
                <th width="10%" >完成率</th>
                <th width="10%" >批改率</th>
                <th >任课教师</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="grade" items="${actionBean.schoolGradesList }" varStatus="varStatus1">
                <c:forEach var="classInfo" items="${actionBean.classInfoList[varStatus1.index] }" varStatus="varStatus2">
                    <tr height="40px">
                        <c:if test="${varStatus2.index == 0}">
                            <td style="background: #eee;" rowSpan="${fn:length(actionBean.classInfoList[varStatus1.index]) }" >
								${grade.gradeString }
                            </td>
                        </c:if>

                        <td>
                            <a href="yjmastereditclassaction?editclass=&clsId=${classInfo.clsId }" >
                                ${classInfo.clsName }
                            </a>  
                        </td>
                        <td>${classInfo.numberOfStudents }</td>
                        <td>
                            <a title="查看班级作业" href="yjmastercheckassignmentaction?masterteacherassignmentinfo=&clsId=${classInfo.clsId }&subjectId=${actionBean.subjectId }" >
	                            ${classInfo.numberOfClassAssignment }
                            </a>
                        </td>
                        <td>
                            <c:if test="${classInfo.avrClassAssignmentSubmittedRatio == 0.0}">0%</c:if>
                            <c:if test="${classInfo.avrClassAssignmentSubmittedRatio == 100.0}">100%</c:if>
                            <c:if test="${classInfo.avrClassAssignmentSubmittedRatio != 0.0 && classInfo.avrClassAssignmentSubmittedRatio != 100.0}">
                                ${classInfo.avrClassAssignmentSubmittedRatio}%
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${classInfo.avrClassAssignmentCorrectedRatio == 0.0}">0%</c:if>
                            <c:if test="${classInfo.avrClassAssignmentCorrectedRatio == 100.0}">100%</c:if>
                            <c:if test="${classInfo.avrClassAssignmentCorrectedRatio != 0.0 && classInfo.avrClassAssignmentCorrectedRatio != 100.0}">
                                ${classInfo.avrClassAssignmentCorrectedRatio}%
                            </c:if>
                        </td>
                        <td>
                            <c:forEach var="teacher" items="${classInfo.classTeachers }">
                                <a class="teacher_name" href="loginaction?switchtouser=&userId=${teacher.userId }">${teacher.userName}</a>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</div>

</fmt:bundle>