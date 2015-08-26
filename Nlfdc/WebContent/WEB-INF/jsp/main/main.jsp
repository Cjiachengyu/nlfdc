<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>宁陵县房地产发展保障管理中心</title>

<style>
span, li, a { font-size: 12px; }
.line_box {display: inline-block; }
.clear { clear: both; }
.hide {display: none; }
.right { float: right; }
.gray { color: grey; }
.main_content { width: 1080px; height: 1245px; margin: auto; margin-top: 5px; background-color: white; position: relative;  }
.row1_box { width: 1070px; margin: 5px; height: 300px; border: solid 1px white; }
.row2_box { width: 1070px; margin: 5px; height: 250px; border: solid 1px white; }
.row3_box { width: 1070px; margin: 5px; height: 250px; border: solid 1px white; }
.row3_plus_box { width: 1068px; margin: 5px; height: 162px; border: solid 1px #0099CC; }
.row4_box { width: 1070px; margin: 5px; height: 250px; border: solid 1px white; }
.row1_left_big { width: 708px; height: 298px; margin: 5px 5px 0px 0px; display: inline-block; border: solid 1px #0099CC;}
.row1_left_big_left { width: 357px; height: 298px; display: inline-block; position: relative; }
.row1_left_big_right { width: 353px; height: 298px; display: inline-block;  position: absolute; } 
.row1_right_small { width: 351px; height: 298px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 5px; right: 5px; }
.row2_col1 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 310px; left: 5px; }
.row2_col2 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 310px; left: 363px;  }
.row2_col3 { width: 351px; height: 505px;  display: inline-block; border: solid 1px #0099CC; position: absolute; top: 310px; right: 5px;}
.row3_col1 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 566px; left: 5px; }
.row3_col2 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 566px; left: 363px;  }
.row3_col3 { width: 351px; height: 250px;  display: inline-block; border: solid 1px #0099CC; position: absolute; top: 566px; right: 5px;}
.row4_col1 { width: 530px; height: 225px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 990px; left: 5px;}
.row4_col2 { width: 530px; height: 225px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 990px; right: 5px;}
.row4_col3 { width: 351px; height: 225px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 990px; right: 5px;}

.box_header { width: 98%; height: 30px; border-bottom: solid 1px #0099CC; position: relative; }
.box_header span { display: inline-block; color: white; text-align: center; padding: 4px 0; font-size: 14px; font-weight: bolder; 
	 margin: 6px 10px 4px 10px; width: 86px; height:17px; background-color: #0066CC; border-top-left-radius: 6px; border-top-right-radius: 6px; }
.box_header a { float: right; display: inline-block; width: 46px; height: 16px; margin: 7px; background-image: url(image/common/more_03.png); }
.box_content { width: 99%; position: relative; } 
.box_content ul {width: 96%; padding-left: 22px; }
.box_content ul li { width: 96%; line-height: 20px; border-bottom: dashed 1px gray; border-bottom-style: dotted; position: relative; }
.show_img {width: 300px; height: 260px; margin: 4px 5px 0px 5px; }
.image_selector { width: 300px; height: 30px; margin: -4px 5px 5px 5px; background: black; text-align: center; position: relative;  }
.dot { display: inline-block; width: 10px; height: 10px; margin: 10px 5px; border-radius: 50%; background-color: white; float: right;}
.choosed {display: inline-block; width: 15px; height: 15px; margin: 8px 5px; border-radius: 50%; background-color: green; float: right; }
.notification_link {color: blue; text-decoration: none; }
.notification_link:hover {color: red; text-decoration: none; }

.rolling_img_box {display: inline-block; width: 772px; height: 146px; margin: 8px 115px; position: relative; overflow: hidden; border: solid 1px black; }
.roll_img { width: 148px; height: 146px; position: absolute; }
</style>

<div class="main_content">
		
	<div class="row1_box">
	 	<div class="row1_left_big">
	 		<div class="row1_left_big_left">
	 			<img id="dot_image_1" src="image/common/sample1.png" class="show_img ">	
	 			<img id="dot_image_2" src="image/common/sample2.png" class="show_img hide">	
	 			<img id="dot_image_3" src="image/common/sample3.png" class="show_img hide">	
	 			<img id="dot_image_4" src="image/common/sample4.png" class="show_img hide">	
	 			<div class="image_selector">
	 				<a id="dot1" class="dot" href="javascript:chooseDot(1)"></a>
	 				<a id="dot2" class="dot" href="javascript:chooseDot(2)"></a>
	 				<a id="dot3" class="dot" href="javascript:chooseDot(3)"></a>
	 				<a id="dot4" class="choosed" href="javascript:chooseDot(4)"></a>
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
							<a class="notification_link" href="##">
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
							<a class="notification_link" href="##">
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
							<a class="notification_link" href="##">
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
							<a class="notification_link" href="##">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    </div>
	 	</div>
	 	
	 	<div class="row2_col3">
	 		<h3>图片链接</h3>
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
							<a class="notification_link" href="##">
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
							<a class="notification_link" href="##">
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
				<img id="sample1" src="image/common/sample1.png" class="roll_img" index="7" style="left: 7px;">
				<img id="sample2" src="image/common/sample2.png" class="roll_img" index="160" style="left: 160px;">
				<img id="sample3" src="image/common/sample3.png" class="roll_img" index="313" style="left: 313px;">
				<img id="sample4" src="image/common/sample4.png" class="roll_img" index="466" style="left: 466px;">
				<img id="sample5" src="image/common/sample1.png" class="roll_img" index="617" style="left: 617px;">
				<img id="sample6" src="image/common/sample2.png" class="roll_img" index="772" style="left: 772px;">
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
							<a class="notification_link" href="##">
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
							<a class="notification_link" href="##">
								${notification.shortTitle }
							</a>
							<span class="right gray">${notification.createDateString }</span>
							</li>
						</c:forEach>
					</ul>
		    	</div>
	 	</div>
	 	
	
	<div style="width: 100%; height: 20px; text-align: center; font-size: 12px; position: absolute; top: 1225px; ">
		今天访问次数： ${actionBean.thisDayCount }， 总访问次数： ${actionBean.totalCount }
	</div>
	
	</div>
</div>

<script src='js/jquery-1.11.2.min.js' type='text/javascript' ></script>

<script type="text/javascript">
	
	$(function(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth();
		var day = date.getDate();
		var weekDay = date.getDay();
		
		var weekDayStr = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
		
		var result = "今天是:"+year+"年"+month+"月"+day+"日 "+weekDayStr[weekDay];
		
		$("#date_info").html(result);	
		
		var speed=50//速度数值越大速度越慢
		var MyMar3=setInterval(move,speed)
		rolling_img_box.onmouseover = function() {clearInterval(MyMar3)}
		rolling_img_box.onmouseout = function() {MyMar3=setInterval(move,speed)}
	});
	
	function move()
	{
		var val1 = $("#sample1").attr("index");
		var val2 = $("#sample2").attr("index");
		var val3 = $("#sample3").attr("index");
		var val4 = $("#sample4").attr("index");
		var val5 = $("#sample5").attr("index");
		var val6 = $("#sample6").attr("index");

		
		val1 = val1 - 1;
		val2 = val2 - 1;
		val3 = val3 - 1;
		val4 = val4 - 1;
		val5 = val5 - 1;
		val6 = val6 - 1;

		if (val1 < -153)
		{
			val1+=925;
		}
		if (val2 < -153)
		{
			val2+=925;
		}
		if (val3 < -153)
		{
			val3+=925;
		}
		if (val4 < -153)
		{
			val4+=925;
		}
		if (val5 < -153)
		{
			val5+=925;
		}
		if (val6 < -153)
		{
			val6+=925;
		}

		$("#sample1").attr("index", val1);
		$("#sample2").attr("index", val2);
		$("#sample3").attr("index", val3);
		$("#sample4").attr("index", val4);
		$("#sample5").attr("index", val5);
		$("#sample6").attr("index", val6);

		$("#sample1").css("left", val1 + "px");
		$("#sample2").css("left", val2 + "px");
		$("#sample3").css("left", val3 + "px");
		$("#sample4").css("left", val4 + "px");
		$("#sample5").css("left", val5 + "px");
		$("#sample6").css("left", val6 + "px");
	}
	
	function chooseDot(index)
	{
		$("#dot_image_1").attr("class", "show_img hide");		
		$("#dot_image_2").attr("class", "show_img hide");		
		$("#dot_image_3").attr("class", "show_img hide");		
		$("#dot_image_4").attr("class", "show_img hide");	
		$("#dot_image_"+index).attr("class", "show_img");
		
		$("#dot1").attr("class", "dot");
		$("#dot2").attr("class", "dot");
		$("#dot3").attr("class", "dot");
		$("#dot4").attr("class", "dot");
		$("#dot"+index).attr("class", "choosed");
	}
</script>

<%@ include file="../component/CommonBottom.jsp"%>
