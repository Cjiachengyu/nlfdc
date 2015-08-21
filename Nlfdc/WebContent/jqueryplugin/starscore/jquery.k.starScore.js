/*
 *  name: starScore
 *  date: 2014-07-03
 *  author: kwu
 *  description: 星星打分的简易jQuery插件
 */

(function($){
    $.fn.extend({
    	starScore: function(options){
			var defaults = {
				// 星星的总数目
				liLength: 5,
				// 初始时点亮的星星的数目
				initIndex: 3,
				// 每个星星对应的信息
				messageArray: [
					"非常简单|十分容易的题目",
					"比较简单|大多数学生可以轻松作答",
					"难度适中|适合于一般的作业练习",
					"比较困难|大多数学生可能会遇到困难",
					"非常困难|需要对概念理解透彻才能答好"
				],
				difficultySign: "难度",
				starSign: "星"
			};
			var opts = $.extend(defaults, options);

			var $myStar = this;
			
			// 根据星星的数目构造对应的html结构
			var starLiHtml = "";
			for (var i = 0; i < opts.liLength; i++)
			{
				starLiHtml += "<li><a href='#'>" + (i + 1) + "</a></li>";
			}
			var starHtml = "<div id='difficulty' style='display: none;'></div>"+
				"<div id='star' class='clearfix'>"+
				"<span>" + opts.difficultySign + "：</span>"+
				"<ul>" + starLiHtml + "</ul>"+
				"<span></span>"+
				"<p></p>"+
				"</div>";
			$myStar.html(starHtml);
			
			var msgArray = opts.messageArray;
			
			// 得到要操纵的dom
			var difficulty = document.getElementById("difficulty");
			var starDiv = document.getElementById("star");
			var sLi = starDiv.getElementsByTagName("li");				// 每个星星的节点
			var sUl = starDiv.getElementsByTagName("ul")[0];			// 所有星星的父元素
			var sSpan = starDiv.getElementsByTagName("span")[1];		// 显示鼠标点击后信息的区域
			var sP = starDiv.getElementsByTagName("p")[0];				// 显示鼠标悬浮后信息的浮动层
			
			// 鼠标悬浮或点击星星时，点亮星星的方法
			var showStar = function(hoverIndexArg, clickIndexArg){
				var index = hoverIndexArg || clickIndexArg;
				for(var i = 0; i < opts.liLength; i++) 
				{
					sLi[i].className = i < index ? "on" : "";
				}
			};

			// 构建初始时的状态
			showStar(0, opts.initIndex);
			difficulty.innerHTML = opts.initIndex;
			if (opts.initIndex > 0) {
				
				if (msgArray[opts.initIndex - 1].split("|")[0] == null || msgArray[opts.initIndex - 1].split("|")[0] == '' )
				{
					sSpan.innerHTML = "";
				}
				else
				{
					sSpan.innerHTML = "<strong> " + (opts.initIndex) + opts.starSign + " </strong>（" + msgArray[opts.initIndex - 1].split("|")[0] + "）";
				}
			}

			var clickIndex = opts.initIndex;
			
			// 给每个星星绑定事件
			for(var i = 0; i < opts.liLength; i++)
			{
				sLi[i].index = i + 1;
				
				// 鼠标悬浮事件
				sLi[i].onmouseover = function ()
				{
					showStar(this.index, 0);
					if (msgArray[this.index - 1].split("|")[0] != null && msgArray[this.index - 1].split("|")[0] != '')
					{
						// 浮动层显示
						sP.style.display = "block";
						// 计算浮动层位置
						sP.style.left = sUl.offsetLeft + this.index * this.offsetWidth - 99 + "px";
						// 构造浮动层文字内容
						sP.innerHTML = "<em><b>" + this.index + "</b>" + opts.starSign + " " + msgArray[this.index - 1].split("|")[0] + "</em>" + msgArray[this.index - 1].split("|")[1];
					}
				};
				
				// 鼠标离开事件（恢复上次点击时的状态）
				sLi[i].onmouseout = function ()
				{
					showStar(0, clickIndex);
					// 关闭浮动层
					sP.style.display = "none";
				};
				
				// 鼠标点击事件
				sLi[i].onclick = function ()
				{
					clickIndex = this.index;
					difficulty.innerHTML = clickIndex;
					if (msgArray[this.index - 1].split("|")[0] == null || msgArray[this.index - 1].split("|")[0] == '')
					{
						sSpan.innerHTML = "";
					}
					else
					{
						sSpan.innerHTML = "<strong>" + (this.index) + opts.starSign + " </strong>（" + msgArray[this.index - 1].split("|")[0] + "）";
					}
				};
			}
			
			return $myStar;
		}
    });
})(jQuery);