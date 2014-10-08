package com.c503.lbs.service;

import com.c503.lbs.entity.Task;
import com.c503.lbs.rest.model.RTLocation;
import com.c503.lbs.rest.model.RestCar;

/**
 * 
 * 测试  上线车辆
 * @author huchaofeng
 *
 */
public class CarLogin {
	
	public static boolean login(RestCar car){
		RTLocation.carRT.put(car.getId(), car);
		return false;
	}

	public static void test() {
		RestCar car = new RestCar();
		car.setId("黑A0001");
		car.setLatitude("1111");
		car.setLongitude("222");
		login(car);
		
		RestCar car3 = new RestCar();
		car3.setId("黑D22222");
		car3.setLatitude("45.20349391402901");
		car3.setLongitude("126.54235576275106");
		login(car3);
		
		Task task = new Task();
		task.setTaskId("0088");
		task.setDescribe("001");
		task.setStatus("0");
		RTLocation.carTask.put("黑D22222", task);
			
	}
}
