<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 

<%@ include file="../component/CommonTop.jsp"%>

<head>
<title>学校管理</title>
<style>
.main_content { width: 100%; margin-bottom: 20px; }
.local_button { width: 120px; margin: 0 20px; }
.operation_div { margin: 15px 0 10px 0; display: block;}
.provinces { width: 150px; height: 30px; padding: 3px; font-size: 14px; }
.cities { width: 150px; height: 30px; padding: 3px; font-size: 14px; }
</style>
</head>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" > 
			当前位置：园所管理
		</span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap" >
	
	<div id="content" class="clearfix">
        <div class="operation_div">
            <div class="right">
                <select id="provinces" class="provinces" onchange="refreshCity(this.value)" >
                   <option value="-1">选择省份</option>
                       <c:forEach var="province" items="${actionBean.provinceList}">
                       <option value="${province.provinceId}" <c:if test="${actionBean.provinceId == province.provinceId }">selected</c:if>  >${province.provinceName}</option>
                   </c:forEach> 
                </select>

                <input type="hidden" id="HiddencityId" value="${actionBean.cityId }">
                <select id="cities" class="cities" onchange="changeCity(this.value)" >
                      <option value="-1" selected>选择城市</option>
                </select>

	            <a class="local_button pink_button" id="add_new_school" href="javascript:addSchool();">添加园所</a>
           	 	<div class="clear"></div>
            </div>
            <div class="clear"></div>
        </div>
	
		<div class="main_content" id="main_content">
			<%@ include file="../yjadmin/YjSchoolListView.jsp"%>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script>
	var htmlVal = {
		htmlUrl : "yjadminschoollistaction",
	};
</script>

<script src="js/yjadmin/yjAdminSchoolList.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
