package screen.pane;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Chromosome;
import model.Point;

public class DrawPane extends JPanel {
	
	private ArrayList<Point> points = null;
	private Chromosome route = null;
	private Chromosome minRoute = null;
	private float pointSize = 3.5f;
	
	// Set the background of the pane
	public DrawPane() {
		super();
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.gray));
		this.setBackground(java.awt.Color.decode("#D6EDFF"));
	}
	
	// Set the points (cities) for pane
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	// Set the lines for route in pane
	public void setLines(Chromosome c) {
		route = c;
		if (minRoute == null) {
			minRoute = c;
		}
	}
	
	// Set the lines for best route in pane
	public void setBestLines (Chromosome c) {
		minRoute = c;
	}
	
	/*
	 * Getters
	 */
	public Chromosome getLines() {
		return route;
	}
	
	public Chromosome getBestLines() {
		return minRoute;
	}
	
	// Set the bound for pane
	public int[] getBound() {
		int minX = 1000, maxX = 0, minY = 1000, maxY = 0; 
		for (Point p : points) {
			int x = p.getX();
			int y = p.getY();
			if (x < minX) {
				minX = x;
			}
			if (x > maxX) {
				maxX = x;
			}
			if (y < minY) {
				minY = y;
			}
			if (y > maxY) {
				maxY = y;
			}
		}
		
		return new int[] {minX, maxX, minY, maxY};
		
	}
	
	// Scale X and y so that the point won't be on the edge of pane
	public float scaleX(int x) {
		int[] bound = getBound();
		return (float) (x-bound[0])/(bound[1] - bound[0])*(getWidth()-50) + 25f;
	}
	
	public float scaleY(int y) {
		int[] bound = getBound();
		return (float) (y-bound[2])/(bound[3] - bound[2])*(getHeight()-50) + 25f;
	}
	
	// Draw the route and point in each generation
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.setColor(java.awt.Color.red);
		if (minRoute != null) {
			Point start = points.get(0);
			float x1 = scaleX(start.getX());
			float y1 = scaleY(start.getY());
			float x2 = scaleX(points.get(minRoute.get(0)).getX());
			float y2 = scaleY(points.get(minRoute.get(0)).getY());
			
			g2.draw(new Line2D.Float(x1+pointSize, y1+pointSize, x2+pointSize, y2+pointSize));
			for (int i = 0; i < points.size()-1; i++) {
				Point p1 = points.get(minRoute.get(i));
				Point p2 = (i < points.size()-2) ? points.get(minRoute.get(i+1)) : points.get(0);
				x1 = scaleX(p1.getX());
				y1 = scaleY(p1.getY());
				x2 = scaleX(p2.getX());
				y2 = scaleY(p2.getY());
				g2.draw(new Line2D.Float(x1+pointSize, y1+pointSize, x2+pointSize, y2+pointSize));
			}
		}
		
		g2.setStroke(new BasicStroke(3));
		g2.setColor(java.awt.Color.blue);
		if (route != null) {
			Point start = points.get(0);
			float x1 = scaleX(start.getX());
			float y1 = scaleY(start.getY());
			float x2 = scaleX(points.get(route.get(0)).getX());
			float y2 = scaleY(points.get(route.get(0)).getY());
			
			g2.draw(new Line2D.Float(x1+pointSize, y1+pointSize, x2+pointSize, y2+pointSize));
			for (int i = 0; i < points.size()-1; i++) {
				Point p1 = points.get(route.get(i));
				Point p2 = (i < points.size()-2) ? points.get(route.get(i+1)) : points.get(0);
				x1 = scaleX(p1.getX());
				y1 = scaleY(p1.getY());
				x2 = scaleX(p2.getX());
				y2 = scaleY(p2.getY());
				g2.draw(new Line2D.Float(x1+pointSize, y1+pointSize, x2+pointSize, y2+pointSize));
			}
		}
		
		g2.setColor(java.awt.Color.black);
		if (points != null) {
			for (Point p : points) {
				float x = scaleX(p.getX());
				float y = scaleY(p.getY());
				
				g2.fill(new Ellipse2D.Float(x, y, 2*pointSize, 2*pointSize));
			}
		}
	}
}
