<%@ page pageEncoding="utf-8"%>

<%@include file="../component/CommonTopSimpleOnlyJs.jsp"%>

<input type="hidden" id="vu_value" value="${actionBean.fileUrl}" />
<input type="hidden" id="video_type" value="${actionBean.fileType}" />

<div style="text-align: center;">
    <div id="vdiv" style="margin: auto;"></div>
</div>

<script src="jqueryplugin/ckplayer/ckplayer.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script>
var vu = $("#vu_value").val();
var videoType = $("#video_type").val();

var flashvars={
		f:vu,
		c:0,
		b:1,
		p:'1',
		l:'image/common/progress.gif',
		t:'1'
		};
var video=[vu + '->video/' + videoType];

var viewSize = getViewportSize();
var width = 940;
var height = 640;
var newDivWidth = Math.min(width, viewSize[0] - 120);
var newDivHeight = Math.min(height, viewSize[1] - 80);

try{
	CKobject.embed('jqueryplugin/ckplayer/ckplayer.swf','vdiv','ckplayer_vdiv', newDivWidth, newDivHeight, false, flashvars, video)
}
catch(e)
{
	AlertDialog("播放器不支持该视频格式！");	
}
</script>
<%@include file="../component/CommonBottomSimple.jsp"%>

