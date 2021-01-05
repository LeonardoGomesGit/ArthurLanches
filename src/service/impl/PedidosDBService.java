package service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.List;

import java.sql.Connection;

import model.entidades.Pedido;
import service.PedidosService;

public class PedidosDBService implements PedidosService {
	
	// dados para acesso ao banco, atualize de acordo com o seu banco de dados
	final String USUARIO = "root";
	final String SENHA = "1234567";
	final String URL_BANCO = "jdbc:mysql://localhost:3306/arthurlanches";
	
	// constantes de acesso
	final String CLASSE_DRIVER = "com.mysql.jdbc.Driver";
	
	final String INSERIR = "INSERT INTO pedido(pedido_nome, pedido_data, pedido_metodpag,pedido_preco,pedido_anotacoes) VALUES(?, STR_TO_DATE(?, '%d/%m/%Y'),?,?,?)";
	

	//formatando a data
	final String FORMATO_DATA = "dd/MM/yyyy";
	final SimpleDateFormat FORMATADOR = new SimpleDateFormat(FORMATO_DATA);


	@Override
	public void salvar(Pedido pedido) {
		try {
			Connection con = conexao();
			PreparedStatement salvar = con.prepareStatement(INSERIR);
			String dateStr = FORMATADOR.format(pedido.getPedido_data());
			salvar.setString(1, pedido.getPedido_nome());
			salvar.setString(2, dateStr);
			salvar.setString(3, pedido.getPedido_metodpag());
			salvar.setDouble(4, pedido.getPedido_preco());
			salvar.setString(5, pedido.getPedido_anotacoes());
			salvar.executeUpdate();
			salvar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR SALVANDO CONTA");
			System.exit(0);
		} 

		
	}

	@Override
	public List<Pedido> buscarTodas() {
		
		
		return null;
	}

	@Override
	public Pedido buscaPorCod(int pedido_cod) {
		
		
		return null;
	}

	@Override
	public void apagar(int pedido_cod) {
	
		
		
	}

	@Override
	public void atualizar(Pedido pedido) {

		
		
	}
	
	// abre uma nova conexão com o banco de dados. Se algum erro for lançado
	// aqui, verifique o erro com atenção e se o banco está rodando
	private Connection conexao() {
		try {
			Class.forName(CLASSE_DRIVER);
			return DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof ClassNotFoundException) {
				System.err.println("VERIFIQUE SE O DRIVER DO BANCO DE DADOS ESTÁ NO CLASSPATH");
			} else {
				System.err.println("VERIFIQUE SE O BANCO ESTÁ RODANDO E SE OS DADOS DE CONEXÃO ESTÃO CORRETOS");
			}
			System.exit(0);
			// o sistema deverá sair antes de chegar aqui...
			return null;
		}
	}


}
