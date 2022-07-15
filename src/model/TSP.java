package model;

import java.util.ArrayList;

public class TSP {
	private ArrayList<Point>points;
	private float[][] distances;
	private int n;
	
	public TSP(int n) {
		this.n = n;
		points = this.generatePoint(n);
		distances = new float[n][n];
		calculateDistances();
	}
	
	public ArrayList<Point> getPoints() {
		return points;
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
					distances[i][j] = points.get(i).distance(points.get(j));
				}
			}
		}
	}
	
	private ArrayList<Point> generatePoint(int n) {
		int min = 10;
		ArrayList<Point>  points = new ArrayList<Point>();
		for (int i = 0; i < n; i++) {
			int len = points.size();
			while (points.size() == len) {
				boolean valid = true;
				int newX = 1 + ((int) (Math.random()*994));
				int newY = 1 + ((int) (Math.random()*994));
				for (int j = 0; j < points.size(); j++) {
					if  ((float) Math.sqrt(Math.pow(newX-points.get(j).getX(), 2) + Math.pow(newY-points.get(j).getY(), 2)) < min) {
						valid = false;
						break;
					}
					if (j < points.size()-1) {
						for (int k = j+1; k < points.size(); k++) {
							Point p1 = points.get(j);
							Point p2 = points.get(k);
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
					Point newPoint = new Point(newX, newY);
					points.add(newPoint);
				}
			}
		}
		return points;
	}
}
