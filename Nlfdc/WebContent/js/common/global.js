// common simple tools
String.prototype.contains = function(str) {
	return this.indexOf(str) > -1 ? true : false;
};

function callElementEvent(ele, event) {
	ele[event]();
};

function callElementEventById(ele, event) {
	$("#" + ele)[0][event]();
};

function getTargetFromEvent(event) {
	event = event || window.event;
	var target = event.target || event.srcElement;
	return target;
}

function scrollAndShine(id) {
	var layer_id = "#" + id;
	var scroll_offset = $(layer_id).offset();
	$("body").animate({
		scrollTop : scroll_offset.top
	}, 300, function() {
		$(layer_id).animate({
			backgroundColor : "rgb(207, 207, 207)"
		}, 600, function() {
			$(layer_id).animate({
				backgroundColor : "white"
			}, 600);
		});
	});
}

function setCookie(name, value, days) {
	if (days == null) {
		days = 365;
	}

	var expires = new Date();
	expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + expires.toGMTString();
}

function getCookie(name) {
	var cookieArray = document.cookie.split("; ");
	var cookie = null;
	for (var i = 0; i < cookieArray.length; i++) {
		var array = cookieArray[i].split("=");
		if (name == array[0]) {
			cookie = array[1];
			break;
		}
	}
	return cookie;
}

function deleteCookie(name) {
	var expires = new Date();
	expires.setTime(expires.getTime() - 1);
	var cookie = getCookie(name);
	if (cookie != null) {
		document.cookie = name + "=" + cookie + ";expires=" + expires.toGMTString();
	}
}

function isTimeOut(result) {
	if (result == "timeOut") {
		AlertDialogWithCallback("抱歉，页面超时，请重新登录", null, function() {
			window.location.href = "loginaction?yjsignout";
		});
		event.stopPropagation();	
	}
}

function createClickCloseMaskLayer(contentDivId, content) {
	var current_time = new Date().getTime();
	var mask_id = current_time + "mask_div";
	var layer_id = current_time + "layer_div";

	var newDiv = document.createElement("div");
	newDiv.id = layer_id;
	newDiv.style.position = "fixed";
	newDiv.style.zIndex = "100";

	var newDivWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
	var newDivHeight = window.innerHeight;

	newDiv.style.width = newDivWidth + "px";
	newDiv.style.height = newDivHeight + "px";
	newDiv.style.top = (window.innerHeight / 2 - newDivHeight / 2) + "px";
	newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth / 2 - newDivWidth / 2) + "px";
	newDiv.className = "full_mask_root layer_root_div";
	newDiv.innerHTML = "<fieldset class='full_mask_content_holder' onclick='javascript:closePopup(\"" + layer_id + "\", \"" + mask_id + "\");'>" + "<div id='" + contentDivId + "' >" + content + "</div>" + "</fieldset>";

	showShade(mask_id, 1);
	document.body.appendChild(newDiv);

	return layer_id + "-" + mask_id;
}

function createFullMaskLayer(contentDivId, title, content) {
	var current_time = new Date().getTime();
	var mask_id = current_time + "mask_div";
	var layer_id = current_time + "layer_div";

	if (document.getElementById(layer_id)) {
		document.removeChild(document.getElementById(layer_id));
	}

	if (document.getElementById(mask_id)) {
		document.removeChild(document.getElementById(mask_id));
	}

	if (title == null || title == "") {
		title = "&nbsp;";
	}

	var newDiv = document.createElement("div");
	newDiv.id = layer_id;
	newDiv.style.position = "fixed";
	newDiv.style.zIndex = "100";

	var newDivWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
	var newDivHeight = window.innerHeight;

	newDiv.style.width = newDivWidth + "px";
	newDiv.style.height = newDivHeight + "px";
	newDiv.style.top = (window.innerHeight / 2 - newDivHeight / 2) + "px";
	newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth / 2 - newDivWidth / 2) + "px";
	newDiv.className = "full_mask_root layer_root_div";
	newDiv.innerHTML = "<div class='popup_nav clearfix relative'>" + "<a class='full_mask_close absolute' href='javascript:closePopup(\"" + layer_id + "\", \"" + mask_id + "\");'>" + "X" + "</a>" + "<span class='full_mask_title relative'>" + title + "</span>" + "</div>" + "<fieldset class='full_mask_content_holder'>" + "<div id='" + contentDivId + "' >" + content + "</div>" + "</fieldset>";

	showShade(mask_id, 1);
	document.body.appendChild(newDiv);

	return layer_id + "-" + mask_id;
}


