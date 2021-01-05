package service;

import java.util.List;

import model.entidades.Pedido;
import service.impl.PedidosDBService;

public interface PedidosService {
	// CREATE
	public void salvar(Pedido pedido);
	
	// RETRIEVE
	public List<Pedido> buscarTodas();

	public Pedido buscaPorCod(int pedido_cod);
	
	// DELETE
	public void apagar(int pedido_cod);
	
	// UPDATE
	public void atualizar(Pedido pedido);
	
	
	// retorna a implementação que escolhemos - no nosso caso o ContasCSVService, 
	// mas poderia ser outro, como ContasDBService...
	public static PedidosService getNewInstance() {
		// return new ContasCSVService();
		return new PedidosDBService();
	}


}
