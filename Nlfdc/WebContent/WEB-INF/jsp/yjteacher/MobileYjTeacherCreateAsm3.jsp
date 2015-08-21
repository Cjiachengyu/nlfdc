<%@ page pageEncoding="utf-8" %>
<%@ include file="../component/CommonTopMobile.jsp" %>

<link href="css/yjteacher/mobileYjTeacherCreateAsm.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet"/>

<script>
    var htmlVal = {htmlUrl: "yjteacreateasmaction"};
</script>

<div id="asm_detail">
        <div>
            <span class="col_1 highlight">名称：</span>
            <div class="col_2">
                <input type='text' class="input_text " id='assignment_name' maxlength="48" value=""/>
            </div>
        </div>
        <div>
            <span class="col_1 highlight">类型：</span>
            <div class="col_2">
                <div class="check_type">
                    <input type='radio' id='assignment_type_homework' value='5' name='asmType' checked='checked'/>
                    <label for='assignment_type_homework' title="需要老师使用客户端进行批改订正">独立完成</label>
                </div>
                <div class="check_type">
                    <input type='radio' id='assignment_type_assit' class='marginL_5' name='asmType' value='6'/>
                    <label for='assignment_type_assit' title="需要老师使用客户端进行批改订正">家长协助</label>
                </div>
                <div class="check_type">
                    <input type='radio' id='assignment_type_broadcast' class='marginL_5' name='asmType' value='4'/>
                    <label for='assignment_type_broadcast' title="资料不需要提交作答">亲子阅读</label>
                </div>
            </div>
        </div>
        <div>
            <span class="col_1 highlight">说明：</span>
            <div class="col_2">
                <textarea id="asm_que_content" cols="50" rows="5" class="text_area"></textarea>
            </div>
        </div>
        <div>
            <span class="col_1 highlight">发班：</span>
            <div class="col_2">
                <c:forEach var="classInfo" items="${actionBean.classesOfTeacher }">
                    <div class="check_class">
                        <input type='checkbox' id='check_class_${classInfo.clsId }' class="class_ids" value='${classInfo.clsId }' checked='checked'/>
                        <label for='check_class_${classInfo.clsId }'> ${classInfo.clsName } </label>
                    </div>
                </c:forEach>
                <div class="clear"></div>
            </div>
        </div>
        <div>
            <div id='start_time_div'>
                <span class="col_1">发布日：</span>
                <div class="col_2">
                    <input type='text' id='assignment_start_time' class='input_text ' readonly placeholder="为空则表示立即发布"/>
                </div>
            </div>
        </div>
        <div>
            <div id='finish_time_div'>
                <span class="col_1">截止日：</span>
                <div class="col_2">
                    <input type='text' id='assignment_finish_time' class='input_text ' readonly placeholder="为空则表示发布时间一周后"/>
                </div>
            </div>
        </div>
        <div>
            <div id='msg_div'>
                <span class="col_1"></span>
                <div class="col_2">
                    <span id="limit_msg" style="font-size: 12px; color: #FE7472; "></span>
                </div>
            </div>
        </div>
</div>
<div id="datePlugin" ></div>
<input type="hidden" id="create_asm_token" value="${actionBean.createAsmToken }">
<div id="creating_tip" class="creating_tip_box">
	<img style="width: 90%; margin-left: 5%;" src="image/yj/creating_asm.gif">
</div>

<div>
    <div class="container create_step">
        <div class="row">
            <a id="back_step" class="col-xs-4 step_block step_btn" href="yjteacreateasmaction?gotostep=&step=2">上一步</a>
            <span class="col-xs-4 step_block step_info">第三步：发布任务</span>
            <a id="create_assignment_btn" class="col-xs-4 step_block step_btn" href="javascript:doCreateAssignment(1);">发布任务</a>
        </div>
    </div>
</div>


<link href="jqueryplugin/datepicker/comon-datepicker.css" rel="stylesheet" />
<script type="text/javascript" src="jqueryplugin/datepicker/datepicker.js" ></script>
<script type="text/javascript" src="jqueryplugin/datepicker/iscroll.js" ></script>

<script charset="utf-8" src="js/yjteacher/yjTeacherCreateAsm3.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

