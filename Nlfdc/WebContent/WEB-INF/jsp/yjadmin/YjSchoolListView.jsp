<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style>
#school_list { width: 100%; }
a { text-decoration: none; color: #2b8ccd; }
</style>

<div id="school_list">
  <table class="light_pink_table">
      <thead >
         <tr>
             <th width="35%">园所名称</th>
             <th width="12%">班级数</th>
             <th width="12%">老师数</th>
             <th width="12%">家长数</th>
             <th width="29%" colSpan="4">操作</th>
         </tr>
      </thead>
  
      <tbody>
         <c:forEach var="schoolInfo" items="${actionBean.schoolInfoList }" varStatus="var">
           <tr>
           		<td class="font_bold"> ${schoolInfo.schoolName }</td>
           		<td> ${schoolInfo.numOfClasses }</td>
           		<td> ${schoolInfo.numOfTeachers }</td>
           		<td> ${schoolInfo.numOfStudents }</td>
           		<td><a href="yjadminclassaction?manageclass=&schoolId=${schoolInfo.schoolId }">管理班级</a></td>
           		<td><a href="yjadmineditschoolaction?editschool=&schoolId=${schoolInfo.schoolId }">编辑园所</a></td>
           </tr>
         </c:forEach>
      </tbody>
     </table>
 </div>

</fmt:bundle>