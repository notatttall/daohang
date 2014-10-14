$(document).ready(function () {
	
	$("#logout").click(function(){
		window.location.href = "http://localhost:8083/808/login.html";
	}); 
	$("#jk").click(function(){
		window.location.href = "http://localhost:8083/808/test.html";
	}); 
	$("#yy").click(function(){
		window.location.href = "http://localhost:8083/808/analysis.html";
	}); 
	
	
	//findTaskByCarId("黑D22222");
	//findTaskByStatus("0");
	
	var task = '{"taskId":"0020","receiver":"黑D22222","sender":"xiaoming","status":"0","sendTime":"20141009","finishTime":"0", '+
				' "dLongitute":"126.23424234","dLatitute":"45.234234","method":"1","describe":"你好，请到中央大街去看看"}';
	//alert(task);
	//addTask(task);
	findTaskByStatus("0");
	
	 $('#add').click(function(){
 		var id =  $('#order_num').val();
 		var state =  $('#order_state').val();
 		var method =  $('#travel_rule').val();
 		var longitude =  $('#longitude').val();
 		var time_begin =  $('#time_begin').val();
 		var order_send =  $('#order_send').val();
 		var order_rec =  $('#order_rec').val();
 		var time_end =  $('#time_end').val();
 		var order_describe =  $('#order_describe').val();
 		var task = '{"taskId":"'+id+'","receiver":"'+order_rec+'","sender":"'+order_send+'",'+
 					'"status":"'+state+'","sendTime":"'+time_begin+'","finishTime":"'+time_end+'", '+
 					'"dLongitute":"'+longitude+'","dLatitute":"'+"测试"+'","method":"'+method+'",'+
 					'"describe":"'+order_describe+'"}';
 		addTask(task);
	});
	 
});


//根据车辆号查找任务列表
function findTaskByCarId(carId){ 

	$.ajax({ 
     	type:"POST", //请求方式 
   	 	url:"servlet/CarServlet", //请求路径 
    	cache: false,  
     	data:{ "method": "getCarTaskBycarId", "carId": carId },  //传参 
    	dataType: 'json',   //返回值类型 
        success:function(tasks){ 
     		
        },
		error : function(json){
		}
    }); 
} 

//根据状态查找任务列表
function findTaskByStatus(status){ 
	
     $.ajax({ 
     	type:"POST", //请求方式 
   	 	url:"servlet/CarServlet", //请求路径 
    	cache: false,  
     	data:{ "method": "getTaskByStatus", "status": status },  //传参 
    	dataType: 'json',   //返回值类型 
        success:function(tasks){ 
     		var str = "";
     		var a = 0;
     		
     		 $.each(tasks, function(i,task){ 
     			 var status;
     			 if( task.status == "0"){
     				status = "未完成";
     			 }
     			 if( task.status == "1"){
      				status = "进行中";
      			 }
     			 if( task.status == "2"){
      				status = "完成";
      			 }
     			 
     			 a = i + 1;
     			
 				 str = str +
 				 "<tr>"+
 				 "<td>"+ a +"</td>"+
 				 "<td>"+task.taskId+"</td>"+
 				 "<td>"+status+"</td>"+
 				 "<td>"+task.sendTime+"</td>"+
 				 "<td>"+task.describe+"</td>"+
 				 "<td height=28px>"+
 				 "<input type='button' value='详情' >"+
 				 "<input type='button' value='修改' >"+
 				 "<input type='button' value='删除'>	"+
 				 "</td>"+
 				 "</tr>";
     			 
     			 
     		 });
     		 /*for(var i=10; i>a; i--){
     			 str = str +
 				 "<tr>"+
 				 "<td>"+ "" +"</td>"+
 				 "<td> </td>"+
 				"<td> </td>"+
 				"<td> </td>"+
 				"<td> </td>"+
 				 "<td height=28px>"+
 				 "<input type='button' value='详情' >"+
 				 "<input type='button' value='修改' >"+
 				 "<input type='button' value='删除'>	"+
 				 "</td>"+
 				 "</tr>";
     		 }*/
     		 $("#task_table").append(str);
        },
		error : function(json){
		}
    }); 
} 

//添加任务
function addTask(task){ 
	
     $.ajax({ 
     	type:"POST", //请求方式 
   	 	url:"servlet/CarServlet", //请求路径 
    	cache: false,  
     	data:{ "method": "addTask", "task": task },  //传参 
    	dataType: 'json',   //返回值类型 
        success:function(tasks){ 
     		alert("添加成功！");
        },
		error : function(json){
		}
    }); 
} 

//修改任务
function modifyTask(task){ 
	
     $.ajax({ 
     	type:"POST", //请求方式 
   	 	url:"servlet/CarServlet", //请求路径 
    	cache: false,  
     	data:{ "method": "modifyTask", "task": task },  //传参 
    	dataType: 'json',   //返回值类型 
        success:function(tasks){ 
     		alert(tasks);
        },
		error : function(json){
		}
    }); 
} 