function createBorderMaskLayer(contentDivId, title, content, width, height) {
	var current_time = new Date().getTime();
	var mask_id = current_time + "mask_div";
	var layer_id = current_time + "layer_div";

	if (document.getElementById(layer_id)) {
		document.removeChild(document.getElementById(layer_id));
	}

	if (document.getElementById(mask_id)) {
		document.removeChild(document.getElementById(mask_id));
	}

	if (width == null || width == 0) {
		width = 1000;
	}

	if (height == null || height == 0) {
		height = 700;
	}

	var newDiv = document.createElement("div");
	newDiv.id = layer_id;
	newDiv.style.position = "fixed";
	newDiv.style.zIndex = "100";

	var viewSize = getViewportSize();
	var newDivWidth = Math.min(width, viewSize[0] - 60);
	var newDivHeight = Math.min(height, viewSize[1] - 20);

	newDiv.style.width = newDivWidth + "px";
	newDiv.style.height = newDivHeight + "px";
	newDiv.style.left = (viewSize[0] / 2 - newDivWidth / 2) + "px";
	newDiv.style.top = (viewSize[1] / 2 - newDivHeight / 2) + "px";
	newDiv.className = "popup layer_root_div";
	newDiv.innerHTML = "<div class='popup_nav clearfix '>" + "<a class='popup_nav_close absolute' href='javascript:closePopup(\"" + layer_id + "\", \"" + mask_id + "\");'>" + "X" + "</a>" + "<span class='popup_nav_title relative'>" + title + "</span>" + "</div>" + "<fieldset class='popup_list'>" + "<div id='" + contentDivId + "' >" + content + "</div>" + "</fieldset>";

	showShade(mask_id, 1);
	document.body.appendChild(newDiv);
}

function createFullMaskLayerWithClose(contentDivId, title, content, width, height) {
	var current_time = new Date().getTime();
	var mask_id = current_time + "mask_div";
	var layer_id = current_time + "layer_div";

	if (document.getElementById(layer_id)) {
		document.removeChild(document.getElementById(layer_id));
	}

	if (document.getElementById(mask_id)) {
		document.removeChild(document.getElementById(mask_id));
	}

	var newDiv = document.createElement("div");
	newDiv.id = layer_id;
	newDiv.style.position = "fixed";
	newDiv.style.zIndex = "100";

	var viewSize = getViewportSize();
	var newDivWidth =  viewSize[0];
	var newDivHeight = viewSize[1] - 80;

	newDiv.style.width = newDivWidth + "px";
	newDiv.style.height = newDivHeight + "px";
	newDiv.style.left = (viewSize[0] / 2 - newDivWidth / 2) + "px";
	newDiv.style.top = (viewSize[1] / 2 - newDivHeight / 2) + "px";
	newDiv.className = "popup layer_root_div";
	newDiv.innerHTML = "<div class='popup_nav clearfix '>" + "<a class='popup_nav_close absolute' href='javascript:closePopup(\"" + layer_id + "\", \"" + mask_id + "\");'>" + "X" + "</a>" + "<span class='popup_nav_title relative'>" + title + "</span>" + "</div>" + "<fieldset class='popup_list'>" + "<div id='" + contentDivId + "' >" + content + "</div>" + "</fieldset>";

	showShade(mask_id, 1);
	document.body.appendChild(newDiv);
}

function showShade(mask_id, zIndex) {
	var newMask = document.createElement("div");
	var width = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
	var height = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);

	newMask.id = mask_id;
	newMask.className = "full_mask_root_div";

	newMask.style.zIndex = zIndex;
	newMask.style.width = width + "px";
	newMask.style.height = height + "px";
	document.body.appendChild(newMask);
}

function closePopup(layer_id, mask_id) {
	document.body.removeChild(document.getElementById(layer_id));
	document.body.removeChild(document.getElementById(mask_id));
}

