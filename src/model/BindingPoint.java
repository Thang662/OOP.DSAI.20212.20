package model;

import javafx.beans.property.DoubleProperty;

public class BindingPoint {
	public DoubleProperty x;
	public DoubleProperty y;
	
	public BindingPoint(DoubleProperty x, DoubleProperty y) {
		this.x = x;
		this.y = y;
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
	
	public String toString() {
		return x + " " + y;
	}
	
	public boolean equals(BindingPoint other) {
		return x.get() == other.getX() && y.get() == other.getY();
	}
	
	public float distance(BindingPoint other) {
		return (float) Math.sqrt((x.get()-other.getX())*(x.get()-other.getX()) + (y.get()-other.getY())*(y.get()-other.getY()));
	}
}
