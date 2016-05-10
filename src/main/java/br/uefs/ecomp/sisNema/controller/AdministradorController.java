package br.uefs.ecomp.sisNema.controller;

import br.uefs.ecomp.sisNema.exceptions.CampoObrigatorioInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNuloException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNuloException;
import br.uefs.ecomp.sisNema.exceptions.FaHabilitadoInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.IntervaloMinimoInsuficienteException;
import br.uefs.ecomp.sisNema.exceptions.LimiteSalasExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.RemocaoNaoPermitidaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNulaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNulaException;
import br.uefs.ecomp.sisNema.model.Cinema;
import br.uefs.ecomp.sisNema.model.Comprador;
import br.uefs.ecomp.sisNema.model.CompradorFan;
import br.uefs.ecomp.sisNema.model.Ingresso;
import br.uefs.ecomp.sisNema.model.Sala;
import br.uefs.ecomp.sisNema.model.Sessao;
import br.uefs.ecomp.sisNema.util.Fila;
import br.uefs.ecomp.sisNema.util.Iterador;
import br.uefs.ecomp.sisNema.util.ListaEncadeada;
import br.uefs.ecomp.sisNema.util.MeuIterador;

public class AdministradorController {

	private ListaEncadeada compradores = new ListaEncadeada();
	private Fila fansHabilitados = new Fila();
	private ListaEncadeada cinemas = new ListaEncadeada();
	private ListaEncadeada ingressos = new ListaEncadeada();
	private ListaEncadeada vendas = new ListaEncadeada();
	private static AdministradorController instance;

	private AdministradorController() {

	}

	public static AdministradorController getInstance() {
		if(instance == null)
			instance = new AdministradorController();
		return instance;
	}

	/**
	 * Retorna as vendas
	 * @return vendas
	 */
	public ListaEncadeada recuperarVendas() {
		return vendas;
	}

	/**
	 * retorna os ingressos vendidos
	 * @return ingressos
	 */
	public ListaEncadeada recuperarIngressos() {
		return ingressos;
	}

	/**
	 * Cadastra um novo comprador
	 * @param novoComprador
	 * @return
	 * @throws CompradorNuloException 
	 * @throws CampoObrigatorioInexistenteException 
	 */
	public Comprador cadastrarComprador(Comprador novoComprador) throws CompradorNuloException, CampoObrigatorioInexistenteException {
		if(novoComprador == null)
			throw new CompradorNuloException();
		if(novoComprador.getEmail() == null || novoComprador.getTelefone() == null || novoComprador.getNome() == null)
			throw new CampoObrigatorioInexistenteException();
		else if(novoComprador.getEmail().trim().isEmpty() || novoComprador.getTelefone().trim().isEmpty() || novoComprador.getDocumento()==0 || novoComprador.getEndereco() == null || novoComprador.getNome().trim().isEmpty())
			throw new CampoObrigatorioInexistenteException();
		compradores.inserirFinal(novoComprador);
		return novoComprador;
	}

	/**
	 * Cadastra um novo comprador fã
	 * @param novoComprador
	 * @return novoComprador
	 * @throws CompradorNuloException
	 * @throws CampoObrigatorioInexistenteException
	 */
	public CompradorFan cadastrarComprador(CompradorFan novoComprador) throws CompradorNuloException, CampoObrigatorioInexistenteException {
		if(novoComprador == null)
			throw new CompradorNuloException();
		if(novoComprador.getRegistro() == 0)
			throw new CampoObrigatorioInexistenteException();
		if(novoComprador.getEmail() == null || novoComprador.getTelefone() == null || novoComprador.getNome() == null)
			throw new CampoObrigatorioInexistenteException();
		else if(novoComprador.getEmail().trim().isEmpty() || novoComprador.getTelefone().trim().isEmpty() || novoComprador.getDocumento()==0 || novoComprador.getEndereco() == null || novoComprador.getNome().trim().isEmpty())
			throw new CampoObrigatorioInexistenteException();
		compradores.inserirFinal( (CompradorFan) novoComprador);
		return novoComprador;
	}