function closeAllLayers() {
	var masks = $(".full_mask_root_div");
	for (var i = 0; i < masks.length; i++) {
		document.body.removeChild(masks[i]);
	}

	var layers = $(".layer_root_div");
	for (var i = 0; i < layers.length; i++) {
		document.body.removeChild(layers[i]);
	}
}

function createClassifySchemaMaskLayer(classifySchemaItemId) {
	var height = 320;
	if ($("#user_role").html() === "2") {
		height = 280;
	}

	createBorderMaskLayer("classify_schema", "选择大纲分类", getLoading(), 350, height);

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?gettextbookselectorclassifyview=",
		data : {
			classifySchemaItemId : classifySchemaItemId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "wrong_id") {
				AlertDialogWithCallback("数据出现错误！", "", function() {
					window.location.href = window.location.href;
				});
			}

			$("#classify_schema").html(result);
		}
	});
}

function initBackToTop() {
	$("#back_to_top").hide();

	// 当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
	$(window).scroll(function() {
		if ($(window).scrollTop() > 100) {
			$("#back_to_top").fadeIn(300);
		}
		else {
			$("#back_to_top").fadeOut(300);
		}
	});

	// 当点击跳转链接后，回到页面顶部位置
	$("#back_to_top").click(function() {
		$('body,html').animate({
			scrollTop : 0
		}, 300);
		return;
	});
}

function getLoading() {
	var loading = "";
	if (window.ActiveXObject) {
		if (parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10) {
			loading = "<div id='fountain'>载入中...</div>";
		}
		else {
			loading = "<div id='fountain'>" + "<div id='fountain_1' class='fountain'></div><div id='fountain_2' class='fountain'></div><div id='fountain_3' class='fountain'>" + "</div><div id='fountain_4' class='fountain'></div><div id='fountain_5' class='fountain'></div><div id='fountain_6' class='fountain'>" + "</div><div id='fountain_7' class='fountain'></div><div id='fountain_8' class='fountain'></div></div>";
		}
	}
	else {
		loading = "<div id='fountain'>" + "<div id='fountain_1' class='fountain'></div><div id='fountain_2' class='fountain'></div><div id='fountain_3' class='fountain'>" + "</div><div id='fountain_4' class='fountain'></div><div id='fountain_5' class='fountain'></div><div id='fountain_6' class='fountain'>" + "</div><div id='fountain_7' class='fountain'></div><div id='fountain_8' class='fountain'></div></div>";
	}
	return loading;
}

function getViewportSize() {
	var myWidth = 0, myHeight = 0;
	if (typeof (window.innerWidth) == 'number') {
		myWidth = window.innerWidth;
		myHeight = window.innerHeight;
	}
	else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
		myWidth = document.documentElement.clientWidth;
		myHeight = document.documentElement.clientHeight;
	}
	else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
		myWidth = document.body.clientWidth;
		myHeight = document.body.clientHeight;
	}
	return [ myWidth, myHeight ];
}

