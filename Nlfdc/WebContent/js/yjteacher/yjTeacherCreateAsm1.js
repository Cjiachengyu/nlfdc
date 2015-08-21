var htmlFn = {
		
	selectTextCallback : function(bookId) {
		htmlFn.getResourceListView();
	},

	selectChapterCallback : function(chapterId, result) {
		$("#resource_list").html(result);
	},

	selectSectionCallback : function(sectionId, result) {
		$("#resource_list").html(result);
	},

	gotoPageCallback : function(result) {
		$("#resource_list").html(result);
	},
		
	getResourceListView : function() {
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?getresourcelistview",
			success : function(result) {
				$("#resource_list").html(result);
			}
		});
	},
	
	addResToAsm : function(resId) {
		// 防止延迟重复点击
		$("#add_que_button_"+resId).attr("href","javascript:void(0)");
		
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?addrestoasm",
			data: {resId: resId},
			success : function(result) {
				if ($("#is_in_mobile_browser").val() == 1)
				{
					isMobileTimeOut(result);
					if (result == "error")
					{
						Alert("出现错误，添加失败！");
					}
				}
				else
				{
					isTimeOut(result);
					if (result == "error")
					{
						AlertDialog("出现错误，添加失败！");
					}
				}

				$("#operate_que_button_"+resId).attr("class", "que_oper_btn_with_icon remove_que_button");
				$("#operate_que_button_"+resId).attr("href", "javascript:htmlFn.removeResFromAsm(" +resId+")");
				$("#operate_que_button_"+resId).html("移除");
				$("#create_asm_res_box_"+resId).attr("class", "simple_list_item clearfix selected_res_box");
				
				//$("#resource_list").html(result);
			}
		});
	},

	removeResFromAsm : function(resId) {
		// 防止延迟重复点击
		$("#remove_que_button"+resId).attr("href","javascript:void(0)");
		
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?removeresfromasm",
			data: {resId: resId, step: 1},
			success : function(result) {
				
				if ($("#is_in_mobile_browser").val() == 1)
				{
					isMobileTimeOut(result);
					if (result == "error")
					{
						Alert("出现错误，移出失败！");
					}
				}
				else
				{
					isTimeOut(result);
					if (result == "error")
					{
						AlertDialog("出现错误，移出失败！");
					}
				}
				
				$("#operate_que_button_"+resId).attr("class", "que_oper_btn_with_icon add_que_button");
				$("#operate_que_button_"+resId).attr("href", "javascript:htmlFn.addResToAsm(" +resId+")");
				$("#operate_que_button_"+resId).html("添加");
				$("#create_asm_res_box_"+resId).attr("class", "simple_list_item clearfix");
//				$("#resource_list").html(result);
			}
		});
	},
	
	clearChoosedResList: function()
	{
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?clearchoosedreslist",
			success : function(result) {
				isTimeOut(result);

				$("#resource_list").html(result);
			}
		});
	},
	
};

$(function(){
	$("#chapter_all").html("所有课时");
	$("#chapter_a_0").html("所有课时");
});

