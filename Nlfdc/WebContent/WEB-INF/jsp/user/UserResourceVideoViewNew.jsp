<%@ page pageEncoding="utf-8"%>

<%@include file="../component/CommonTopSimpleOnlyJs.jsp"%>

<head>
<style>
.disable_button {display: inline-block; padding: 4px 5px 4px 5px; color: white; background: gray; font-size: 35px;margin: 5%; text-decoration: none; min-height: 40px; min-width: 20%; text-align: center; border-radius: 5px; }
.disable_web_button {display: inline-block; padding: 5px; color: white; background: gray; font-size: 17px; min-width: 80px; text-align: center; }
.download_link {text-decoration: none; }
</style>

<script type="text/javascript">
  document.createElement('video');document.createElement('audio');document.createElement('track');
</script>

<!-- 引入video.js的样式文件 -->
<link rel="stylesheet" type="text/css" href="jqueryplugin/video-js/video-js.css" />

<!-- 引入video.js的脚本文件 -->
<script src="jqueryplugin/video-js/video.js" type="text/javascript" charset="utf-8"></script>

<input type="hidden" id="isMobile" value="${actionBean.isMobile }">


<script>
videojs.options.flash.swf = "jqueryplugin/video-js/video-js.swf";

$(function(){
	var isMobile = $("#isMobile").val();
	
	var viewSize = getViewportSize();
	
	if (isMobile == 0)
	{
		var width = 940;
		var height = 640;
		var newDivWidth = Math.min(width, viewSize[0] - 100);
		var newDivHeight = Math.min(height, viewSize[1] - 60);
		
		$("#example_video_1").attr("width", newDivWidth);
		$("#example_video_1").attr("height", newDivHeight);
	}
	else
	{
		var newDivWidth = viewSize[0] - 10;
		$("#example_video_1").attr("width", newDivWidth);
		$("#example_video_1").attr("height", newDivWidth);
	}
	
	 var myPlayer = videojs('example_video_1');
	    videojs("example_video_1").ready(function(){
	        var myPlayer = this;
	        myPlayer.play();
	 });

});


function setDownloaded(type)
{
	if (type == "web")
	{
		window.location.href = "userresourceitemaction?handledownloadrequest";
		$("#download_web").attr("onclick", "javascript:void(0)");
		$("#download_web").attr("class", "disable_web_button");
	}
	else
	{
		window.location.href = type;
		$("#download_mobile").attr("onclick", "javascript:void(0)");
		$("#download_mobile").attr("class", "disable_button");
	}
}
</script>


</head>
    <video id="example_video_1" class="video-js vjs-default-skin" controls autoplay="true"  data-setup='{"example_option":true}'
    	<c:if test="${actionBean.isMobile == 1 }">style="position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;"	</c:if>
    	<c:if test="${actionBean.isMobile != 1 }">style="margin: auto;"	</c:if>
    >
        <!-- HTML5 <video> sources -->
        <source src="${actionBean.fileUrl}" type='video/mp4'/>
 		<source src="${actionBean.fileUrl}" type='video/webm'/>
        <source src="${actionBean.fileUrl}" type='video/ogg'/>
       	
        <!-- HTML-Fallback - Download Links -->
        <div  style="text-align: center">
            <h3 style="font-size: 2.5em;">
                	<c:if test="${actionBean.isMobile != 1 }">
                	该视频不支持预览，请尝试升级浏览器
                	<br><br>
                	或
                	<br><br>
                		<a id="download_web" onclick="setDownloaded('web')" class="download_link">下载视频</a>
                	</c:if>
                	<c:if test="${actionBean.isMobile == 1 }">
                	该视频不支持预览，请尝试
                	<br><br>
                		<a id="download_mobile" onclick="setDownloaded('userresourceitemaction?handledownloadrequest=&isMobile=1&resId=${actionBean.resource.resId }'" class="download_link">下载视频</a>
                	</c:if>
            </h3>
        </div>
    </video>
