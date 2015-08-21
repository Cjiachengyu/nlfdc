<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>常见问题</title>

<script type="text/javascript">
var htmlVal = {
    htmlUrl: "yjadminproblemmanageaction"
};

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
		$(".sub_menu_button").attr("class","sub_menu_button");
		$("#tab_title"+i).attr("class","sub_menu_button font_bold bg_light_gray highlight");
		$(".tab_page").hide();
		$("#tab"+i).show();
	}
}

$(function(){
	judgeBrowserVersion();
});

</script>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<a class="sub_menu_button font_bold bg_light_gray highlight" id="tab_title1" href="javascript:showTab(1)">常见问题</a>
		<a class="sub_menu_button" id="tab_title2" href="javascript:showTab(2)" >添加用户</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap" >
	<div id="content" class="clearfix">
		<div class="tab_page" id="tab1" >
	        <%@ include file="../component/ManageUser.jsp" %>
		</div>
		
		<div class="tab_page" id="tab2" style="display:none;">
	        <%@ include file="YjAddEditor.jsp" %>
		</div>
	</div>
</div>

<%@ include file="../component/CommonBottom.jsp"%>