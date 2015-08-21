<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>创建任务3</title>

<link href="css/yjteacher/yjTeacherCreateAsm3.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div class="bg_white simple_shadow create_step_bg">
	<div class="wrap">
		<div class="create_step create_step_2" >
			<a id="web_back_step" class="create_step_btn create_step_btn_prev left" href="yjteacreateasmaction?gotostep=&step=2" ></a>
			<a class="create_step_btn create_step_btn_next_disable right"></a>
		</div>
	</div>
</div>
<div class="wrap" >
	<div id="asm_detail" class="content simple_content_bg relative clearfix">
        	<!-- 没考虑重发任务功能 -->
        	<div>
	        	<span class="col_1 highlight" > 任务名称 ： </span>
	        	<div class="col_2" >
	                <input type='text' class="input_text " id='assignment_name' name='asmName' maxlength="48" value=""/>
	        	</div>
        	</div>
            <div>
                <span class="col_1 highlight" > 任务类型 ： </span>
                <div class="col_2" >
		        	<div class="check_type" >
	                    <input type='radio' id='assignment_type_homework' name='asmType' value='5' checked='checked' />
	                    <label for='assignment_type_homework' title="需要老师使用客户端进行批改订正" >独立完成</label>
		        	</div>
		        	<div class="check_type" >
	                    <input type='radio' id='assignment_type_assit' class='marginL_5' name='asmType' value='6' />
	                    <label for='assignment_type_assit' title="需要老师使用客户端进行批改订正" >家长协助</label>
		        	</div>
		        	<div class="check_type" >
	                    <input type='radio' id='assignment_type_broadcast' class='marginL_5' name='asmType' value='4' />
	                    <label for='assignment_type_broadcast' title="资料不需要提交作答" >亲子阅读</label>
		        	</div>
                </div>
            </div>
            <div>
                <span class="col_1 highlight" > 任务说明 ： </span>
                <div class="col_2" >
		        	<textarea id="asm_que_content" cols="50" rows="5" class="text_area" ></textarea>
                </div>
            </div>
        	<div>
	        	<span class="col_1 highlight" > 发布班级： </span>
	        	<div class="col_2" >
		        	<c:forEach var="classInfo" items="${actionBean.classesOfTeacher }">
		        		<div class="check_class" >
		                    <input type='checkbox' id='check_class_${classInfo.clsId }' value="${classInfo.clsId }" class="class_ids" checked='checked'/>
		                    <label for='check_class_${classInfo.clsId }'> ${classInfo.clsName } </label>
			        	</div>
		        	</c:forEach>
		        	<div class="clear" ></div>
	        	</div>
        	</div>
        	<div>
            <div id='start_time_div' >
                 <span class="more_col_1" > 发布日 ： </span>
                 <div class="more_col_2" >
                     <input type='text' id='assignment_start_time' class='input_text' readonly placeholder="为空则表示立即发布"/>
                 </div>
            </div>
            <div id='finish_time_div' >
                <span class="more_col_1" > 截止日 ： </span>
                <div class="more_col_2" >
	               <input type='text' id='assignment_finish_time' class='input_text' readonly placeholder="为空则表示发布时间一周后"/>
                </div>
            </div>
            <div>
            	<span class="more_col_1" ></span>
                <div class="more_col_2" >
					<a id="create_assignment_btn" class="pink_button" style="width: 290px; " href="javascript:doCreateAssignment(0);"> 发布任务 </a>
                </div>
            </div>
                
        	</div>
		<div class="clear" ></div>
	
	</div>

	<div id="creating_tip" class="creating_tip_box">
		<img style="margin-top: 10%;" src="image/yj/creating_asm.gif">
	</div>
</div>
<input type="hidden" id="create_asm_token" value="${actionBean.createAsmToken }">

<script>
   	var htmlVal = { htmlUrl: "yjteacreateasmaction", };
</script>   

<!-- 时间选择器 -->
<link rel="stylesheet" type="text/css" href="jqueryplugin/web-timepicker/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="jqueryplugin/web-timepicker/css/timepicker.css" />
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui.js"></script>
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui-slide.min.js"></script>
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui-timepicker-addon.js"></script>

<script charset="utf-8" src="js/yjteacher/yjTeacherCreateAsm3.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
