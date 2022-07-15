package screen;

import controller.MainScreenControllerFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainScreenFX extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/screen/MainScreenFX.fxml"));
		loader.setController(new MainScreenControllerFX());
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
