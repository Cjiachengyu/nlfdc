var htmlFn = {
		gotoPageCallback : function(result) {
			$("#textbook_list").html(result);
		},
};

function reselect()
{
	var schemaId = $("#schema_selecter").val();
	var subjectId = $("#subject_selecter").val();
	var category = $("#category_selecter").val();
	var gradeIndex = $("#grade_selecter").val();
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?reselect=",
		data : {
			schemaId: schemaId , 
			subjectId: subjectId,
			category: category,
			gradeIndex: gradeIndex,
		},
		success : function(result) {
			isTimeOut(result);
	
			window.location.href = window.location.href;
		}
	});
}

function editTextbook(textbookId)
{
	window.location.href = htmlVal.htmlOutUrl + "?turntoedittextbookpage=&textbookId="+textbookId;
}

$(function(){
	$("#admin_add_textbook_button").bind("click",function(){
		window.location.href =  htmlVal.htmlOutUrl + "?turntoaddtextbookpage=";
	});
});
