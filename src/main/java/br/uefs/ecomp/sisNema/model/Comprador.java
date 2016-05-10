package br.uefs.ecomp.sisNema.model;

import br.uefs.ecomp.sisNema.util.ListaEncadeada;

public class Comprador implements Comparable<Comprador>{
	private String nome;
	private Endereco endereco;
	private String telefone;
	private String email;
	private int documento;
	private ListaEncadeada ingressos;
	
	public Comprador() {
		ingressos = new ListaEncadeada();
	}
	
	
	/**
	 * @return o nome do comprador
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * @param o nome a ser inserido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * @return o endereço do comprador
	 */
	public Endereco getEndereco() {
		return endereco;
	}


	/**
	 * @param o endereço a ser inserido
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	/**
	 * @return o telefone do comprador
	 */
	public String getTelefone() {
		return telefone;
	}


	/**
	 * @param o telefone a ser inserido
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	/**
	 * @return o e-mail do comprador
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param o e-mail a ser inserido
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return o número do documento do comprador
	 */
	public int getDocumento() {
		return documento;
	}


	/**
	 * @param o número do documento a ser inserido
	 */
	public void setDocumento(int documento) {
		this.documento = documento;
	}


	/**
	 * @return a lista de ingressos de um comprador
	 */
	public ListaEncadeada getIngressos() {
		return ingressos;
	}


	/**
	 * @param a lista de ingressos a ser inserida
	 */
	public void setIngressos(ListaEncadeada ingressos) {
		this.ingressos = ingressos;
	}


	/**
	 * Compara o nome de um comprador com o de outro
	 * @param comprador
	 * @return -1 se o nome tiver menor valor alfabético, 1 se o nome tiver maior valor alfabético
	 * 0 se os nomes forem iguais
	 */
	@Override
	public int compareTo(Comprador comprador) {
		if(this.nome.compareTo(((Comprador) comprador).getNome()) < 0)
			return -1;
		else if(this.nome.compareTo(((Comprador) comprador).getNome()) > 0)
			return 1;
		return 0;
	}
	
}
