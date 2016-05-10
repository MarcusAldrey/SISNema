package br.uefs.ecomp.sisNema.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import br.uefs.ecomp.sisNema.controller.AdministradorController;
import br.uefs.ecomp.sisNema.controller.CompradorController;
import br.uefs.ecomp.sisNema.exceptions.CampoObrigatorioInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNuloException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNuloException;
import br.uefs.ecomp.sisNema.exceptions.IntervaloMinimoInsuficienteException;
import br.uefs.ecomp.sisNema.exceptions.LimiteIngressosExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.LimiteSalasExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.RemocaoNaoPermitidaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNulaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNulaException;
import br.uefs.ecomp.sisNema.util.CriarObjetos;
import br.uefs.ecomp.sisNema.util.Iterador;


public class CinemaTest {

	private AdministradorController controllerAdministrador;

	@Before
	public void setUp() throws Exception {
		AdministradorController.zerarSingleton();
		controllerAdministrador = AdministradorController.getInstance();
	}

	@Test
	public void testListarCinemasSucesso() {

		try{
			CriarObjetos.criarCinema(controllerAdministrador);
		}catch(CampoObrigatorioInexistenteException cause){
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping 2");
		cine.setQtdSalas(9);
		cine.setEndereco(CriarObjetos.criarEndereco());

		try{
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch(CampoObrigatorioInexistenteException cause){
			fail();
		} catch(CinemaNuloException cause){
			fail();
		}

		/*O teste foi modificado para receber um iterador */
		
		//assertEquals(2, controllerAdministrador.listarCinemas().obterTamanho());
		
		int i;
		Iterador iterador = controllerAdministrador.listarCinemas();
		for(i = 0; iterador.temProximo(); i++)
			iterador.obterProximo();
		assertEquals(2,i);

	}

	@Test
	public void testCadastrarCinemaSucesso() {

		int id = 0;

		try{
			id = CriarObjetos.criarCinema(controllerAdministrador);
		}catch(CampoObrigatorioInexistenteException cause){
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		assertNotEquals(0, id);

		Cinema cine = null;

		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		}

		assertNotNull(cine);

		assertEquals("ORIENT Cineplace Boulevard Shopping", cine.getNome());

		Cinema cine2 = new Cinema();
		cine2.setNome("ORIENT Cineplace Boulevard Shopping 2");
		cine2.setQtdSalas(9);
		cine2.setEndereco(CriarObjetos.criarEndereco());

		try{
			cine2 = controllerAdministrador.cadastrarCinema(cine2);
		}catch(CampoObrigatorioInexistenteException cause){
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		assertNotNull(cine2);

		int id2 = cine2.getId();

		try {
			cine2 = controllerAdministrador.recuperarCinema(id2);
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		}

		assertNotNull(cine2);
		assertEquals("ORIENT Cineplace Boulevard Shopping 2", cine2.getNome());

	}

	@Test
	public void testCadastrarCinemaNulo()  {						
		Cinema cine = null;

		try{
			cine = controllerAdministrador.cadastrarCinema(cine);
			fail();
		}catch(CinemaNuloException cause){
			assertTrue(true);
		}catch(CampoObrigatorioInexistenteException cause){
			fail();
		}								
	}

	@Test
	public void testCadastrarCinemaSemNome() {						
		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("     ");
		cine.setQtdSalas(9);
		cine.setEndereco(end);

		try{
			cine = controllerAdministrador.cadastrarCinema(cine);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		}catch(CinemaNuloException cause){
			fail();
		}
	}

	public void testCadastrarCinemaSemQuantidadeSalas() {						
		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");		
		cine.setEndereco(end);

		try{
			cine = controllerAdministrador.cadastrarCinema(cine);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		}catch(CinemaNuloException cause){
			fail();
		}
	}

	public void testCadastrarCinemaSemEndereco() {						

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");		
		cine.setQtdSalas(9);

		try{
			cine = controllerAdministrador.cadastrarCinema(cine);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		}catch(CinemaNuloException cause){
			fail();
		}
	}

	@Test
	public void testRecuperarCinemaSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		try{
			CriarObjetos.criarCinema(controllerAdministrador);
		}catch(CampoObrigatorioInexistenteException cause){
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine2 = new Cinema();
		cine2.setNome("ORIENT Cineplace Boulevard Shopping 2");
		cine2.setQtdSalas(10);
		cine2.setEndereco(end);

		try {
			cine2 = controllerAdministrador.cadastrarCinema(cine2);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine3 = new Cinema();
		cine3.setNome("ORIENT Cineplace Boulevard Shopping 3");
		cine3.setQtdSalas(7);
		cine3.setEndereco(end);

		try {
			cine3 = controllerAdministrador.cadastrarCinema(cine3);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine4 = new Cinema();
		cine4.setNome("ORIENT Cineplace Boulevard Shopping 4");
		cine4.setQtdSalas(4);
		cine4.setEndereco(end);

		try {
			cine4 = controllerAdministrador.cadastrarCinema(cine4);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cineRecuperado = null;

		try {
			cineRecuperado = controllerAdministrador.recuperarCinema(cine3.getId());
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		}

		assertNotNull(cineRecuperado);
		assertEquals(cine3.getId(), cineRecuperado.getId());
		assertEquals(cine3.getNome(), cineRecuperado.getNome());
		assertEquals(cine3.getQtdSalas(), cineRecuperado.getQtdSalas());
	}

	@Test
	public void testRecuperarCinemaInexistente() {

		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);

		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine2 = new Cinema();
		cine2.setNome("ORIENT Cineplace Boulevard Shopping 2");
		cine2.setQtdSalas(10);
		cine2.setEndereco(end);

		try {
			cine2 = controllerAdministrador.cadastrarCinema(cine2);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine3 = new Cinema();
		cine3.setNome("ORIENT Cineplace Boulevard Shopping 3");
		cine3.setQtdSalas(7);
		cine3.setEndereco(end);

		try {
			cine3 = controllerAdministrador.cadastrarCinema(cine3);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine4 = new Cinema();
		cine4.setNome("ORIENT Cineplace Boulevard Shopping 4");
		cine4.setQtdSalas(4);
		cine4.setEndereco(end);

		try {
			cine4 = controllerAdministrador.cadastrarCinema(cine4);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		try{
			controllerAdministrador.recuperarCinema(1010669244);
			fail();
		}catch(CinemaNaoEncontradoException cause){
			assertTrue(true);
		}

	}

	@Test
	public void testAlterarCinemaSucesso() {

		int id = 0;

		try {
			id = CriarObjetos.criarCinema(controllerAdministrador);
		} catch (CinemaNuloException cause) {
			fail();
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}

		Cinema cine = null; 

		try {
			cine = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		}

		assertEquals("ORIENT Cineplace Boulevard Shopping", cine.getNome());
		assertEquals(9, cine.getQtdSalas());

		Cinema cineAlterar = new Cinema();

		cineAlterar.setId(cine.getId());
		cineAlterar.setNome("ORIENT Cineplace Boulevard Shopping Alterado");
		cineAlterar.setQtdSalas(10);
		//Adicionado endereço ao cinema, para que não lance CampoObrigatorioInexistenteException
		cineAlterar.setEndereco(CriarObjetos.criarEndereco());

		try {
			controllerAdministrador.alterarCinema(cineAlterar);
		} catch (CinemaNuloException cause) {
			fail();

		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		//Try catch adicionado para capturar um CinemaNaoEncontradoException
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}

		Cinema cineAlterado = null;

		try {
			cineAlterado = controllerAdministrador.recuperarCinema(id);
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		}

		assertEquals("ORIENT Cineplace Boulevard Shopping Alterado", cineAlterado.getNome());
		assertEquals(10, cineAlterado.getQtdSalas());

	}

	@Test
	public void testAlterarCinemaNulo() {	

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

		assertEquals("ORIENT Cineplace Boulevard Shopping", cine.getNome());
		assertEquals(9, cine.getQtdSalas());

		try{
			controllerAdministrador.alterarCinema(null);
			fail();
		}catch(CinemaNuloException cause){
			assertTrue(true);
			//Try catch adicionado para capturar um CinemaNaoEncontradoException
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}								
	}

	@Test
	public void testAlterarCinemaSemNome() {		

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

		assertEquals("ORIENT Cineplace Boulevard Shopping", cine.getNome());
		assertEquals(9, cine.getQtdSalas());

		Cinema cineAlterar = new Cinema();

		cineAlterar.setId(cine.getId());
		cineAlterar.setNome("        ");

		try {
			controllerAdministrador.alterarCinema(cineAlterar);
			fail();
		} catch (CinemaNuloException e) {
			fail();
		//Try catch adicionado para capturar um CinemaNaoEncontradoException
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		}
	}

	public void testAlterarCinemaSemQuantidadeSalas() {						
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

		assertEquals("ORIENT Cineplace Boulevard Shopping", cine.getNome());
		assertEquals(9, cine.getQtdSalas());

		Cinema cineAlterar = new Cinema();

		cineAlterar.setId(cine.getId());
		cineAlterar.setQtdSalas(0);

		try{
			controllerAdministrador.alterarCinema(cineAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CinemaNuloException e) {
			fail();
		//Try catch adicionado para capturar um CinemaNaoEncontradoException
		} catch (CinemaNaoEncontradoException e) {
			fail();	
		}
	}

	public void testAlterarCinemaSemEndereco() {						

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

		assertEquals("ORIENT Cineplace Boulevard Shopping", cine.getNome());
		assertEquals(9, cine.getQtdSalas());

		Cinema cineAlterar = new Cinema();

		cineAlterar.setId(cine.getId());
		cineAlterar.setEndereco(null);

		try{
			controllerAdministrador.alterarCinema(cineAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		//Try catch adicionado para capturar um CinemaNaoEncontradoException
		} catch (CinemaNaoEncontradoException e) {
			fail();	
		} catch (CinemaNuloException e) {
			fail();
		}
	}

	@Test
	public void testRemoverCinemaSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		try{
			CriarObjetos.criarCinema(controllerAdministrador);
		}catch(CampoObrigatorioInexistenteException cause){
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine2 = new Cinema();
		cine2.setNome("ORIENT Cineplace Boulevard Shopping 2");
		cine2.setQtdSalas(10);
		cine2.setEndereco(end);

		try {
			cine2 = controllerAdministrador.cadastrarCinema(cine2);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine3 = new Cinema();
		cine3.setNome("ORIENT Cineplace Boulevard Shopping 3");
		cine3.setQtdSalas(7);
		cine3.setEndereco(end);

		try {
			cine3 = controllerAdministrador.cadastrarCinema(cine3);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine4 = new Cinema();
		cine4.setNome("ORIENT Cineplace Boulevard Shopping 4");
		cine4.setQtdSalas(4);
		cine4.setEndereco(end);

		try {
			cine4 = controllerAdministrador.cadastrarCinema(cine4);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cineRecuperado = null;

		try {
			cineRecuperado = controllerAdministrador.recuperarCinema(cine3.getId());
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		}

		assertNotNull(cineRecuperado);
		assertEquals(cine3.getId(), cineRecuperado.getId());
		assertEquals(cine3.getNome(), cineRecuperado.getNome());
		assertEquals(cine3.getQtdSalas(), cineRecuperado.getQtdSalas());


		try {
			controllerAdministrador.removerCinema(cine3.getId());
		} catch (CinemaNaoEncontradoException cause) {
			fail();
		} catch (RemocaoNaoPermitidaException e) {
			fail();
		}

		try {
			cineRecuperado = controllerAdministrador.recuperarCinema(cine3.getId());
			fail();
		} catch (CinemaNaoEncontradoException cause) {
			assertTrue(true);
		}
	}

	@Test
	public void testRemoverCinemaInexistente() {

		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);

		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine2 = new Cinema();
		cine2.setNome("ORIENT Cineplace Boulevard Shopping 2");
		cine2.setQtdSalas(10);
		cine2.setEndereco(end);

		try {
			cine2 = controllerAdministrador.cadastrarCinema(cine2);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine3 = new Cinema();
		cine3.setNome("ORIENT Cineplace Boulevard Shopping 3");
		cine3.setQtdSalas(7);
		cine3.setEndereco(end);

		try {
			cine3 = controllerAdministrador.cadastrarCinema(cine3);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		Cinema cine4 = new Cinema();
		cine4.setNome("ORIENT Cineplace Boulevard Shopping 4");
		cine4.setQtdSalas(4);
		cine4.setEndereco(end);

		try {
			cine4 = controllerAdministrador.cadastrarCinema(cine4);
		} catch (CampoObrigatorioInexistenteException cause) {
			fail();
		}catch(CinemaNuloException cause){
			fail();
		}

		try {
			controllerAdministrador.removerCinema(1010669244);
			fail();
		} catch (CinemaNaoEncontradoException cause) {
			assertTrue(true);
		} catch (RemocaoNaoPermitidaException e) {
			fail();
		}
	}

	@Test
	public void testRemoverCinemaComIngresso() {

		Endereco end = CriarObjetos.criarEndereco();

		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);

		Sessao sessao = new Sessao();
		sessao.setHoraInicio(15);
		sessao.setHoraFim(17);

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

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
			controllerAdministrador.cadastrarSessao(cine,sala,sessao);
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
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		int qtdIngressos = 3;

		CompradorController controllerComprador = CompradorController.getInstance();

		try {
			controllerComprador.comprarIngresso(comprador.getDocumento(),cine.getId(),1,15,qtdIngressos,60.00);
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (SessaoNaoEncontradaException e) {
			fail();
		} catch (LimiteIngressosExcedidoException e) {
			fail();
		}

		try {
			controllerAdministrador.removerCinema(cine.getId());
			fail();
		} catch (RemocaoNaoPermitidaException cause) {
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} 
	}
}