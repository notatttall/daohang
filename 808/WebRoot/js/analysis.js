;(function($){	
	$.fn.tab=function(options){
		var opt={
			tabNav:'.tab-nav li',   //选项卡tab
			tabMain:'.tab-cont',   //选项卡主体内容
			tabTrigger:'click',    //tab绑定的触发事件
			hoverClass:'tab-hover',  //tab鼠标hover时的class
			activeClass:'tab-active'  //tab选中的效果的class
		}		
		var option=$.extend({},opt,options);
		var $tabNav=$(option.tabNav),
		    $cont=$(option.tabMain);
		   $tabNav.on(option.tabTrigger,function(){
		      var $tabIndex=$(this).index();
			  $(this).addClass(option.activeClass).siblings().removeClass(option.activeClass);
			  $(option.tabMain).eq($tabIndex).show().siblings(option.tabMain).hide();
		   })
		   $tabNav.hover(function(){
		        $(this).addClass(option.hoverClass).siblings().removeClass(option.hoverClass);
		   },function(){
		        $(this).removeClass(option.hoverClass);
		   })		
	}
 
})(jQuery);
$(document).ready(function () {
	$(".tab-nav").tab();
	
	$('.caidan_top li').click(function(){
		$('.caidan_top li').removeClass('head_active');	
		$(this).addClass('head_active');
	});
	$("#logout").click(function(){
		window.location.href = "http://localhost:8083/808/login.html";
	}); 
	$("#jk").click(function(){
		window.location.href = "http://localhost:8083/808/test.html";
	}); 
	$("#yy").click(function(){
		window.location.href = "http://localhost:8083/808/analysis.html";
	}); 
		
});