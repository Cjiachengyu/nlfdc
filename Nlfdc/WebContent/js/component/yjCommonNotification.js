var dateString;

var htmlFn = {
	gotoPageCallback : function(result) {
		$("#resource_list").html(result);
	},

	selectDate : function(dateString, isGet) {
		dateString = dateString.substr(0,10);
		$("#time").val(dateString);
		
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?selectdate",
			data : {
				dateString : dateString,
				isGet: isGet
			},
			success : function(result) {
				isTimeOut(result);

				$("#notification_list").html(result);
			}
		});
	},
	
	turnToPublishPage: function()
	{
		window.location.href =  htmlVal.htmlUrl + "?turntopublishpage=";
	},
	
};

$(function(){
	$("#time").datetimepicker({
	       showHour: false,
	       showMinute: false,
	       showTime: false,
	    });
	
});