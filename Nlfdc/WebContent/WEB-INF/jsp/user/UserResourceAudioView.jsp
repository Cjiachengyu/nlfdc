<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../component/CommonTopSimpleOnlyJs.jsp"%>

<input type="hidden" id="audio_type" value="${actionBean.fileType}" />
<div
	<c:if test="${actionBean.isMobile != 1 }">style="text-align: center; margin-top: 200px; "</c:if>
	<c:if test="${actionBean.isMobile == 1 }">style="text-align: center; margin-top: 50%; "</c:if>
	>
	<c:if test="${actionBean.fileUrl != ''}">
		<span class="fake_audio" audio_path="${actionBean.fileUrl}" style="max-width: 80%; " >${actionBean.resource.resName}</span>
	</c:if>
	<c:if test="${actionBean.fileUrl == ''}">
		<h2>出现错误，加载资源失败！</h2>
	</c:if>
</div>

<c:if test="${actionBean.isMobile == 1 }">

<script>
	$(function(){
		var fakeAudio = $(".fake_audio");
		var audio = "<audio src='" + $(".fake_audio").attr("audio_path") + "' controls autoplay></audio>";
		fakeAudio.html(audio);
	});
</script>
</c:if>

<c:if test="${actionBean.isMobile != 1 }">
<script>
$(function(){
	var audioType = $("#audio_type").val();
	if (audioType != "")
	{
		replaceAudioHtml(audioType, "true");
	}
});
</script>
</c:if>