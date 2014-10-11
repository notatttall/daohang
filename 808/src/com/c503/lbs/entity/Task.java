package com.c503.lbs.entity;

import java.io.Serializable;

/**
 * 
 * 任务类
 * 
 * @author huchaofeng
 *
 */
public class Task implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 548253074535753310L;
	
	/**
	 * 任务编号
	 */
	private String taskId;
	
	/**
	 *任务接收者 (车辆编号)
	 */
	private String receiver;
	
	/**
	 *发送者 
	 */
	private String sender;
	
	/**
	 *任务状态：未完成(0)、进行中(1)、完成(2) 
	 */
	private String status;
	
	/**
	 *任务下发时间 
	 */
	private String sendTime;
	
	/**
	 *任务完成时间 
	 */
	private String finishTime;
	
	/**
	 *目的地经度 
	 */
	private String dLongitute;
	
	/**
	 *目的地纬度 
	 */
	private String dLatitute;
	
	/**
	 *行驶规则：时间最少、路径最短 
	 */
	private String method;
	
	/**
	 *任务描述 
	 */
	private String describe;
	

	public Task() {
		super();
	}
	
	public Task(String taskId, String receiver, String sender, String status,
			String sendTime, String finishTime, String dLongitute,
			String dLatitute, String method, String describe) {
		super();
		this.taskId = taskId;
		this.receiver = receiver;
		this.sender = sender;
		this.status = status;
		this.sendTime = sendTime;
		this.finishTime = finishTime;
		this.dLongitute = dLongitute;
		this.dLatitute = dLatitute;
		this.method = method;
		this.describe = describe;
	}

	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getSendTime() {
		return sendTime;
	}


	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


	public String getFinishTime() {
		return finishTime;
	}


	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}


	public String getdLongitute() {
		return dLongitute;
	}


	public void setdLongitute(String dLongitute) {
		this.dLongitute = dLongitute;
	}


	public String getdLatitute() {
		return dLatitute;
	}


	public void setdLatitute(String dLatitute) {
		this.dLatitute = dLatitute;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getDescribe() {
		return describe;
	}


	public void setDescribe(String describe) {
		this.describe = describe;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Task [dLatitute=" + dLatitute + ", dLongitute=" + dLongitute
				+ ", describe=" + describe + ", finishTime=" + finishTime
				+ ", method=" + method + ", receiver=" + receiver
				+ ", sendTime=" + sendTime + ", sender=" + sender + ", status="
				+ status + ", taskId=" + taskId + "]";
	}

}

	
