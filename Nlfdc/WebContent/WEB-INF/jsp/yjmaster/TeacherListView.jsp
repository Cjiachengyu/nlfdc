<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
                <th width="8%">名次</th>
                <th width="10%">教师</th>
                <th width="8%">活跃度</th>
                <th width="8%">资源数</th>
                <th width="8%">作业数</th>
                <th width="13%">教授科目</th>
                <th width="28%">任课班级</th>
                <th width="12%">设置</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="teacherInfo" items="${actionBean.teacherList}" varStatus="var">
                <tr height="40px">
                    <td> ${var.index + 1} </td>
                    <td> 
                        <a href="loginaction?switchtouser=&userId=${teacherInfo.userId }" > 
                        	${teacherInfo.teacherName} 
                        </a> 
                    </td>
                    <td>${teacherInfo.totalNumber}</td>
                    <td>
                        <a title="查看老师资源" href="yjmastercheckresourceaction?mastercheckresourceinfo=&teaId=${teacherInfo.userId }&subjectId=${actionBean.subjectId }">
                            ${teacherInfo.numberOfResource}
                        </a>              			
                    </td>
                    <td>
                        <a title="查看老师作业" href="yjmastercheckassignmentaction?masterteacherassignmentinfo=&teaId=${teacherInfo.userId }&subjectId=${actionBean.subjectId }">
                            ${teacherInfo.numberOfAssignment}
                        </a>
                    </td>
                    <td>${teacherInfo.subject}</td>
                    <td>${teacherInfo.classes}</td>
                    <td>
                        <a href="javascript:setClass(${teacherInfo.userId },'${teacherInfo.teacherName}')" >班级</a>&nbsp
                        <a href="javascript:setSubject(${teacherInfo.userId },'${teacherInfo.teacherName}')" >科目</a>&nbsp
                        <a href="javascript:setTextbook(${teacherInfo.userId },'${teacherInfo.teacherName}')" >课本</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</fmt:bundle>