function replaceAudioHtml(audioType, autoPlay) {
	
	if (audioType == null)
	{
		audioType = "mp3";
	}
	
	if (autoPlay == null)
	{
		autoPlay = "false";
	}
	
	var navigatorResult = function() {
		var isOpera = false, isWebkit = false, g_oHasQuickTime = false, isGecko = false, isOthers = false, isIE = false, ieVersion = 0;

		var hasQuickTime = function() {
			var np = navigator.plugins;
			for (var i = 0, npLenth = np.length; i < npLenth; i++) {
				if (np[i].description.toLowerCase().indexOf("quicktime") > -1) {
					return true;
				}
			}
			return false;
		};

		var ua = navigator.userAgent;
		if (window.opera) {
			isOpera = true;
			g_oHasQuickTime = hasQuickTime();
		}
		else if (/AppleWebKit\/(\S+)/.test(ua)) {
			isWebkit = true;
		}
		else if (/rv:([^\)]+)\) Gecko\/\d{8}/.test(ua)) {
			isGecko = true;
			g_oHasQuickTime = hasQuickTime();
		}
		else if (/MSIE ([^;]+)/.test(ua)) {
			isIE = true;
			ieVersion = RegExp["$1"];
		}
		else {
			isOthers = true;
		}

		return {
			isOpera : isOpera,
			isWebkit : isWebkit,
			isGecko : isGecko,
			g_oHasQuickTime : g_oHasQuickTime,
			isOthers : isOthers,
			isIE : isIE,
			ieVersion : ieVersion
		};
	}();

	var $fakeAudios = $(".fake_audio");
	for (var i = 0; i < $fakeAudios.size(); i++) {
		var fakeAudio = $fakeAudios[i];
		var path = fakeAudio.attributes["audio_path"].nodeValue;
		if (path) {
			var audio = "";

			if (navigatorResult.isWebkit) {
				if (autoPlay == "true")
				{
					audio = "<audio src='" + path + "' controls autoplay></audio>";
				}
				else
				{
					audio = "<audio src='" + path + "' controls ></audio>";
				}
			}
			else if (navigatorResult.isGecko || navigatorResult.isOpera) {
				if (navigatorResult.g_oHasQuickTime) {
					audio = "<embed src='" + path + "' type='audio/" + audioType + "' autostart='" + autoPlay + "' width='300px' height='30px'></embed>";
				}
				else {
					audio = "请安装插件 <a href='http://www.apple.com/quicktime/' target='_blank' style='color: blue; text-decoration: underline;'>" + "QuickTime</a> ，并重启浏览器来播放此段音频文件";
				}
			}
			else if (navigatorResult.isIE && navigatorResult.ieVersion < 10) {
				audio = "<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' width='300px' height='45px'>" + "<param name='AutoStart' value='"+ autoplay+"' />" + "<param name='Src' value='" + path + "' /></object>";
			}
			else {
				if (autoPlay == "true")
				{
					audio = "<audio src='" + path + "' controls autoplay></audio>";
				}
				else
				{
					audio = "<audio src='" + path + "' controls ></audio>";
				}
			}

			var fakeAudioHtml = fakeAudio.innerHTML;
			var extra = fakeAudioHtml.substring((fakeAudioHtml.indexOf("."+audioType) + (audioType.length+1) ));
			var extraSpan = "";
			if (extra) {
				extraSpan = "<span>" + extra + "</span>";
			}
			fakeAudio.innerHTML = audio + extraSpan;
		}
	}
}

function printAllPrpos(obj) {
	// 用来保存所有的属性名称和值
	var props = "";
	// 开始遍历
	for ( var p in obj) {
		// 方法
		if (typeof (obj[p]) == "function") {
			// obj[p]();
		}
		else {
			// p 为属性名称，obj[p]为对应属性的值
			props += p + "=" + obj[p] + ";  ";
		}
	}
	// 最后显示所有的属性
	AlertDialog(props, "");
	// return props;
}

function getMac() {
	var wmi = GetObject("winmgmts:{impersonationLevel=impersonate}");
	if (!wmi)
		return false;
	var pr = wmi.ExecQuery("SELECT MACAddress FROM Win32_NetworkAdapterConfiguration WHERE IPEnabled = True");
	if (!pr)
		return false;
	try {
		var e = new Enumerator(pr);
	}
	catch (e) {
		return false;
	}
	var mac = [], s;
	for (; !e.atEnd(); e.moveNext()) {
		s = e.item();
		mac.push(s.MACAddress);
	}
	return mac;
}

function AlertDialog(msg, type) {
	var tp = "information";
	if (type == null) {
		tp = "error";
	}
	new $.Zebra_Dialog(msg, {
		'type' : tp,
		'title' : '消息提示'
	});
}

function AlertDialogWithCallback(msg, type, callBack) {
	var tp = "information";
	if (type == null) {
		tp = "error";
	}
	new $.Zebra_Dialog(msg, {
		'type' : tp,
		'title' : '消息提示',
		'onClose' : callBack
	});
}

function ConfirmDialog(msg, yesFunc, cancleFun) {
	new $.Zebra_Dialog(msg, {
		'type' : 'question',
		'title' : '确认',
		'buttons' : [ {
			caption : '确定',
			callback : yesFunc
		}, {
			caption : '取消',
			callback : cancleFun
		} ]
	});
}