	/**
	 * Cadastra uma nova sala em um cinema
	 * @param cinema
	 * @param novaSala
	 * @return
	 * @throws CinemaNuloException
	 * @throws SalaNulaException
	 * @throws CampoObrigatorioInexistenteException
	 * @throws LimiteSalasExcedidoException
	 * @throws CinemaNaoEncontradoException 
	 */
	public Sala cadastrarSala(Cinema cinema, Sala novaSala) throws CinemaNuloException, SalaNulaException, CampoObrigatorioInexistenteException, LimiteSalasExcedidoException, CinemaNaoEncontradoException {
		if(cinema == null)
			throw new CinemaNuloException();
		Cinema cinema2 = obterCinema(cinema.getId());
		if(cinema2 == null)
			throw new CinemaNaoEncontradoException();
		if(novaSala == null)
			throw new SalaNulaException();
		if( cinema2.getSalas().obterTamanho() + 1 > cinema.getQtdSalas())
			throw new LimiteSalasExcedidoException();
		if(novaSala.getNumeroSala() == 0 || novaSala.getQtdCadeiras() == 0)
			throw new CampoObrigatorioInexistenteException();
		cinema2.getSalas().inserirFinal(novaSala);
		return novaSala;
	}

	/**
	 * Cadastra uma nova sessão em uma sala de um cinema
	 * @param cinema
	 * @param sala
	 * @param novaSessao
	 * @return novaSessao
	 * @throws SessaoNulaException
	 * @throws CampoObrigatorioInexistenteException
	 * @throws IntervaloMinimoInsuficienteException
	 * @throws CinemaNaoEncontradoException
	 * @throws SalaNaoEncontradaException
	 * @throws CinemaNuloException
	 * @throws SalaNulaException
	 */
	public Sessao cadastrarSessao(Cinema cinema, Sala sala, Sessao novaSessao)throws SessaoNulaException, CampoObrigatorioInexistenteException, IntervaloMinimoInsuficienteException, CinemaNaoEncontradoException, SalaNaoEncontradaException, CinemaNuloException, SalaNulaException {
		if(cinema == null)
			throw new CinemaNuloException();
		if(sala == null)
			throw new SalaNulaException();
		if(novaSessao == null)
			throw new SessaoNulaException();
		MeuIterador iterador = (MeuIterador) cinemas.iterador();
		Cinema cinema2 = recuperarCinema(cinema.getId());
		if(cinema2 == null)
			throw new CinemaNaoEncontradoException();
		Sala sala2 = cinema2.obterSala(sala.getNumeroSala());
		if(sala2 == null)
			throw new SalaNaoEncontradaException();
		if(novaSessao.getHoraFim() == 0 || novaSessao.getHoraInicio() == 0)
			throw new CampoObrigatorioInexistenteException();
		ListaEncadeada sessoes = sala.getSessoes();
		iterador = (MeuIterador) sessoes.iterador();
		Sessao atual;
		int horaInicio;
		while(iterador.temProximo()) {
			atual = (Sessao) iterador.obterProximo();
			horaInicio = atual.getHoraInicio();
			if(novaSessao.getHoraInicio() != atual.getHoraInicio())
				if(horaInicio - novaSessao.getHoraInicio() < 3 && horaInicio - novaSessao.getHoraInicio() > -3) 
					throw new IntervaloMinimoInsuficienteException();
		}
		sala2.getSessoes().inserirFinal(novaSessao);
		return novaSessao;
	}

	/**
	 * Lista todos os compradores cadastrados
	 * @return iterador
	 */
	public Iterador listarCompradores() {
		compradores.mergeSort(compradores);
		return compradores.iterador();
	}

	/**
	 * Remove um comprador da lista de compradores
	 * @param documentoComprador
	 * @return comprador removido
	 * @throws RemocaoNaoPermitidaException 
	 */
	public Comprador removerComprador(int documentoComprador) throws CompradorNaoEncontradoException, RemocaoNaoPermitidaException{
		Comprador comprador = (Comprador) recuperarComprador(documentoComprador);
		if (comprador == null)
			throw new CompradorNaoEncontradoException();
		if( !(comprador.getIngressos().estaVazia() ))
			throw new RemocaoNaoPermitidaException();

		/*	Iterador iterador = ingressos.iterador();
		Ingresso atual;
		while(iterador.temProximo()) {
			atual = (Ingresso) iterador.obterProximo();
			if(atual.getComprador().getDocumento()==documentoComprador)
				throw new RemocaoNaoPermitidaException();
		}*/
		int index = compradores.getIndex(comprador);
		compradores.remover(index);
		return comprador;
	}


