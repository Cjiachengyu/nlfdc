<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<title>作业详情</title>
 <link href="css/yjteacher/yjAsmClassCompletion.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<!-- 班级列表 -->
<div id="class_header" style="width: 220px; height: 550px; margin-right: 20px; margin-top: 0px; border: solid 1px lightgray; " class="left bg_white yj_book_bg">
	<div style="padding: 20px 0px; ">
		<span class="pad10 font_bold" >已发布班级：</span>
	</div>
	<c:forEach var="classAssign" items="${actionBean.assignedClassAssignmentList }">
		<div style="padding: 12px;" 
		<c:if test="${actionBean.currentClassAssignment.clsId == classAssign.clsId }"> class="selected_class" </c:if>
		>
			<a href="javascript:selectClass(${classAssign.clsId })">${classAssign.clsName }</a>
		</div>
	</c:forEach>
	<div class="clear"></div>
</div>

<div class="bg_white" style="width: 758px; margin: 0 0 20px 240px; border: solid 1px lightgray;  ">
		
		<c:if test="${actionBean.currentClassAssignment.asmType != 4 && actionBean.currentClassAssignment.uncorrectedList != null && fn:length(actionBean.currentClassAssignment.uncorrectedList) != 0}"> 
			<div class="operate_div">
				<a class="blue_button right mar10" href="javascript:correctStudentAsmByBatch()" >批量批改</a>
				<div class="clear"></div>
			</div>
		</c:if>
			
		<div id="status_4" class="student_asm_box">
				
				<!-- 不是资料类型 -->
				<c:if test="${actionBean.currentAsm.asmType != 4 }">
					
					<c:if test="${actionBean.currentClassAssignment.uncorrectedList != null && fn:length(actionBean.currentClassAssignment.uncorrectedList) != 0}">
						<c:forEach var="assign" items="${actionBean.currentClassAssignment.uncorrectedList }">
							<div class="student_info_box" onclick="correctStudentAsm(${assign.stuId })">
								<div class="student_title left">
									<img title="待批改" src="image/yj/stu_comming.png">
								</div>
								
								<div class="student_name_and_status left">
									<label class="student_name_label">${assign.studentName }</label>
										<label class="student_asm_status_label" style="color: #FE7472;">${assign.assignStatusString }</label>
								</div>
							</div>
						</c:forEach>
					</c:if>
					
					<c:if test="${actionBean.currentClassAssignment.finishedList != null && fn:length(actionBean.currentClassAssignment.finishedList) != 0}">
						<c:forEach var="assign" items="${actionBean.currentClassAssignment.finishedList }">
							<div class="student_info_box" onclick="viewCorrectedStuAsm(${assign.stuId })" >
								<div class="student_title left">
									<img title="已完成" src="image/yj/stu_finished.png">
								</div>
								
								<div class="student_name_and_status left">
									<label class="student_name_label">${assign.studentName }</label>
									<div style="width: 100%; height:20px;">
											<!-- 幼教只有1-5分 -->
											<c:if test="${assign.stuAsmScore gt 0}"><img src="image/yj/flower-on.png" class="stu_score_flower"></c:if>
											<c:if test="${assign.stuAsmScore gt 1}"><img src="image/yj/flower-on.png" class="stu_score_flower"></c:if>
											<c:if test="${assign.stuAsmScore gt 2}"><img src="image/yj/flower-on.png" class="stu_score_flower"></c:if>
											<c:if test="${assign.stuAsmScore gt 3}"><img src="image/yj/flower-on.png" class="stu_score_flower"></c:if>
											<c:if test="${assign.stuAsmScore gt 4}"><img src="image/yj/flower-on.png" class="stu_score_flower"></c:if>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					
					<c:if test="${actionBean.currentClassAssignment.undownloadedList != null && fn:length(actionBean.currentClassAssignment.undownloadedList) != 0}">
						<c:forEach var="assign" items="${actionBean.currentClassAssignment.undownloadedList }">
							<div class="student_info_box" onclick="showTip('1')" >
								<div class="student_title left">
									<img title="未下载" src="image/yj/stu_undownload.png">
								</div>
								
								<div class="student_name_and_status left">
									<label class="student_name_label">${assign.studentName }</label>
		 								<!-- <label class="student_asm_status_label" style="color: #C9C9C9;">${assign.assignStatusString }</label> --> 
								</div>
							</div>
						</c:forEach>
					</c:if>
				
				</c:if>
				
				<!-- 资料类型，不需要批改 -->
				<c:if test="${actionBean.currentAsm.asmType == 4 }">
					
					<c:if test="${actionBean.currentClassAssignment.finishedList != null && fn:length(actionBean.currentClassAssignment.finishedList) != 0}">
						<c:forEach var="assign" items="${actionBean.currentClassAssignment.finishedList }">
							<div class="student_info_box" >
								<div class="student_title left">
									<img title="已读" src="image/yj/stu_finished.png">
								</div>
								
								<div class="student_name_and_status left">
									<label class="student_name_label">${assign.studentName }</label>
									<label class="student_asm_status_label" style="color: #99C833;">已读</label>
								</div>
							</div>
						</c:forEach>
					</c:if>
					
					<c:if test="${actionBean.currentClassAssignment.undownloadedList != null && fn:length(actionBean.currentClassAssignment.undownloadedList) != 0}">
						<c:forEach var="assign" items="${actionBean.currentClassAssignment.undownloadedList }">
							<div class="student_info_box" >
								<div class="student_title left">
									<img title="未读" src="image/yj/stu_undownload.png">
								</div>
								
								<div class="student_name_and_status left">
									<label class="student_name_label">${assign.studentName }</label>
									<!-- <label class="student_asm_status_label" style="color: #C9C9C9;">未读</label> -->
								</div>
							</div>
						</c:forEach>
					</c:if>
					
				</c:if>
		</div>
</div>

<script src="js/yjteacher/yjAsmClassCompletion.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>