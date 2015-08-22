/*Javascript代码片段*/
/*下面我们来写JS，用的是JQuery库*/

var isFirst = true;
$(function(){
  //li.item的点击事件
  $(".item").click(function(){
    
	var clickedFristMenuId = $(this).val();
	var currentFirstMenuId = $("#current_first_menuid").val();
	
	if ((clickedFristMenuId == currentFirstMenuId) && !isFirst )
	{
		return;
	}
	else
	{
		$("#current_first_menuid").val(clickedFristMenuId);
	}
	
	isFirst = false;
    //siblings:是当前点击的对象的兄弟元素，也就是所有的li.item元素
    //移除所有兄弟元素的cover这个class值
    //children:是找到当前点击元素的子级元素span
    $(this).siblings().removeClass("cover").children("span").removeClass("bod").siblings().slideUp(300);

    //addClass:给当前点击元素添加cover这个clas值
    //alert($(this).children("span")[0].innerHTML);
    if($(this).children("span")[0].innerHTML != "*")
    {
    	//alert("ok");
    	$(this).addClass("cover").children("span").addClass("bod").siblings().slideDown(300);
   		$(this).children("span")[0].innerHTML="*";
   		for (var i = $(this).siblings().children("span").length - 1; i >= 0; i--) {
   			$(this).siblings().children("span")[i].innerHTML = "";
   		};
   		
    }
    else
    {
    	$(this).removeClass("cover").children("span").removeClass("bod").siblings().slideUp(300);
    	$(this).children("span")[0].innerHTML="";
    }
    
    $(".citem").removeClass("selected_citem");
   // alert("firstMenuId: " + $(this).val());
    
  });

  $(".citem").bind("click", function(){
	  var clickedSecondMenuId = $(this).val();
	  var currentSecondMenuId = $("#current_second_menuid").val();
	  if (clickedSecondMenuId == currentSecondMenuId)
	  {
		  return;
	  }
	  else
	  {
		  $("#current_second_menuid").val(clickedSecondMenuId);
	  }
	  
      $(".citem").removeClass("selected_citem");      
      $(this).addClass("selected_citem");
      
     // alert("secondMenuId: " + $(this).val());
      return false;

  });

  $(".citem").delegate(".edit","click", function(e) {
      alert("edit"+this.id);
      e.stopPropagation();
    });

   $(".citem").delegate(".del","click", function(e) {
      alert("delete"+this.id);
      e.stopPropagation();
    });

   
   $(".cover")[0].click();
});


