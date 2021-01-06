package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private static Scene mainScene;
	
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainGUI.fxml"));
			ScrollPane scrollPane = loader.load();
			
	        TableView<ObservableList<String>> table = new TableView<ObservableList<String>>();
	        table.prefHeightProperty().bind(scrollPane.heightProperty());
	        table.prefWidthProperty().bind(scrollPane.widthProperty());
	        primaryStage.setResizable(false);
	        scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("leonardo");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} //teste
	
	
	
	public static void main(String[] args) {
		launch(args);

	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

}
