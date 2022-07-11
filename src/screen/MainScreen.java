package screen;

import java.io.BufferedReader;
import java.io.FileReader;

import controllers.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Map;
import model.TravelingSalesman;
import model.TravelingSalesman.SelectionType;

public class MainScreen extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainScreen.fxml"));
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
		loader.setController(new MainScreenController(new TravelingSalesman(200, 25, 26, 200, 1000, 0.4, route, 5, 2, SelectionType.TOURNAMENT, Map.loadMap(), Map.loadLocationX(), Map.loadLocationY())));
		stage.setOnCloseRequest(null);

		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Genetic Algorithm");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}
