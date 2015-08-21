if (navigator.webkitGetUserMedia)
{
	document.getElementById("record_html5").style.display = "block";
}
else
{
	document.getElementById("record_flash").style.display = "block";
	
	var hasFlash = (function () {
		var np = navigator.plugins;
		for (var i = 0, npLenth = np.length; i < npLenth; i++) 
		{ 
			if (np[i].description.toLowerCase().indexOf("flash") > -1) 
			{
				return true;
			} 
		}
		return false;
	})();

	if (hasFlash || window.attachEvent)
	{
		var swfVersionStr = "11.1.0";
		var xiSwfUrlStr = "swf/playerProductInstall.swf";
		var flashvars = {};

		var params = {};
		params.quality = "high";
		params.bgcolor = "#ffffff";
		params.allowscriptaccess = "sameDomain";
		params.allowfullscreen = "true";

		var attributes = {};
		attributes.id = "sound_record";
		attributes.name = "sound_record";
		attributes.align = "middle";

		swfobject.embedSWF(
			"swf/sound_record.swf", "flashContent", 
			"238", "140", 
			swfVersionStr, xiSwfUrlStr, 
			flashvars, params, attributes);
	}
	else
	{
		document.getElementById("flashNoContent").style.display = "block";
	}
}