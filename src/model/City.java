package model;

import javafx.beans.value.ObservableValue;

public class City {
	private String name;
	public ObservableValue<Double> x;
	public ObservableValue<Double> y;
	
	// Constructor with name, x, y
	public City(String name, ObservableValue<Double> x, ObservableValue<Double> y) {
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
	public ObservableValue<Double> getX() {
		return x;
	}
	public ObservableValue<Double> getY() {
		return y;
	}
}