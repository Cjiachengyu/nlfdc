<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 包含页面，不需要添加title -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style type="text/css">
#class_list { width: 100%; } 
.class_items{ width: 100%; } 
a { text-decoration: none; color: #2b8ccd; }
.hide a{ display: none; }
.show a{ display: block; background: #3598db; color:#ffffff; text-align: center;border-radius: 5px; height:23px; padding-top: 5px; }
</style>

<div id="class_list">
    <div class="class_items">
			<table class="light_pink_table" >
            <thead class="thead">
                <tr>
                    <th width="20%">班级</th>
                    <th width="20%">家长数</th>
                    <th width="20%">老师数</th>
                    <th width="40%" colSpan="2" >操作</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="grade" items="${actionBean.schoolGradesList }" varStatus="var">
                    <c:forEach var="classInfo" items="${actionBean.classInfoList[var.index] }" varStatus="var2">
						<tr height="40px" >
                            <td>${classInfo.clsName }</td>
                            <td>${classInfo.numberOfStudents }</td>
                            <td>${classInfo.numberOfTeachers }</td>
                            <td><a href="javascript:adminEditClass(${classInfo.clsId }, ${actionBean.editingSchool.schoolId })">管理该班级</a></td>
                            <td><a href="javascript:checkUserInfos(${classInfo.clsId })">查看班级用户信息</a> </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</fmt:bundle>
