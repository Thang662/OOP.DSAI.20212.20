package screen;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


public class Test extends Application{
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Test.fxml"));
		loader.setController(new TestScreenController());
		stage.setOnCloseRequest(null);

		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Genetic Algorithm");
		stage.setScene(scene);
		stage.show();
	}
}
