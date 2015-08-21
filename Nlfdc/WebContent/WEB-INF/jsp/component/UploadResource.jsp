<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="css/component/uploadResource.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<style>
</style>

<!-- 模块1， 用于选择资源文件，设置资源信息，  初始显示-->
<div id="main_content" >
    <div class="file_selector">
        <div id="add_file">
            <a id="add_file_btn" class="pink_button">选择文件</a>
        </div>
        
        <div id="file_info">
            <div class="col_1 margin_top10 hide" id="file_name">
                <span>文件：</span>
                <span id="file_name_value"></span>
            </div>
            <div class="col_2 margin_top10 hide" id="file_size">
                <span>大小：</span>
                <span id="file_size_value"></span>
            </div>
        </div>
    </div>
    
    <div class="res_setter">
        <div id="set_resource_info">
            <%@ include file="ClassifySelector.jsp" %>
            <div class="clear"></div>
        </div>	

        <c:if test="${sessionScope.realUser.userRole == 9 }">
	        <div id="share_to_school_and_class">
		        <%@ include file="ShareResourceToSchoolAndClass.jsp" %>
	        </div>
        </c:if>

        <div class="clear"></div>
    </div>

    <div id="upload_file">
        <a id="upload_file_btn" class="pink_button disable_look" >上传资源</a>
    </div>
</div>	

<!--模块2，用于显示资源文件上传进度，初始不显示  -->
<div id="file_upload_progress" class="hide" >
    <div id="progress_holder">
        <div id="progress_bar" style="width: 0%;"></div>
        <span id="progress_tip">0%</span>
    </div>

    <div class="clear"></div>

    <a id="cancle_upload_file_btn" class="pink_button">取消上传</a>
</div>

<script src="js/component/schemaClassify.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="jqueryplugin/plupload/js/plupload.full.min.js"></script>
<script src="js/component/uploadResource.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

