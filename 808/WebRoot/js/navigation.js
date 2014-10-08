 
var i = 100;
var interval ;
$(document).ready(function () {
	 
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
   /*  $('#path1').click(function(){
    	 if($(this).hasClass('active')){
 			$(this).removeClass('active');	
 			stopAnimation();
 		}else{
 			$(this).addClass('active');
 			pathId = "2014100701";
 			startAnimation(pathId);
 		}
	});*/
   //轨迹回放
     $('#path2').click(function(){
    	 if($(this).hasClass('active')){
 			$(this).removeClass('active');	
 			stopAnimation();
 		}else{
 			$(this).addClass('active');
 			pathId = "001";
 			startAnimation(pathId);
 		}
	});

 });
