package controllers;


import java.awt.TextArea;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.City;
import model.Map;


public class MainScreenController {
	ArrayList<City> cities;
	Line[] lines = new Line[100];
	int[] temp1 = {4, 9, 20, 0, 22, 24, 7, 11, 13, 15, 17, 2, 23, 10, 14, 19, 21, 6, 8, 25, 3, 16, 12, 1, 18};
	
	// Constructor
	public MainScreenController(ArrayList<City> cities) {
		super();
		this.cities = cities;
	}

    @FXML
    private Button startButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button quitButton;
    
    @FXML
    private Pane map;
    
    @FXML
    private ListView<String> route;

    @FXML
    void loadMap(ActionEvent event) throws FileNotFoundException, IOException {
    	ObservableList<String> temp = FXCollections.observableArrayList();
    	for (int i = 0; i < (cities.size() - 1); i++) {
//			map.getChildren().add(new Circle(cities.get(i).getX(), cities.get(i).getY(), 5, Color.CYAN));
			temp.add(cities.get(i).getName());
//			line = new Line(cities.get(i).getX(), cities.get(i).getY(), cities.get(i + 1).getX(), cities.get(i+ 1).getY());
//			line.setStroke(Color.web("#BC243C"));
//			line.setStrokeWidth(4);
			lines[i] = new Line();
    		lines[i].startXProperty().bind(cities.get(temp1[i]).getX());
    		lines[i].startYProperty().bind(cities.get(temp1[i]).getY());
    		lines[i].endXProperty().bind(cities.get(temp1[i + 1]).getX());
    		lines[i].endYProperty().bind(cities.get(temp1[i + 1]).getY());
    		lines[i].setStroke(Color.web("#BC243C"));
    		lines[i].setStrokeWidth(4);
			map.getChildren().add(lines[i]);
            }
//		map.getChildren().add(new Circle(cities.get(25).getX(), cities.get(25).getY(), 5, Color.CYAN));
//		temp.add(cities.get(25).getName());
//		line = new Line(cities.get(25).getX(), cities.get(25).getY(), cities.get(0).getX(), cities.get(0).getY());
//		line.setStroke(Color.web("#BC243C"));
//		line.setStrokeWidth(4);
//		map.getChildren().add(line);
    	route.setItems((ObservableList<String>) temp);
    }

    @FXML
    void quitProgram(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	Alert.AlertType type = Alert.AlertType.CONFIRMATION;
    	Alert alert = new Alert(type, "");
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.getDialogPane().setHeaderText("Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK) {
    		stage.close();
    	}
    	else {
    		event.consume();
    	}
    }
    
    @FXML
    void startAlgorithm(ActionEvent event) {
    	System.out.println("Button clicked");
    }
}
