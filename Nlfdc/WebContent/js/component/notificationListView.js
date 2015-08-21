function deleteNotification(notificationId){
		ConfirmDialog("确定删除此通知？", function() {
			$.ajax({
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				type : "post",
				url : htmlVal.htmlUrl + "?deletenotification=",
				data : {
					notificationId : notificationId
				},
				success : function(result) {
					isTimeOut(result);
					
					if(result == "exception")
					{
						AlertDialog("出现错误，删除通知失败！");				
					}
					else
					{
						$("#notification_list").html(result);
					}
				}
			});
		}, function() {

		});
}

function setNotificationReaded(notificationId)
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?setnotificationreaded=",
		data : {
			notificationId : notificationId
		},
		success : function(result) {
			isTimeOut(result);
			
			if (result == "ok")
			{
				$("#notification_"+notificationId).attr("href", "javascript:viewNotification("+notificationId+", 0)");
				$("#not_read_tip_"+notificationId).hide();
				$("#operation_"+notificationId).hide();
			}
			
		//	$("#notification_list").html(result);
		}
	});
}

function viewNotification(notificationId, isGet)
{
	window.location.href = htmlVal.htmlUrl + "?viewnotification=&notificationId="+notificationId+"&isGet="+isGet;
}