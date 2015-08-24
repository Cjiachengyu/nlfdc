<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>宁陵县房地产发展保障管理中心</title>

<style>
span, li, a { font-size: 12px; }
.line_box {display: inline-block; }
.clear { clear: both; }
.hide {display: none; }
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
.row2_col3 { width: 351px; height: 250px;  display: inline-block; border: solid 1px #0099CC; position: absolute; top: 310px; right: 5px;}
.row3_col1 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 566px; left: 5px; }
.row3_col2 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 566px; left: 363px;  }
.row3_col3 { width: 351px; height: 250px;  display: inline-block; border: solid 1px #0099CC; position: absolute; top: 566px; right: 5px;}
.row4_col1 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 990px; left: 5px;}
.row4_col2 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 990px; left: 363px;}
.row4_col3 { width: 351px; height: 250px; display: inline-block; border: solid 1px #0099CC; position: absolute; top: 990px; right: 5px;}

.box_header { width: 98%; height: 30px; border-bottom: solid 1px #0099CC; position: relative; }
.box_header span { display: inline-block; color: white; text-align: center; padding: 4px 0; font-size: 14px; font-weight: bolder; 
	 margin: 6px 10px 4px 10px; width: 86px; height:17px; background-color: #0066CC; border-top-left-radius: 6px; border-top-right-radius: 6px; }
.box_header a { float: right; display: inline-block; width: 46px; height: 16px; margin: 7px; background-image: url(image/common/more_03.png); }
.box_content { width: 99%; position: relative; } 
.box_content ul {width: 96%; padding-left: 22px; }
.box_content ul li { width: 92%; line-height: 20px; border-bottom: dashed 1px gray; border-bottom-style: dotted; }
.show_img {width: 300px; height: 260px; margin: 4px 5px 0px 5px; }
.image_selector { width: 300px; height: 30px; margin: -4px 5px 5px 5px; background: black; text-align: center; position: relative;  }
.dot { display: inline-block; width: 10px; height: 10px; margin: 10px 5px; border-radius: 50%; background-color: white; float: right;}
.choosed {display: inline-block; width: 15px; height: 15px; margin: 8px 5px; border-radius: 50%; background-color: green; float: right; }
.notification_link {color: blue; text-decoration: none; }
.notification_link:hover {color: red; text-decoration: none; }
</style>

<div class="main_content">
		
	<div class="row1_box">
	 	<div class="row1_left_big">
	 		<div class="row1_left_big_left">
	 			<!-- 第1行第1个 放动态图 -->
	 			<img id="image_1" src="image/common/sample1.png" class="show_img ">	
	 			<img id="image_2" src="image/common/sample2.png" class="show_img hide">	
	 			<img id="image_3" src="image/common/sample3.png" class="show_img hide">	
	 			<img id="image_4" src="image/common/sample4.png" class="show_img hide">	
	 			<div class="image_selector">
	 				<a class="dot"></a>
	 				<a class="dot"></a>
	 				<a class="dot"></a>
	 				<a class="choosed"></a>
	 			</div>	
	 		</div>
	 		
	 		<div class="row1_left_big_right">
	 			<!-- 第1行第2个  政务公开-->
	 			<div class="box_header">
	 				<span>${actionBean.menuSelector.firstMenuList[1].firstMenuName }</span>
	 				<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[1].firstMenuId }"></a>
				</div>
	 		
	 			<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[1] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
				</div>	
	 			
	 		</div>
	 		
	 	</div>
	 	
	 	<div class="row1_right_small">
	 		<!-- 第1行第3个  住房保障 -->
			<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[2].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[2].firstMenuId }"></a>
			</div>
	 		
	 		<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[2] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
		    </div>	
	 	</div>
	 	
	</div>
	
	<div class="row2_box">
	 	<div class="row2_col1">
	 		<!-- 第二行第一个 房屋登记 -->
			<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[3].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[3].firstMenuId }"></a>
			</div>
			<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[3] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
		    </div>
	 	</div>
	 	
	 	<div class="row2_col2">
	 		<!-- 第二行第二个 维修资金-->
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[4].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[4].firstMenuId }"></a>
				</div>
				
				<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[4] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
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
	 			<!-- 第三行第一个 物业管理 -->
					<div class="box_header">
						<span>${actionBean.menuSelector.firstMenuList[5].firstMenuName }</span>
						<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[5].firstMenuId }"></a>
					</div>
					<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[5] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
		    		</div>
	 		</div>
	 		
	 		<div class="row3_col2">
	 				<!-- 第三行第二个 网上备案 -->
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[7].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[7].firstMenuId }"></a>
				</div>
				<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[7] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
		    	</div>
	 		</div>
	 	
	 		<div class="row3_col3">
	 			<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[8].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[8].firstMenuId }"></a>
				</div>
				<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[8] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
		    	</div>
	 		</div>
	 	
	</div>
	
	<div class="row3_plus_box">
		<!-- 第四行 滚动图 -->
	 			<img src="image/common/act_pic_03.png" style="margin: 12px 5px; display: inline-block;">

	 			<div id="butong_net_left" style="overflow:hidden;width: 1000px; margin-top:5px; display: inline-block;">
				<table cellpadding="0" cellspacing="0" border="0">
				<tr><td id="butong_net_left1" valign="top" align="center">
				<table cellpadding="2" cellspacing="0" border="0">
				<tr align="center">
				<td><img src="image/common/sample1.png"  style="width: 148px; height: 146px;"></td>
				<td><img src="image/common/sample2.png"  style="width: 148px; height: 146px;"></td>
				<td><img src="image/common/sample3.png"  style="width: 146px; height: 146px;"></td>
				<td><img src="image/common/sample4.png"  style="width: 146px; height: 146px;"></td>
				</tr>
				</table>
				</td>
				<td id="butong_net_left2" valign="top" ></td>
				</tr>
				</table>
				</div>
	</div>

	<div class="row4_box">
	 	<div class="row4_col1">
	 		<!-- 第五行 第一个 信息中心 -->
				<div class="box_header">
					<span>${actionBean.menuSelector.firstMenuList[9].firstMenuName }</span>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.firstMenuList[9].firstMenuId }"></a>
				</div>
	 			<div class="box_content">
					<ul>
						<c:forEach var="notification" items="${actionBean.firstMenuNotificationList[9] }">
							<li>[${notification.secondMenuName }] <a class="notification_link" href="##">${notification.shortTitle }</a></li>
						</c:forEach>
					</ul>
		    	</div>
	 	</div>
	 	
	 	<div class="row4_col2">
	 	<!-- 第五行 第二个 监督执法 -->
				<div class="box_header">
					<span></span>
					<a href="javascript:void(0)"></a>
				</div>
				
	 	</div>
	 	
	 	<div class="row4_col3">
	 		<div class="box_header">
					<span></span>
					<a href="javascript:void(0)"></a>
			</div>
			
	 	</div>
	</div>
	
</div>

<script src='js/jquery-1.11.2.min.js' type='text/javascript' ></script>

<script type="text/javascript">
	var speed=30//速度数值越大速度越慢
	butong_net_left2.innerHTML=butong_net_left1.innerHTML;

	function Marquee3(){
		if(butong_net_left2.offsetWidth-butong_net_left.scrollLeft<=0)
		{
			butong_net_left.scrollLeft-=butong_net_left1.offsetWidth
		}
		else
		{
			butong_net_left.scrollLeft++
		}
	}
	var MyMar3=setInterval(Marquee3,speed)
	butong_net_left.onmouseover = function() {clearInterval(MyMar3)}
	butong_net_left.onmouseout = function() {MyMar3=setInterval(Marquee3,speed)}

	$(function(){
		
	});
</script>

	

<%@ include file="../component/CommonBottom.jsp"%>
