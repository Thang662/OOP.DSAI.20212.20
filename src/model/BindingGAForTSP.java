package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BindingGAForTSP extends GeneticAlgorithm{
	public BindingTSP tsp;
	
	public BindingGAForTSP(int n) {
		super(n);
		tsp = new BindingTSP(n);
	}
	
	@Override
	public ArrayList<Chromosome> solveNext() {
		step++;
		Random random = new Random();
		while (chromosomes.size() < generateSize*2) {
			int index1 = random.nextInt(chromosomes.size());
			int index2 = random.nextInt(chromosomes.size());
			while (index1 == index2) {
				index2 = random.nextInt(chromosomes.size());
			}
			Chromosome[] children = crossover(chromosomes.get(index1), chromosomes.get(index2));
			for (Chromosome child : children) {
				mutate(child);
				if (! chromosomes.contains(child)) {
					chromosomes.add(child);
				}
			}
		}
		select();
		Chromosome bestChromosome = currentBest();
		for (int j = 0; j < bestChromosome.size(); j++) {
			tsp.getPoints().get(j + 1).setX(tsp.getLocationX().get(bestChromosome.get(j)));
			tsp.getPoints().get(j + 1).setY(tsp.getLocationY().get(bestChromosome.get(j)));
        }
		return chromosomes;
	}
	
	@Override
	public float objectiveFunction(Chromosome c) {
		float[][] costs = tsp.getCost();
		int n = tsp.getNumberOfPoints();
		float total = costs[0][c.get(0)];
		for (int i = 0; i < n-1; i++) {
			total += i < n - 2 ? costs[c.get(i)][c.get(i+1)] : costs[i][0]; 
		}
		return total;
	}
}
