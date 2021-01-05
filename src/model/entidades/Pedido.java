package model.entidades;

import java.util.Date;

public class Pedido {

	private Integer pedido_cod;
	private String pedido_nome;
    private Date pedido_data;
    private String pedido_metodpag;
    private Double pedido_preco;
    private String pedido_anotacoes;
    		
   
    public Pedido() {
    	
    }
    
	public Pedido(Integer pedido_cod, String pedido_nome, Date pedido_data, String pedido_metodpag, Double pedido_preco,
			String pedido_anotacoes) {
		this.pedido_cod = pedido_cod;
		this.pedido_nome = pedido_nome;
		this.pedido_data = pedido_data;
		this.pedido_metodpag = pedido_metodpag;
		this.pedido_preco = pedido_preco;
		this.pedido_anotacoes = pedido_anotacoes;
	}
	public Integer getPedido_cod() {
		return pedido_cod;
	}
	public void setPedido_cod(Integer pedido_cod) {
		this.pedido_cod = pedido_cod;
	}
	public String getPedido_nome() {
		return pedido_nome;
	}
	public void setPedido_nome(String pedido_nome) {
		this.pedido_nome = pedido_nome;
	}
	public Date getPedido_data() {
		return pedido_data;
	}
	public void setPedido_data(Date pedido_data) {
		this.pedido_data = pedido_data;
	}
	public String getPedido_metodpag() {
		return pedido_metodpag;
	}
	public void setPedido_metodpag(String pedido_metodpag) {
		this.pedido_metodpag = pedido_metodpag;
	}
	public Double getPedido_preco() {
		return pedido_preco;
	}
	public void setPedido_preco(Double pedido_preco) {
		this.pedido_preco = pedido_preco;
	}
	public String getPedido_anotacoes() {
		return pedido_anotacoes;
	}
	public void setPedido_anotacoes(String pedido_anotacoes) {
		this.pedido_anotacoes = pedido_anotacoes;
	}
    
    
}