function isTeacher() {
	return $("#user_role").html() === "2";
}

function isStudent() {
	return $("#user_role").html() === "1";
}

function enablePlaceHolder() {
	supportPlaceholder = 'placeholder' in document.createElement('input'),

	placeholder = function(input) {
		var text = input.attr('placeholder'), defaultValue = input.defaultValue;
		if (!defaultValue) {
			input.val(text).addClass("phcolor");
		}

		input.focus(function() {
			if (input.val() == text) {
				$(this).val("");
			}
		});

		input.blur(function() {
			if (input.val() == "") {
				$(this).val(text).addClass("phcolor");
			}
		});

		// 输入的字符不为灰色
		input.keydown(function() {
			$(this).removeClass("phcolor");
		});
	};

	// 当浏览器不支持placeholder属性时，调用placeholder函数
	if (!supportPlaceholder) {
		$('input').each(function() {
			text = $(this).attr("placeholder");
			if ($(this).attr("type") == "text") {
				placeholder($(this));
			}
		});
	}
}

function setFullWindowBg(holderId) {
	window.onresize = window.onload = function() {
		var width = window.innerWidth;
		var height = window.innerHeight;
		if (typeof width != "number") {
			if (document.compatMode == "CSS1Compat") {
				width = document.documentElement.clientWidth;
				height = document.documentElement.clientHeight;
			}
			else {
				width = document.body.clientWidth;
				height = document.body.clientHeight;
			}
		}

		var lay = document.getElementById(holderId);
		if (lay !== null) {
			if (width > 1060) {
				lay.style.width = "100%";
			}
			else {
				lay.style.width = "1060px";
			}
			if (height > 720) {
				lay.style.height = "100%";
			}
			else {
				lay.style.height = "720px";
			}
		}
	};
}

function viewResource(resId, resName)
{
	if ($("#is_in_mobile_browser").val() === "1") {
		// createFullMaskLayerWithClose("view_resource_box", resName, getLoading(), null, null);
		window.location.href = "userresourceitemaction?gotoresourceitem&resId=" + resId + "&isMobile=1";
	}
	else {
		createBorderMaskLayer("view_resource_box", resName, getLoading(), null, null);

		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : "userresourceitemaction?gotoresourceitem",
			data : {resId : resId }, 
			success : function(result) {
				isTimeOut(result);
		
				if (result.indexOf("#_image_#") == 0)
				{
					closeAllLayers();
					var imageUrl = result.substring(9); 
						
					var viewSize = getViewportSize();
					
					createBorderMaskLayer("yjtea_check_big_image", "<label style='font-size: 14px;'>查看图片</label> <a class='right' href='javascript:addImage()'><img title='放大' src='image/common/add.png' style='width: 30px; margin-left: 20px;'></a> <a class='right' href='javascript:minusImage()'><img title='缩小' src='image/common/minus.png' style='width: 30px;'></a>", getLoading(), viewSize[0], viewSize[1]);
					
					$("#yjtea_check_big_image").html("<div style='text-align: center;'><img id='view_big_image' src='" + imageUrl + "' style='max-width: 95%; max-height: "+(viewSize[1]-100)+"px;' ></div>");
					
				}
				else
				{
					$("#view_resource_box").html(result);
				}
			}
		});
	}
}

function addImage() {
	var width = $("#view_big_image").width() + 100;
	var style = "width:"+width+"px;"
	$("#view_big_image").attr("style",style);
}

function minusImage() {
	var width = ($("#view_big_image").width() - 100) > 0 ? ($("#view_big_image").width() - 100) : $("#view_big_image").width();
	var style = "width:"+width+"px;"
	$("#view_big_image").attr("style",style);
}

/* 移动端使用  */
function Alert(msg, callback) {
	openDialog("消息提示", msg, "确定", callback);
}

function Confirm(msg, okCallback, cancelCallback) {
	openDialog("请确认您的操作", msg, "确定", okCallback, "取消", cancelCallback);
}

