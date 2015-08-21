<%@ page pageEncoding="utf-8" %>
<%@ include file="../component/CommonTopMobile.jsp" %>
<title>上传资源</title>

<link href="css/yjteacher/mobileYjTeacherUploadRes.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<script>
    var htmlVal = {htmlUrl: "yjteacreateasmaction"};
    
    function switchChoice()
    {
    	var shareToSchoolObj = $("#share_to_school")[0];
    	if (shareToSchoolObj.checked)
    	{
    		$("#check_image").attr("src", "image/common/check_off.png");
    		shareToSchoolObj.checked = false;
    	}
    	else
    	{
    		$("#check_image").attr("src", "image/common/check_on.png");
    		shareToSchoolObj.checked = true;
    	}
    }
</script>

<div id="main_content" >
	<div class="file_selector">
       <div class="file_name_box">
         	<span class="">文件：</span>
            <span class="high_light_color" id="file_name_value"></span>
         </div>
         <div class="file_size_box">
         	<span class="">大小：</span>
            <span class="high_light_color" id="file_size_value"></span>
         </div>
    </div>
    
    <div class="share_box">
    	<div class="share_to_school_box">
    		 <span>共享到学校： </span>
    		 <img id="check_image" src="image/common/check_on.png" onclick="switchChoice()">
    		 <input type="checkbox" id="share_to_school" class="hide" checked >
    	</div>
    </div>
    
    <div class="res_setter">
          	<select class="selector" id="schema_classify_textbook_selector" onchange="schema_classify_select_textbook(this.value);">
	  			<c:forEach var="textbook" items="${actionBean.tsClassifyModule.textbookList}">
				<option value="${textbook.bookId}" <c:if test="${textbook.isSelected == true}"> selected="selected" </c:if> >
					${textbook.bookName}
				</option>
				</c:forEach>
			</select>
			<br>

			<select class="selector" id="schema_classify_chapter_selector" onchange="schema_classify_select_chapter(this.value);">
	  			<c:forEach var="chapter" items="${actionBean.tsClassifyModule.chapterList}">
				<option value="${chapter.chapterId}" <c:if test="${chapter.isSelected == true}"> selected="selected" </c:if> >
					${chapter.chapterName}
				</option>
				</c:forEach>
			</select>
    </div>
    
    <div class="operation_box">
        	<a class="left_half_block" id="add_file_btn" >选择文件</a>
        	<a class="disable_look" id="upload_file_btn">上传资源</a>
        	<div class="clear"></div>
    </div>
</div>

<!--模块2，用于显示资源文件上传进度，初始不显示  -->
<div id="file_upload_progress" class="hide">
    <div id="progress_holder">
        <div id="progress_bar" style="width: 10%;"></div>
        <span id="progress_tip">10%</span>
    </div>

    <div class="clear"></div>

    <a id="cancle_upload_file_btn" class="pink_button">取消上传</a>
</div>

<script src="js/component/schemaClassify.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="jqueryplugin/plupload/js/plupload.full.min.js"></script>
<script src="js/yjteacher/mobileYjTeacherUploadResource.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
