var htmlFn = {

	moveUp: function(resId){
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?moveup",
			data: {resId: resId},
			success : function(result) {
				isTimeOut(result);
				
				if (result == "error")
				{
					AlertDialog("出现错误，上移失败！");
				}
				else
				{
					$("#asm_res_list").html(result);
				}
			}
		});
	},
		
	moveDown: function(resId){
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?movedown",
			data: {resId: resId },
			success : function(result) {
				isTimeOut(result);
				
				if (result == "error")
				{
					AlertDialog("出现错误，下移失败！");
				}
				else
				{
					$("#asm_res_list").html(result);
				}
			}
		});
	},
		
	removeFromAsm: function(resId){
		$.ajax({
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			type : "post",
			url : htmlVal.htmlUrl + "?removeresfromasm",
			data: {resId: resId, step: 2},
			success : function(result) {
				isTimeOut(result);
				
				if (result == "error")
				{
					AlertDialog("出现错误，删除失败！");
				}
				else
				{
					var num = parseInt($("#create_asm_res_num").html()) - 1;
					$("#create_asm_res_num").html(num);
					$("#asm_res_list").html(result);
				}
			}
		});
	},
		
};