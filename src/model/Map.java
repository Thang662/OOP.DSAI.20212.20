package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;

public class Map {
	private static final String COMMA_DELIMITER = ",";
	private static ArrayList<City> cities = new ArrayList<City>();

	public ArrayList<City> getCities() {
		return cities;
	}
	
	public static ArrayList<City> loadMap() throws FileNotFoundException, IOException{
		try (BufferedReader br = new BufferedReader(new FileReader("src\\controllers\\cities_new.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        ObservableValue<Double> x = new SimpleDoubleProperty(Double.parseDouble(values[3])).asObject();
		        ObservableValue<Double> y = new SimpleDoubleProperty(Double.parseDouble(values[4])).asObject();
		        Map.cities.add(new City(values[0], x, y));
		    }
		}
		return cities;
	}
	
//	public static void main (String[] args) throws FileNotFoundException, IOException {
//		Map.loadMap();
//	}
}
