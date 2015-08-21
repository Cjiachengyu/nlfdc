function selectSchema(scmId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?selectschema=",
		data : {
			selectedSchemaId : scmId
		},
		success : function(result) {
			isTimeOut(result);

			$("#textbooks_in_schema").html(result);
			getTextbookOutList();
		}
	});
}

function getTextbookOutList() {
	var category = $("#category").val();
	var subjectId = $("#subject").val();

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url :  htmlVal.htmlUrl + "?gettextbookoutlist=",
		data : {
			selectedCategory : category,
			selectedSubjectId : subjectId
		},
		success : function(result2) {
			isTimeOut(result2);

			$("#textbooks_out_schema").html(result2);
		}
	});
}

function addTextbookToSchema(bookId) {
	var scmId = $("#schema").val();
	if (scmId == -1) {
		AlertDialogWithCallback("左侧没有大纲被选择，无法添加课本到大纲！", null, function() {
			$("#schema").focus();
		});
		return;
	}

	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url :  htmlVal.htmlUrl + "?addtextbooktoschema=",
		data : {
			bookId : bookId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == 'ok') {
				var scmId = $("#schema").val();
				selectSchema(scmId);
			}
			else {
				AlertDialog("添加失败");
			}
		}
	});
}
function deleteTextbookFromSchema(bookId) {
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url :  htmlVal.htmlUrl + "?deletetextbookfromschema=",
		data : {
			bookId : bookId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == 'ok') {
				var scmId = $("#schema").val();
				selectSchema(scmId);
			}
			else {
				AlertDialog("删除失败");
			}
		}
	});
}

function addSchema() {
	createBorderMaskLayer("add_schema_form", "添加大纲", getLoading(), 450, 250);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddschemaview=",
		success : function(result) {
			isTimeOut(result);

			$("#add_schema_form").html(result);
		}
	});
}

$(function(){
	$("#admin_add_schema_button").bind("click",addSchema);
});
