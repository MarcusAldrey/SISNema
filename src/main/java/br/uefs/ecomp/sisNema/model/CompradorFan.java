package br.uefs.ecomp.sisNema.model;

public class CompradorFan extends Comprador{
	private int registro;
	public CompradorFan() {
		super();
		this.registro = 0;
	}
	public int getRegistro() {
		return registro;
	}
	public void setRegistro(int registro) {
		this.registro = registro;
	}
}
