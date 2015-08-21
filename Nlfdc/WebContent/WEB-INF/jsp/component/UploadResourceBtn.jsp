<%@ page pageEncoding="utf-8" %> 

<script type="text/javascript">
 function getUploadResourcePage(){
	createBorderMaskLayer("upload_resource_div", "上传资源", getLoading(), 700, 480);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getuploadresourcepage=",
		success : function(result) {
			isTimeOut(result);

			$("#upload_resource_div").html(result);
		}
	});
}
</script>

<a class="blue_button" href="javascript:getUploadResourcePage()">上传资源</a>