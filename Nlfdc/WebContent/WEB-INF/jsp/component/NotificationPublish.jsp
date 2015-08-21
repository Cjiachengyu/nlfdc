<%@ page pageEncoding="utf-8" %> 


<link href="jqueryplugin/umeditor/themes/default/css/umeditor.min.css?jscssimgversion=${actionBean.jsCssImgVersion}" type="text/css" rel="stylesheet">
<link href="css/component/notificationPublish.css?jscssimgversion=${actionBean.jsCssImgVersion}" type="text/css" rel="stylesheet">


<div id="content_editor_bar_append" style="display:none;" >
        <form id='content_image_form' enctype='multipart/form-data' method='post' target='iframe' class="relative right" >
        <a id='content_edit_insert_image' class='simple_shine insert_file' href='##'>插入图片</a>
        <input type='file' id='content_image' class="input_file" name='image' onchange='htmlFn.uploadImage("content")' /> </form>
</div>

<div id="content" >
		<div class="notify_target">
			<c:if test="${sessionScope.realUser.userRole == 9}">
				<span class="notification_tip1">通知班级：</span>
				<div class="check_type hide" id="check_type_4" >
		        	<input type='radio' id='notify_target_4' name='notify_target_type' value='4' checked/>
		        	<label for='notify_target_4' class="check_label font14" >部分班级</label>
				</div>
				
				<div id="class_list" class="tea_class_list" >
			    	<c:forEach var="classInfo" items="${actionBean.classesToChoose }">
			    		<div class="check_class mar5" >
			    			<input type='checkbox' id='check_class_${classInfo.clsId }' value="${classInfo.clsId }" class="class_ids" />
			                <label for='check_class_${classInfo.clsId }' class="font14"> ${classInfo.clsName } </label>
				        </div>
			        </c:forEach>
			        <div class="clear" ></div>
		       	</div>
			</c:if>
			<c:if test="${sessionScope.realUser.userRole == 10}">
			<span class="notification_tip1">通知类型：</span>
				<div class="check_type_choosed" id="check_type_1" onclick="htmlFn.setChoosed(1)" >
		        	<input type='radio' id='notify_target_1' name='notify_target_type' value='1' checked='checked' />
		        	<label for='notify_target_1' class="check_label font14" >全园</label>
				</div>
			    <div class="check_type" id="check_type_2" onclick="htmlFn.setChoosed(2)">
		        	<input type='radio' id='notify_target_2' name='notify_target_type' value='2' />
		        	<label for='notify_target_2' class="check_label font14" >全体老师</label>
				</div>
			    <div class="check_type" id="check_type_3" onclick="htmlFn.setChoosed(3)">
		        	<input type='radio' id='notify_target_3' name='notify_target_type' value='3' />
		        	<label for='notify_target_3' class="check_label font14" >全体家长</label>
				</div>
				<div class="check_type" id="check_type_4" onclick="htmlFn.setChoosed(4)">
		        	<input type='radio' id='notify_target_4' name='notify_target_type' value='4' />
		        	<label for='notify_target_4' class="check_label font14" >部分班级</label>
				</div>
				
				<div id="class_list" class="class_list" >
			    	<c:forEach var="classInfo" items="${actionBean.classesToChoose }">
			    		<div class="check_class mar5" >
			    			<input type='checkbox' id='check_class_${classInfo.clsId }' value="${classInfo.clsId }" class="class_ids" />
			                <label for='check_class_${classInfo.clsId }' class="font14"> ${classInfo.clsName } </label>
				        </div>
			        </c:forEach>
			        <div class="clear" ></div>
		       	</div>
			</c:if>
		</div>
		
		<div class="notification">
			<div class="notification_title_box">
				<span class="notification_tip">标题：</span>
				<input type="text" id="notification_title" class="notification_title" maxLength="128">
			</div>
			<div class="notification_content_box">
				<span class="notification_tip">内容：</span>
				<div class="notification_content" id="notification_content">
					<script type="text/plain" contentEditable="true" id="content_edit_div" class="content_box" ></script>
				</div>
			</div>
		</div>
		
		<div class="operation_div">
	        <a class="right pink_button" id="publish_notification_btn" href="javascript:htmlFn.doPublishNotification();">发布通知</a>
	        <div class="clear"></div>
		</div>
</div>


<script src="jqueryplugin/umeditor/umeditor.config.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="jqueryplugin/umeditor/umeditor.min.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="jqueryplugin/umeditor/lang/zh-cn/zh-cn.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<script src="js/common/jquery.form.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<script src="js/component/notificationPublish.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

