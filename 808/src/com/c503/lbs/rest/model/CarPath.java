package com.c503.lbs.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 轨迹类
 * @author huchaofeng
 *
 */
public class CarPath {
	
	private String pathId;
	private List<Location> locations = new ArrayList<Location>();
	
	public CarPath() {
		super();
	}
	
	public CarPath(String pathId, List<Location> locations) {
		super();
		this.pathId = pathId;
		this.locations = locations;
	}

	public String getPathId() {
		return pathId;
	}

	public void setPathId(String pathId) {
		this.pathId = pathId;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}