function openDialog(title, content, btn1, callback1, btn2, callback2) {
	// 打开一个对话框，下面可以有按钮
	var current_time = new Date().getTime();
	var mask_id = current_time + "mask_div";
	var layer_id = current_time + "layer_div";

	if (document.getElementById(layer_id)) {
		document.removeChild(document.getElementById(layer_id));
	}

	if (document.getElementById(mask_id)) {
		document.removeChild(document.getElementById(mask_id));
	}

	var width = 350;
	var height = 200;

	var newDiv = document.createElement("div");
	newDiv.id = layer_id;
	newDiv.style.position = "fixed";
	newDiv.style.zIndex = "500";

	var viewSize = getViewportSize();
	var newDivWidth = Math.min(width, viewSize[0] - 40);
	var newDivHeight = Math.min(height, viewSize[1] - 40);

	var htmlPopupNavTitle = "<span class='popup_nav_title relative'>" + title + "</span>";
	var htmlPopupNav = "<div class='popup_nav clearfix'>" + htmlPopupNavTitle + "</div>";
	var htmlPopupContentWrapper = "<div style='margin: 40px auto 0 auto; text-align: center; width: " + (newDivWidth - 80) + "px;'>" + content + "</div>";
	var htmlPopupContent = "<div class='popup_content_holder' style='width: " + newDivWidth + "px; height: " + (newDivHeight - 90) + "px;'>" + htmlPopupContentWrapper + "</div>";
	var htmlPopup = htmlPopupNav + htmlPopupContent;

	var popupBtnBar = document.createElement("div");
	popupBtnBar.style.width = newDivWidth + "px";
	popupBtnBar.style.height = 50 + "px";

	var btnCount = 0;
	if (btn1) {
		btnCount = btnCount + 1;
	}

	if (btn2) {
		btnCount = btnCount + 1;
	}

	var btnAllWidth = 100 / btnCount;
	var btnWidth = btnAllWidth * 0.6;
	var btnMargin = btnAllWidth * 0.2;

	function appendBtn(btn, callback) {
		var newA = document.createElement("a");
		newA.onclick = function() {
			if (callback) {
				callback();
			}
			closePopup(layer_id, mask_id);
		};
		newA.innerHTML = btn;
		newA.className = "popup_dialog_btn";
		newA.style.width = btnWidth + "%";
		newA.style.marginLeft = btnMargin + "%";
		newA.style.marginRight = btnMargin + "%";

		popupBtnBar.appendChild(newA);
	}

	if (btn1)
	{
		appendBtn(btn1, callback1);
	}

	if (btn2)
	{
		appendBtn(btn2, callback2);
	}

	newDiv.style.width = newDivWidth + "px";
	newDiv.style.height = newDivHeight + "px";
	newDiv.style.left = (viewSize[0] / 2 - newDivWidth / 2) + "px";
	newDiv.style.top = (viewSize[1] / 2 - newDivHeight / 2) + "px";
	newDiv.className = "dialog_root_div";
	newDiv.innerHTML = htmlPopup;
	newDiv.appendChild(popupBtnBar);

	showShade(mask_id, 400);
	document.body.appendChild(newDiv);
	$(".popup_blur_content_holder").addClass("blur");
}

function isMobileTimeOut(result)
{
	if (result == "timeOut") {
		window.myjsAndroid.jsFunction();
	}
}

// 判断浏览器版本是否过低，是则显示升级浏览器提示
function judgeBrowserVersion()
{
	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		var version = $.browser.version - 0;
		if (version < 10) {
			$("#update_broswer_tip").show();
		}
	}
    var ua = navigator.userAgent.toLowerCase();
    var Sys = {};
    var s;
    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    //以下进行测试
    if (Sys.firefox)
    {
    	if (Sys.firefox < 35)
    	{
    		$("#update_broswer_tip").show();
    	}
    }
    else if (Sys.chrome)
    {
    	if (Sys.chrome < 30)
    	{
    		$("#update_broswer_tip").show();
    	}
    }
    else if (Sys.opera)
    {
    	if (Sys.opera < 10)
    	{
    		$("#update_broswer_tip").show();
    	}
    }
    else if (Sys.safari)
    {
    	if (Sys.safari < 3.1)
    	{
    		$("#update_broswer_tip").show();
    	}
    }
}

// 关闭升级浏览器提示
function closeTip()
{
	$("#update_broswer_tip").hide();	
}