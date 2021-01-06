package view;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
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
	private TableColumn<Pedido, Integer> clCod;
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
	@FXML
	private  Button btnLimpar;
	@FXML
	private Button btnApagar;
	@FXML
	private Button btnAtualizar;

	private PedidosService service;
	
	MainGuiController load = new MainGuiController();
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = PedidosService.getNewInstance();
		configuraColunas();
		atualizaDadosTabela();
		configuraBindings();
		
	}
	
	//botão salvar
	public void onbtnSalvarAction() {
		Pedido c = new Pedido();
		pegaValores(c);
		service.salvar(c);
		configuraColunas();
		atualizaDadosTabela();
	}
	
	public void onbtnAtualizarAction() {
		Pedido c = tblPedido.getSelectionModel().getSelectedItem();
		pegaValores(c);
		service.atualizar(c);
		atualizaDadosTabela();
	}
	
	public void onbtnLimparAction() {
		tblPedido.getSelectionModel().select(null);
		txtAnotacoes.setText("");
		txtMetodPag.setText("");
		txtnome.setText("");
		txtPreco.setText(null);
		data.setValue(null);

	}
	
	public void onbtnApagarAction() {
		Pedido c = tblPedido.getSelectionModel().getSelectedItem();
		pegaValores(c);
		service.apagar(c.getPedido_cod());
		atualizaDadosTabela();
	}

	
	
	public void atualizaDadosTabela() {
		tblPedido.getItems().setAll(service.buscarTodas());
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
		clCod.setCellValueFactory(new PropertyValueFactory<>("pedido_cod"));
		clNome.setCellValueFactory(new PropertyValueFactory<>("pedido_nome"));
		clData.setCellValueFactory(new PropertyValueFactory<>("pedido_data"));
		clMetod.setCellValueFactory(new PropertyValueFactory<>("pedido_metodpag"));
		clPreco.setCellValueFactory(new PropertyValueFactory<>("pedido_preco"));
		clAnotacoes.setCellValueFactory(new PropertyValueFactory<>("pedido_anotacoes"));	}
	
	
	private void configuraBindings() {
		
		BooleanBinding camposPreenchidos = txtnome.textProperty().isEmpty().or(txtAnotacoes.textProperty().isEmpty())
				.or(txtPreco.textProperty().isNull()).or(txtMetodPag.textProperty().isEmpty()).or(data.valueProperty().isNull());

		
		BooleanBinding algoSelecionado = tblPedido.getSelectionModel().selectedItemProperty().isNull();
		
		btnApagar.disableProperty().bind(algoSelecionado);
		btnAtualizar.disableProperty().bind(algoSelecionado);
		btnLimpar.disableProperty().bind(algoSelecionado);

		
		btnSalvar.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));


		tblPedido.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
			if (n != null) {
				String s=String.valueOf(n.getPedido_preco());  

				LocalDate date = null;
				date = n.getPedido_data().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				txtnome.setText(n.getPedido_nome());
				txtMetodPag.setText(n.getPedido_metodpag());
				txtPreco.setText(s);
				txtAnotacoes.setText(n.getPedido_anotacoes());
				data.setValue(date);

			}
		});

		
		
		
		
	}






public static Double tryParseToDouble(String str) {
	try {
		return Double.parseDouble(str);
	} catch (NumberFormatException e) {
		return null;
	}
}
}
