package com.c503.lbs.dao;

import java.util.List;

import com.c503.lbs.entity.Task;

/**
 * 
 * 任务数据库操作接口
 * @author huchaofeng
 *
 */
public interface ITaskDao {
	

	/**
	 * 根据任务号查找任务
	 * @param id  任务号
	 * @return  任务
	 */
	public  Task findTaskById(String id);

	/**
	 * 状态查找任务
	 * @param status  状态
	 * @return
	 */
	public List<Task> findTasksByStatus(String status);
	
	/**
	 * 根据车辆编号查找任务
	 * @param status  接收者（车辆编号）
	 * @return
	 */
	public List<Task> findTasksByReceiver(String receiver);
	
	/**
	 * 添加任务
	 * @param task  任务
	 * @return 是否添加成功
	 */
	public boolean addTask(Task task);
	
	/**
	 * 修改任务
	 * @param task  任务
	 * @return  是否修改成功
	 */
	public boolean modifyTask(Task task);

}
