<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonAdminTop.jsp" %>

<title>常见问题</title>

<style>
.input_file { position: absolute; top: 0; left: 0; width: 158px; height: 32px; filter: alpha(opacity:0); opacity: 0;  }
.tab_page { width: 100%; margin-bottom: 20px; }
.image_box {width: 80%; margin: auto; margin-top: 30px; padding-bottpm: 5px; text-align: center; }
.news_and_roll_image { min-width: 400px; min-height: 400px; max-width: 90%; }
.file_choose_div {width: 158px; margin: auto; margin-top: 20px; }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<a class="sub_menu_button font_bold bg_light_gray highlight" id="tab_title1" href="javascript:showTab(1)">新闻图片</a>
		<a class="sub_menu_button black" id="tab_title2" href="javascript:showTab(2)" >滚动图片</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap" >
	<div id="content" class="clearfix">
		<div class="tab_page" id="tab1" >
	         <c:forEach var="newsImage" items="${actionBean.newsImageList }" varStatus="var">
				<div class="image_box">
					<div class="image">
						<img id="image_${newsImage.imageId }" class="news_and_roll_image"
							 src="${newsImage.imageUrl }">
					</div>
					
					<div class="file_choose_div">
	          			<form id="submitImage_${newsImage.imageId }" enctype='multipart/form-data' method='post' class="relative" >
	                   		<input class="blue_button" style="width: 158px; " type='button' value='更换新闻图片${var.index + 1 }' />
	                   		<input class="input_file" type="file" id="image_file_${newsImage.imageId }" name="imageFile" 
	                   		onchange="checkImage(this.value, ${newsImage.imageId })"/>
			  			</form>
          			</div>
				</div>
					         	
	         </c:forEach>
		</div>
		
		<div class="tab_page" id="tab2" style="display:none;">
	         <c:forEach var="rollImage" items="${actionBean.rollImageList }" varStatus="var">
				<div class="image_box">
					<div class="image">
						<img id="image_${rollImage.imageId }" class="news_and_roll_image"
							src="${rollImage.imageUrl }" >
					</div>
					
					<div class="file_choose_div">
	          			<form id="submitImage_${rollImage.imageId }" enctype='multipart/form-data' method='post' class="relative" >
	                   		<input class="blue_button" style="width: 158px; " type='button' value='更换滚动图片${var.index + 1 }' />
	                   		<input class="input_file" type="file" id="image_file_${rollImage.imageId }" name="imageFile" 
	                   		onchange="checkImage(this.value, ${rollImage.imageId })"/>
			  			</form>
          			</div>
				</div>
					         	
	         </c:forEach>
	    </div>
	</div>
</div>

<script src="js/common/jquery.form.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script type="text/javascript">
var htmlVal = {
    htmlUrl: "adminimagemanageaction"
};

var isUploading = false;
//初始化为1， 第一块tab显示；
var index = 1; 
function showTab(i)
{
	if(index ==  i)
	{
		return false;
	}
	else
	{
		index = i;
		$(".sub_menu_button").attr("class","sub_menu_button black");
		$("#tab_title"+i).attr("class","sub_menu_button font_bold bg_light_gray highlight");
		$(".tab_page").hide();
		$("#tab"+i).show();
	}
}

function checkImage(filePath, imageId) {
	//$("#file_text_field").val(filePath);
	if (isUploading == true)
	{
		AlertDialog("上一张图片正在上传,请等待完成后再操作","");
		return false;
	}
	
	var image = $("#image_file_"+imageId).val();

	if (image !== "") {
		var array = image.split(".");
		var type = array[array.length - 1];
		type = type.toLowerCase();
		if (type != "jpg" && type != "png" && type != "gif") {
			AlertDialog("文件只能是 .jpg/.png/.gif");
		//	$("#file_text_field").val("");
		}
		else
		{
			var form = $("#submitImage_"+imageId);

			var options = {
				url : htmlVal.htmlUrl + '?uploadimage=',
				type : 'post',
				success : function(result) {
					isTimeOut(result);

					if (result == "ok")
					{
						updateImage(imageId);
					}
					else if (result == "error")
					{
						AlertDialogWithCallback("图片上传出现错误，请重新选择文件！","",function(){
							form[0].reset();
						});
					}
				}
			};
			form.ajaxSubmit(options);
			
			isUploading = true; 
		}
	}
}

function updateImage(imageId)
{
	var form = $("#submitImage_"+imageId);
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?updateimage=",
	    data : { imageId: imageId }, 
		success : function(result) {
			isTimeOut(result);
			
			isUploading = false;
			
			if (result == "error") {
				AlertDialogWithCallback("更新图片出现错误，请重新选择文件！","",function(){
					form[0].reset();
				});
			}
			else
			{
				$("#image_"+imageId).attr("src",result);
			}
		}
	});
}

</script>


<%@ include file="../component/CommonBottom.jsp"%>