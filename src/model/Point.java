package model;

import java.util.ArrayList;

public class Point {
	private int x, y;
	
	// Constructor with x, y
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Getters 
	 */
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return x + " " + y;
	}
	
	// Check if two points have the same location
	public boolean equals(Point other) {
		return x == other.getX() && y == other.getY();
	}
	
	// Compute the distance between two points
	public float distance(Point other) {
		return (float) Math.sqrt((x-other.getX())*(x-other.getX()) + (y-other.getY())*(y-other.getY()));
	}
}
