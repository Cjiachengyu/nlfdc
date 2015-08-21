<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../component/CommonTopSimpleOnlyJs.jsp"%>

<link rel="stylesheet" href="jqueryplugin/audioplay/audioplay_mobile.css" />
<style>
.pink_button {display: inline-block; padding: 4px 5px 4px 5px; color: white; background: #FAB2B0; margin: 5%; text-decoration: none; min-height: 40px; min-width: 20%; text-align: center; border-radius: 5px; }
</style>

<input type="hidden" id="audio_type" value="${actionBean.fileType}" />

	<c:if test="${actionBean.fileUrl != ''}">
	<div id="wrapper" >
		<h4 style="font-size: 2.5em;">${actionBean.resource.resName}</h4>
		<audio preload="auto" controls="controls" >
    		<source src="${actionBean.fileUrl}" />
  		</audio>
		<a id="start_btn" href="javascript:start()" >
			<img id="play_btn" style=" width: 30%; height: 30%; margin:auto; margin-top: 5%; " src="image/yj/stop.jpg">
		</a>
	</div>
	
	<div id="help_info" style="text-align: center; display: none; height: 50%; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;">
            <h3 style="font-size: 2.5em;">
                	该音频不支持预览，请尝试
                	<br><br>
                	<a id="download_mobile" href="userresourceitemaction?handledownloadrequest=&isMobile=1&resId=${actionBean.resource.resId }" onclick="setDownloaded('mobile')" class="pink_button">下载音频</a>
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
     
     function start()
     {
    	 $(".audioplayer-playpause a")[0].click();
    	 $("#start_btn").attr("href", "javascript:stop();");
    	 $("#play_btn").attr("src", "image/yj/play.jpg");
     }
     
     function stop()
     {
    	 $(".audioplayer-playpause a")[0].click();
    	 $("#start_btn").attr("href", "javascript:start();");
    	 $("#play_btn").attr("src", "image/yj/stop.jpg");
     }
</script>  
