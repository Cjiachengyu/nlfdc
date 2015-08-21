<!DOCTYPE html>
<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../component/CommonTop.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设置学校</title>

<link rel="stylesheet" type="text/css" href="css/yjmaster/yjMasterEditSchool.css?jscssimgversion=${actionBean.jsCssImgVersion}"></link>

<div class="bg_white shadow">
	<div class="wrap">
		<a class="sub_menu_button font_bold bg_light_gray highlight" >设置学校</a>
		<a class="sub_menu_button " href="yjmastersettingaction?subMenuType=2" >修改密码</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="clearfix">
		<div class="mar20 pad20">
			<div>
				<span class="col_1"> 学校名称 ： </span>
				<div class="col_2">
					<input type="hidden" id="old_school_name" value="${sessionScope.school.schoolName }">
					<input type='text' class="input_text " id='school_name' maxlength="30" value='${sessionScope.school.schoolName }' />
				</div>
				<a class="col_3 pink_button" href="javascript:changeSchoolName();"> 修改 </a>
			</div>
			<div>
				<span class="col_1"> 校训 ： </span>
				<div class="col_2">
					<input type="hidden" id="old_school_motto" value="${sessionScope.school.schoolMotto }">
					<input type='text' class="input_text " id='school_motto' maxlength="32" value='${sessionScope.school.schoolMotto }' />
				</div>
				<a class="col_3 pink_button" href="javascript:changeSchoolMotto();"> 修改 </a>
			</div>
			<div>
			    <span class="col_1"> 校徽 ： </span>
			    <div class="col_2">
			   
					<form id="submitSchoolIconForm" enctype='multipart/form-data' method='post' class="relative" >
                        <input class="file_path" type='text' id='file_text_field' value='请选择图片...' />
                        <input class="choose_file_btn" type='button' value='浏览图片' />
                        <input class="input_file" type="file" id="schoolIconSrc" name="schoolIcon" onchange="checkSchoolIconImg(this.value)"/>
					</form>
				
				</div>
				<a class="col_3 pink_button" href="javascript:changeSchoolIcon();"> 修改 </a>
			</div>
			
			<div class="clear"></div>
		</div>
	</div>
</div>

<script src="js/common/jquery.form.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="js/yjmaster/yjMasterEditSchool.js?jscssimgversion=${actionBean.jsCssImgVersion}" ></script>

<%@ include file="../component/CommonBottom.jsp"%>
