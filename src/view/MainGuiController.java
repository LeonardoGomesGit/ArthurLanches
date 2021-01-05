package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class MainGuiController implements Initializable {
	
	@FXML
	private MenuItem menuItemCliente;
	
	@FXML
	private MenuItem menuItemPedido;
	
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemClienteAction() {
		System.out.println("flinston");
	}
	@FXML
	public void onMenuItemPedidoAction() {
		
			loadView("/view/PedidoGUI.fxml", x -> {});
		
	}
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("flinston3");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		}
		catch (IOException e) {
            System.out.println("Fica flinstons");  
		}
	}	
	
	
	
}
