package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PedidoGuiController {


	@FXML
	private Button btnNovo;
	
	public void onBtnNovoAction () {
		load.loadView("/view/PedidonewGUI.fxml", x -> {});
	}
	
	MainGuiController load = new MainGuiController();

}
