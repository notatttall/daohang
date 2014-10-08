package com.c503.lbs.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.c503.lbs.entity.Car;
import com.c503.lbs.rest.model.CarPath;
import com.c503.lbs.rest.model.Location;
import com.c503.lbs.rest.model.RestCar;
import com.c503.lbs.service.CoordinateUtil;
import com.c503.lbs.service.LatLng;

/**
 * 
 * 对数据库操作
 * @author huchaofeng
 *
 */
public class Dao {
	private Connection conn = null;
	private PreparedStatement statement = null;
	
	private static Dao dao = null;
	private Dao() {
		String url = "jdbc:mysql://localhost:3306/808?characterEncoding=UTF-8"; // 数据库地址，端口，数据库名称，字符集
		String username = "root"; // 数据库用户名
		String password = "hcf"; // 数据库密码
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载驱动，必须导入包mysql-connector-java-5.1.6-bin.jar
			conn = DriverManager.getConnection(url, username, password);
		}
		// 捕获加载驱动程序异常
		catch (ClassNotFoundException cnfex) {
			System.err.println("装载 JDBC/ODBC 驱动程序失败。");
			cnfex.printStackTrace();
		}
		// 捕获连接数据库异常
		catch (SQLException sqlex) {
			System.err.println("无法连接数据库");
			sqlex.printStackTrace();
		}
	}
	public static Dao getInstance() {
	   if (dao == null) {
		   dao = new Dao();
	   }
	   return dao;
	}

	public Connection getConn() {
		return conn;
	}
	public PreparedStatement getStatement() {
		return statement;
	}
	
	// 关闭数据库
	public void deconnSQL() {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			System.out.println("关闭数据库异常：");
			e.printStackTrace();
		}
	}

	/**
	 * 根据车牌号创建一张表
	 * @param carId  车牌号
	 * @return
	 */
	public boolean createCarTable(String carId){
		
		String sql = "CREATE TABLE  " + carId + " (keyId int  NOT NULL AUTO_INCREMENT,id varchar(20) not null,status varchar(20),warkingStatus varchar(20)," +
		" driverName varchar(20),driverIDCard varchar(50),certificate varchar(50),organization varchar(50)," +
		"speed varchar(20),longitude varchar(20),latitude varchar(20),elevation varchar(20)," +
		"time varchar(50),oil varchar(20),tirePressure varchar(20),pathID varchar(20),gasSolubility varchar(20),mileage varchar(20),direction varchar(20)," +
		"primary key (keyId));";
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加车辆实时信息
	 * @param car  车辆实时信息
	 * @return   是否添加成功
	 */
	public boolean addCarRTInfo(Car car){
		String sql = "insert into " +car.getId()+ "(id,status,warkingStatus,driverName," +
		"driverIDCard,certificate,organization,speed,longitude,latitude,elevation,time,oil" +
		",tirePressure,pathID,gasSolubility,mileage,direction" + ")values('" + car.getId()+"','"+car.getStatus()+"','"+car.getWarkingStatus()+"','"+car.getDriverName()
		+"','" + car.getDriverIDCard() + "','" + car.getCertificate() + "','"+ car.getOrganization() + "','"  + car.getSpeed()+"','"+car.getLongitude()+"','"+car.getLatitude()
		+"','" + car.getElevation()+"','"+car.getTime()+"','"+car.getOil()
		+"','"+car.getTirePressure()+"','"+car.getPathID() + "','" + car.getGasSolubility() + "','" + car.getMileage() + "','" + car.getDirection() +"')";
		try {
			statement = conn.prepareStatement(sql);
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
	 * 根据车牌号查找车的详细信息
	 * @param carId 车牌号
	 * @return Car 车辆信息
	 */
	public RestCar findByCarId(String carId){
		
		String sql =  "select * from "+carId+" order by keyId  desc limit 1";
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RestCar car = new RestCar();
		try {
			while (rs.next()) {
				car.setId(rs.getString(2));
				car.setStatus(rs.getString(3));
				car.setWarkingStatus(rs.getString(4));
				car.setDriverName(rs.getString(5));
				car.setDriverIDCard(rs.getString(6));
				car.setCertificate(rs.getString(7));
				car.setSpeed(rs.getString(9));
				car.setLongitude(rs.getString(10));
				car.setLatitude(rs.getString(11));
				car.setElevation(rs.getString(12));
				car.setTime(rs.getString(13));
				car.setOil(rs.getString(14));
				car.setTirePressure(rs.getString(15));
				//使用pathid来存储里程
				car.setPathID(rs.getString(18));
				car.setDirection(rs.getString(19));
			}
		} catch (SQLException e) {
			System.out.println("据车牌号查找车的详细信息出错。");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("显示出错。");
			e.printStackTrace();
		}
		return car;
	}
	
	//根据车牌号查找车辆轨迹号
	public CarPath findByPathId(String carId,String pathId){
		
		String sql =  "select * from "+carId+" where pathId = ?";
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, pathId);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		CarPath carPath = new CarPath();
		ArrayList<Location> locations = new ArrayList<Location>();
		try {
			while (rs.next()) {
				Location location = new Location();
				//修正经纬度
				if("2014100701".equals(pathId)){
					LatLng ll = new LatLng(Double.parseDouble(rs.getString(11)), Double.parseDouble(rs.getString(10)));
		      		ll = CoordinateUtil.transformFromWGSToGCJ(ll);
		      		location.setLatitude(ll.latitude);
					location.setLongitude(ll.longitude);
				}else{
					location.setLatitude(Double.parseDouble(rs.getString(11)));
					location.setLongitude(Double.parseDouble(rs.getString(10)));
				}
				
				locations.add(location);
			}
		} catch (SQLException e) {
			System.out.println("据车牌号查找车的详细信息出错。");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("显示出错。");
			e.printStackTrace();
		}
		
		carPath.setPathId(pathId);
		carPath.setLocations(locations);
		
		return carPath;
	}

	//根据轨迹号查找车辆轨迹
	
	public static void main(String[] args) {
		
		/*Dao dao = Dao.getInstance();
		CarPath  path = dao.findByPathId("黑D22222","2014100701");
		System.out.println(path.getPathId());*/
		
		/*RestCar  car = Dao.getInstance().findByCarId("黑D22222");
		System.out.println(car.getLatitude());*/
		
		 //写入数据库
  	/*  Car car = new Car();
  	  car.setId("黑D22222");
  	  car.setDriverName("damin");
  	  car.setStatus("正常");
  	  car.setSpeed("11.1");
  	  car.setLatitude("11.1");
  	  car.setLongitude("11.1");
  	  car.setElevation("11.1");
  	  car.setOil("11.1");
  	  car.setTime("11.1");
  	  car.setMileage("11.1");
  	  car.setGasSolubility("11.1");
  	  car.setCertificate("A级驾照");
  	  car.setOrganization("车管所");
  	  car.setDriverIDCard("23423423423424");
  	  car.setWarkingStatus("0");
  	  car.setTirePressure("45pa");
  	  car.setPathID("0002");
  	  car.setStatus("0");
  	  car.setDirection("11.1");
  	  Dao.getInstance().addCarRTInfo(car);*/
	}
}