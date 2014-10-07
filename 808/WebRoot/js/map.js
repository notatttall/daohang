
//lineArr轨迹数组   marker2轨迹车辆  polyline轨迹
var mapObj,toolBar,inforWindow,lineArr,marker2,polyline;
//起、终点
var start_xy = new AMap.LngLat(126.538103,45.856);
var end_xy = new AMap.LngLat(126.548803,45.825);
$(document).ready(function () {
	
	//初始化地图
	mapInit();
	
	//driving_route();
});
//初始化地图对象，加载地图
function mapInit(){
	
	mapObj = new AMap.Map("map");
	
	var point = new AMap.LngLat(126.538803,45.826); // 创建点坐标
	mapObj.setCenter(point); // 设置地图中心点坐标
	
	//在地图中添加ToolBar插件
	mapObj.plugin(["AMap.ToolBar"],function(){		
		toolBar = new AMap.ToolBar();
		mapObj.addControl(toolBar);		
	});
	//加载比例尺插件
	mapObj.plugin(["AMap.Scale"],function(){		
		scale = new AMap.Scale();
		mapObj.addControl(scale);
	});
	//在地图中添加鹰眼插件
	mapObj.plugin(["AMap.OverView"],function(){
		//加载鹰眼
		overView = new AMap.OverView({
			visible:true //初始化隐藏鹰眼
		});
		mapObj.addControl(overView);
	});

	//窗体信息
	inforWindow = new AMap.InfoWindow({  
		autoMove:true,
		offset:new AMap.Pixel(5,-20)
	});
	
	//绘制轨迹
	AMap.event.addListener(mapObj,"complete",completeEventHandler); 
	
}

//地图图块加载完毕后执行函数
function completeEventHandler(){  

	lineArr = new Array(); 
	var startLocation;  //车辆起始位置
	var carId = "黑D22222";
	var pathId = "2014100701";
	 $.ajax({ 
     	type:"POST", //请求方式 
   	 	url:"servlet/CarServlet", //请求路径 
    	cache: false,
    	async: false,  
     	data:{ "method": "getCarPath", "carId": carId,"pathId":pathId},  //传参 
    	dataType: 'json',   //返回值类型 
        success:function(CarPath){ 
          $.each(CarPath.locations, function(i,location){  
	          if( i == 0){
	         	  startLocation = new AMap.LngLat(location.longitude,location.latitude);
	          }
		      lineArr.push(new AMap.LngLat(location.longitude,location.latitude));
  		  });
        },
		error : function(json){
		}
    }); 
    
	 marker2 = new AMap.Marker({
		map:mapObj,
		//draggable:true, //是否可拖动
		position:startLocation,//基点位置
		icon:"http://code.mapabc.com/images/car_03.png", //marker图标，直接传递地址url
		offset:new AMap.Pixel(-26,-13), //相对于基点的位置
		autoRotation:true
	});
	//绘制轨迹
	polyline  = new AMap.Polyline({
		map:mapObj,
		path:lineArr,
		strokeColor:"#00A",//线颜色
		strokeOpacity:1,//线透明度
		strokeWeight:3,//线宽
		strokeStyle:"solid"//线样式
	});
	mapObj.setFitView();
  }
//开始播放轨迹
function startAnimation() { 
	polyline.show();
	marker2.show();
	marker2.moveAlong(lineArr,500);
	
}
//暂停播放轨迹
  function stopAnimation() {   
	polyline.hide();
	marker2.hide();
	marker2.stopMove();
 }
  
  
  
//驾车导航
function driving_route() {
	var MDrive;
	mapObj.plugin(["AMap.Driving"], function() {
		var DrivingOption = {
			//驾车策略，包括 LEAST_TIME，LEAST_FEE, LEAST_DISTANCE,REAL_TRAFFIC
			policy: AMap.DrivingPolicy.LEAST_TIME 
		};        
        MDrive = new AMap.Driving(DrivingOption); //构造驾车导航类 
        AMap.event.addListener(MDrive, "complete", driving_routeCallBack); //返回导航查询结果
        MDrive.search(start_xy, end_xy); //根据起终点坐标规划驾车路线
    });
}
//导航结果展示
function driving_routeCallBack(data) {
	var routeS = data.routes;
	if (routeS.length <= 0) {
		document.getElementById("result").innerHTML = "未查找到任何结果!<br />建议：<br />1.请确保所有字词拼写正确。<br />2.尝试不同的关键字。<br />3.尝试更宽泛的关键字。";
	} 
	else{ 
		route_text="";
	 	for(var v =0; v< routeS.length;v++){
			//驾车步骤数
			steps = routeS[v].steps
			var route_count = steps.length;
			//行车距离（米）
			var distance = routeS[v].distance;
			//拼接输出html
			for(var i=0 ;i< steps.length;i++){
				route_text += "<tr><td align=\"left\" onMouseover=\"driveDrawFoldline('" + i + "')\">" + i +"." +steps[i].instruction  + "</td></tr>";
			}
		}
		//输出行车路线指示
		route_text = "<table cellspacing=\"5px\"><tr><td style=\"background:#e1e1e1;\">路线</td></tr><tr><td><img src=\"http://code.mapabc.com/images/start.gif\" />&nbsp;&nbsp;北京南站</td></tr>" + route_text + "<tr><td><img src=\"http://code.mapabc.com/images/end.gif\" />&nbsp;&nbsp;北京西站</td></tr></table>";
		document.getElementById("result").innerHTML = route_text;
		drivingDrawLine();
	} 	
}

