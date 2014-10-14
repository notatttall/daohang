/**
 * @author hu
 */

$(function () {
		function resizeBanner(){
			var ControlWidth=document.documentElement.clientWidth,
				ControlHeight=document.documentElement.clientHeight,
			    obj=$(".top-img"),
			    banner_width="",main_height="",
			    
			banner_height= ControlHeight -70 - 200;
			banner_width = (ControlWidth -obj.width())/2 ;
			banner_width = banner_width >0 ? banner_width  + "px" : "0px"
			banner_height = banner_height >292 ? banner_height  + "px" : "292px"
			
			$(".top").css("height",banner_height);
			
		
			$(".top-img").css("left",banner_width);

		}
	
		resizeBanner();
		
		$(window).resize(function(){
			resizeBanner();
			
		}); 
	
});


$(function() {

	//全局回车事件,按回车执行用户登录
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			window.location.href = "http://localhost:8083/808/test.html";
		}
	}
	$("#btnSubmit").click(function(){
		window.location.href = "http://localhost:8083/808/test.html";
	}); 

});