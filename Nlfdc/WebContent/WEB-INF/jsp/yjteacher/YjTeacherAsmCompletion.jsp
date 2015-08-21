<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ include file="../component/CommonTop.jsp"%>

<title>任务详情</title>
<style>
.content { min-height: 500px; }
.assignment_info { margin: 30px 0; }
.asm_content { margin: 30px 0; border: solid 2px lightgray; }
.asm_que_content { margin-bottom: 25px; }
.asm_operate_btn { padding: 5px 15px; color: white; background: #323436; margin-left: 20px;}
.unsend_classes { margin-bottom: 30px; border: solid 1px lightgray; }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" >当前位置：<a	href="yjteaasmaction">我的任务 </a>&gt; 任务详情</span>
		
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div class="content clearfix">
		<!-- 作业信息 -->
		<div class="assignment_info" >
				<div class="left">
					<c:if test="${actionBean.currentAsm.asmType == 4 }"><img src="image/yj/yj_asm_type4.png" style="height: 50px; margin: 10px 5px 0px 0px;"></c:if>
					<c:if test="${actionBean.currentAsm.asmType == 5 }"><img src="image/yj/yj_asm_type5.png" style="height: 50px; margin: 10px 5px 0px 0px;"></c:if>
					<c:if test="${actionBean.currentAsm.asmType == 6 }"><img src="image/yj/yj_asm_type6.png" style="height: 50px; margin: 10px 5px 0px 0px;"></c:if>
				</div>
				
				<div class="left">
					<span class="font_yahei mar5" style="font-size: 22px; display: block; " >${actionBean.currentAsm.asmName }</span>
	                <span class="font_small gray mar5 ">发布:
						<c:if test="${actionBean.currentAsm.startTime > 0 }">
	    	            	<font color="black">${actionBean.currentAsm.startTimeString }</font> 
			            </c:if>
						<c:if test="${actionBean.currentAsm.startTime == 0 }">
							<font color="black">未限制</font>
						</c:if>
	                </span>
	                
	                <span class="font_small green mar5 ">
	               		<img src="image/yj/time_icon.png" style="height: 14px; " >
	                	<div style="display: inline-block;">
		                	截止:
				            <c:if test="${actionBean.currentAsm.finishTime > 0 }">
		    	            	<font color="green">${actionBean.currentAsm.finishTimeString }</font>
		    	            </c:if> 
				            <c:if test="${actionBean.currentAsm.finishTime == 0 }">
				            	<font color="green">未限制</font>
				            </c:if>
	                	</div>
	                </span>
	                <c:if test="${actionBean.currentAsm.asmType != 4 }">
					<span class="font_small gray mar5 ">提交(&nbsp<font color="black">${fn:length(actionBean.currentClassAssignment.uncorrectedList ) + fn:length(actionBean.currentClassAssignment.finishedList ) }</font>&nbsp)</span>
					<span class="font_small gray mar5 ">评改(&nbsp<font color="black">${fn:length(actionBean.currentClassAssignment.finishedList ) }</font>&nbsp)</span>
	                </c:if>
	                <c:if test="${actionBean.currentAsm.asmType == 4 }">
					<span class="font_small gray mar5 ">阅读(&nbsp<font color="black">${fn:length(actionBean.currentClassAssignment.finishedList ) }</font>&nbsp)</span>
	                </c:if>
	                
				</div>
			
			<div class="clear"></div>
		</div>

            <div class="asm_content">
                <div class="asm_que_content">
                      <div class="pad10" style="width: 900px; "> 
                        	<c:if test="${actionBean.asmQue.contentHtml == ''}">
                        		未设置任务描述
                        	</c:if>
                        	${actionBean.asmQue.contentHtml }
                      </div>
                </div>
				
				<hr>
				<h5 style="padding: 5px 10px;background: #eaeaea;">附件：共${fn:length(actionBean.asmContainedResList) }个</h5>
                <div class="asm_res_files">
                    <c:forEach var="resource" items="${actionBean.asmContainedResList }">
                        <div class="pad10">
                        	<c:if test="${resource.resFileType == 'video' }"><img src="image/yj/video.png" style="height: 15px;"></c:if>
                        	<c:if test="${resource.resFileType == 'image' }"><img src="image/yj/image.png" style="height: 15px;"></c:if>
                        	<c:if test="${resource.resFileType == 'audio' }"><img src="image/yj/audio.png" style="height: 15px;"></c:if>
                        	<c:if test="${resource.resFileType == 'other' }"><img src="image/yj/other.png" style="height: 15px;"></c:if>
                        
                            <a href="javascript:viewResource(${resource.resId}, '${resource.resName}')" >
                                ${resource.resName}
                            </a>
                         </div>
                    </c:forEach>
                </div>
            </div>

		<!-- 未发布 -->
		<c:if test="${actionBean.unassignClassAssignmentList != null && fn:length(actionBean.unassignClassAssignmentList) != 0}">
			<div class="unsend_classes bg_white" >
				<div class="pad10" style="background: #F0F0F0; ">
					<span class="font_bold" >未发布班级：</span>
				</div>
				<c:forEach var="classAssign" items="${actionBean.unassignClassAssignmentList }">
					<div class="pad10" >
						<div class="inline" style="min-width: 180px;" >
							<span style="padding: 10px 0;" >${classAssign.clsName }</span>
						</div>
						<a id="resendAssignmentBtn" style="padding: 5px 20px; color: black; background: lightblue; " 
							href="javascript:sendAssignmentToClass(${classAssign.clsId })"
							>
							发布到该班级
						</a>
					</div>
				</c:forEach>
			</div>
		</c:if>

		<div style="height: 1px; " ></div>
		
		<!-- 已发布 -->
		<c:if test="${actionBean.assignedClassAssignmentList != null && fn:length(actionBean.assignedClassAssignmentList) != 0}">
			<div id="assign_class_info" >
				<%@ include file="AsmClassCompletion.jsp"%>
			</div>
		</c:if>

		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript">
	var htmlVal = {htmlUrl: "yjteacherassignmentinfoaction", };

	function selectClass(clsId) {
		$.ajax({
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type : "post",
			url : htmlVal.htmlUrl + "?selectclass",
			data : {
				clsId : clsId
			},
			success : function(result) {
				isTimeOut(result);
				
				if (result == "refresh")
				{
					window.location.href = window.location.href;					
				}
			}
		});
	}
	
	function sendAssignmentToClass(clsId)
	{
		$("#resendAssignmentBtn").attr("href", "javascript:void(0)");
		
		$.ajax({
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			type : "post",
			url : htmlVal.htmlUrl + "?sendassignment",
			data : {
				clsId : clsId
			},
			success : function(result) {
				isTimeOut(result);
				
				if (result == "ok")
				{
					window.location.href = window.location.href;					
				}
				else if (result == "exception")
				{
					AlertDialogWithCallback("服务器出现异常，发布失败！", null, function(){
						window.location.href = window.location.href;				
					});
				}
			}
		});
	}
	
</script>

<%@ include file="../component/CommonBottom.jsp"%>
