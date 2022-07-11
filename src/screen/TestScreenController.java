package screen;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class TestScreenController {

    @FXML
    private Pane pane;
    public DoubleProperty xStart;
	public DoubleProperty yStart;
	public DoubleProperty xEnd;
	public DoubleProperty yEnd;
	public Line line;
	
	public void initialize() {
		line = new Line();
		xStart = new SimpleDoubleProperty();
		yStart = new SimpleDoubleProperty();
		xEnd = new SimpleDoubleProperty();
		yEnd = new SimpleDoubleProperty();
		xStart.set(0);
		yStart.set(0);
		xEnd.set(100);
		yEnd.set(100);
		line.endXProperty().bind(xStart);
		xStart.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				int i = 100;
				try {
					Thread.sleep(i * 10);
					line.setTranslateX(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		line.endYProperty().bind(yStart);
		line.startXProperty().bind(xEnd);
		line.startYProperty().bind(yEnd);
		pane.getChildren().add(line);
		
		for (int i = 0; i < 10; i++) {
			xStart.set(i);
		}
	}
}

