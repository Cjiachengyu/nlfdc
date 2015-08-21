<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
.pink_button {display: inline-block; padding: 4px 5px 4px 5px; color: white; background: #FAB2B0; font-size: 35px;margin: 5%; text-decoration: none; min-height: 40px; min-width: 20%; text-align: center; border-radius: 5px; }
.pink_button:hover { background: #FE7472; color: white; }
.disable_button {display: inline-block; padding: 4px 5px 4px 5px; color: black; background: gray; font-size: 35px;margin: 5%; text-decoration: none; min-height: 40px; min-width: 20%; text-align: center; border-radius: 5px; }
.disable_web_button {display: inline-block; padding: 4px 5px 4px 5px; color: white; background: gray; font-size: 35px;margin: 5%; text-decoration: none; min-height: 40px; min-width: 20%; text-align: center; border-radius: 5px; }
.disable_web_button:hover {color: white;}
</style>

<script src='js/jquery-1.7.1.min.js' type='text/javascript' ></script>
<script>
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

<c:if test="${actionBean.isMobile != 1 }">
<div style="text-align: center; height: 50%; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;">
	<span style="font-size: 17px; ">该资源无法预览</span>
	<br>
	<a id="download_web" class="pink_button" onclick="setDownloaded('web')" >点击下载</a>
</div>
</c:if>
	
<c:if test="${actionBean.isMobile == 1 }">
<div style="text-align: center; height: 50%; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;">
	<span style="font-size: 35px; ">该资源无法预览</span>
	<br>
	<a id="download_mobile" class="pink_button" onclick="setDownloaded('userresourceitemaction?handledownloadrequest=&isMobile=1&resId=${actionBean.resource.resId }')">点击下载</a>
</div>
</c:if>
