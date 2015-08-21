var jsMessage = new Object();

(function() {
	var lang;
	var dom = document.getElementById("lang");
	if (dom == null) {
		lang = "zh";
	}
	else {
		lang = dom.innerHTML;
	}

	if (lang == "en") {
		jsMessage.pageSkipError = "Please input the correct page number";
	}
	else {
		jsMessage.pageSkipError = "请输入正确页数后再跳转";
	}
})();