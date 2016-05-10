package br.uefs.ecomp.sisNema.model;

import br.uefs.ecomp.sisNema.util.Iterador;
import br.uefs.ecomp.sisNema.util.ListaEncadeada;

public class Sala {
	private int numeroSala;
	private int qtdCadeiras;
	private ListaEncadeada sessoes;
	
	public Sala() {
		sessoes = new ListaEncadeada();
	}
	public int getNumeroSala() {
		return numeroSala;
	}
	public void setNumeroSala(int numeroSala) {
		this.numeroSala = numeroSala;
	}
	public int getQtdCadeiras() {
		return qtdCadeiras;
	}
	public void setQtdCadeiras(int qtdCadeiras) {
		this.qtdCadeiras = qtdCadeiras;
	}
	public ListaEncadeada getSessoes() {
		return sessoes;
	}
	public void setSessoes(ListaEncadeada sessoes) {
		this.sessoes = sessoes;
	}
	
	/**
	 * Obtem uma sessão pelo seu horário de início
	 * @param horarioInicio
	 * @return sessao caso encontre, null caso não encontre
	 */
	public Sessao obterSessao(int horarioInicio) {
	Iterador iterador = sessoes.iterador();
	Sessao sessao = null;
	while(iterador.temProximo()) {
		sessao = (Sessao) iterador.obterProximo();
		if(sessao.getHoraInicio() == horarioInicio)
			break;
	}
	if(sessao != null)
		return sessao;
	return null;
	}
}