	/**
	 * Cadastra um novo cinema
	 * @param novoCinema
	 * @return novoCinema
	 * @throws CinemaNuloException
	 * @throws CampoObrigatorioInexistenteException
	 */
	public Cinema cadastrarCinema(Cinema novoCinema) throws CinemaNuloException, CampoObrigatorioInexistenteException{
		if (novoCinema == null)
			throw new CinemaNuloException();
		else if(novoCinema.getNome().equals("     "))
			throw new CampoObrigatorioInexistenteException();
		cinemas.inserirFinal(novoCinema);
		return novoCinema;
	}

	/**
	 * Encontra um cinema na lista de cinemas cadastrados
	 * @param idCinema
	 * @return
	 */
	public Cinema obterCinema(int idCinema) {
		Iterador iterador = cinemas.iterador();
		while(iterador.temProximo()) {
			Cinema atual = (Cinema) iterador.obterProximo();
			if(atual.getId()==idCinema)
				return atual;
		}
		return null;
	}

	/**
	 * Remove um cinema da lista de cinemas cadastrados
	 * @param idCinema
	 * @return cinema
	 * @throws CinemaNaoEncontradoException 
	 * @throws RemocaoNaoPermitidaException 
	 */
	public boolean removerCinema(int idCinema) throws CinemaNaoEncontradoException, RemocaoNaoPermitidaException {
		Cinema cinema = obterCinema(idCinema);
		if(cinema == null)
			throw new CinemaNaoEncontradoException();
		Iterador iterador = ingressos.iterador();
		Ingresso atual = null;
		while(iterador.temProximo()) {
			atual = (Ingresso) iterador.obterProximo();
			if(atual.getCinema().getId()==idCinema)
				throw new RemocaoNaoPermitidaException();
		}				
		int index = cinemas.getIndex(cinema);
		cinemas.remover(index);
		return true;		
	}	

	/**
	 * Lista os cinemas cadastrados
	 * @return iterador
	 */
	public Iterador listarCinemas() {
		return cinemas.iterador();
	}

	/**
	 * Encontra um comprador na lista de compradores cadastrados
	 * @param documentoComprador
	 * @return comprador
	 * @throws CompradorNaoEncontradoException 
	 */
	public Comprador recuperarComprador(int documentoComprador) throws CompradorNaoEncontradoException {
		MeuIterador iterador = (MeuIterador) compradores.iterador();
		Comprador atual = null;
		while(iterador.temProximo()) {
			atual = (Comprador) iterador.obterProximo();
			if(atual.getDocumento()== documentoComprador) {
				if(atual instanceof CompradorFan)
					return (CompradorFan) atual;
				else
					return atual;
			}

		}
		throw new CompradorNaoEncontradoException();
	}

	public static void zerarSingleton() {
		instance = new AdministradorController();
	}

	/**
	 * Encontra um cinema na lista de cinemas cadastrados
	 * @param id
	 * @return cinema
	 * @throws CinemaNaoEncontradoException
	 */
	public Cinema recuperarCinema(int id) throws CinemaNaoEncontradoException  {
		MeuIterador iterador = (MeuIterador) cinemas.iterador();
		Cinema cinema = null;
		while(iterador.temProximo()) {
			cinema = (Cinema) iterador.obterProximo();
			if(cinema.getId() == id)
				return cinema;
		}
		throw new CinemaNaoEncontradoException();
	}

	/**
	 * Retorna a fila de fãs Habilitados
	 * @return fansHabilitados
	 */
	public Fila recuperarFansHabilitados() {
		return fansHabilitados;
	}

