package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TravelingSalesman extends Thread{
	public TravelingSalesman(int generationSize, int genomeSize, int numberOfCities, int reproductionSize, int maxIterations, double d,
			double[][] travelPrices, int startingCity, int tournamentSize, SelectionType selectionType, ArrayList<City> cities, ArrayList<Double> locationX, ArrayList<Double> locationY) {
		super();
		this.generationSize = generationSize;
		this.genomeSize = genomeSize;
		this.numberOfCities = numberOfCities;
		this.reproductionSize = reproductionSize;
		this.maxIterations = maxIterations;
		this.mutationRate = d;
		this.travelPrices = travelPrices;
		this.startingCity = startingCity;
		this.tournamentSize = tournamentSize;
		this.selectionType = selectionType;
		this.cities = cities;
		this.locationX = locationX;
		this.locationY = locationY;
	}

	private int generationSize;
	private int genomeSize;
	private int numberOfCities;
	private int reproductionSize;
	private int maxIterations;
	private double mutationRate;
	private double[][] travelPrices;
	private int startingCity;
	private int targetFitness;
	private int tournamentSize;
	private SelectionType selectionType;
	ArrayList<Double> locationX;
	ArrayList<Double> locationY;
	ArrayList<City> cities;
	double bestDistance;
	List<SalesmanGenome> population;
	SalesmanGenome globalBestGenome;
	
	
	public enum SelectionType {
	    TOURNAMENT,
	    ROULETTE
	}
	
	
	// We select reproductionSize genomes based on the method
	// predefined in the attribute selectionType
	public List<SalesmanGenome> selection(List<SalesmanGenome> population) {
	    List<SalesmanGenome> selected = new ArrayList<>();
	    SalesmanGenome winner;
	    for (int i=0; i < reproductionSize; i++) {
	        if (selectionType == SelectionType.ROULETTE) {
	            selected.add(rouletteSelection(population));
	        }
	        else if (selectionType == SelectionType.TOURNAMENT) {
	            selected.add(tournamentSelection(population));
	        }
	    }
	    return selected;
	}

	public SalesmanGenome rouletteSelection(List<SalesmanGenome> population) {
	    int totalFitness = population.stream().map(SalesmanGenome::getFitness).mapToInt(Double::intValue).sum();
	    
	    // We pick a random value - a point on our roulette wheel
	    Random random = new Random();
	    int selectedValue = random.nextInt(totalFitness);
	    
	    // Because we're doing minimization, we need to use reciprocal
	    // value so the probability of selecting a genome would be
	    // inversely proportional to its fitness - the smaller the fitness
	    // the higher the probability
	    float recValue = (float) 1/selectedValue;
	    
	    // We add up values until we reach out recValue, and we pick the
	    // genome that crossed the threshold
	    float currentSum = 0;
	    for (SalesmanGenome genome : population) {
	        currentSum += (float) 1/genome.getFitness();
	        if (currentSum >= recValue) {
	            return genome;
	        }
	    }
	    
	    // In case the return didn't happen in the loop above, we just
	    // select at random
	    int selectRandom = random.nextInt(generationSize);
	    return population.get(selectRandom);
	}

	// A helper function to pick n random elements from the population
	// so we could enter them into a tournament
	public static <E> List<E> pickNRandomElements(List<E> list, int n) {
	    Random r = new Random();
	    int length = list.size();

	    if (length < n) return null;

	    for (int i = length - 1; i >= length - n; --i) {
	        Collections.swap(list, i , r.nextInt(i + 1));
	    }
	    return list.subList(length - n, length);
	}

	// A simple implementation of the deterministic tournament - the best genome
	// always wins
	public SalesmanGenome tournamentSelection(List<SalesmanGenome> population) {
	    List<SalesmanGenome> selected = pickNRandomElements(population, tournamentSize);
	    return Collections.min(selected);
	}
	
	public List<SalesmanGenome> crossover(List<SalesmanGenome> parents) {
	    // Housekeeping
	    Random random = new Random();
	    int breakpoint = random.nextInt(genomeSize);
	    List<SalesmanGenome> children = new ArrayList<>();

	    // Copy parental genomes - we copy so we wouldn't modify in case they were
	    // chosen to participate in crossover multiple times
	    List<Integer> parent1Genome = new ArrayList<>(parents.get(0).getGenome());
	    List<Integer> parent2Genome = new ArrayList<>(parents.get(1).getGenome());
	    
	    // Creating child 1
	    for (int i = 0; i < breakpoint; i++) {
	        int newVal;
	        newVal = parent2Genome.get(i);
	        Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
	    }
	    children.add(new SalesmanGenome(parent1Genome, numberOfCities, travelPrices, startingCity));
	    parent1Genome = parents.get(0).getGenome(); // Reseting the edited parent
	    
	    // Creating child 2
	    for (int i = breakpoint; i < genomeSize; i++) {
	        int newVal = parent1Genome.get(i);
	        Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
	    }
	    children.add(new SalesmanGenome(parent2Genome, numberOfCities, travelPrices, startingCity));

	    return children;
	}
	
	public SalesmanGenome mutate(SalesmanGenome salesman) {
	    Random random = new Random();
	    float mutate = random.nextFloat();
	    if (mutate < mutationRate) {
	        List<Integer> genome = salesman.getGenome();
	        Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
	        return new SalesmanGenome(genome, numberOfCities, travelPrices, startingCity);
	    }
	    return salesman;
	}
	
	public List<SalesmanGenome> createGeneration(List<SalesmanGenome> population) {
	    List<SalesmanGenome> generation = new ArrayList<>();
	    int currentGenerationSize = 0;
	    while (currentGenerationSize < generationSize) {
	        List<SalesmanGenome> parents = pickNRandomElements(population, 2);
	        List<SalesmanGenome> children = crossover(parents);
	        children.set(0, mutate(children.get(0)));
	        children.set(1, mutate(children.get(1)));
	        generation.addAll(children);
	        currentGenerationSize += 2;
	    }
	    return generation;
	}
	
	public SalesmanGenome optimize() throws InterruptedException {
	    List<SalesmanGenome> population = initialPopulation();
	    SalesmanGenome globalBestGenome = population.get(0);
	    System.out.println(population);
	    for (int i = 0; i < maxIterations; i++) {
	        List<SalesmanGenome> selected = selection(population);
	        population = createGeneration(selected);
	        globalBestGenome = Collections.min(population);
	        List<Integer> temp = globalBestGenome.getGenome();
	        System.out.println(temp.size());
	        for (int j = 0; j < temp.size(); j++) {
	        	if (j >= (this.startingCity - 1)) {
	        		this.cities.get(j + 1).setX(this.locationX.get(temp.get(j)));
		        	this.cities.get(j + 1).setY(this.locationY.get(temp.get(j)));
	        	}
	        	else {
		        	this.cities.get(j).setX(this.locationX.get(temp.get(j)));
		        	this.cities.get(j).setY(this.locationY.get(temp.get(j)));
	        	}
	        }
//	        Thread.sleep(100);
//	    	int min = 0;
//	        int max = 700;
//	    	this.cities.get(0).setX((Math.random()*(max-min+1)+min));
	        System.out.println("Iteration " + i + " " + globalBestGenome.getFitness());
	        this.bestDistance = globalBestGenome.getFitness();
	        System.out.println(globalBestGenome.getGenome());
	        if (globalBestGenome.getFitness() < targetFitness)
	            break;
	    }
	    return globalBestGenome;
	}
	
public SalesmanGenome nextStep() throws InterruptedException {	    
	    List<SalesmanGenome> selected = selection(this.population);
        this.population = createGeneration(selected);
        this.globalBestGenome = Collections.min(population);
        List<Integer> temp = globalBestGenome.getGenome();
        System.out.println(temp.size());
        for (int j = 0; j < temp.size(); j++) {
        	if (j >= (this.startingCity - 1)) {
        		this.cities.get(j + 1).setX(this.locationX.get(temp.get(j)));
	        	this.cities.get(j + 1).setY(this.locationY.get(temp.get(j)));
        	}
        	else {
	        	this.cities.get(j).setX(this.locationX.get(temp.get(j)));
	        	this.cities.get(j).setY(this.locationY.get(temp.get(j)));
        	}
        }
//        Thread.sleep(100);
//    	int min = 0;
//        int max = 700;
//    	this.cities.get(0).setX((Math.random()*(max-min+1)+min));
//        System.out.println("Iteration " + i + " " + globalBestGenome.getFitness());
        this.bestDistance = globalBestGenome.getFitness();
        System.out.println(globalBestGenome.getGenome());
		return globalBestGenome;
	}

	public List<SalesmanGenome> initialPopulation() {
		List<SalesmanGenome> population = new ArrayList<SalesmanGenome>();
		for (int i = 0; i < 5; i++) {
			population.add(new SalesmanGenome(numberOfCities, travelPrices, startingCity));
		}
		this.population = population;
		this.globalBestGenome = population.get(0);
		return population;
	}
	
	public ObservableList<String> getRoute(){
		ObservableList<String> temp = FXCollections.observableArrayList();
		List<Integer> temp1 = this.globalBestGenome.getGenome();
		temp.add(this.cities.get(this.startingCity - 1).getName() + "->" + this.cities.get(temp1.get(0)).getName());
		for (int i = 0; i < temp1.size(); i++) {
			if (i < (temp1.size() - 1)) {
				temp.add(this.cities.get(temp1.get(i)).getName() + "->" + this.cities.get(temp1.get(i + 1)).getName());
			}
			else {
				temp.add(this.cities.get(temp1.get(i)).getName() + "->" + this.cities.get(this.startingCity - 1).getName());
			}
		}
		return temp;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public int getStartingCity() {
		return startingCity;
	}
	
	public double getBestDistance() {
		return bestDistance;
	}

	public ArrayList<Double> getLocationX() {
		return locationX;
	}

	public ArrayList<Double> getLocationY() {
		return locationY;
	}
}
