package service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
	//comandos de injeçao sql
	final String INSERIR = "INSERT INTO pedido(pedido_nome, pedido_data, pedido_metodpag,pedido_preco,pedido_anotacoes) VALUES(?, STR_TO_DATE(?, '%d/%m/%Y'),?,?,?)";
	final String ATUALIZAR = "UPDATE pedido SET pedido_nome=?,pedido_data = STR_TO_DATE(?, '%d/%m/%Y'), pedido_metodpag = ?, pedido_preco = ?, pedido_anotacoes = ?  WHERE pedido_cod = ?";
	final String BUSCAR = "SELECT pedido_cod, pedido_nome,STR_TO_DATE(pedido_data, '%d/%m/%Y'), pedido_metodpag,pedido_preco,pedido_anotacoes FROM pedido WHERE pedido_cod = ?";
	final String BUSCAR_TODOS = "SELECT pedido_cod, pedido_nome,DATE_FORMAT(pedido_data, '%d/%m/%Y'), pedido_metodpag,pedido_preco,pedido_anotacoes FROM pedido";
	final String APAGAR = "DELETE FROM pedido WHERE pedido_cod = ?";


// 	final String BUSCAR_TODOS = "SELECT pedido_cod, pedido_nome,DATE_FORMAT(pedido_data, '%d/%m/%Y'), pedido_metodpag,pedido_preco,pedido_anotacoes FROM pedido";

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
		List<Pedido> pedidos = new ArrayList<>();
		try {
			Connection con = conexao();
			PreparedStatement buscarTodos = con.prepareStatement(BUSCAR_TODOS);
			ResultSet resultadoBusca = buscarTodos.executeQuery();
			while (resultadoBusca.next()) {
				Pedido pedido = extraiPedido(resultadoBusca);
				pedidos.add(pedido);
			}
			buscarTodos.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR BUSCANDO PEDIDOS.");
			System.exit(0);
		} 
		return pedidos;
	}

	

	@Override
	public Pedido buscaPorCod(int pedido_cod) {
		Pedido pedido = null;
		try {
			Connection con = conexao();
			PreparedStatement buscar = con.prepareStatement(BUSCAR);
			buscar.setInt(1, pedido_cod);
			ResultSet resultadoBusca = buscar.executeQuery();
			resultadoBusca.next();
			pedido = extraiPedido(resultadoBusca);
			buscar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR BUSCANDO CONTA COM ID " + pedido_cod);
			System.exit(0);
		} 
		return pedido;

		
	}
	@Override
	public void atualizar(Pedido pedido) {
		try {
			Connection con = conexao();
			PreparedStatement atualizar = con.prepareStatement(ATUALIZAR);
			String dateStr = FORMATADOR.format(pedido.getPedido_data());
			atualizar.setString(1, pedido.getPedido_nome());
			atualizar.setString(2, dateStr);
			atualizar.setString(3, pedido.getPedido_metodpag());
			atualizar.setDouble(4, pedido.getPedido_preco());
			atualizar.setString(5, pedido.getPedido_anotacoes());
			atualizar.setInt(6, pedido.getPedido_cod());

			atualizar.executeUpdate();
			atualizar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR ATUALIZANDO CONTA COM ID " + pedido.getPedido_cod());
			System.exit(0);
		} 

	}



	@Override
	public void apagar(int pedido_cod) {
	
		try {
			Connection con = conexao();
			PreparedStatement apagar = con.prepareStatement(APAGAR);
			apagar.setInt(1, pedido_cod);
			apagar.executeUpdate();
			apagar.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR APAGANDO PEDIDO COM ID " + pedido_cod);
			System.exit(0);
		} 
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
	
	private Pedido extraiPedido(ResultSet resultadoBusca) throws SQLException, ParseException {
		Date pedidodata = FORMATADOR.parse(resultadoBusca.getString(3));
		Pedido pedido = new Pedido();
		pedido.setPedido_cod(resultadoBusca.getInt(1));
		pedido.setPedido_nome(resultadoBusca.getString(2));
		pedido.setPedido_data(pedidodata);
		pedido.setPedido_metodpag(resultadoBusca.getString(4));
		pedido.setPedido_preco(resultadoBusca.getDouble(5));
		pedido.setPedido_anotacoes(resultadoBusca.getString(6));
		return pedido;
	}



}
