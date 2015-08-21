<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 被包含页面，不需要设置title -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style type="text/css">
#students_of_class { width: 100%; } 
.students { width: 100% ; min-height: 600px; }
.students_of_class { width: 100%;} 
.students_of_class tr { border: solid 1px #e5e5e5; }
.students_of_class tr td {  text-align: left; font-size: 12px; }
</style>

<div id="students_of_class">
    <table class="students">
        <tbody>
            <tr>
                <td class="bodyTd">
                    <div class="in_div" >
                        <table class="table">
                        	<thead>
	                			<tr>
	                				<td class="contentTd font_head" >姓名</td>
	                				<td class="contentTd font_head" >学号</td>
	                				<td class="contentTd font_head" >登录名</td>
	                				<td class="contentTd font_head" >操作</td>
	                			</tr>
                			</thead>
                            <c:forEach var="userClass" items="${actionBean.studentsIn }">
                                <tr>
                                    <td class="contentTd font_bold">${userClass.userName }</td>
                                    <td class="contentTd font_lighter">${userClass.loginId }</td>
                                    <td class="contentTd font_lighter">${userClass.loginName }</td>
                                    <td class="contentTd font_lighter" >
                                    	<a href="javascript:resetPass(${userClass.userId })" class="reset_pass" >重置密码</a>
                                    	<a href="javascript:moveOut(${userClass.userId })">移除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </td> 
                <td class="bodyTd" >
                    <div class="out_div">
                        <table class="table">
                        	<thead>
	                			<tr>
	                				<td class="contentTd font_head" >姓名</td>
	                				<td class="contentTd font_head" >学号</td>
	                				<td class="contentTd font_head" >登录名</td>
	                				<td class="contentTd font_head" >操作</td>
	                			</tr>
                			</thead>
                            <c:forEach var="userClass" items="${actionBean.studentsOut }">
                                <tr>
                                    <td class="contentTd2 font_bold">${userClass.userName }</td>
                                    <td class="contentTd2 font_lighter">${userClass.loginId }</td>
                                    <td class="contentTd2 font_lighter">${userClass.loginName }</td>
                                   
                                    <td class="contentTd2 font_lighter last_operativ" >
                                    	<a href="javascript:resetPass(${userClass.userId })" class="reset_pass" >重置密码</a>
                                    	<a href="javascript:moveIn(${userClass.userId },0)">加入</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </td> 
            </tr>
        </tbody>
    </table>
</div>

</fmt:bundle>
