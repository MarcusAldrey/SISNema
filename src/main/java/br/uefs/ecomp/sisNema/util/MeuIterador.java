package br.uefs.ecomp.sisNema.util;

import br.uefs.ecomp.sisNema.util.Celula;

public class MeuIterador implements Iterador {

	private Celula atual;
	private Celula primeiro;

	public MeuIterador(Celula primeiro) {
		atual = (Celula) primeiro;
		this.primeiro = primeiro;
	}

	public void reset() {
		atual = primeiro;
	}

	public boolean temProximo() {
		return atual != null;
	}

	public Object obterProximo() {
		if(temProximo()) {
			Object objetoAtual = atual.getObjeto();
			atual = atual.getProx();
			return objetoAtual;
		}
		return null;
	}
}
