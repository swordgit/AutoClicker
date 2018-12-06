package com.autoclicker;

import java.awt.Robot;
import java.awt.event.InputEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

@SuppressWarnings({"rawtypes","unchecked", "restriction"})
public class Controller extends Application {
	private boolean leftClickStarted = false;
	private String status = "Clicker Ready!";
	private Label setKeyLabel = new Label("Set Key : "), statusMsg = new Label(getStatus());
	private KeyCode key;
    
	public static void main(String[] args) {
		launch(args);
	}    

	@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("AutoClicker");
        GridPane gridPane = createControlPanel();
        Scene scene = new Scene(gridPane, 300, 150);
        Robot clicker = new Robot();
        Thread clicking = new Thread(new Runnable() {
			@Override
			public void run() {
				while(leftClickStarted) {
					clicker.mousePress(InputEvent.BUTTON1_MASK);
					clicker.mouseRelease(InputEvent.BUTTON1_MASK);
					clicker.delay(500);
				}
			}			
		});	
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == key) {
					leftClickStarted = !leftClickStarted;
					if(leftClickStarted) {
						setStatus("Clicker Started!");
						statusMsg.setText(getStatus());
						clicking.start();
					} else {
						setStatus("Clicker Stopped!");
						statusMsg.setText(getStatus());
						clicking.interrupt();
					}
				}
			}
		});
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	private GridPane createControlPanel() {
    	GridPane gridPane = new GridPane();
    	gridPane.setPadding(new Insets(20,20,20,20));
    	gridPane.setHgap(10);
    	gridPane.setVgap(10);
    	ColumnConstraints columnOneConstraints = new ColumnConstraints(100,100,Double.MAX_VALUE);
    	columnOneConstraints.setHalignment(HPos.CENTER);
    	ColumnConstraints columnTwoConstraints = new ColumnConstraints(100,100,Double.MAX_VALUE);
    	columnTwoConstraints.setHgrow(Priority.ALWAYS);
    	gridPane.getColumnConstraints().addAll(columnOneConstraints,columnTwoConstraints);
    	addUIControls(gridPane);
    	return gridPane;  	
    }
    
	private void addUIControls(GridPane gridPane) {
		Configuration.getInstance();
		final ComboBox keySelection = new ComboBox(Configuration.getKeyConfig());
		final Button setConfig = new Button("Set");
		
		gridPane.add(setKeyLabel, 0, 0, 1, 1);
		GridPane.setHalignment(setKeyLabel, HPos.CENTER);
		GridPane.setMargin(setKeyLabel, new Insets(10, 0, 10, 0));
		
		gridPane.add(keySelection, 1, 0, 1, 1);
		GridPane.setHalignment(keySelection, HPos.CENTER);
		GridPane.setMargin(keySelection, new Insets(10, 0, 10 ,0));
		
		gridPane.add(statusMsg, 1, 1, 1, 1);
		GridPane.setHalignment(statusMsg, HPos.CENTER);
		GridPane.setMargin(statusMsg, new Insets(10, 0, 10 ,0));
		
		gridPane.add(setConfig, 0, 1, 1, 1);
		GridPane.setHalignment(setConfig, HPos.CENTER);
		GridPane.setMargin(setConfig, new Insets(10, 0, 10 ,0));
		
		setConfig.setPrefWidth(100);
		keySelection.getSelectionModel().selectFirst();
		setKey(Configuration.getKeyCode(keySelection.getSelectionModel().getSelectedItem().toString()));
		keySelection.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setKey(Configuration.getKeyCode(keySelection.getSelectionModel().getSelectedItem().toString()));
			}
		});
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 public KeyCode getKey() {
		return key;
	}

	public void setKey(KeyCode key) {
		this.key = key;
	}
}
