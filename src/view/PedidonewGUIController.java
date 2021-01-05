package view;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.entidades.Pedido;
import service.PedidosService;

public class PedidonewGUIController implements Initializable {
	@FXML
	private TextField txtnome;
	@FXML
	private DatePicker data;
	@FXML
	private TextField txtMetodPag;
	@FXML
	private TextField txtPreco;
	@FXML
	private TextField txtAnotacoes;
	@FXML
	private Button btnSalvar;
	
	private PedidosService service;

	
	
	public void onbtnSalvarAction() {
		Pedido c = new Pedido();
		pegaValores(c);
		service.salvar(c);
	}
	
	
	
	private void pegaValores(Pedido p) { 
		p.setPedido_nome(txtnome.getText());
		p.setPedido_data(dataSelecionada());
		p.setPedido_metodpag(txtMetodPag.getText());
		p.setPedido_preco(tryParseToDouble(txtPreco.getText()));
		p.setPedido_anotacoes(txtAnotacoes.getText());
	}
	
	private Date dataSelecionada() {
		LocalDateTime time = data.getValue().atStartOfDay();
		return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		service = PedidosService.getNewInstance();

		
	}
	
	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}



	
	
}