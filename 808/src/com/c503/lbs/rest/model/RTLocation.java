package com.c503.lbs.rest.model;

import java.util.HashMap;

import com.c503.lbs.entity.Task;


public class RTLocation {
	
	/**
	 * 在线车辆实时信息  String为车辆编号
	 */
	public static HashMap<String,RestCar> carRT = new HashMap<String,RestCar>();
	
	/**
	 * 在线车辆实时任务  String为车辆编号
	 */
	public static HashMap<String,Task> carTask = new HashMap<String,Task>();

}
