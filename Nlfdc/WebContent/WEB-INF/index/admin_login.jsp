<%@ page pageEncoding="utf-8"%>
<%@ include file="../jsp/component/CommonTopSimple.jsp"%>

<title>宁陵县房地产发展保障管理中心</title>

<link href="css/index/admin_login.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div id="update_broswer_tip" style="display: none;">
    <div style=" width: 100%; height: 50px; text-align: center; color: gray; line-height: 3em; position: relative; " >
		<h3 style="height: 50px; ">您的浏览器太旧了,请&nbsp<a href="http://browsehappy.com/" target="_blank">升级浏览器</a>&nbsp以便正常使用该网站。</h3>	
		<a style="position: absolute; right: 10px; bottom: -12px;" onclick="closeTip()"><img style="height: 15px; width: 15px;" src="image/common/closelabel.gif"></a>			
	</div>
</div>

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

<!-- 
<img id="bg_img" style="position: fixed; top:0; left:0; z-index:-100; " src="image/common/main_background_new.jpg" />
 -->

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

<%@ include file="../jsp/component/CommonBottomSimple.jsp"%>
