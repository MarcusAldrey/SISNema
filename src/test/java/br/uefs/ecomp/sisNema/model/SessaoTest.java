package br.uefs.ecomp.sisNema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import br.uefs.ecomp.sisNema.controller.AdministradorController;
import br.uefs.ecomp.sisNema.exceptions.CampoObrigatorioInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNuloException;
import br.uefs.ecomp.sisNema.exceptions.IntervaloMinimoInsuficienteException;
import br.uefs.ecomp.sisNema.exceptions.LimiteSalasExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNulaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNulaException;
import br.uefs.ecomp.sisNema.util.CriarObjetos;
import br.uefs.ecomp.sisNema.util.Iterador;

public class SessaoTest {

	private AdministradorController controllerAdministrador;

	@Before
	public void setUp() throws Exception {
		AdministradorController.zerarSingleton();
		controllerAdministrador = AdministradorController.getInstance();
	}

	@Test
	public void testListarSessoesSucesso() {
		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(15);
		sessao.setHoraFim(17);

		try {
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
		} catch (SessaoNulaException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		/* Teste alterado para receber um iterador e não uma lista encadeada */

		//assertEquals(1,controllerAdministrador.listarSessoes(cine,salaTeste).obterTamanho());

		int x;
		Iterador iterador = controllerAdministrador.listarSessoes(cine,salaTeste);

		for(x = 0; iterador.temProximo(); x++)
			iterador.obterProximo();

		assertEquals(1,x);


	}

	@Test
	public void testCadastrarSessaoSucesso() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		Sala sala = new Sala();
		sala.setNumeroSala(2);
		sala.setQtdCadeiras(200);

		try {
			controllerAdministrador.cadastrarSala(cine,sala);
		} catch (SalaNulaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
			if(salaTeste.getNumeroSala()==1){
				break;
			}
		}

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(15);
		sessao.setHoraFim(17);

		try {
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
		} catch (SessaoNulaException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		sessao = new Sessao();
		sessao.setHoraInicio(16);
		sessao.setHoraFim(18);

		i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
			if(salaTeste.getNumeroSala()==2){
				break;
			}
		}

		try {
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
		} catch (SessaoNulaException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
			if(salaTeste.getNumeroSala()==1){
				break;
			}
		}

		Iterador i2 = salaTeste.getSessoes().iterador();
		sessao = null;
		while(i2.temProximo()){
			sessao = (Sessao)i2.obterProximo();
		}
		assertEquals(15,sessao.getHoraInicio());
		assertEquals(17,sessao.getHoraFim());

		i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
			if(salaTeste.getNumeroSala()==2){
				break;
			}
		}

