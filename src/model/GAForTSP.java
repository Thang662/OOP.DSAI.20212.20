package model;

import java.util.ArrayList;

public class GAForTSP extends GeneticAlgorithm {
	public TSP tsp;
	
	
	public GAForTSP(int n) {
		super(n);
		tsp = new TSP(n);
	}
	
	public ArrayList<Point> getPoints() {
		return this.tsp.getPoints();
	}

	public float objectiveFunction(Chromosome c) {
		float[][] costs = tsp.getCost();
		int n = tsp.getNumberOfPoints();
		float total = costs[0][c.get(0)];
		for (int i = 0; i < n-1; i++) {
			total += i < n - 2 ? costs[c.get(i)][c.get(i+1)] : costs[c.get(i)][0]; 
		}
		return total;
	}
	
	
}
