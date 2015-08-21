/*
 *  name: according
 *  date: 2014-06-13
 *  author: kwu
 *  description: 手风琴菜单的简易jQuery插件
 */

// 对jQuery的扩展，立即执行的函数，$为形参，jQuery为传入的实际参数
(function($) {
	$.fn.extend({

		// 为jQuery的实例增加方法，jQuery.prototype = jQuery.fn
		accordion : function(options) {
			// 默认的参数设置
			var defaults = {
				accordion : "true", // 是否为手风琴似的
				speed : 300, // 动画的速度
				closedSign : '[+]', // 未展开时的标签
				openedSign : '[-]' // 展开时的标签
			};

			// 将传入的参数与默认值合并
			var opts = $.extend({}, defaults, options);

			// 此时this为调用这个方法的dom的jQuery对象
			var $accordion = this;

			$accordion.find("li").each(function() {
				var $this = $(this);
				var $this_a = $(this).find("a:first");

				// 在有子内容（ul）的a后面加上一个openedSign标记
				if ($this.find("ul").size() != 0) {
					$this_a.append("<span class=\"expander\" >" + opts.closedSign + "</span>");
				}

				// 避免href为#时，点击a回到顶部
				if ($this_a.attr("href") == "#") {
					$this_a.click(function() {
						return false;
					});
				}
			});

			$accordion.find("li.active").each(function() {
				var $this = $(this);

				// 展开class为active的li的父元素ul
				$this.parents("ul").slideDown(opts.speed);

				// 将原先的closedSign标记替换为openedSign
				$this.parents("ul").parent("li").find("span:first").html(opts.openedSign);
			});

			$accordion.find("li a").click(function() {
				var $this = $(this);
				if ($this.parent().find("ul").size() != 0) {
					// 如果accordion为true，菜单为手风琴似的，一个部分打开，其它部分会关闭
					if (opts.accordion) {
						// 如果和a同级的ul元素是展开的，则什么都不做
						if (!$this.parent().find("ul").is(':visible')) {
							// 点击的a的所有祖先ul
							var parents = $this.parent().parents("ul");

							// 所有展开的ul
							var ul_visible = $accordion.find("ul:visible");

							ul_visible.each(function(visibleIndex) {
								var close = true;
								parents.each(function(parentIndex) {
									// 展开的ul为点击的a的祖先ul时，则不收起此ul
									if (parents[parentIndex] == ul_visible[visibleIndex]) {
										close = false;
									}
								});
								if (close) {
									$(ul_visible[visibleIndex]).slideUp(opts.speed, function() {
										$(this).parent("li").find("span:first").html(opts.closedSign);
									});
								}
							});
						}
					}

					// 如果和a同级的第一个ul元素是展开的，则收起，并将a后面span的openedSign标记替换为closedSign，反之亦然
					if ($this.parent().find("ul:first").is(":visible")) {
						$this.parent().find("ul:first").slideUp(opts.speed, function() {
							$this.parent("li").find("span:first").delay(opts.speed).html(opts.closedSign);
						});
					}
					else {
						$this.parent().find("ul:first").slideDown(opts.speed, function() {
							$this.parent("li").find("span:first").delay(opts.speed).html(opts.openedSign);
						});
					}
				}
			});

			// 返回jQuery对象，使得插件可链式操作
			return $accordion;
		}
	});
})(jQuery);