	/**
	 * Distribui as camisas para os compradores fans habilitados
	 * @param quantidadeDeCamisas
	 * @throws FaHabilitadoInexistenteException
	 */
	public void distribuirCamisas(int quantidadeDeCamisas) throws FaHabilitadoInexistenteException {
		CompradorFan atual;
		while(quantidadeDeCamisas > 0) {
			atual = (CompradorFan) fansHabilitados.removerInicio();
			if(atual == null)
				throw new FaHabilitadoInexistenteException();
			quantidadeDeCamisas--;
		}
	}

	/**
	 * Altera as informações de um cinema
	 * @param cine
	 * @throws CinemaNaoEncontradoException
	 * @throws CinemaNuloException
	 * @throws CampoObrigatorioInexistenteException
	 */
	public void alterarCinema(Cinema cine) throws CinemaNaoEncontradoException, CinemaNuloException, CampoObrigatorioInexistenteException {
		if (cine==null)
			throw new CinemaNuloException();
		Cinema cinemaAlterar = recuperarCinema(cine.getId());
		if (cinemaAlterar == null)
			throw new CinemaNaoEncontradoException();
		if(cine.getEndereco() == null || cine.getNome().trim().isEmpty() || cine.getQtdSalas() == 0)
			throw new CampoObrigatorioInexistenteException();
		cinemaAlterar.setNome(cine.getNome());
		cinemaAlterar.setEndereco(cine.getEndereco());
		cinemaAlterar.setQtdSalas(cine.getQtdSalas());
	}

	/**
	 * Altera as informações de um comprador
	 * @param compradorAlterar
	 * @throws CompradorNuloException
	 * @throws CampoObrigatorioInexistenteException
	 * @throws CompradorNaoEncontradoException
	 */
	public void alterarComprador(Comprador compradorAlterar) throws CompradorNuloException, CampoObrigatorioInexistenteException, CompradorNaoEncontradoException {
		if(compradorAlterar==null)
			throw new CompradorNuloException();
		if(compradorAlterar.getEmail().trim().isEmpty() || compradorAlterar.getDocumento()==0 || compradorAlterar.getEndereco() == null|| compradorAlterar.getNome().trim().isEmpty()|| compradorAlterar.getTelefone().trim().isEmpty())
			throw new CampoObrigatorioInexistenteException();
		Comprador comprador = recuperarComprador(compradorAlterar.getDocumento());
		if(comprador == null)
			throw new CompradorNaoEncontradoException();
		comprador.setDocumento(compradorAlterar.getDocumento());
		comprador.setEmail(compradorAlterar.getEmail());
		comprador.setNome(compradorAlterar.getNome());
		comprador.setEndereco(compradorAlterar.getEndereco());
		comprador.setTelefone(compradorAlterar.getTelefone());
	}


	/**
	 * Alterar as informações de um comprador Fan
	 * @param compradorAlterar
	 * @throws CompradorNuloException
	 * @throws CampoObrigatorioInexistenteException
	 * @throws CompradorNaoEncontradoException
	 */
	public void alterarComprador(CompradorFan compradorAlterar) throws CompradorNuloException, CampoObrigatorioInexistenteException, CompradorNaoEncontradoException {
		if(compradorAlterar==null)
			throw new CompradorNuloException();
		if(compradorAlterar.getEmail().trim().isEmpty() || compradorAlterar.getDocumento()==0 || compradorAlterar.getEndereco() == null || compradorAlterar.getNome().trim().isEmpty() || compradorAlterar.getTelefone().trim().isEmpty() || compradorAlterar.getRegistro() == 0)
			throw new CampoObrigatorioInexistenteException();
		CompradorFan comprador = (CompradorFan) recuperarComprador(compradorAlterar.getDocumento());
		if(comprador == null)
			throw new CompradorNaoEncontradoException();
		comprador.setDocumento(compradorAlterar.getDocumento());
		comprador.setEmail(compradorAlterar.getEmail());
		comprador.setNome(compradorAlterar.getNome());
		comprador.setEndereco(compradorAlterar.getEndereco());
		comprador.setTelefone(compradorAlterar.getTelefone());
		comprador.setRegistro(compradorAlterar.getRegistro());
	}

	/**
	 * Lista as salas de um cinema
	 * @param cine
	 * @return iterador
	 */
	public Iterador listarSalas(Cinema cine) {
		return cine.getSalas().iterador();
	}

