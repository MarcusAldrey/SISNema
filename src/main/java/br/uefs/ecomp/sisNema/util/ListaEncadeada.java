package br.uefs.ecomp.sisNema.util;

public class ListaEncadeada implements ILista, IFila {

	private Celula primeiro;
	private int tamanhoLista;

	public ListaEncadeada() {
		primeiro = null;
		tamanhoLista = 0;
	}

	public boolean estaVazia() {
		return primeiro == null;
	}

	public int obterTamanho() {
		return tamanhoLista;
	}
	
	public Celula getInicio() {
		return primeiro;
	}

	public void inserirInicio(Object o) {

		Celula nova = new Celula();
		nova.setObjeto(o);

		if(this.estaVazia())
			primeiro = nova;

		else {
			Celula aux = primeiro;
			primeiro = nova;
			nova.setProx(aux);
		}
		tamanhoLista++;
	}

	public void inserirFinal(Object o) {
		Celula nova = new Celula();
		nova.setObjeto(o);
		Celula aux = primeiro;

		if (this.estaVazia())
			primeiro = nova;

		else {
			while(aux.getProx() != null)
				aux = aux.getProx();
			aux.setProx(nova);
		}
		tamanhoLista++;
	}

	public Object removerInicio() {
		if (primeiro != null) {
			Celula aux = primeiro;
			primeiro = primeiro.getProx();
			tamanhoLista--;
			return aux.getObjeto();
		}
		else
			return null;
	}

	public Object removerFinal() {
		Celula aux = primeiro;
		Celula anterior = primeiro;

		while(aux.getProx() != null) {
			anterior = aux;
			aux = aux.getProx();
		}

		Object removido = aux.getObjeto();
		anterior.setProx(null);
		tamanhoLista--;
		return removido;
	}

	public Object remover(int index) {
		Celula aux = primeiro;
		Celula anterior = primeiro;
		if(index == 0) {
			removerInicio();
			return aux;
		}
		else {

			for(int i = index; i>0; i--) {
				anterior = aux;
				aux = aux.getProx();
			}

			Object removido = aux.getObjeto();
			anterior.setProx(aux.getProx());
			tamanhoLista--;
			return removido;
		}
	}

	public Object remover(Object retirar){
		if (primeiro == null || retirar == null) {
			return null;
		}

		if (primeiro.getObjeto().equals(retirar)) {
			return this.removerInicio();
		}

		else {
			Celula aux = primeiro.getProx();
			Celula ant = primeiro;

			while (!aux.getObjeto().equals(retirar) && aux != null) {
				ant = aux;
				aux = aux.getProx();
			}
			if (aux == null) {
				return null;
			}

			ant.setProx(aux.getProx());
			aux.setProx(null);
			return aux.getObjeto();
		}
	}




	public Object recuperar(int index) {
		Celula aux = primeiro;

		for(int i = index; i>0; i--)
			aux = aux.getProx();

		return aux.getObjeto();
	}

	public int getIndex(Object objeto) {
		int i = 0;
		Celula aux = primeiro;
		while( !(aux.getObjeto().equals(objeto)) ) {
			aux = aux.getProx();
			i++;
		}
		return i;
	}

	public ListaEncadeada mergeSort(ListaEncadeada lista) 
	{ 
		ListaEncadeada esquerda = new ListaEncadeada();
		ListaEncadeada direita = new ListaEncadeada();
		if(lista.obterTamanho() > 1) {
			int i;
			int meio = lista.obterTamanho()/2;
			for(i = 0; i<meio;i++) 
				esquerda.inserirFinal(lista.removerInicio());
			for(i = 0;!lista.estaVazia();i++)
				direita.inserirFinal(lista.removerInicio());
			mergeSort(esquerda);
			mergeSort(direita);
			merge(lista,esquerda, direita);
		}
		return lista;
	}
		
	public void merge(ListaEncadeada principal, ListaEncadeada esquerda, ListaEncadeada direita) 
	{ 
		while(esquerda.getInicio() != null && direita.getInicio() != null) { 
		Object objetoA;
		Object objetoB;
			objetoA = (Comparable) esquerda.getInicio().getObjeto();
			objetoB = (Comparable) direita.getInicio().getObjeto();
			if ( ((Comparable) objetoA).compareTo(((Comparable) objetoB)) < 0)
				principal.inserirFinal(esquerda.removerInicio());
			else
				principal.inserirFinal(direita.removerInicio());
		}
		while(esquerda.getInicio() != null)
			principal.inserirFinal(esquerda.removerInicio());
		while(direita.getInicio() != null)
			principal.inserirFinal(direita.removerInicio());
	} 


public Iterador iterador() {
	MeuIterador novo = new MeuIterador(primeiro);
	return novo;
}

@Override
public Object recuperarInicio() {
	if(!(this.estaVazia())) {
		Object recuperado = this.recuperar(0);
		return recuperado;
	}
	return null;
}
}