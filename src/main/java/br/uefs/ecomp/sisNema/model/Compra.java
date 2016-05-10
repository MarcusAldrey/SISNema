package br.uefs.ecomp.sisNema.model;

import java.util.Date;

public class Compra {
	private static int idTotal = 0;
	private int id;
	private Comprador comprador;
	private Date data;
	private int qtdDeIngressos;
	private double valorTotal;
	
	public Compra (Comprador comprador, int qtdDeIngressos, double valorTotal) {
		idTotal++;
		this.id = idTotal;
		this.comprador = comprador;
		this.qtdDeIngressos = qtdDeIngressos;
		this.valorTotal = valorTotal;
		data = new Date();
	}

	/**
	 * @return o comprador
	 */
	public Comprador getComprador() {
		return comprador;
	}

	/**
	 * @param comprador o novo comprador
	 */
	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	/**
	 * @return a data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param a nova data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return a qtdDeIngressos
	 */
	public int getQtdDeIngressos() {
		return qtdDeIngressos;
	}

	/**
	 * @param qtdDeIngressos a nova qtdDeIngressos
	 */
	public void setQtdDeIngressos(int qtdDeIngressos) {
		this.qtdDeIngressos = qtdDeIngressos;
	}

	/**
	 * @return o valorTotal
	 */
	public double getValorTotal() {
		return valorTotal;
	}

	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
	 * @return o id
	 */
	public int getId() {
		return id;
	}

	
}