//绘制驾车导航路线
function drivingDrawLine(s) {
	//起点、终点图标
	var sicon = new AMap.Icon({
		image: "http://api.amap.com/Public/images/js/poi.png",
		size:new AMap.Size(44,44),
		imageOffset: new AMap.Pixel(-334, -180)
	});
	var startmarker = new AMap.Marker({
		icon : sicon, //复杂图标
		visible : true, 
		position : start_xy,
		map:mapObj,
		offset : {
			x : -16,
			y : -40
		}
	});
	var eicon = new AMap.Icon({
		image: "http://api.amap.com/Public/images/js/poi.png",
		size:new AMap.Size(44,44),
		imageOffset: new AMap.Pixel(-334, -134)
	});
	var endmarker = new AMap.Marker({
		icon : eicon, //复杂图标
		visible : true, 
		position : end_xy,
		map:mapObj,
		offset : {
			x : -16,
			y : -40
		}
	});
	//起点到路线的起点 路线的终点到终点 绘制无道路部分
	var extra_path1 = new Array();
	extra_path1.push(start_xy);
	extra_path1.push(steps[0].path[0]);
	var extra_line1 = new AMap.Polyline({
		map: mapObj,
		path: extra_path1,
		strokeColor: "#9400D3",
		strokeOpacity: 0.7,
		strokeWeight: 4,
		strokeStyle: "dashed",
		strokeDasharray: [10, 5]
	});

	var extra_path2 = new Array();
	var path_xy = steps[(steps.length-1)].path;
	extra_path2.push(end_xy);
	extra_path2.push(path_xy[(path_xy.length-1)]);
	var extra_line2 = new AMap.Polyline({
		map: mapObj,
		path: extra_path2,
		strokeColor: "#9400D3",
		strokeOpacity: 0.7,
		strokeWeight: 4,
		strokeStyle: "dashed",
		strokeDasharray: [10, 5]
	});
	
	var drawpath = new Array(); 
	for(var s=0; s<steps.length; s++) {
		var plength = steps[s].path.length;
		for (var p=0; p<plength; p++) {
			drawpath.push(steps[s].path[p]);
		}
	}
	var polyline = new AMap.Polyline({
		map: mapObj,
		path: drawpath,
		strokeColor: "#9400D3",
		strokeOpacity: 0.7,
		strokeWeight: 4,
		strokeDasharray: [10, 5]
	});
	mapObj.setFitView();
}
//绘制驾车导航路段
function driveDrawFoldline(num) {
	var drawpath1 = new Array();
	drawpath1 = steps[num].path;
	if(polyline != null) {
		polyline.setMap(null);
	}
	polyline = new AMap.Polyline({
			map: mapObj,
			path: drawpath1,
			strokeColor: "#FF3030",
			strokeOpacity: 0.9,
			strokeWeight: 4,
			strokeDasharray: [10, 5]
		});

	mapObj.setFitView(polyline);
}

function test(){

	/*var arr = new Array();//经纬度坐标数组                 
	arr.push(new AMap.LngLat(116.368904 ,39.913423));                   
	arr.push(new AMap.LngLat(116.382122,39.901176));                   
	arr.push(new AMap.LngLat(116.387271,39.912501));                   
	arr.push(new AMap.LngLat(116.398258,39.904600));   
	                
	var polyline = new AMap.Polyline({                   
	  map:mapObj,                 
	  path:arr,                   
	  strokeColor:"red",                   
	  strokeOpacity:0.4,                   
	  strokeWeight:3                  
	});                   
	//调整视野到合适的位置及级别                 
	mapObj.setFitView();*/
	
	var marker1 = new AMap.Marker({ //创建自定义点标注                 
		  map:mapObj,                 
		  position: new AMap.LngLat(0,0),                 
		  offset: new AMap.Pixel(-10,-32),                 
		  icon: "images/car-1-1-4.png"                 
	}); 
	
/*	//绘制初始路径
	var path = [];
	//当前坐标
	path.push(new AMap.LngLat(126.538103,45.856));
	//目的地
	path.push(new AMap.LngLat(126.548803,45.825));
	mapObj.plugin("AMap.DragRoute",function(){
		route = new AMap.DragRoute(mapObj, path, AMap.DrivingPolicy.LEAST_FEE); //构造拖拽导航类
		route.search(); //查询导航路径并开启拖拽导航
	});*/
	
}