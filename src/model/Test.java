package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import model.TravelingSalesman.SelectionType;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		final String COMMA_DELIMITER = ",";
		double[][] route = new double[26][26];
		// TODO Auto-generated method stub
		try (BufferedReader br = new BufferedReader(new FileReader("src\\controllers\\distances.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        route[Integer.parseInt(values[0])][Integer.parseInt(values[1])] = Double.parseDouble(values[2]);
		    }
		}
		TravelingSalesman test = new TravelingSalesman(200, 25, 26, 200, 2000, 0.2, route, 5, 2, SelectionType.TOURNAMENT);
		test.optimize();

	}

}
