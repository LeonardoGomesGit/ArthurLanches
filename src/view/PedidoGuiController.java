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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entidades.Pedido;
import service.PedidosService;

public class PedidoGuiController implements Initializable {

	@FXML
	public TableView<Pedido> tblPedido;
	@FXML
	private TableColumn<Pedido, String> clCod;
	@FXML
	private TableColumn<Pedido, String> clNome;
	@FXML
	private TableColumn<Pedido, Date> clData;
	@FXML
	private TableColumn<Pedido, String> clMetod;
	@FXML
	private TableColumn<Pedido, Double> clPreco;
	@FXML
	private TableColumn<Pedido, String> clAnotacoes;
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
	
	public PedidoGuiController(){
		
	}
	
	public void onBtnNovoAction () {
		load.loadView("/view/PedidonewGUI.fxml", x -> {});
	}
	
	MainGuiController load = new MainGuiController();
	
	public void atualizaDadosTabela() {
		tblPedido.getItems().setAll(service.buscarTodas());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = PedidosService.getNewInstance();
		atualizaDadosTabela();

		
	}
	
	public void onbtnSalvarAction() {
		Pedido c = new Pedido();
		pegaValores(c);
		service.salvar(c);
		atualizaDadosTabela();
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
	
	
	private void configuraColunas() {
		clCod.setCellValueFactory(new PropertyValueFactory<>("Código"));
		clNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		clData.setCellValueFactory(new PropertyValueFactory<>("Data"));
		clMetod.setCellValueFactory(new PropertyValueFactory<>("Método de pagamento"));
		clPreco.setCellValueFactory(new PropertyValueFactory<>("Preço"));
		clAnotacoes.setCellValueFactory(new PropertyValueFactory<>("Anotacoes"));	}





public static Double tryParseToDouble(String str) {
	try {
		return Double.parseDouble(str);
	} catch (NumberFormatException e) {
		return null;
	}
}
}
