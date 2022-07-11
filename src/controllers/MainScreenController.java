package controllers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.City;
import model.SalesmanGenome;
import model.TravelingSalesman;


public class MainScreenController {
//	ArrayList<City> cities;
	Line[] lines = new Line[100];
	TravelingSalesman tsp;
//	int[] temp1 = {4, 9, 20, 0, 22, 24, 7, 11, 13, 15, 17, 2, 23, 10, 14, 19, 21, 6, 8, 25, 3, 16, 12, 1, 18};
	
	// Constructor
	public MainScreenController(TravelingSalesman tsp) {
		super();
		this.tsp = tsp;
	}
	
	@FXML
    private TextField distance;

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
//    	ObservableList<String> temp = FXCollections.observableArrayList();
    	for (int i = 0; i < tsp.getCities().size(); i++) {
			map.getChildren().add(new Circle(tsp.getCities().get(i).getX(), tsp.getCities().get(i).getY(), 5, Color.CYAN));
//			temp.add(cities.get(temp1[i + 1]).getName());
//			line = new Line(cities.get(i).getX(), cities.get(i).getY(), cities.get(i + 1).getX(), cities.get(i+ 1).getY());
//			line.setStroke(Color.web("#BC243C"));
//			line.setStrokeWidth(4);
//			lines[i] = new Line();
//    		lines[i].startXProperty().bind(cities.get(temp1[i]).getbindX());
//    		lines[i].startYProperty().bind(cities.get(temp1[i]).getbindY());
//    		lines[i].endXProperty().bind(cities.get(temp1[i + 1]).getbindX());
//    		lines[i].endYProperty().bind(cities.get(temp1[i + 1]).getbindY());
//    		lines[i].setStroke(Color.web("#BC243C"));
//    		lines[i].setStrokeWidth(4);
//			map.getChildren().add(lines[i]);
//			System.out.println(i);
            }
    	lines[0] = new Line();
		lines[0].startXProperty().bind(this.tsp.getCities().get(this.tsp.getStartingCity() - 1).getbindX());
		lines[0].startYProperty().bind(this.tsp.getCities().get(this.tsp.getStartingCity() - 1).getbindY());
		lines[0].endXProperty().bind(this.tsp.getCities().get(0).getbindX());
		lines[0].endYProperty().bind(this.tsp.getCities().get(0).getbindY());
		lines[0].setStroke(Color.CADETBLUE);
		lines[0].setStrokeWidth(4);
		map.getChildren().add(lines[0]);
		for (int i = 1; i < (this.tsp.getCities().size() - 1); i++) {
//			if (i == (this.tsp.getStartingCity() - 1)) continue;
			if (i == (this.tsp.getStartingCity() -1)) {
				lines[i] = new Line();
				lines[i].startXProperty().bind(this.tsp.getCities().get(i - 1).getbindX());
				lines[i].startYProperty().bind(this.tsp.getCities().get(i - 1).getbindY());
				lines[i].endXProperty().bind(this.tsp.getCities().get(i + 1).getbindX());
				lines[i].endYProperty().bind(this.tsp.getCities().get(i + 1).getbindY());
				System.out.println("line " + i + "city " + (i - 1) + "city" + (i + 1));
			}
			else if (i >= this.tsp.getStartingCity()) {
				lines[i] = new Line();
				lines[i].startXProperty().bind(this.tsp.getCities().get(i).getbindX());
				lines[i].startYProperty().bind(this.tsp.getCities().get(i).getbindY());
				lines[i].endXProperty().bind(this.tsp.getCities().get(i + 1).getbindX());
				lines[i].endYProperty().bind(this.tsp.getCities().get(i + 1).getbindY());
				System.out.println("line " + i + "city " + (i) + "city" + (i + 1));
			}
			else {
				lines[i] = new Line();
				lines[i].startXProperty().bind(this.tsp.getCities().get(i - 1).getbindX());
				lines[i].startYProperty().bind(this.tsp.getCities().get(i - 1).getbindY());
				lines[i].endXProperty().bind(this.tsp.getCities().get(i).getbindX());
				lines[i].endYProperty().bind(this.tsp.getCities().get(i).getbindY());
				System.out.println("line " + i + "city " + (i - 1) + "city" + (i));
			}
    		lines[i].setStroke(Color.web("#BC243C"));
    		lines[i].setStrokeWidth(4);
			map.getChildren().add(lines[i]);
		}
		lines[25] = new Line();
		lines[25].endXProperty().bind(this.tsp.getCities().get(25).getbindX());
		lines[25].endYProperty().bind(this.tsp.getCities().get(25).getbindY());
		lines[25].startXProperty().bind(this.tsp.getCities().get(this.tsp.getStartingCity() - 1).getbindX());
		lines[25].startYProperty().bind(this.tsp.getCities().get(this.tsp.getStartingCity() - 1).getbindY());
		lines[25].setStroke(Color.BLUE);
		lines[25].setStrokeWidth(4);
		map.getChildren().add(lines[25]);
		Timeline t = new Timeline();
//		t.getKeyFrames().add(new KeyFrame(
//				Duration.seconds(2), 
//				new KeyValue(null, null)
//				));
//		t.play();
//		map.getChildren().add(new Circle(cities.get(25).getX(), cities.get(25).getY(), 5, Color.CYAN));
//		temp.add(cities.get(25).getName());
//		lines[25] = new Line(cities.get(25).getX(), cities.get(25).getY(), cities.get(0).getX(), cities.get(0).getY());
//		map.getChildren().add(lines[25]);
//		line.setStroke(Color.web("#BC243C"));
//		line.setStrokeWidth(4);
//		map.getChildren().add(line);
//    	route.setItems((ObservableList<String>) temp);
//    	System.out.println(temp);
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
    void startAlgorithm(ActionEvent event) throws InterruptedException {
    	System.out.println("Button clicked");
//    	int min = 0;
//        int max = 700;
//    	this.tsp.getCities().get(0).setX((Math.random()*(max-min+1)+min));
//		this.tsp.optimize();
//		for (City city: this.tsp.getCities()) {
//			System.out.println("x: " + city.getX() + " y: " + city.getY());
//		}
    	tsp.initialPopulation();
    	Thread test = new Thread(this::handleAlgorithm);
    	test.start();
		System.out.println("Initial x: " + this.tsp.getCities().get(tsp.getStartingCity() - 1).getX() + " Initial y: " + this.tsp.getCities().get(tsp.getStartingCity() - 1).getY());
    }

	private Runnable handleAlgorithm() {
		// TODO Auto-generated method stub
	    for (int i = 0; i < 1000; i++) {
	    	Platform.runLater(() -> {
	    		try {
	    			route.setItems(tsp.getRoute());
					tsp.nextStep();
					distance.setText(String.valueOf(tsp.getBestDistance()));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	});
	    	try {
	    		Thread.sleep(100);
	    	}
	    	catch (InterruptedException iex){
	    		
	    	}
	    }
		return tsp;
	}
}
