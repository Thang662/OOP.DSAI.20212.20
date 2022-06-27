package screen;

import controllers.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Map;

public class MainScreen extends Application{
	@Override
	public void start(Stage stage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
//		
//		Scene scene = new Scene(root);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainScreen.fxml"));
		loader.setController(new MainScreenController(Map.loadMap()));
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
