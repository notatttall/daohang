package com.c503.lbs.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.c503.lbs.dao.Dao;
import com.c503.lbs.entity.Car;
import com.c503.lbs.entity.Task;
import com.c503.lbs.rest.model.RTLocation;
import com.c503.lbs.rest.model.RestCar;
import com.c503.lbs.service.CoordinateUtil;
import com.c503.lbs.service.LatLng;
import com.example.jtt808.FrameBase;
import com.example.jtt808.FrameFactory;
import com.example.jtt808.FrameLocaInfoRep;
import com.example.jtt808.FrameLogin;
import com.example.jtt808.FrameServerAnswer;
import com.example.jtt808.argument.FrameType;

class Handler implements Runnable{
    private Socket socket;
    public Handler(Socket socket){
        this.socket=socket;
    }
    
    public void run(){
    		
    		try {
    			//路径编号
    			String pathId = "001";
    			while(true){
	                InputStream socketIn = socket.getInputStream();
	                OutputStream socketOut = socket.getOutputStream();
	                byte[] temp = new byte[1024 * 4];
	                int length = socketIn.read(temp, 0, 1024 * 4);
	               
	                while(-1 != length){
	                
	                	  FrameBase fBase = FrameFactory.CreatFrame(temp,length);
	                	 
	                	  //终端注册
	                      if(fBase.getm_FrameHead().getID() == FrameType.MSG_ZDZC){
	
	                      }
	                      
	                      //保存车辆实时信息，并写入数据库
	                      Car car = new Car();
	                     
	                      //终端鉴权
	                      if(fBase.getm_FrameHead().getID() == FrameType.MSG_ZDJQ){
	                    	  	FrameLogin fraLogin = (FrameLogin)fBase;
	    	                  	System.out.println("终端鉴权::");
	    	                  	System.out.println("鉴权码::"+fraLogin.getM_strRegCode());
	    	                  	
	    	                  	//设置轨迹号
	    	                  	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	    	                  	pathId = sdf.format(new Date());
	    	                  	System.out.println("鉴权时间："+sdf.format(new Date()));
	    	                  	
	    	                  	//在线车辆
	    	                  	//RTLocation.carRT.put(key, value);
	    	                  	
	    	                  	//如果鉴权码为808，则返回平台通用应答
	    	                  	if(fraLogin.getM_strRegCode().equals("808")){
	    	                  		FrameServerAnswer frameServer = new FrameServerAnswer("18734135922",2014,false,false);
	    	                  		frameServer.setInfo(333, 1);
	    	                  		byte[] bDataServer = frameServer.getM_DataBuf();
	    	                  		socketOut.write(bDataServer);
	    	                  		socketOut.flush();
	    	                  		length = -1;
	    	                  	}
	                      }
	                      
	                      //位置信息汇报
	                      if(fBase.getm_FrameHead().getID() == FrameType.MSG_WZXXHB){
	    			        	  FrameLocaInfoRep fa = (FrameLocaInfoRep)fBase;
	    			        	//将数据写入在线车辆容器、数据库。并打印响应信息
	    			        	saveMessage(pathId, car, fa);
                      			//响应
                      			FrameServerAnswer frameServer = new FrameServerAnswer("18734135922",2014,false,false);
                      			frameServer.setInfo(333, 1);
                      			byte[] bDataServer = frameServer.getM_DataBuf();
                      			socketOut.write(bDataServer);
                      			socketOut.flush();
		                  		length = -1;
	    		                  
	                      }
	                	}
	    			}
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
	            try {
	                if(socket!=null)
	                    socket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
            }
    }

	/**
	 * 将数据写入在线车辆容器、数据库。并打印响应信息
	 * @param pathId
	 * @param car
	 * @param fa
	 */
	private void saveMessage(String pathId, Car car, FrameLocaInfoRep fa) {
		//写入数据库
		  car.setId("黑D22222");
		  car.setDriverName("damin");
		  car.setStatus("正常");
		  car.setSpeed(String.valueOf(fa.getM_ispeed()));
		  
		  //传输过来的经纬度反了
		  LatLng ll = new LatLng(fa.getM_dlongitude(),fa.getM_dlatitude());
		  ll = CoordinateUtil.transformFromWGSToGCJ(ll);
		  car.setLatitude(String.valueOf(ll.latitude));
		  car.setLongitude(String.valueOf(ll.longitude));
		  
		  car.setElevation(String.valueOf(fa.getM_ielevation()));
		  car.setOil(String.valueOf(fa.getM_doilMass()));
		  car.setTime(fa.getM_date().toString());
		  car.setMileage(String.valueOf(fa.getM_dmileage()));
		  car.setGasSolubility(String.valueOf(fa.getM_ddangerGas()));
		  car.setCertificate("A级驾照");
		  car.setOrganization("车管所");
		  car.setDriverIDCard("23423423423424");
		  car.setWarkingStatus("0");
		  car.setTirePressure("45pa");
		  car.setStatus("0");
		  car.setPathID(pathId);
		  car.setDirection(String.valueOf(fa.getM_iorientation()));
		  Dao.getInstance().addCarRTInfo(car);
		  
		  //写入实时车辆位置容器
		  RestCar restCar = new RestCar();
		  restCar.setId("黑D22222");
		  restCar.setDriverName("damin");
		  restCar.setStatus("正常");
		  restCar.setSpeed(String.valueOf(fa.getM_ispeed()));
		  
		  restCar.setLatitude(String.valueOf(ll.latitude));
		  restCar.setLongitude(String.valueOf(ll.longitude));
		
		  restCar.setElevation(String.valueOf(fa.getM_ielevation()));
		  
		  restCar.setOil(String.valueOf(fa.getM_doilMass()));
		  restCar.setTime(fa.getM_date().toString());
		  restCar.setGasSolubility(String.valueOf(fa.getM_ddangerGas()));
		  restCar.setCertificate("A级驾照");
		  restCar.setOrganization("车管所");
		  restCar.setDriverIDCard("23423423423424");
		  restCar.setWarkingStatus("0");
		  restCar.setTirePressure("45pa");
		  restCar.setPathID(pathId);
		  restCar.setStatus("0");
		  restCar.setDirection(String.valueOf(fa.getM_iorientation()));
		  RTLocation.carRT.put(car.getId(), restCar);
		  
		  System.out.println(fa.getM_dataLen());
		  System.out.println("位置信息汇报::");
		  System.out.println("报警标志:紧急报警:"+fa.getM_warningMark().urgency); 
		  System.out.println("报警标志:超速报警:"+fa.getM_warningMark().overspeed); 
		  System.out.println("报警标志:GXSS模块故障:"+fa.getM_warningMark().GXSSModel); 
		  System.out.println("状态:开/关:"+fa.getM_locationStatus().acc); 
		  System.out.println("状态:定位:"+fa.getM_locationStatus().location);
		  System.out.println("状态:北纬/南纬:"+fa.getM_locationStatus().isNorth);
		  //设置反了
		  System.out.println("纬度::"+fa.getM_dlatitude()); 
		  System.out.println("修正后的经纬度："+ String.valueOf(ll.longitude));
		  System.out.println("经度::"+fa.getM_dlongitude()); 
		  System.out.println("修正后的经纬度："+ String.valueOf(ll.latitude));
		  
		  System.out.println("高程::"+fa.getM_ielevation()); 
		  System.out.println("速度::"+fa.getM_ispeed());
		  System.out.println("方向::"+fa.getM_iorientation());  
		  System.out.println("时间::"+fa.getM_date()); 
		  System.out.println("油量::"+fa.getM_doilMass()); 
		  System.out.println("里程::"+fa.getM_dmileage());
		  System.out.println("有害气体::"+fa.getM_ddangerGas());
	}
}