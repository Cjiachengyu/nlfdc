function deleteNotification(notificationId){
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
						$("#admin_nofication_list").html(result);
					}
				}
			});
}

function unDeleteNotification(notificationId){
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?undeletenotification=",
			data : {
				notificationId : notificationId
			},
			success : function(result) {
				isTimeOut(result);
				
				if(result == "exception")
				{
					AlertDialog("出现错误，还原通知失败！");				
				}
				else
				{
					$("#admin_nofication_list").html(result);
				}
			}
		});
}

function viewNotification(notificationId, isGet)
{
	window.location.href = htmlVal.htmlUrl + "?viewnotification=&notificationId="+notificationId+"&isGet="+isGet;
}