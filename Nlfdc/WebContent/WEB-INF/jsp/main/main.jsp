<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>宁陵县房地产发展保障管理中心</title>
<link href="css/main/main.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div class="main_content">
		
	<div class="row1_box">
	 	<div class="row1_left_big">
	 		<div class="row1_left_big_left">
	 			<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.newsImageList[0].notificationId }"
	 				title="${actionBean.newsImageList[0].notificationTitle }">
	 			<img id="dot_image_1" src="${actionBean.newsImageList[0].imageUrl }" class="show_img ">	
	 			</a>
	 			<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.newsImageList[1].notificationId }"
	 				title="${actionBean.newsImageList[1].notificationTitle }">
	 			<img id="dot_image_2" src="${actionBean.newsImageList[1].imageUrl }" class="show_img hide">
	 			</a>	
	 			<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.newsImageList[2].notificationId }"
	 				title="${actionBean.newsImageList[2].notificationTitle }">
	 			<img id="dot_image_3" src="${actionBean.newsImageList[2].imageUrl }" class="show_img hide">
	 			</a>	
	 			<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.newsImageList[3].notificationId }"
	 				title="${actionBean.newsImageList[3].notificationTitle }">
	 			<img id="dot_image_4" src="${actionBean.newsImageList[3].imageUrl }" class="show_img hide">
	 			</a>
	 			<div class="image_selector">
	 				<a id="dot1" class="dot" href="##" onmouseover="chooseDot(1)"></a>
	 				<a id="dot2" class="dot" href="##" onmouseover="chooseDot(2)"></a>
	 				<a id="dot3" class="dot" href="##" onmouseover="chooseDot(3)"></a>
	 				<a id="dot4" class="choosed" href="##" onmouseover="chooseDot(4)"></a>
	 			</div>	
	 		</div>
	 		
	 		<div class="row1_left_big_right">
	 			<div class="box_header">
	 				<span>${actionBean.menuSelector.firstMenuList[1].firstMenuName }</span>
	 				<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[1].firstMenuId }"></a>
				</div>
	 		
	 			<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[1] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
				</div>	
	 		</div>
	 	</div>
	 	
	 	<div class="row1_right_small">
			<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[2].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[2].firstMenuId }"></a>
			</div>
	 		
	 		<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[2] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    </div>	
	 	</div>
	 	
	</div>
	
	<div class="row2_box">
	 	<div class="row2_col1">
			<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[3].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[3].firstMenuId }"></a>
			</div>
			<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[3] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    </div>
	 	</div>
	 	
	 	<div class="row2_col2">
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[4].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[4].firstMenuId }"></a>
				</div>
				
				<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[4] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    </div>
	 	</div>
	 	
	 	<div class="row2_col3" style="text-align: center; ">
	 		<a href="index?showtip" target="_blank"><img src="image/common/link_pic1.png" style="margin-top: 33px;"></a>
	 		<a href="index?showtip" target="_blank"><img src="image/common/link_pic1.png" style="margin-top: 33px;"></a>
	 		<a href="index?showtip" target="_blank"><img src="image/common/link_pic1.png" style="margin-top: 33px;"></a>
	 		<a href="index?showtip" target="_blank"><img src="image/common/link_pic1.png" style="margin-top: 33px;"></a>
	 	</div>
	</div>
	
	<div class="row3_box">
	 		<div class="row3_col1">
					<div class="box_header">
						<span>${actionBean.menuSelector.firstMenuList[5].firstMenuName }</span>
						<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[5].firstMenuId }"></a>
					</div>
					<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[5] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    		</div>
	 		</div>
	 		
	 		<div class="row3_col2">
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[7].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[7].firstMenuId }"></a>
				</div>
				<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[7] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    	</div>
	 		</div>
	 	
	</div>
	
	<div class="row3_plus_box">
		<!-- 第四行 滚动图 -->
	 			<img src="image/common/act_pic_03.png" style="margin: 12px 5px; display: inline-block;">
				
				<div id="rolling_img_box" class="rolling_img_box">
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[0].notificationId }"
					title="${actionBean.rollImageList[0].notificationTitle }">
				<img id="sample1" src="${actionBean.rollImageList[0].imageUrl }" class="roll_img" index="7" style="left: 7px;">
				</a>
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[1].notificationId }"
					title="${actionBean.rollImageList[1].notificationTitle }">
				<img id="sample2" src="${actionBean.rollImageList[1].imageUrl }" class="roll_img" index="160" style="left: 160px;">
				</a>
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[2].notificationId }"
					title="${actionBean.rollImageList[2].notificationTitle }">
				<img id="sample3" src="${actionBean.rollImageList[2].imageUrl }" class="roll_img" index="313" style="left: 313px;">
				</a>
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[3].notificationId }"
					title="${actionBean.rollImageList[3].notificationTitle }">
				<img id="sample4" src="${actionBean.rollImageList[3].imageUrl }" class="roll_img" index="466" style="left: 466px;">
				</a>
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[0].notificationId }"
					title="${actionBean.rollImageList[0].notificationTitle }">
				<img id="sample5" src="${actionBean.rollImageList[0].imageUrl }" class="roll_img" index="617" style="left: 617px;">
				</a>
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[1].notificationId }"
					title="${actionBean.rollImageList[1].notificationTitle }">
				<img id="sample6" src="${actionBean.rollImageList[1].imageUrl }" class="roll_img" index="772" style="left: 772px;">
				</a>
				<a href="commonusernotificationmain?viewnotification=&notificationId=${actionBean.rollImageList[2].notificationId }"
					title="${actionBean.rollImageList[2].notificationTitle }">
				<img id="sample7" src="${actionBean.rollImageList[2].imageUrl }" class="roll_img" index="925" style="left: 925px;">
				</a>
				</div>
	</div>

	<div class="row4_box">
	 	<div class="row4_col1">
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[8].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[8].firstMenuId }"></a>
				</div>
				<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[8] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    	</div>
	 	</div>
	
	 	<div class="row4_col2">
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[9].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[9].firstMenuId }"></a>
				</div>
	 			<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[9] }">
							<li>
							[${notification.secondMenuName }] 
							<a class="notification_link" href="commonusernotificationmain?viewnotification=&notificationId=${notification.notificationId }"
								title="${notification.title }">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    	</div>
	 	</div>
	 	
	
	<div style="width: 100%; height: 20px; text-align: center; font-size: 12px; position: absolute; top: 1225px;">
		今天访问次数： ${actionBean.thisDayCount }， 总访问次数： ${actionBean.totalCount }
	</div>
	
	</div>
</div>

<script type="text/javascript">
	var rollingImageLength = $("#rolling_img_box img").length;
	
	$(function(){
		var speed=50//速度数值越大速度越慢
		var MyMar3=setInterval(move,speed)
		rolling_img_box.onmouseover = function() {clearInterval(MyMar3)}
		rolling_img_box.onmouseout = function() {MyMar3=setInterval(move,speed)}
	});
	
	function move()
	{
		for( var i = 1; i <= rollingImageLength; i++)
		{
			var val = $("#sample"+i).attr("index");
			val--;
			if (val < -153)
			{
				val+=1070;
			}
			$("#sample"+i).attr("index", val);
			$("#sample"+i).css("left", val + "px");

		}
	}
	
	function chooseDot(index)
	{
		$(".show_img").addClass("hide");
		$("#dot_image_"+index).attr("class", "show_img");
		$(".choosed").attr("class", "dot");
		$("#dot"+index).attr("class", "choosed");
	}
</script>

<%@ include file="../component/CommonBottom.jsp"%>