package br.uefs.ecomp.sisNema.model;

import br.uefs.ecomp.sisNema.util.ListaEncadeada;
import br.uefs.ecomp.sisNema.util.MeuIterador;

public class Cinema {
	private static int idTotal = 0;
	private int id;
	private String nome;
	private int qtdSalas;
	private Endereco endereco;
	private ListaEncadeada salas;

	public Cinema() {
		idTotal++;
		this.id = idTotal;
		salas = new ListaEncadeada();
	}
	
	/**
	 * Retorna uma sala a partir de seu número
	 * @param numeroSala
	 * @return sala caso encontre, null caso não encontre
	 */
	public Sala obterSala(int numeroSala) {
		MeuIterador iterador = (MeuIterador) salas.iterador();
		Sala sala = null;
		while(iterador.temProximo()) {
			sala = (Sala) iterador.obterProximo();
			if(sala.getNumeroSala() == numeroSala)
				return sala;
		}
		return null;
	}

	/**
	 * @return o id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id o id a ser alterado
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return o nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome o nome a ser alterado
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return a qtdSalas
	 */
	public int getQtdSalas() {
		return qtdSalas;
	}

	/**
	 * @param qtdSalas o qtdSalas a ser alterado
	 */
	public void setQtdSalas(int qtdSalas) {
		this.qtdSalas = qtdSalas;
	}

	/**
	 * @return o endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco o endereco a ser alterado
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return as salas
	 */
	public ListaEncadeada getSalas() {
		return salas;
	}

	/**
	 * @param salas as salas a serem alteradas
	 */
	public void setSalas(ListaEncadeada salas) {
		this.salas = salas;
	}

}
