package com.c503.lbs.rest.model;
/**
 * 返回给前台的坐标
 * 
 * @author huchaofeng
 *
 */
public class Location{
		public double latitude;
		public double longitude;
		
		public Location() {
		}
		
		public Location(double latitude, double longitude) {
			super();
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
}