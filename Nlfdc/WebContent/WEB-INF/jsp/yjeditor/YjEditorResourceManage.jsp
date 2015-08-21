<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>
<title>系统资源</title>
<style>
.main_content { float: left; width: 710px; padding: 10px 20px 0px 30px; }
#resource_list { width: 100%; }
.top_operator_bar_div { padding: 10px 0; float: right; }
.upload_resource_btn {margin: 5px 10px 5px 20px;  padding: 5px 15px; border-radius: 5px; background: #2b8ccd; font-size: 14px; color: white;}
.yj_school_selector {width: 200px; height: 30px; }
</style>

<script>
var htmlVal = { 
		htmlUrl: "yjeditorresourcemanageaction",
		limitFileSize: 52428800,
		limitFileSizeString: "50M",
	};
	
</script>

<c:if test="${sessionScope.realUser.userRole == 11}"><script>
$(function(){
	judgeBrowserVersion();
});
</script></c:if>

<div class="bg_white simple_shadow">
	<div class="wrap">
       	<a <c:if test="${actionBean.showType == 5 }">class="sub_menu_button font_bold bg_light_gray highlight"</c:if>
       	   <c:if test="${actionBean.showType != 5 }">class="sub_menu_button"</c:if>
       	   href="yjeditorresourcemanageaction?switchshowtype=&showType=5">系统资源</a>
		<a <c:if test="${actionBean.showType == 6 }">class="sub_menu_button font_bold bg_light_gray highlight"</c:if>
       	   <c:if test="${actionBean.showType != 6 }">class="sub_menu_button"</c:if>
		   class="sub_menu_button" href="yjeditorresourcemanageaction?switchshowtype=&showType=6" >园本资源</a>
		<div class="clear"></div> 
	</div>
</div>

<div class="wrap">
	<div class="clearfix">
		<div class="left_bar">
			<%@ include file="../component/SchemaCatalogue.jsp"%>
		</div>

		<div class="main_content">
            <div class="top_operator_bar_div right" >
            	<c:if test="${actionBean.showType == 5 }">
                 <%@ include file="../component/UploadResourceBtn.jsp"%>
                </c:if>
            	<c:if test="${actionBean.showType == 6 }">
					<select id="yj_school_selector" class="yj_school_selector" onchange="switchSchool(this.value)">
						<c:forEach var="school" items="${actionBean.allYjSchoolList }">
							<option value="${school.schoolId }">${school.schoolName }</option>
						</c:forEach>
					</select>
	            </c:if>
            </div>

            <div class="clear"></div>

            <div id="resource_list">
                <%@ include file="../component/ResourceListView.jsp"%>
            </div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script src="js/yjeditor/yjeditorResourceManage.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
