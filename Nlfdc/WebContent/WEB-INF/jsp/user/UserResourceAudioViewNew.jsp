<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../component/CommonTopSimpleOnlyJs.jsp"%>

<link rel="stylesheet" href="jqueryplugin/audioplay/audioplay.css" />
<style>
.pink_button {display: inline-block; padding: 4px 5px 4px 5px; color: white; background: #FAB2B0; margin: 5%; text-decoration: none; min-height: 30px; min-width: 20%; text-align: center; border-radius: 5px; }
.pink_button:hover { background: #FE7472; color: white; }
</style>

<input type="hidden" id="audio_type" value="${actionBean.fileType}" />

	<c:if test="${actionBean.fileUrl != ''}">
		<div id="wrapper">
			<audio preload="auto" controls="controls" >
	    		<source src="${actionBean.fileUrl}" />
	  		</audio>
		</div>
		
		<div id="help_info" style="display: none; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto; height: 50%; text-align: center;">
	            <h3 style="font-size: 20px;">
	                	该音频不支持预览，请尝试升级浏览器
	                	<br><br>
	                	或
	                	<br><br>
	                    <a id="download_web" href="userresourceitemaction?handledownloadrequest" onclick="setDownloaded('web')" class="pink_button">下载音频</a>
	            </h3>
	    </div>
	</c:if>
	<c:if test="${actionBean.fileUrl == ''}">
		<h2>出现错误，加载资源失败！</h2>
	</c:if>

<script src="jqueryplugin/audioplay/main.js"></script>
<script src="jqueryplugin/audioplay/audioplayer.js"></script>
<script type="text/javascript">
     $(function()
     {
    	 try{
          	$('audio').audioPlayer();
           	$(".audioplayer-playpause a")[0].click();
    	 }
    	 catch(e)
    	 {
    		 $("#wrapper").hide();
    		 $("#help_info").show();
    	 }
     });
     
</script>  
