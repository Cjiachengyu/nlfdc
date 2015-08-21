<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../component/CommonTop.jsp"%>

<link rel="stylesheet" type="text/css" href="css/yjadmin/yjAdminEditSchool.css?jscssimgversion=${actionBean.jsCssImgVersion}">
<title>编辑学校</title>

<div class="bg_white simple_shadow">
    <div class="wrap">
        <span class="current_location"></span> 
	    <span class="pad10 inline font_small"> 当前位置：<a href="yjadminschoollistaction">园所管理</a> &gt; 编辑园所：${actionBean.editingSchool.schoolName}</span>
        <div class="clear"></div>
    </div>
</div>

<div class="wrap" >
    <div class="block_content_whole" >
        <div class="block_content_title_div" >
            <span class="block_content_title" >基本信息</span>
            <div class="clear"></div>
        </div>
        <div class="block_content_div">
            <div class="block_content_col_row" >
                <span class="block_content_col_1">名称：</span>
                <div class="block_content_col_2">
                    <input type='text' class="block_content_input_text" id="schoolName" maxlength="16" oninput="check1(this.value)" onpropertychange="check1(this.value)"
                        placeholder="不超过16个字符" value="${actionBean.editingSchool.schoolName }" /> 
                    <span id="msg1" class="none" style="display: none;"> <font size="1" color="red">*长度达到最大值</font> </span>
                </div>
            </div>
            <div class="block_content_col_row" >
                <span class="block_content_col_1">省份：</span>
                <div class="block_content_col_2">
                    <select class="block_content_input_select" id="provincesIn" onchange="getSchoolCityIn(this.value)">
                        <option value="-1">选择省份</option>
                        <c:forEach var="province" items="${actionBean.provinceList}">
                            <option value="${province.provinceId}"
                                <c:if test="${actionBean.editingSchool.provinceId == province.provinceId }">selected</c:if>
                            >${province.provinceName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="block_content_col_row" >
                <span class="block_content_col_1">城市：</span>
                <div class="block_content_col_2">
                    <input type="hidden" id="hide_cityId" value="${actionBean.editingSchool.cityId }" />
                    <select id="citiesIn" class="block_content_input_select">
                        <option value="-1">选择城市</option>
                    </select>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="block_content_btn_div" >
	        <a class="local_button pink_button" id="button" href="javascript:doUpdateSchool();">保存修改</a>
        	
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>

    <div class="block_content_whole" >
        <div class="block_content_title_div" >
            <span class="block_content_title" >学校大纲</span>
            <div class="clear"></div>
        </div>
        <div class="block_content_div">
            <div class="half_screen_div">
                <div>已添加大纲(点击移除)：</div>
                <div class="scm_block" >
                    <ul id="schemaIn">
                        <c:forEach var="schemaIn" items="${actionBean.schemaInEditingSchoolList }">
                            <li class="scm_item" ><a href="javascript:deleteSchema(${schemaIn.scmId })" title="删除大纲">${schemaIn.scmName }</a></li>
                        </c:forEach>
                    </ul>
                    <div class="clear"></div>
                </div>
            </div>

            <div class="half_screen_div">
                <div>可添加大纲(点击添加)：</div>
                <div class="scm_block" >
                    <ul id="schemaOut">
                        <c:forEach var="schemaOut" items="${actionBean.schemaOutEditingSchoolList }">
                            <li class="scm_item" ><a href="javascript:addSchema(${schemaOut.scmId })" title="添加大纲">${schemaOut.scmName }</a></li>
                        </c:forEach>
                    </ul>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="block_content_whole" >
        <div class="block_content_title_div" >
            <span class="block_content_title" >校长</span>
            <div class="clear"></div>
        </div>
        <div class="block_content_div">
            <div class="school_member_div" id="school_masters_info">
                <%@ include file="EditSchoolMastersInfoList.jsp"%>
            </div>
            <div class="clear"></div>
        </div>
        <div class="block_content_btn_div" >
	        <a class="local_button pink_button" id="add_new_master" href="javascript:addNewMaster();" >添加园长</a>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>

    <div class="block_content_whole" >
        <div class="block_content_title_div" >
            <span class="block_content_title" >老师</span>
            <div class="clear"></div>
        </div>
        <div class="block_content_div">
            <div class="school_member_div" id="school_teachers_info">
                <%@ include file="EditSchoolTeachersInfoList.jsp"%>
            </div>
            <div class="clear"></div>
        </div>
        <div class="block_content_btn_div" >
        	<c:if test="${actionBean.teachersInEditingSchoolList != null && fn:length(actionBean.teachersInEditingSchoolList) != 0}">
		        <a class="local_button pink_button" href="yjadmineditschoolaction?downuserinfoexc=&userRole=9" >下载老师Excel</a>
        	</c:if>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>

    <div class="block_content_whole" >
        <div class="block_content_title_div" >
        	<c:if test="${sessionScope.realUser.userRole == 12}"><span class="block_content_title" >家长</span></c:if>
            <div class="clear"></div>
        </div>
        <div class="block_content_div">
            <div class="school_member_div" id="school_students_info">
                <%@ include file="EditSchoolStudentsInfoList.jsp"%>
            </div>
            <div class="clear"></div>
        </div>
        <div class="block_content_btn_div" >
        	<c:if test="${actionBean.studentsInEditingSchoolList != null && fn:length(actionBean.studentsInEditingSchoolList) != 0}">
		        <a class="local_button pink_button" href="yjadmineditschoolaction?downuserinfoexc=&userRole=8" >下载家长Excel</a>
        	</c:if>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>

</div>

<script>
	var htmlVal = {	htmlUrl : "yjadmineditschoolaction", };
</script>

<script src="js/yjadmin/yjAdminEditSchool.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>

