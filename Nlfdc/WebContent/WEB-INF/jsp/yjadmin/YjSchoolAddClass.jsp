<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 

<!-- 弹出框-添加班级，不需要添加title -->
<link rel="stylesheet" type="text/css" href="css/yjadmin/yjAdminSchoolAddClass.css?jscssimgversion=${actionBean.jsCssImgVersion}">
<div class="main_box">	
	<div id="add_class_type" class="add_class_type_div relative">
        <input type="hidden" id="add_class_type_value" value="1" />
        <a id="add_class_type1" class="add_class_type_button add_class_type_selected" style="left:10px;" >单独添加班级</a>
        <a id="add_class_type2" class="add_class_type_button" style=" right:10px;" >批量添加班级</a>
    </div>
    
	<div class="clearfix">
		 <div id="main1" class="main1">
            <div id="main_content1" class="main_content1">
                <%@ include file="SchoolAddOneClass.jsp"%>
            </div>
        </div>

        <div id="main2" class="main2" style="display:none; ">
            <div id="main_content2" class="main_content2">
                <%@ include file="SchoolAddBatchClass.jsp"%>
            </div>
        </div>
	</div>
</div>

<input type="hidden" value="${actionBean.minYear }" id="minYear" >
<input type="hidden" value="${actionBean.thisYear }" id="thisYear" >

<script src="js/yjadmin/yjAdminSchoolAddClass.js?jscssimgversion=${actionBean.jsCssImgVersion}" type="text/javascript"></script>

