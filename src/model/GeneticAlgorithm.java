package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class GeneticAlgorithm {
	protected final int generateSize = 100;
	protected ArrayList<Chromosome> chromosomes;
	protected int step = 0;
	
	// Constructor with n 
	public GeneticAlgorithm(int n) {
		this.chromosomes = this.generateChromosome(n, generateSize);
	}
	
	// Get current step of the algorithm
	public int getStep() {
		return step;
	}
	
	// Generates n chromosomes
	public ArrayList<Chromosome> generateChromosome(int len, int amount) {
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
		Chromosome base = new Chromosome();
		for (int i = 1; i < len; i++) {
			base.add(i);
		}
		while (chromosomes.size() < amount) {
			Collections.shuffle(base);
			if (! chromosomes.contains(base)) {
				Chromosome newChromosome = new Chromosome(base);
				chromosomes.add(newChromosome);
			}
		}
		return chromosomes;
	}
	
	// Creates children chromosomes based on the 2 parent chromosomes 
	public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
		Random random = new Random();
		int breakpoint = 1 + random.nextInt(parent1.size()-2);
		
		ArrayList<Integer> cut = new ArrayList<Integer>( parent1.subList(breakpoint, parent1.size()));
		Chromosome child1 = new Chromosome(parent1.subList(0, breakpoint));
		Chromosome child2 = new Chromosome();
		
		for (Integer gene : parent2) {
			if (! cut.contains(gene)) {
				child2.add(gene);
			}
		}
		child2.addAll(cut);
		
		for (int i = breakpoint; i < parent1.size(); i++) {
			for (Integer gene : parent2) {
				if (cut.contains(gene)) {
					child1.add(gene);
					cut.remove(gene);
				}
			}
		}
		Chromosome[] offsprings = {child1, child2};
		return offsprings;
	}
	
	// Swap two genes of the chromosomes with fixed rate
	public void mutate(Chromosome child) {
		Random random = new Random();
		Collections.swap(child, random.nextInt(child.size()), random.nextInt(child.size()));
	}
	
	public abstract float objectiveFunction(Chromosome c);
	
	// Keeps the best chromosomes to create more children for the next generation
	public void select() {
		ArrayList<Float> costs = new ArrayList<Float>();
		for (Chromosome c : chromosomes) {
			costs.add(objectiveFunction(c));
		}
		while (chromosomes.size() > generateSize) {
			int indexMax = costs.indexOf(Collections.max(costs));
			chromosomes.remove(chromosomes.get(indexMax));
			costs.remove(indexMax);
		}
	}
	
	// Implements the genetic algorithm by one generation
	public ArrayList<Chromosome> solveNext() {
		step++;
		Random random = new Random();
		
		//cross over 60%
		while (chromosomes.size() < generateSize*1.6) {
			int index1 = random.nextInt(chromosomes.size());
			int index2 = random.nextInt(chromosomes.size());
			while (index1 == index2) {
				index2 = random.nextInt(chromosomes.size());
			}
			Chromosome[] children = crossover(chromosomes.get(index1), chromosomes.get(index2));
			for (Chromosome child : children) {
				chromosomes.add(child);
			}
		}
		//mutate 30%
		while (chromosomes.size() < generateSize*1.9) {
			int index1 = random.nextInt(chromosomes.size());
			Chromosome chromosome = chromosomes.get(index1);
			mutate(chromosome);
			chromosomes.add(chromosome);
		}
		//random new 10%
		ArrayList<Chromosome> newGeneration = generateChromosome(chromosomes.get(0).size()+1,(int)(generateSize*0.1));
		chromosomes.addAll(newGeneration);
		
		select();
		return chromosomes;
	}
	
	public Chromosome currentBest() {
		ArrayList<Float> costs = new ArrayList<Float>();
		for (Chromosome c : chromosomes) {
			costs.add(objectiveFunction(c));
		}
		return chromosomes.get(costs.indexOf(Collections.min(costs)));
	}
}


