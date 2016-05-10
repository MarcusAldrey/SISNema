package br.uefs.ecomp.sisNema.model;

public class Ingresso {
	private static int idTotal = 0;
	private int id;
	private Comprador comprador;
	private Cinema cinema;
	private Sala sala;
	private Sessao sessao;
	
	public Ingresso() {
		idTotal++;
		this.id = idTotal;
	}
	
	public int getId() {
		return this.id;
	}
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	public Sala getSala() {
		return this.sala;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
}