		i2 = salaTeste.getSessoes().iterador();
		sessao = null;
		while(i2.temProximo()){
			sessao = (Sessao)i2.obterProximo();
		}
		assertEquals(16,sessao.getHoraInicio());
		assertEquals(18,sessao.getHoraFim());
	}

	@Test
	public void testCadastrarSessaoNula() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Sessao sessao = null;

		try{
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
			fail();
		}catch(SessaoNulaException cause){
			assertTrue(true);
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarSessaoSemHoraInicio() {

		int id = 0;

		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Sessao sessao = new Sessao();
		sessao.setHoraFim(17);

		try{
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarSessaoSemHoraFim() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException | CampoObrigatorioInexistenteException e) {
			fail();
		}
		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(15);

		try{
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarSessaoComIntervaloMinimoInsuficiente() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(15);
		sessao.setHoraFim(17);

		try {
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		Sessao sessao2 = new Sessao();
		sessao2.setHoraInicio(17);
		sessao2.setHoraFim(19);

		try{
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao2);
			fail();
		}catch(IntervaloMinimoInsuficienteException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarSessaoEmCinemaNaoEncontrado() {

		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.cadastrarSessao(cine,sala,sessao);
			fail();
		}catch(CinemaNaoEncontradoException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarSessaoEmCinemaNulo() {

		Cinema cine = null;

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.cadastrarSessao(cine,sala,sessao);
			fail();
		}catch(CinemaNuloException cause){
			assertTrue(true);
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarSessaoEmSalaNaoEncontrada() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		}
		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.cadastrarSessao(cine,sala,sessao);
			fail();
		}catch(SalaNaoEncontradaException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarSessaoEmSalaNula() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		}

		Sala sala = null;

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.cadastrarSessao(cine,sala,sessao);
			fail();
		}catch(SalaNulaException cause){
			assertTrue(true);
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		}

	}

	@Test
	public void testAlterarSessaoSucesso() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSessao(cine, controllerAdministrador);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		int horaInicioAntiga = 15, horaFimAntiga = 17, horaInicioNova = 16, horaFimNova = 18;

		try {
			controllerAdministrador.alterarSessao(cine,salaTeste,horaInicioAntiga,horaFimAntiga,horaInicioNova,horaFimNova);
		} catch (SessaoNulaException e1) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Iterador i2 = salaTeste.getSessoes().iterador();
		Sessao sessao = null;
		while(i2.temProximo()){
			sessao = (Sessao)i2.obterProximo();
		}
		assertEquals(16,sessao.getHoraInicio());
		assertEquals(18,sessao.getHoraFim());
	}

	@Test
	public void testAlterarSessaoComHorarioZerado() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		try {
			CriarObjetos.criarSessao(cine, controllerAdministrador);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		int horaInicioAntiga = 15, horaFimAntiga = 17, horaInicioNova = 0, horaFimNova = 0;

		try{
			controllerAdministrador.alterarSessao(cine,salaTeste,horaInicioAntiga,horaFimAntiga,horaInicioNova,horaFimNova);
			fail();
		}catch(SessaoNulaException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
	}


	@Test
	public void testAlterarSessaoComIntervaloMinimoInsuficiente() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		}

		try {
			CriarObjetos.criarSala(cine, controllerAdministrador);
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		} catch (SalaNulaException e1) {
			fail();
		} catch (LimiteSalasExcedidoException e1) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		}

		try {
			CriarObjetos.criarSessao(cine, controllerAdministrador);
		} catch (SessaoNulaException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e1) {
			fail();
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		} catch (SalaNaoEncontradaException e1) {
			fail();
		} catch (CinemaNuloException e1) {
			fail();
		} catch (SalaNulaException e1) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		}

		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(18);
		sessao.setHoraFim(20);

		try {
			controllerAdministrador.cadastrarSessao(cine,salaTeste,sessao);
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SessaoNulaException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}

		try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		salaTeste = null;
		i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}

		int horaInicioAntiga = 18, horaFimAntiga = 20, horaInicioNova = 17, horaFimNova = 19;

		try{
			controllerAdministrador.alterarSessao(cine,salaTeste,horaInicioAntiga,horaFimAntiga,horaInicioNova,horaFimNova);
			fail();
		}catch(IntervaloMinimoInsuficienteException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		}
	}

	@Test
	public void testAlterarSessaoEmCinemaNaoEncontrado() {

		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.alterarSessao(cine,sala,17,19,18,20);
			fail();
		}catch(CinemaNaoEncontradoException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		}

	}

	@Test
	public void testAlterarSessaoEmCinemaNulo() {

		Cinema cine = null;

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.alterarSessao(cine,sala,17,19,18,20);
			fail();
		}catch(CinemaNuloException cause){
			assertTrue(true);
		} catch (SessaoNulaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		}

	}

	@Test
	public void testAlterarSessaoEmSalaNaoEncontrada() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.alterarSessao(cine,sala,17,19,18,20);
			fail();
		}catch(SalaNaoEncontradaException cause){
			assertTrue(true);
		} catch (CinemaNuloException e) {
			fail();
		} catch (SessaoNulaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		}

	}

	@Test
	public void testAlterarSessaoEmSalaNula() {

		int id = 0;
		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Cinema cine = null;
		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Sala sala = null;

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(17);
		sessao.setHoraFim(19);

		try{
			controllerAdministrador.alterarSessao(cine,sala,17,19,18,20);
			fail();
		}catch(SalaNulaException cause){
			assertTrue(true);
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SessaoNulaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (IntervaloMinimoInsuficienteException e) {
			fail();
			// Adicionados Try catch para CampoObrigatorioInexistenteException e SessaoNaoEncontradaException
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		}

	}
}