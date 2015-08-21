<%@ page pageEncoding="utf-8" %>
<%@ include file="../component/CommonTopMobile.jsp" %>
<title>创建任务3</title>

<style>
.msg_box {width: 90%; margin-left: 5%; text-align: center; margin-top: 40%; }
h2 {margin: 40px; }
h3 {color: red; margin-top: 40px; }
.back_tip_mask {position: fixed; left: 0; right: 0; bottom: 0; top: 0; width: 100%; height: 100%; z-index: -1; }
</style>
<script>
    var htmlVal = {htmlUrl: "yjteacreateasmaction"};
    var leftSeconds = 4;
    
    function showMsg()
    {
    	leftSeconds--;
    	if (leftSeconds > 0)
    	{
    		$("#show_left_seconds").html("<font color='red'>"+leftSeconds+"</font> 秒后返回");
    	}
    	
    	if (leftSeconds == 0)
    	{
    		 window.myjsAndroid.jsFunction();
    	}
    	setTimeout("showMsg()", 1000);
    }
    
    $(function(){
    	 window.setTimeout("showMsg()", 0); 
    });
</script>

<div style="text-align: center; width: 100%; margin-top: 5%;">
	<span id="show_left_seconds"></span>
</div>

<div class="msg_box">
	${actionBean.createAsmSuccessOrFailMsg }    
</div>

