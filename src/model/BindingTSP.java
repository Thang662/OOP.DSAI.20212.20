package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;

public class BindingTSP {
	private ArrayList<BindingPoint> bindingPoints;
	private float[][] distances;
	private int n;
	private ArrayList<Float> locationX = new ArrayList<Float>();
	private ArrayList<Float> locationY = new ArrayList<Float>();
	
	public BindingTSP(int n) {
		this.n = n;
		bindingPoints = this.generatePoint(n);
		distances = new float[n][n];
		calculateDistances();
	}
	
	public ArrayList<BindingPoint> getPoints() {
		return bindingPoints;
	}
	
	public ArrayList<Float> getLocationX() {
		return locationX;
	}
	
	public ArrayList<Float> getLocationY() {
		return locationY;
	}
	
	public int getNumberOfPoints() {
		return n;
	}
	
	public float[][] getCost() {
		return distances;
	}
	
	private void calculateDistances() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					distances[i][j] = 0;
				} else {
					distances[i][j] = bindingPoints.get(i).distance(bindingPoints.get(j));
				}
			}
		}
	}
	
	private ArrayList<BindingPoint> generatePoint(int n) {
		int min = 10;
		ArrayList<BindingPoint>  bindingPoints = new ArrayList<BindingPoint>();
		for (int i = 0; i < n; i++) {
			int len = bindingPoints.size();
			while (bindingPoints.size() == len) {
				boolean valid = true;
				int newX = 1 + ((int) (Math.random()*994));
				int newY = 1 + ((int) (Math.random()*994));
				for (int j = 0; j < bindingPoints.size(); j++) {
					if  ((float) Math.sqrt(Math.pow(newX-bindingPoints.get(j).getX(), 2) + Math.pow(newY-bindingPoints.get(j).getY(), 2)) < min) {
						valid = false;
						break;
					}
					if (j < bindingPoints.size()-1) {
						for (int k = j+1; k < bindingPoints.size(); k++) {
							BindingPoint p1 = bindingPoints.get(j);
							BindingPoint p2 = bindingPoints.get(k);
							if ((newX - p1.getX()) * (p2.getY() - p1.getY()) == (newY - p1.getY()) * (p2.getX() - p1.getX())) {
								valid = false;
								break;
							}
						}
					}
					if (!valid) {
						break;
					}
				}
				if (valid) {
					float newX_float = (float) (newX) / 994 * 900;
					float newY_float = (float) (newY) / 994 * 600;
					BindingPoint newPoint = new BindingPoint(new SimpleDoubleProperty(newX_float), new SimpleDoubleProperty(newY_float));
					bindingPoints.add(newPoint);
					locationX.add(newX_float);
					locationY.add(newY_float);
				}
			}
		}
		return bindingPoints;
	}
}
