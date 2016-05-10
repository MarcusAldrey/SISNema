package br.uefs.ecomp.sisNema.util;

public class Celula {

	private Object objeto;
	private Celula prox;

	public Object getObjeto()	{
		return objeto;
	}

	public Celula getProx() {
		return prox;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

	public void setProx(Celula prox) {
		this.prox = prox;
	}
}
