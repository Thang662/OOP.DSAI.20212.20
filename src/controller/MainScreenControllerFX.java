package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BindingGAForTSP;
import screen.HelpScreen;
import screen.dialog.MessageDialog;

public class MainScreenControllerFX {
	BindingGAForTSP algo;
	Thread test;
	boolean runable;
	Line[] lines;
	int citiesNum;
	int iterations;
	
	@FXML
    private ListView<String> route;

    @FXML
    private TextField distance;

    @FXML
    private Label iteration;

    @FXML
    private TextField citiesInput;

    @FXML
    private TextField iterationsInput;

    @FXML
    private Pane map;

    @FXML
    private Button startButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button stopButton;
    
    @FXML
    void initialize() {
    	distance.setEditable(false);
    	startButton.setVisible(false);
    	stopButton.setVisible(false);
    	
    }

    @FXML
    void loadMap(ActionEvent event) throws FileNotFoundException, IOException {
    	runable = false;
    	route.setItems(null);
    	iteration.setText(null);
    	distance.setText(null);
    	startButton.setVisible(true);
    	try {
			citiesNum = Integer.parseInt(citiesInput.getText());
			if (citiesNum < 0) {
				throw new NegativeInput();
			} else if (citiesNum > 500) {
				throw new InputOutOfRange();
			}
		} catch (NumberFormatException e1) {
			new MessageDialog("ERROR: invalid data type entered in 'Number of cities'", "Please enter a number");
		} catch (NegativeInput e2) {
			new MessageDialog("ERROR: non-possitive number entered in 'Number of cities'", "Please enter a possitive number");
		} catch (InputOutOfRange e3) {
			new MessageDialog("ERROR: number entered in 'Number of cities'", "Please enter a possitive number less than or equals 500");
		} catch (Exception ex) {
			new MessageDialog(ex.getMessage());
		}
    	map.getChildren().clear();
    	if (citiesNum == 0) {
    		citiesNum = 100;
    		citiesInput.setText("" + 100);
    	}
    	lines = new Line[citiesNum];
		algo = new BindingGAForTSP(citiesNum);
    	for (int i = 0; i < algo.tsp.getPoints().size(); i++) {
			map.getChildren().add(new Circle(algo.tsp.getPoints().get(i).getX(), algo.tsp.getPoints().get(i).getY(), 5, Color.CYAN));
			if (i == (algo.tsp.getPoints().size() - 1)) {
				lines[i] = new Line();
				lines[i].startXProperty().bind(algo.tsp.getPoints().get(i).getbindX());
				lines[i].startYProperty().bind(algo.tsp.getPoints().get(i).getbindY());
				lines[i].endXProperty().bind(algo.tsp.getPoints().get(0).getbindX());
				lines[i].endYProperty().bind(algo.tsp.getPoints().get(0).getbindY());
				lines[i].setStroke(Color.BLUE);
				lines[i].setStrokeWidth(4);
			}
			else {
				lines[i] = new Line();
				lines[i].startXProperty().bind(algo.tsp.getPoints().get(i).getbindX());
				lines[i].startYProperty().bind(algo.tsp.getPoints().get(i).getbindY());
				lines[i].endXProperty().bind(algo.tsp.getPoints().get(i + 1).getbindX());
				lines[i].endYProperty().bind(algo.tsp.getPoints().get(i + 1).getbindY());
				if (i == 0) {
					lines[i].setStroke(Color.CADETBLUE);
					lines[i].setStrokeWidth(4);
				}
				else {
					lines[i].setStroke(Color.web("#BC243C"));
		    		lines[i].setStrokeWidth(4);
				}
			}
			lines[i].setVisible(false);
			map.getChildren().add(lines[i]);
    	}
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
    void showHelp(ActionEvent event) {
    	new HelpScreen();
    }
    
    @FXML
    void startAlgorithm(ActionEvent event) throws InterruptedException {
    	stopButton.setVisible(true);
    	startButton.setVisible(false);
    	runable = true;
    	try {
    		iterations = Integer.parseInt(iterationsInput.getText());
			if (iterations < 0) {
				throw new NegativeInput();
			}
		} catch (NumberFormatException e1) {
			new MessageDialog("ERROR: invalid data type entered in 'Number of interations'", "Please enter a number");
		} catch (NegativeInput e2) {
			new MessageDialog("ERROR: non-possitive number entered in 'Number of interations'", "Please enter a possitive number");
		} catch (Exception ex) {
			new MessageDialog(ex.getMessage());
		}
    	for (int i = 0; i < algo.tsp.getPoints().size(); i++) {
    		lines[i].setVisible(true);
    	}
    	if (iterations == 0) iterationsInput.setText("Infinite");
    	iterationsInput.setEditable(false);
    	test = new Thread(this::handleAlgorithm);
    	test.start();
    }

	private Runnable handleAlgorithm() {
		// TODO Auto-generated method stub
		while (runable) {
	    	Platform.runLater(() -> {
    			algo.solveNext();
				distance.setText(String.valueOf(algo.objectiveFunction(algo.currentBest())));
				route.setItems(processingRoute());
				iteration.setText("" + algo.getStep());
				if (algo.getStep() == iterations) {
					runable = false;
					startButton.setVisible(true);
					stopButton.setVisible(false);
					iterationsInput.setEditable(true);
				}
	    	});
	    	try {
	    		Thread.sleep(50);
	    	}
	    	catch (InterruptedException iex){
	    		
	    	}
		}
		return null;
	}
	
	@FXML
    void stopAlgorithm(ActionEvent event) throws InterruptedException {
		runable = false;
		iterationsInput.setEditable(true);
		startButton.setVisible(true);
		stopButton.setVisible(false);
		citiesInput.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(newValue) == citiesNum) startButton.setVisible(true);
				else startButton.setVisible(false);
			}
		});
    }
	
	private ObservableList<String> processingRoute() {
		ObservableList<String> temp = FXCollections.observableArrayList();
		temp.add("[0, " + algo.currentBest().toString().replace("[", ""));
		return temp;
	}
	
	private class NegativeInput extends Exception {
	}
	
	private class InputOutOfRange extends Exception {
	}
}
