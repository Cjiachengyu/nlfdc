<%@ page pageEncoding="utf-8"%>
<%@ include file="../jsp/component/CommonTopSimple.jsp"%>

<link href="image/common/blank.ico" type="image/x-icon" rel="shortcut icon" /> 
<title>宁陵县房地产管理局</title>

<link href="css/index/admin_login.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div id="wrap" style="text-align: center; height: 50%; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;" >

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

<img id="bg_img" style="position: fixed; top:0; left:0; z-index:-100; " src="image/index/admin_index_bg.png" />

<!-- 
 -->
<div id="footer">
    <div style=" top: 0px; width: 100%; text-align: center; font-size: 15px; font-family: Arial; color: gray; line-height: 4em; position: relative; " >
        <p>Copyright &copy; 宁陵县房地产发展保障管理中局</p>
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
