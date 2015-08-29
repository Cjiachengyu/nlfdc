<%@ page pageEncoding="utf-8"%>
<%@ include file="../jsp/component/CommonTopSimple.jsp"%>

<title>宁陵县房地产发展保障管理中心</title>

<link href="css/index/admin_login.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div id="wrap" style="min-height: 500px; margin-top: 140px; padding: 0 70px;" >

	<div class="login_box">
		<div>
			<div class="tab_item tab_selected">管理员登录登录</div> 
            <div class="clear"></div>
		</div>

        <div class="tab_content">
            <div id="tab_content_login">
                <div id="tip_email" class="input_tip tip_email hide">请输入用户名</div>
                <div id="tip_pwd" class="input_tip tip_pwd hide">密码错误</div>

                <div class="login_items_wrap" >
                    <div class="input_holder input_holder_1" >
                        <span class="input_icon input_icon_name"></span> 
                        <input id="input_email" class="input_text" type="text" placeholder="用户名">
                        <div class="clear"></div>
                    </div>

                    <div class="input_holder">
                        <span class="input_icon input_icon_pwd"></span> 
                        <input id="input_password" class="input_text" type="password" placeholder="密码">
                        <div class="clear"></div>
                    </div>

                    <div>
                        <input id="login_btn" class="input_btn" type="submit" value="登   录" />
                    </div>
                </div>
            </div>

        </div>
	</div>
    <div class="clear"></div>
    
</div>

<div id="footer">
    <div style=" top: 0px; width: 100%; text-align: center; font-size: 15px; font-family: Arial; color: gray; line-height: 4em; position: relative; " >
        <p>Copyright &copy; 宁陵县房地产发展保障管理中心</p>
    </div>
</div>

<script src='js/index/index.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
<script>
$(function(){
	var setFooterPosition = function() {
        var bodyHeight = $("body").height();
        var htmlClientHeight = $(window).height();
        var position = "static";
        if (htmlClientHeight > bodyHeight) {
            position = "absolute";
        }
        else {
            position = "static";
        }

        $("#footer").css({
            "position" : position
        });
	};
	setFooterPosition();
	window.onresize = function() { setFooterPosition(); };

    function doResize() {
        img = $("#bg_img");
        img.css("width",  $(window).width());
        img.css("height", $(window).height());
    }
    $(window).resize(function() { doResize(); });
    $(document).ready(function() { doResize(); });
});

</script>
