package com.c503.lbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.c503.lbs.entity.Task;

/**
 * 任务数据库操作 实现类
 * 
 * @author huchaofeng
 *
 */
public class TaskDaoImp implements ITaskDao {
	
	public boolean addTask(Task task) {
		Dao dao = Dao.getInstance();
		Connection conn = dao.getConn();
		PreparedStatement statement = dao.getStatement();
		String sql = "insert into task (taskId,receiver,sender,status," +
		"sendTime,finishTime,dLongitute,dLatitute,method,describeTask" + 
		")values(?,?,?,?,?,?,?,?,?,?)";
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, task.getTaskId());
			statement.setString(2, task.getReceiver());
			statement.setString(3, task.getSender());
			statement.setString(4, task.getStatus());
			statement.setString(5, task.getSendTime());
			statement.setString(6, task.getFinishTime());
			statement.setString(7, task.getdLongitute());
			statement.setString(8, task.getdLatitute());
			statement.setString(9, task.getMethod());
			statement.setString(10, task.getDescribe());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("插入数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("插入时出错：");
			e.printStackTrace();
		}
		return false;
	}

	public Task findTask(String id) {
		Dao dao = Dao.getInstance();
		Connection conn = dao.getConn();
		PreparedStatement statement = dao.getStatement();
		String sql =  "select * from task where taskId = ?";
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Task task = new Task();
		try {
			while (rs.next()) {
				task.setTaskId(rs.getString(2));
				task.setReceiver(rs.getString(3));
				task.setSender(rs.getString(4));
				task.setStatus(rs.getString(5));
				task.setSendTime(rs.getString(6));
				task.setFinishTime(rs.getString(7));
				task.setdLongitute(rs.getString(8));
				task.setdLatitute(rs.getString(9));
				task.setMethod(rs.getString(10));
				task.setDescribe(rs.getString(11));
			}
		} catch (SQLException e) {
			System.out.println("据车牌号查找车的详细信息出错。");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("显示出错。");
			e.printStackTrace();
		}
		return task;
	}

	public List<Task> findTasks(String status) {

		Dao dao = Dao.getInstance();
		Connection conn = dao.getConn();
		PreparedStatement statement = dao.getStatement();
		
		String sql =  "select * from task where status = ?";
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, status);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Task> tasks = new ArrayList<Task>();
		try {
			while (rs.next()) {
				Task task = new Task();
				task.setTaskId(rs.getString(2));
				task.setReceiver(rs.getString(3));
				task.setSender(rs.getString(4));
				task.setStatus(rs.getString(5));
				task.setSendTime(rs.getString(6));
				task.setFinishTime(rs.getString(7));
				task.setdLongitute(rs.getString(8));
				task.setdLatitute(rs.getString(9));
				task.setMethod(rs.getString(10));
				task.setDescribe(rs.getString(11));
				tasks.add(task);
			}
		} catch (SQLException e) {
			System.out.println("据车牌号查找车的详细信息出错。");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("显示出错。");
			e.printStackTrace();
		}
		return tasks;
	}

	public boolean modifyTask(Task task) {
		Dao dao = Dao.getInstance();
		Connection conn = dao.getConn();
		PreparedStatement statement = dao.getStatement();
		String sql = "UPDATE task SET receiver = ?,sender = ?," +
				"status = ?,sendTime = ?,finishTime = ?,dLongitute = ?," +
				"dLatitute = ?,method = ?,describeTask = ? WHERE taskId = ?";
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, task.getReceiver());
			statement.setString(2, task.getSender());
			statement.setString(3, task.getStatus());
			statement.setString(4, task.getSendTime());
			statement.setString(5, task.getFinishTime());
			statement.setString(6, task.getdLongitute());
			statement.setString(7, task.getdLatitute());
			statement.setString(8, task.getMethod());
			statement.setString(9, task.getDescribe());
			statement.setString(10, task.getTaskId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("插入数据库时出错：");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("插入时出错：");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据车牌号创建一张表
	 * @param carId  车牌号
	 * @return
	 */
	public boolean createTaskTable(){
		Dao dao = Dao.getInstance();
		Connection conn = dao.getConn();
		PreparedStatement statement = dao.getStatement();
		String sql = "CREATE TABLE  Task (keyId int  NOT NULL AUTO_INCREMENT,taskId varchar(20) not null,receiver varchar(20),sender varchar(20)," +
		" status varchar(20),sendTime varchar(20),finishTime varchar(20),dLongitute varchar(20)," +
		"dLatitute varchar(20),method varchar(20),describeTask varchar(200)," +
		" primary key (keyId));";
		System.out.println(sql);
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		
	/*	Task task  = new Task();
		task.setTaskId("0001");
		task.setReceiver("张三");
		task.setDescribe("这是一个测试");
		task.setdLatitute("45.343423424");
		task.setdLongitute("126.23423234");
		task.setFinishTime("20141009");
		task.setSendTime("20141008");  
		task.setMethod("0");
		task.setSender("李四");
		task.setStatus("未完成");
		new TaskDaoImp().modifyTask(task);*/
		//Task task = new TaskDaoImp().findTask("0001");
		List<Task> tasks = new TaskDaoImp().findTasks("未完成");
		System.out.println(tasks.get(0).getdLatitute());
		System.out.println(tasks.get(1).getdLatitute());
	}
}
