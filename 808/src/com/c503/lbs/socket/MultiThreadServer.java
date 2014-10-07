package com.c503.lbs.socket;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

public class MultiThreadServer {
	
    private int port=8888;
    private ServerSocket serverSocket;
    private ExecutorService executorService;//线程池
    private final int POOL_SIZE=10;//单个CPU线程池大小
    
    public MultiThreadServer() throws IOException{
        serverSocket=new ServerSocket(port);
        //Runtime的availableProcessor()方法返回当前系统的CPU数目.
        executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        System.out.println("服务器启动，端口" + port );
    }
    
    public void service(){
        while(true){
            Socket socket=null;
            try {
            	System.out.println("开始...");
                //接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
                socket=serverSocket.accept();
                executorService.execute(new Handler(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void close(){
    	try {
            if(serverSocket!=null)
            	serverSocket.close();
            	System.out.println("socket端口"+ port +"已经关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //测试
    public static void main(String[] args) throws IOException {
        new MultiThreadServer().service();
    }

}