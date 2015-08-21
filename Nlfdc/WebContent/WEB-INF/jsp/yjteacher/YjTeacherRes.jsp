<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>
<title>我的资源</title>

<style>
.main_content {float: left; width: 710px; padding: 20px 0 0 30px; }
#resource_list { width: 100%; }
.top_operator_bar_div { padding: 10px 0; float: right; }
.que_oper_btn_with_icon { margin: 0 8px; padding-left: 38px; height: 38px; float: right; }
.operation_div { width: 100%; height: 40px; margin-bottom: 20px; border-bottom: solid 2px #E0E0E0;  }
</style>

<script>
var htmlVal = { 
		htmlUrl: "yjteares", 
		limitFileSize: 20971520,
		limitFileSizeString: "20M",
	};
</script>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" > 当前位置：我的资源 </span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div class="clearfix">
		<div class="left_bar">
			<%@ include file="../component/SchemaCatalogue.jsp"%>
		</div>

		<div class="main_content">
			
			<div class="operation_div relative">
				
        		<div class="right upload_res_box" >
	        		<%@ include file="../component/UploadResourceBtn.jsp"%>
        		</div>	
			</div>
			
            <div id="resource_list">
                <%@ include file="../component/ResourceListView.jsp"%>
            </div>
            
		</div>
		<div class="clear"></div>
	</div>

</div>

<input type="hidden" id="is_yj_teacher" value="1">

<script src="js/yjteacher/yjTeacherRes.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>

