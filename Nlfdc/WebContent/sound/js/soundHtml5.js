(function (window) {
	// dom元素
	var rStart = document.getElementById("record_start"),
	    rStop = document.getElementById("record_stop"),
	    rPlay = document.getElementById("record_play"),
	    rPause = document.getElementById("record_pause"),
	    rUpload = document.getElementById("record_upload"),
	    audio = document.getElementById("record_audio"),
	    description = document.getElementById("record_description"),
		info = document.getElementById("record_info"),
	// 对象、流
		recorder = null,
		windowURL = window.URL || window.webkitURL;
		navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia,
		audioStream = null;
		
	// 录音信息控制对象
	var timeCtrlObj = (function () {
		var startTime = 0,
			audioTime = 0,
			endTime = 0,
			timeId = null;

		var start = function () {
			description.innerHTML = "开始录音：";
			info.innerHTML = 0;
			startTime = new Date().getTime();
			
			clearTimeout(timeId);
			
			timeId = setInterval(function () {
				var s = Math.floor((new Date().getTime() - startTime) / 1000);
				info.innerHTML = s;
			}, 1000);
		};
		
		var stop = function () {
			startTime = 0;
			audioTime = endTime = info.innerHTML;
			
			clearInterval(timeId);
		};
		
		var play = function () {
			description.innerHTML = "播放录音：";
			
			clearTimeout(timeId);
			
			if (endTime <= 0)
			{
				endTime = audioTime;
			}
			timeId = setInterval(function () {
				if (endTime > 0)
				{
					endTime--;
					info.innerHTML = endTime;
				}
				else
				{
					clearTimeout(timeId);
					
					setDomStatues(rPlay, "inline", false);
				    setDomStatues(rPause, "none", true);
				}
			}, 1000);
		};
		
		var pause = function () {
			clearTimeout(timeId);
		};
		
		var cancle = function () {
			clearTimeout(timeId);
			endTime = 0;
			
			setDomStatues(rPlay, "inline", false);
		    setDomStatues(rPause, "none", true);
		};
		
		var upload = function (uploadInfo) {
			description.innerHTML = "上传录音：";
			info.innerHTML = uploadInfo;
		};
		
		var error = function (errorInfo) {
			description.innerHTML = "出错了：";
			info.innerHTML = errorInfo;
		};
		
		var success = function (successInfo) {
			description.innerHTML = "成功上传！";
			info.innerHTML = successInfo;
		};
		
		return {
			start: start,
			stop: stop,
			play: play,
			pause: pause,
			cancle: cancle,
			upload: upload,
			error: error,
			success: success
		};
	})();
	
	function setDomStatues (dom, display, disabled) 
	{
	    dom.style.display = display;
	    dom.disabled = disabled;
	}

	function audioCtrl (command) 
	{
		switch (command)
        {
            case "play":
                audio.play();
                break;
            case "pause":
                audio.pause();
                break;
            case "stop":
            	audio.pause(); 
            	if (audio.currentTime != 0)
            	{
            		audio.currentTime = 0; 
            	}
                break;
        }
	}

	function startRecording ()
	{
		if (navigator.getUserMedia)
	    {
			navigator.getUserMedia({
	            audio: true,
	            video: false
	        }, successFn, failFn);
		}
	    else
	    {
	    	timeCtrlObj.error("抱歉，您的浏览器不支持录音的功能，或者请检查您是否禁用了麦克风");
		}
	}

	function successFn (stream) 
	{
		var context,
			audioContent = window.webkitAudioContext || window.AudioContext;
		try
	    {
			context = new audioContent();
			// 将声音流输入这个对象
			var audioInput = context.createMediaStreamSource(stream);
			recorder = new Recorder(audioInput);
		}
	    catch (e)
	    {
			// console.log("new context error: " + e);
			timeCtrlObj.error("抱歉，您的浏览器不支持录音的功能，或者请检查您是否禁用了麦克风");
		}
		recorder.record();
		
		setDomStatues(rStart, "none", true);
	    setDomStatues(rStop, "inline", false);
	    setDomStatues(rPlay, "inline", true);
	    setDomStatues(rPause, "none", true);
	    setDomStatues(rUpload, "inline", true);
	    
	    timeCtrlObj.start();

	    audioCtrl("stop");
	}
	
	function failFn () 
	{
		timeCtrlObj.error("抱歉，您的浏览器不支持录音的功能，或者请检查您是否禁用了麦克风");
	}

	function stopRecording ()
	{
		timeCtrlObj.stop();
		
		recorder.stop();
		recorder.exportWAV(function (stream) {
			var audio = document.getElementById("record_audio");
		    audio.src = windowURL.createObjectURL(stream);
		    audioStream = stream;
		});
	}

	function uploadRecord ()
	{
		audioCtrl ("stop"); 
		
		timeCtrlObj.cancle();
		timeCtrlObj.upload("正在上传，请稍后。。。");
		
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("POST", "../uploadrecordaction?saverecordforhtml5=");
		xmlhttp.overrideMimeType("application/octet-stream");
		xmlhttp.responseType = "text";
	    xmlhttp.onreadystatechange = function () {
	        if (xmlhttp.readyState === 4)
	        {
	        	if (xmlhttp.status === 200)
	        	{
	        		timeCtrlObj.success("");
	        	}
	        	else if (xmlhttp.status === 500)
	        	{
	        		timeCtrlObj.error("出了点小问题，请重试一下");
	        	}
	        }
	    };
		xmlhttp.send(audioStream);
	}

	// 初始状态
	setDomStatues(rStop, "none", true);
	setDomStatues(rPlay, "inline", true);
	setDomStatues(rPause, "none", true);
	setDomStatues(rUpload, "inline", true);

	// 添加事件
	rStart.onclick = function () {
	    startRecording();
	};

	rStop.onclick = function () {
	    stopRecording();

	    setDomStatues(this, "none", true);
	    setDomStatues(rStart, "inline", false);
	    setDomStatues(rPlay, "inline", false);
	    setDomStatues(rUpload, "inline", false);
	};

	rPlay.onclick = function () {
	    audioCtrl("play");

	    setDomStatues(this, "none", true);
	    setDomStatues(rPause, "inline", false);
	    
	    timeCtrlObj.play();
	};

	rPause.onclick = function () {
	    audioCtrl("pause");

	    setDomStatues(this, "none", true);
	    setDomStatues(rPlay, "inline", false);
	    
	    timeCtrlObj.pause();
	};

	rUpload.onclick = function () {
	    uploadRecord();
	};
})(window);