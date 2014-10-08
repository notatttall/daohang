package com.c503.lbs.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.c503.lbs.entity.Task;

public class ObjectClient {

	public static void main(String[] arg) throws Exception {
		
		String _pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(_pattern);
		
		Task task = new Task();
		task.setDescribe("任务完成");
		task.setStatus("2");
		task.setFinishTime(format.format(new Date()));
		
		//连接到服务器端
		Socket socketConnection = new Socket(InetAddress.getLocalHost(), 6688);
		//使用ObjectOutputStream和ObjectInputStream  进行对象数据传输
		ObjectOutputStream out = new ObjectOutputStream(socketConnection.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socketConnection.getInputStream());
		//将客户端的对象数据流输出到服务器端去
		out.writeObject(task);
		out.flush();
		
		//读取服务器端返回的对象数据流
		Task unfinishedTask = (Task) in.readObject();
		System.out.println("任务描述："+unfinishedTask.getDescribe());
		System.out.println("任务状态：" +unfinishedTask.getStatus());
		
		
		
		out.close();
		in.close();
	}
}
