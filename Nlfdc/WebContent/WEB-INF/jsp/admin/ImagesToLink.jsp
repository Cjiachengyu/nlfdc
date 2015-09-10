<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.image_box { width: 100%; height: 440px; oveflow: scroll; }
.news_image_box { width: 92%; margin: auto; margin-top: 5px; }
.roll_image_box { width: 92%; margin: auto; margin-top: 5px; }
.title { }
.all_image_box {width: 100%; height: 200px; }
.single_image_box {display: inline-block; margin: 4px; border: solid 3px white; }
.image { width: 190px; height: 190px; margin: 0; }
.operation_box {width: 95%; margin-top: 30px; }
.choosed { border: solid 3px red; }
.ok_btn { float: right; width: 200px; margin-bottom: 10px; }
</style>
<!-- 弹出框-添加班级，不需要添加title -->
<div style="width: 100%; oveflow: scroll; ">
	<div class="news_image_box">
		<span class="title">新闻图片：</span>
		<br>
		<div class="all_image_box">
			<c:forEach var="newsImage" items="${actionBean.newsImageList }">
				<div id="image_box_${newsImage.imageId }" class="single_image_box" onclick="choose(${newsImage.imageId })">
					<img src="${newsImage.imageUrl }" class="image">
				</div>
			</c:forEach>
		</div>		
	</div>	
	
	<div class="roll_image_box">
		<span class="title">滚动图片：</span>
		<br>
		<div class="all_image_box" style="height: 400px;">
			<c:forEach var="rollImage" items="${actionBean.rollImageList }">
				<div id="image_box_${rollImage.imageId }" class="single_image_box" onclick="choose(${rollImage.imageId })">
					<img src="${rollImage.imageUrl }" class="image">
				</div>
			</c:forEach>
		</div>	
	</div>
	
	<div class="operation_box">
		<a class="blue_button ok_btn" href="javascript:doLinkImage();">确定</a>
	</div>
</div>

<script>
var choosedImageId = 0;

function choose(imageId)
{
	choosedImageId = imageId;
	$(".single_image_box").removeClass("choosed");
	$("#image_box_"+imageId).addClass("choosed");
}

function doLinkImage()
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?dolinkimage=",
		data: {imageId: choosedImageId },
		success : function(result) {
			isTimeOut(result);

			if (result == "ok")
			{
				AlertDialogWithCallback("关联成功！","",function(){
					closeAllLayers();
				});
			}
			else
			{
				AlertDialogWithCallback("关联失败！","",function(){
					closeAllLayers();
				});
			}
		}
	});
	
}
</script>