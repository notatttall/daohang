 
var i = 100;
var interval ;
$(document).ready(function () {
	
	$('.caidan_top li').click(function(){
		$('.caidan_top li').removeClass('head_active');	
		$(this).addClass('head_active');
	});
	$("#logout").click(function(){
		window.location.href = "http://localhost:8083/808/login.html";
	}); 
	$("#yy").click(function(){
		window.location.href = "http://localhost:8083/808/analysis.html";
	}); 
	$("#jk").click(function(){
		window.location.href = "http://localhost:8083/808/test.html";
	}); 
	
	
	 $('li.button a').click(function (e) {
        var dropDown = $(this).parent().next();
        $('.dropdown').not(dropDown).slideUp('slow');
        dropDown.slideToggle('slow');
        e.preventDefault();
    })
    
    var str = "<li class=\"li3\"><a href=\"#\">"+ "黑D22222" + "</a></li>";
 
     $("#ul1").html(str);
     $('.li3').click(function(){
    		if($(this).hasClass('active')){
    			$(this).removeClass('active');	
    		}else{
    			$(this).addClass('active');
    			var point = new AMap.LngLat(126.538803,45.826); // 创建点坐标
    			mapObj.setCenter(point); // 设置地图中心点坐标
    		}
 	});
     
     //轨迹回放
     $('.path').click(function(){
    	 if($('.path').hasClass('active')){
 			$('.path').removeClass('active');	
 			stopAnimation();
 		}else{
 			$(this).addClass('active');
 			pathId = $(this).text();
 			startAnimation(pathId);
 		}
	});
     
     //任务管理
     $('#task').click(function(){
    	 mapObj.destroy();
    	 $(".task").css("display","block");
    	 //$(".modify").css("display","none");
	});
   /*  $('.path').click(function(){
    	 
    	 $(this).addClass('active');
		 pathId = $(this).text();
		 
    	 if($(this).hasClass('active')){
 			$(this).removeClass('active');	
 			stopAnimation();
 		}else{
 			
 		}
	});*/

 });
