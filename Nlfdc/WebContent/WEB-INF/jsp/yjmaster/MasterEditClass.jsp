<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>编辑班级</title>
<link rel="stylesheet" type="text/css" href="css/yjmaster/yjMasterEditClass.css?jscssimgversion=${actionBean.jsCssImgVersion}">

<input type="hidden" id="admin_editing_schoolId" value="${actionBean.schoolId }">

<div class="bg_white simple_shadow">
    <div class="wrap">
        <span class="current_location"></span> 
        <c:if test="${sessionScope.user.userRole == 10 }">
        	<span class="pad10 inline font_small" >当前位置：<a href="yjmasterclassaction">班级情况</a> &gt; 编辑班级 </span>
        </c:if>
        <c:if test="${sessionScope.user.userRole == 12 }">
        	<span class="pad10 inline font_small" >当前位置：<a href="yjadminschoollistaction">学校管理</a> &gt; <a href="yjadminclassaction?manageclass=&schoolId=${actionBean.schoolId }">班级管理 </a> &gt; 编辑班级 </span>
        </c:if>
        <div class="clear"></div>
    </div>
</div>

<div class="wrap">

    <div class="block_content_whole" >
        <div class="block_content_title_div" >
            <span class="block_content_title" >班级信息</span>
            <div class="clear"></div>
        </div>
        <div class="block_content_div">
            <div class="block_content_col_row" >
                <span class="block_content_col_1">班级名称：</span>
                <div class="block_content_col_2">
                    <input type="text" value="${actionBean.classInfo.clsName }" id="classNameText" class="block_content_input_text" maxlength=16 >
                </div>
            </div>
            <div class="block_content_col_row" >
                <span class="block_content_col_1">入学年份：</span> 
                <div class="block_content_col_2">
                    <select class="block_content_input_select" id="classEnteryear" onchange="getSchoolCityIn(this.value)">
                        <c:forEach var="num" begin="${actionBean.minYear }" end="${actionBean.thisYear }" >
                            <option value ="${num }" 
                            <c:if test="${num == actionBean.classInfo.entranceYear }"> selected</c:if> >${num } 年</option> 
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="block_content_btn_div" >
	            <a class="local_button pink_button" href="javascript:changeClassInfo()">保存修改</a>
	            <c:if test="${sessionScope.user.userRole == 12}">
    	            <a class="local_button pink_button" href="javascript:addUser(${actionBean.classInfo.clsId })">添加用户</a>
        	    </c:if>
	            <a class="local_button pink_button" href="javascript:deleteClass()">删除班级</a>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>

    <div id="class_role_type" class="class_role_type_div relative">
        <input type="hidden" id="class_role_type_value" value="1" />
        <a id="class_role_type1" class="class_role_type_button class_role_type_selected font_bold" style="color:#000000;" >老师</a>
        <a id="class_role_type2" class="class_role_type_button font_bold" style="left:100px; color:#000000; " >家长</a>
    </div>

    <div id="content" class="clearfix">	
        <div id="main1" class="main1">
            <table class="thead">
                <tr align="left">
                    <td class="titleTd font_lighter">当前班级用户</td>
                    <td class="titleTd">
                        <div class="info font_lighter">
                        	<select class="reselectClass" onchange="reselectClass1(this.value)" >
                        		<option value="0" selected>不在班级</option>
                        		<c:forEach var="classinfo" items="${actionBean.outClassInfos }">
                        			<option value="${classinfo.clsId }" <c:if test="${classinfo.clsId == outChoosedClassId1}">selected</c:if> >${classinfo.clsName }</option>
                        		</c:forEach>
                        	</select>
                        </div>
                        <div class="searchBox">
                            <input type="text" id="searchText" class="searchText" placeholder="按姓名搜索" oninput ="judgeKey(event,1)" onpropertychange="judgeKey(event,1)" >
                            <img title="输入部分或完整用户名搜索" src="image/common/search.jpg" class="searchImg" onclick="searchTeachers()">
                        </div>
                    </td>
                </tr>
            </table>
            <div id="main_content1" class="main_content1">
                <%@ include file="TeacherListViewOfClass.jsp"%>
            </div>
        </div>

        <div id="main2" class="main2">
            <table class="thead">
                <tr align="left">
                    <td class="titleTd font_lighter">当前班级用户</td>
                    <td class="titleTd">
                        <div class="info font_lighter">
                        	<select class="reselectClass" onchange="reselectClass2(this.value)" >
                        		<option value="0" selected>不在班级</option>
                        		<c:forEach var="classinfo" items="${actionBean.outClassInfos }">
                        			<option value="${classinfo.clsId }" <c:if test="${classinfo.clsId == outChoosedClassId2}">selected</c:if> >${classinfo.clsName }</option>
                        		</c:forEach>
                        	</select>
                        </div>
                        <div class="searchBox">
                            <input type="text" id="searchText2" class="searchText" placeholder="按姓名搜素" oninput ="judgeKey(event,2)" onpropertychange="judgeKey(event,2)" >
                            <img title="输入部分或完整用户名搜索" src="image/common/search.jpg" class="searchImg" onclick="searchStudents()">
                        </div>
                    </td>
                </tr>
            </table>
            <div id="main_content2" class="main_content2">
                <%@ include file="StudentListViewOfClass.jsp"%>
            </div>
        </div>
    </div>
</div>

<c:if test="${sessionScope.user.userRole == 12 }">
    <script>
    var schoolId = $("#admin_editing_schoolId").val();
    var htmlVal = { 
    		htmlUrl: "yjadmineditcls",
    		htmlOutUrl: "yjadminclassaction?manageclass=&schoolId="+schoolId,
    };
    </script>
</c:if>
<c:if test="${sessionScope.user.userRole == 10 }">
    <script>
    var htmlVal = { 
    		htmlUrl: "yjmastereditclassaction",
    		htmlOutUrl: "yjmasterclassaction",
    };
    </script>
</c:if>

<script src="js/yjmaster/yjMasterEditClass.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
