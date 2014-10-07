
//实时车辆标记
var marker1 = null;
$(document).ready(function () {
   
    $("#table1 tr:gt(0)").hover(
    function () { $(this).addClass("hover") },
    function () { $(this).removeClass("hover") })
    
    //实时更新地图车辆
    marker1 = new AMap.Marker({ //创建自定义点标注                 
		  map:mapObj,                 
		  offset: new AMap.Pixel(-10,-32),                 
		  icon: "images/car-1-1-4.png"                 
	}); 
    
    var interval = setInterval("findByCarId()",3000);
});

//查找车辆详细信息
function findByCarId(){ 
	 var carId ="黑D22222";
     $.ajax({ 
     	type:"POST", //请求方式 
   	 	url:"servlet/CarServlet", //请求路径 
    	cache: false,  
     	data:{ "method": "getCarInfo", "carId": carId },  //传参 
    	dataType: 'json',   //返回值类型 
        success:function(car){ 
     		//实时更新表格
     		var str =  "<td>"+car.id+"</td> <td>"+car.driverName+"</td><td>"+car.driverIDCard+
     			"</td><td>"+car.speed+"</td><td>"+car.longitude+"</td> <td>"+car.latitude+"</td><td>"+car.elevation+
     			"</td><td>"+car.oil+"</td><td>"+car.tirePressure+"</td> <td>"+car.pathID+"</td>";
     	    $("#tableHead").html(str);
     	   
     	    //XXX通过设置经纬度。。。
     	    /*if( marker1 != null){
     	    	marker1.setMap(null);
     	    }*/
     		marker1.setPosition(new AMap.LngLat(car.longitude,car.latitude));
     		
     		//clickEventListener=AMap.event.addListener(marker,'click',updateInfo(car));
     		//AMap.event.addListener(marker, "complete", updateInfo(car)); //查询成功时的回调函数
     		//updateInfo(car);
        },
		error : function(json){
		}
    }); 
} 

//更新信息窗体的内容
function updateInfo(car){
	var info = []; 
	info.push("<div><div style=\"\">车辆监控系统</div> "); 
	info.push("<div style=\"padding:0px 0px 0px 4px;\"><b>"+car.id+"位置信息</b>");  
	info.push("油量："+car.oil+"  速度："+ car.speed+"  车辆状态："+car.warkingStatus);  
	info.push("驾驶员名字:"+ car.driverName +"</div></div>"); 
	inforWindow.setContent(info.join("<br/>") );  //在信息窗体中显示新的信息内容
	inforWindow.open(mapObj,new AMap.LngLat(car.longitude,car.latitude));
//	mapObj.setCenter(new AMap.LngLat(car.longitude,car.latitude));
}