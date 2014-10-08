package com.c503.lbs.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.c503.lbs.dao.Dao;
import com.c503.lbs.entity.Car;
import com.c503.lbs.rest.model.CarPath;
import com.c503.lbs.rest.model.RTLocation;
import com.c503.lbs.rest.model.RestCar;
import com.c503.lbs.service.CarLogin;
import com.c503.lbs.socket.MultiThreadServer;

/**
 * 提供与车相关的服务
 * 
 * @author huchaofeng
 *
 */
public class CarServlet extends HttpServlet {
	private boolean flag = true;
	private MultiThreadServer mtd = null;

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5507433468512032321L;

	/**
	 * Constructor of the object.
	 */
	public CarServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// 关闭socket
		mtd.close();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//把GET请求转化为POST请求
		doPost(request,response);
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//第一次访问时，启动socket端口
		if(flag){
			flag = false;
			try {
				CarLogin.test();
				mtd = new MultiThreadServer();
				mtd.service();
				System.out.println("22222");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); 
		
		//得到ajax传递过来的paramater 
		String method= request.getParameter("method");
		
		//获取实时位置信息里的车辆号
		if(method.equals("getCarListBystatus")){
			List<String> li = new ArrayList<String>();
			Set<String> mapKey = RTLocation.carRT.keySet();
			Iterator<String> keys = mapKey.iterator();
			while(keys.hasNext()){
				String key = keys.next();
				li.add(key);
				System.out.println("key:"+key);
			}
			JSONArray jsonArray2 = JSONArray.fromObject( li );
			out.print(jsonArray2);
			return;
		}
		
		//获取车辆实时信息
		/*if(method.equals("getCarInfo")){
			String carId= request.getParameter("carId");
			CarService cs = CarService.getInstance();
			RestCar  car = cs.findByCarId(carId);
			JSONObject jo=JSONObject.fromObject(car);
			out.print(jo);
			return;	
		}*/
		
		//获取车辆实时信息---直接通过实时车辆位置容器获取
		if(method.equals("getCarInfo")){
			String carId= request.getParameter("carId");
			RestCar  car = RTLocation.carRT.get(carId);
			JSONObject jo=JSONObject.fromObject(car);
			out.print(jo);
			return;	
		}
		
		//获取历史轨迹号
	/*	if(method.equals("getCarPath")){
			String carId= request.getParameter("carId");
			Dao dao = Dao.getInstance();
			List<String>  path = dao.findByCarId(carId);
			JSONArray jsonArray2 = JSONArray.fromObject( path );
			out.print(jsonArray2);
			return;
		}*/
		
		//获取车辆历史轨迹
		if(method.equals("getCarPath")){
			String pathId= request.getParameter("pathId");
			String carId= request.getParameter("carId");
			Dao dao = Dao.getInstance();
			CarPath  path = dao.findByPathId(carId,pathId);
			JSONObject jo=JSONObject.fromObject( path );
			out.print(jo);
			return;
		}
		
	}


	public void init() throws ServletException {
	}

}
