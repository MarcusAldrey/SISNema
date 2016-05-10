package br.uefs.ecomp.sisNema.controller;

import br.uefs.ecomp.sisNema.exceptions.CinemaNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.LimiteIngressosExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNaoEncontradaException;
import br.uefs.ecomp.sisNema.model.Cinema;
import br.uefs.ecomp.sisNema.model.Comprador;
import br.uefs.ecomp.sisNema.model.CompradorFan;
import br.uefs.ecomp.sisNema.model.Ingresso;
import br.uefs.ecomp.sisNema.model.Sala;
import br.uefs.ecomp.sisNema.model.Sessao;
import br.uefs.ecomp.sisNema.model.Compra;
import br.uefs.ecomp.sisNema.util.ListaEncadeada;

public class CompradorController {
	public static CompradorController instance;
	private ListaEncadeada compras = new ListaEncadeada();
	AdministradorController controllerAdministrador = AdministradorController.getInstance();

	/**
	 * Realiza a compra de um ingresso para um comprador
	 * @param documento
	 * @param idCinema
	 * @param numeroSala
	 * @param horarioInicio
	 * @param qtdIngressos
	 * @param valorTotal
	 * @throws CompradorNaoEncontradoException
	 * @throws CinemaNaoEncontradoException
	 * @throws SalaNaoEncontradaException
	 * @throws SessaoNaoEncontradaException
	 * @throws LimiteIngressosExcedidoException 
	 */
	public void comprarIngresso(int documento, Integer idCinema, int numeroSala, int horarioInicio, int qtdIngressos, double valorTotal) throws CompradorNaoEncontradoException, CinemaNaoEncontradoException, SalaNaoEncontradaException, SessaoNaoEncontradaException, LimiteIngressosExcedidoException {
		Comprador comprador = controllerAdministrador.recuperarComprador(documento);
		Cinema cinema = controllerAdministrador.recuperarCinema(idCinema);
		int ingressosVendidos = controllerAdministrador.recuperarIngressos().obterTamanho();
		Sala sala = cinema.obterSala(numeroSala);
		if(sala == null)
			throw new SalaNaoEncontradaException();
		if(ingressosVendidos + qtdIngressos > sala.getQtdCadeiras())
			throw new LimiteIngressosExcedidoException();
		Sessao sessao = sala.obterSessao(horarioInicio);
		if(sessao == null)
			throw new SessaoNaoEncontradaException();
		for(int i = 0; i < qtdIngressos; i++) {
			Ingresso ingresso = new Ingresso();
			ingresso.setComprador(comprador);
			ingresso.setCinema(cinema);
			ingresso.setSala(sala);
			ingresso.setSessao(sessao);
			controllerAdministrador.recuperarIngressos().inserirFinal(ingresso);
			comprador.getIngressos().inserirFinal(ingresso);
		}
		Compra compra = new Compra(comprador, qtdIngressos, valorTotal);
		compras.inserirFinal(compra);
		if(comprador instanceof CompradorFan)
			controllerAdministrador.recuperarFansHabilitados().inserirFinal(comprador);
	}

	public static void zerarSingleton() {
		instance = new CompradorController();
	}

	public static CompradorController getInstance() {
		if(instance == null)
			return new CompradorController();
		return instance;
	}
	
	/**
	 * Retorna uma lista com todos os ingressos de um comprador
	 * @param comprador
	 * @return ingressos
	 */
	public ListaEncadeada recuperarIngressos(Comprador comprador) {
		return comprador.getIngressos();
	}

}