	/**
	 * Altera as informações de uma sala de um cinema
	 * @param cinema
	 * @param sala
	 * @throws CinemaNuloException
	 * @throws CinemaNaoEncontradoException
	 * @throws SalaNulaException
	 * @throws CampoObrigatorioInexistenteException
	 * @throws SalaNaoEncontradaException
	 */
	public void alterarSala(Cinema cinema, Sala sala) throws CinemaNuloException, CinemaNaoEncontradoException, SalaNulaException, CampoObrigatorioInexistenteException, SalaNaoEncontradaException {
		if(cinema == null)
			throw new CinemaNuloException();
		if(obterCinema(cinema.getId()) == null)
			throw new CinemaNaoEncontradoException();
		if(sala == null)
			throw new SalaNulaException();
		if(sala.getNumeroSala() == 0 || sala.getQtdCadeiras() == 0)
			throw new CampoObrigatorioInexistenteException();
		Sala alterada = cinema.obterSala(sala.getNumeroSala());
		if( alterada == null)
			throw new SalaNaoEncontradaException();
		alterada.setQtdCadeiras(sala.getQtdCadeiras());	
	}

	/**
	 * Altera as informações de uma sessão em uma sala de um cinema
	 * @param cinema
	 * @param sala
	 * @param inicioAntiga
	 * @param fimAntiga
	 * @param inicioNova
	 * @param fimNova
	 * @throws CinemaNuloException
	 * @throws SalaNulaException
	 * @throws SalaNaoEncontradaException
	 * @throws SessaoNulaException
	 * @throws CinemaNaoEncontradoException
	 * @throws IntervaloMinimoInsuficienteException
	 * @throws CampoObrigatorioInexistenteException
	 * @throws SessaoNaoEncontradaException
	 */
	public void alterarSessao(Cinema cinema, Sala sala, int inicioAntiga, int fimAntiga, int inicioNova, int fimNova) throws CinemaNuloException, SalaNulaException, SalaNaoEncontradaException, SessaoNulaException, CinemaNaoEncontradoException, IntervaloMinimoInsuficienteException, CampoObrigatorioInexistenteException, SessaoNaoEncontradaException {
		if(inicioNova == 0 || fimNova == 0)
			throw new SessaoNulaException();
		if(cinema == null)
			throw new CinemaNuloException();
		if(sala == null)
			throw new SalaNulaException();
		MeuIterador iterador = (MeuIterador) cinemas.iterador();
		Cinema cinema2 = recuperarCinema(cinema.getId());
		if(cinema2 == null)
			throw new CinemaNaoEncontradoException();
		Sala sala2 = cinema.obterSala(sala.getNumeroSala());
		if(sala2 == null)
			throw new SalaNaoEncontradaException();
		ListaEncadeada sessoes = sala.getSessoes();
		iterador = (MeuIterador) sessoes.iterador();
		Sessao alterada = null;
		while(iterador.temProximo()) {
			alterada = (Sessao) iterador.obterProximo();
			if(alterada.getHoraInicio() == inicioAntiga)
				break;
		}
		if(alterada == null)
			throw new SessaoNaoEncontradaException();
		iterador.reset();
		Sessao atual;
		int horaInicio;
		while(iterador.temProximo()) {
			atual = (Sessao) iterador.obterProximo();
			horaInicio = atual.getHoraInicio();
			if(inicioAntiga != atual.getHoraInicio()) //Evita que a sessão se compare com ela mesma
				if(horaInicio - inicioNova < 3 && horaInicio - inicioNova > -3) // Verifica se existe um intervalo de pelo menos 3 horas entre as sessões					
					throw new IntervaloMinimoInsuficienteException();
		}
		alterada.setHoraInicio(inicioNova);
		alterada.setHoraFim(fimNova);
	}

	/**
	 * Lista as sessões de uma sala de um cinema
	 * @param cine
	 * @param salaTeste
	 * @return iterador
	 */
	public Iterador listarSessoes(Cinema cine, Sala salaTeste) {
		return (cine.obterSala(salaTeste.getNumeroSala())).getSessoes().iterador();
	}

}