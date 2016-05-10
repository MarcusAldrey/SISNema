package br.uefs.ecomp.sisNema.util;

public class Fila implements IFila {
	
	private ListaEncadeada fila = new ListaEncadeada();
	
	@Override
	public boolean estaVazia() {
		if(fila.obterTamanho()==0)
			return true;
		return false;
	}

	@Override
	public int obterTamanho() {
		return fila.obterTamanho();
	}

	@Override
	public void inserirFinal(Object o) {
		fila.inserirFinal(o);		
	}

	@Override
	public Object removerInicio() {
		Object removido = fila.removerInicio();
		return removido;
	}

	@Override
	public Object recuperarInicio() {
		if(!(fila.estaVazia())) {
			Object recuperado = fila.recuperar(0);
			return recuperado;
		}
		return null;
	}

	public Iterador iterador() {
		return fila.iterador();
	}

}
