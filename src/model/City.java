package model;

import javafx.beans.property.DoubleProperty;

public class City {
	private String name;
	public DoubleProperty x;
	public DoubleProperty y;
	
	// Constructor with name, x, y
	public City(String name, DoubleProperty x, DoubleProperty y) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Getters and setters
	 */
	public String getName() {
		return name;
	}
	public Double getX() {
		return x.get();
	}
	public Double getY() {
		return y.get();
	}
	public DoubleProperty getbindX() {
		return x;
	}
	public DoubleProperty getbindY() {
		return y;
	}
	public void setX(double value) {
		this.x.set(value);
	}
	public void setY(double value) {
		this.y.set(value);
	}
}