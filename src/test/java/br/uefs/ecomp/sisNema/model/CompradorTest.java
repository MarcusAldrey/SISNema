package br.uefs.ecomp.sisNema.model;

import static org.junit.Assert.assertEquals;
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

public class CompradorTest {

	private AdministradorController controllerAdministrador;

	@Before
	public void setUp() throws Exception {
		AdministradorController.zerarSingleton();
		controllerAdministrador = AdministradorController.getInstance();
	}

	@Test
	public void testListarCompradoresSucesso() {
		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Sicrano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Beltrano de Tal");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-6312");
		comprador2.setEmail("sicrano@tal.com.br");
		comprador2.setDocumento(4321);

		Comprador comprador3 = new Comprador();
		comprador3.setNome("Fulano de Tal");
		comprador3.setEndereco(end);
		comprador3.setTelefone("(75)3489-6313");
		comprador3.setEmail("beltrano@tal.com.br");
		comprador3.setDocumento(2341);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador3);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		/* Teste alterado para receber um iterador e não uma lista encadeada */

		//assertEquals(3,controllerAdministrador.listarCompradores().obterTamanho());

		int x;
		Iterador iterador = controllerAdministrador.listarCompradores();
		
		for(x = 0; iterador.temProximo(); x++)
			iterador.obterProximo();
		
		assertEquals(3,x);

		Iterador i = controllerAdministrador.listarCompradores();

		Comprador compradorRecuperado = (Comprador)i.obterProximo();
		assertEquals("Beltrano de Tal",compradorRecuperado.getNome());

		compradorRecuperado = (Comprador)i.obterProximo();
		assertEquals("Fulano de Tal",compradorRecuperado.getNome());

		compradorRecuperado = (Comprador)i.obterProximo();
		assertEquals("Sicrano de Tal",compradorRecuperado.getNome());
	}

	@Test
	public void testCadastrarCompradorSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Sicrano de Tal");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-6312");
		comprador2.setEmail("sicrano@tal.com.br");
		comprador2.setDocumento(4321);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste=null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		assertNotNull(compradorTeste);
		assertEquals(comprador.getDocumento(), compradorTeste.getDocumento());

		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador2.getDocumento());
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		assertNotNull(compradorTeste);
		assertEquals(comprador2.getDocumento(), compradorTeste.getDocumento());

	}

	@Test
	public void testCadastrarCompradorFanSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Sicrano de Tal");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-6312");
		compradorFan2.setEmail("sicrano@tal.com.br");
		compradorFan2.setDocumento(4321);
		compradorFan2.setRegistro(987);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(compradorFan2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		assertNotNull(compradorFanTeste);
		assertEquals(compradorFan.getDocumento(), compradorFanTeste.getDocumento());

		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan2.getDocumento());
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		assertNotNull(compradorFanTeste);
		assertEquals(compradorFan2.getDocumento(), compradorFanTeste.getDocumento());
		
		/* Adicionado '.obtertamanho' ao final de recuperarFansHabilitados */
		
		//assertEquals(0,controllerAdministrador.recuperarFansHabilitados());
		assertEquals(0,controllerAdministrador.recuperarFansHabilitados().obterTamanho());
	}

	@Test
	public void testCadastrarCompradorNulo() {

		Comprador comprador = null;

		try{
			controllerAdministrador.cadastrarComprador(comprador);
			fail();
		}catch(CompradorNuloException cause){
			assertTrue(true);
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarCompradorFanNulo() {

		CompradorFan compradorFan = null;

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CompradorNuloException cause){
			assertTrue(true);
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarCompradorSemNome() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("      ");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-1263");
		comprador2.setEmail("fulano@tal.com.br");
		comprador2.setDocumento(1234);

		try{
			controllerAdministrador.cadastrarComprador(comprador);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

		try{
			controllerAdministrador.cadastrarComprador(comprador2);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorFanSemNome() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("      ");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-1263");
		compradorFan2.setEmail("fulano@tal.com.br");
		compradorFan2.setDocumento(1234);
		compradorFan2.setRegistro(987);

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

		try{
			controllerAdministrador.cadastrarComprador(compradorFan2);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorSemEndereco() {

		Comprador comprador = new Comprador();
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);
		comprador.setNome("Comprador Fulano");

		try{
			controllerAdministrador.cadastrarComprador(comprador);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorFanSemEndereco() {

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Comprador Fan Fulano");
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarCompradorSemTelefone() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Comprador Fulano");
		comprador.setEndereco(end);
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Comprador Fulano 2");
		comprador2.setEndereco(end);
		comprador2.setTelefone("      ");
		comprador2.setEmail("fulano@tal.com.br");
		comprador2.setDocumento(1234);

		try{
			controllerAdministrador.cadastrarComprador(comprador);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

		try{
			controllerAdministrador.cadastrarComprador(comprador2);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorFanSemTelefone() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setEndereco(end);
		compradorFan.setNome("Comprador Fan");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Comprador Fan 2");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("      ");
		compradorFan2.setEmail("fulano@tal.com.br");
		compradorFan2.setDocumento(1234);
		compradorFan2.setRegistro(987);

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

		try{
			controllerAdministrador.cadastrarComprador(compradorFan2);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorSemEmail() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Comprador Fulano");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Comprador Fulano 2");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-1263");
		comprador2.setEmail("         ");
		comprador2.setDocumento(1234);

		try{
			controllerAdministrador.cadastrarComprador(comprador);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

		try{
			controllerAdministrador.cadastrarComprador(comprador2);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorFanSemEmail() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setEndereco(end);
		compradorFan.setNome("Comprador Fan");
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Comprador Fan 2");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-1263");
		compradorFan2.setEmail("       ");
		compradorFan2.setDocumento(1234);
		compradorFan2.setRegistro(987);

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

		try{
			controllerAdministrador.cadastrarComprador(compradorFan2);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testCadastrarCompradorSemDocumento() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Comprador Fulano");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");

		try{
			controllerAdministrador.cadastrarComprador(comprador);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarCompradorFanSemDocumento() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setEndereco(end);
		compradorFan.setNome("Comprador Fan");
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setRegistro(2098);

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}
	}

	@Test
	public void testCadastrarCompradorFanSemRegisroFan() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setEndereco(end);
		compradorFan.setNome("Comprador Fan");
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);

		try{
			controllerAdministrador.cadastrarComprador(compradorFan);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException | CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		assertNotNull(compradorTeste);
		assertEquals("Fulano de Tal", compradorTeste.getNome());

		Comprador compradorAlterar = new Comprador();
		compradorAlterar.setDocumento(compradorTeste.getDocumento());
		compradorAlterar.setEmail(compradorTeste.getEmail());
		compradorAlterar.setEndereco(compradorTeste.getEndereco());
		compradorAlterar.setTelefone(compradorTeste.getTelefone());
		compradorAlterar.setNome("Fulano de Tal Novo");

		try {
			controllerAdministrador.alterarComprador(compradorAlterar);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		assertNotNull(compradorTeste);
		assertEquals("Fulano de Tal Novo", compradorTeste.getNome());

	}

	@Test
	public void testAlterarCompradorFanSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		assertNotNull(compradorFanTeste);
		assertEquals("Fulano de Tal", compradorFanTeste.getNome());

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(compradorFanTeste.getDocumento());
		compradorFanAlterar.setEmail(compradorFanTeste.getEmail());
		compradorFanAlterar.setEndereco(compradorFanTeste.getEndereco());
		compradorFanAlterar.setTelefone(compradorFanTeste.getTelefone());
		compradorFanAlterar.setRegistro(compradorFanTeste.getRegistro());
		compradorFanAlterar.setNome("Fulano de Tal Novo");

		try {
			controllerAdministrador.alterarComprador(compradorFanAlterar);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		assertNotNull(compradorFanTeste);
		assertEquals("Fulano de Tal Novo", compradorFanTeste.getNome());
	}

	@Test
	public void testAlterarCompradorNulo() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		compradorTeste = null;

		try{
			controllerAdministrador.alterarComprador(compradorTeste);
			fail();
		}catch(CompradorNuloException cause){
			assertTrue(true);
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorFanNulo() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		compradorFanTeste = null;

		try{
			controllerAdministrador.alterarComprador(compradorFanTeste);
			fail();
		}catch(CompradorNuloException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorSemNome() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		Comprador compradorAlterar = new Comprador();
		compradorAlterar.setDocumento(compradorTeste.getDocumento());
		compradorAlterar.setEmail(compradorTeste.getEmail());
		compradorAlterar.setEndereco(compradorTeste.getEndereco());
		compradorAlterar.setTelefone(compradorTeste.getTelefone());
		compradorAlterar.setNome("");

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		compradorAlterar.setNome("         ");		

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testAlterarCompradorFanSemNome() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(compradorFanTeste.getDocumento());
		compradorFanAlterar.setEmail(compradorFanTeste.getEmail());
		compradorFanAlterar.setEndereco(compradorFanTeste.getEndereco());
		compradorFanAlterar.setTelefone(compradorFanTeste.getTelefone());
		compradorFanAlterar.setRegistro(compradorFanTeste.getRegistro());
		compradorFanAlterar.setNome("");

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		compradorFanAlterar.setNome("         ");

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testAlterarCompradorSemEndereco() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		Comprador compradorAlterar = new Comprador();
		compradorAlterar.setDocumento(compradorTeste.getDocumento());
		compradorAlterar.setEmail(compradorTeste.getEmail());
		compradorAlterar.setEndereco(null);
		compradorAlterar.setTelefone(compradorTeste.getTelefone());
		compradorAlterar.setNome(compradorTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorFanSemEndereco() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(compradorFanTeste.getDocumento());
		compradorFanAlterar.setEmail(compradorFanTeste.getEmail());
		compradorFanAlterar.setEndereco(null);
		compradorFanAlterar.setTelefone(compradorFanTeste.getTelefone());
		compradorFanAlterar.setRegistro(compradorFanTeste.getRegistro());
		compradorFanAlterar.setNome(compradorFanTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorSemEmail() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		Comprador compradorAlterar = new Comprador();
		compradorAlterar.setDocumento(compradorTeste.getDocumento());
		compradorAlterar.setEmail("");
		compradorAlterar.setEndereco(compradorTeste.getEndereco());
		compradorAlterar.setTelefone(compradorTeste.getTelefone());
		compradorAlterar.setNome(compradorTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		compradorAlterar.setEmail("         ");

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

	}

	@Test
	public void testAlterarCompradorFanSemEmail() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(compradorFanTeste.getDocumento());
		compradorFanAlterar.setEmail("");
		compradorFanAlterar.setEndereco(compradorFanTeste.getEndereco());
		compradorFanAlterar.setTelefone(compradorFanTeste.getTelefone());
		compradorFanAlterar.setRegistro(compradorFanTeste.getRegistro());
		compradorFanAlterar.setNome(compradorFanTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		compradorFanAlterar.setEmail("     ");

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testAlterarCompradorSemTelefone() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		Comprador compradorAlterar = new Comprador();
		compradorAlterar.setDocumento(compradorTeste.getDocumento());
		compradorAlterar.setEmail(compradorTeste.getEmail());
		compradorAlterar.setEndereco(compradorTeste.getEndereco());
		compradorAlterar.setTelefone("");
		compradorAlterar.setNome(compradorTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

		compradorAlterar.setTelefone("         ");		

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}

	}

	@Test
	public void testAlterarCompradorFanSemTelefone() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(compradorFanTeste.getDocumento());
		compradorFanAlterar.setEmail(compradorFanTeste.getEmail());
		compradorFanAlterar.setEndereco(compradorFanTeste.getEndereco());
		compradorFanAlterar.setTelefone("");
		compradorFanAlterar.setRegistro(compradorFanTeste.getRegistro());
		compradorFanAlterar.setNome(compradorFanTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}

		compradorFanAlterar.setTelefone("     ");

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}

	}

	@Test
	public void testAlterarCompradorSemDocumento() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorTeste = null;
		try {
			compradorTeste = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		Comprador compradorAlterar = new Comprador();
		compradorAlterar.setDocumento(0);
		compradorAlterar.setEmail(compradorTeste.getEmail());
		compradorAlterar.setEndereco(compradorTeste.getEndereco());
		compradorAlterar.setTelefone(compradorTeste.getTelefone());
		compradorAlterar.setNome(compradorTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorFanSemDocumento() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(0);
		compradorFanAlterar.setEmail(compradorFanTeste.getEmail());
		compradorFanAlterar.setEndereco(compradorFanTeste.getEndereco());
		compradorFanAlterar.setTelefone(compradorFanTeste.getTelefone());
		compradorFanAlterar.setRegistro(compradorFanTeste.getRegistro());
		compradorFanAlterar.setNome(compradorFanTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CompradorNuloException e) {
			fail();
		//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}
	}

	@Test
	public void testAlterarCompradorFanSemRegisroFan() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanTeste = null;
		try {
			compradorFanTeste = (CompradorFan) controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException e1) {
			fail();
		}

		CompradorFan compradorFanAlterar = new CompradorFan();
		compradorFanAlterar.setDocumento(compradorFanTeste.getDocumento());
		compradorFanAlterar.setEmail(compradorFanTeste.getEmail());
		compradorFanAlterar.setEndereco(compradorFanTeste.getEndereco());
		compradorFanAlterar.setTelefone(compradorFanTeste.getTelefone());
		compradorFanAlterar.setRegistro(0);
		compradorFanAlterar.setNome(compradorFanTeste.getNome());

		try{
			controllerAdministrador.alterarComprador(compradorFanAlterar);
			fail();
		} catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
			//Adicionado Try catch para o caso de não encontrar o comprador
		} catch (CompradorNaoEncontradoException e) {
			fail();
		} catch (CompradorNuloException e) {
			fail();
		}
	}

	@Test
	public void testRecuperarCompradorSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Sicrano de Tal");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-6312");
		comprador2.setEmail("sicrano@tal.com.br");
		comprador2.setDocumento(4321);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorRecuperado = null;

		try {
			compradorRecuperado = controllerAdministrador.recuperarComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException cause) {
			fail();
		}

		assertNotNull(compradorRecuperado);
		assertEquals(compradorRecuperado.getDocumento(), comprador.getDocumento());
	}
	@Test
	public void testRecuperarCompradorInexistente() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Sicrano de Tal");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-6312");
		comprador2.setEmail("sicrano@tal.com.br");
		comprador2.setDocumento(4321);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		try {
			controllerAdministrador.recuperarComprador(101066);
			fail();
		} catch (CompradorNaoEncontradoException cause) {
			assertTrue(true);
		}
	}

	@Test
	public void testRecuperarCompradorFanSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Sicrano de Tal");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-6312");
		compradorFan2.setEmail("sicrano@tal.com.br");
		compradorFan2.setDocumento(4321);
		compradorFan2.setRegistro(987);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(compradorFan2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanRecuperado = null;

		try {
			compradorFanRecuperado = (CompradorFan)controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException cause) {
			fail();
		}

		assertNotNull(compradorFanRecuperado);
		assertEquals(compradorFanRecuperado.getDocumento(), compradorFan.getDocumento());
	}
	@Test
	public void testRecuperarCompradorFanInexistente() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Sicrano de Tal");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-6312");
		compradorFan2.setEmail("sicrano@tal.com.br");
		compradorFan2.setDocumento(4321);
		compradorFan2.setRegistro(987);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(compradorFan2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		try {
			controllerAdministrador.recuperarComprador(101066);
			fail();
		} catch (CompradorNaoEncontradoException cause) {
			assertTrue(true);
		}
	}

	@Test
	public void testRemoverCompradorSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Sicrano de Tal");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-6312");
		comprador2.setEmail("sicrano@tal.com.br");
		comprador2.setDocumento(4321);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Comprador compradorRemovido = null;

		try {
			compradorRemovido = controllerAdministrador.removerComprador(comprador.getDocumento());
		} catch (CompradorNaoEncontradoException cause) {
			fail();
		} catch (RemocaoNaoPermitidaException e) {
			fail();
		}

		assertNotNull(compradorRemovido);
		assertEquals(compradorRemovido.getDocumento(), comprador.getDocumento());

		try {
			controllerAdministrador.recuperarComprador(comprador.getDocumento());
			fail();
		} catch (CompradorNaoEncontradoException cause) {
			assertTrue(true);
		}
	}

	@Test
	public void testRemoverCompradorInexistente() {

		Endereco end = CriarObjetos.criarEndereco();

		Comprador comprador = new Comprador();
		comprador.setNome("Fulano de Tal");
		comprador.setEndereco(end);
		comprador.setTelefone("(75)3489-1263");
		comprador.setEmail("fulano@tal.com.br");
		comprador.setDocumento(1234);

		Comprador comprador2 = new Comprador();
		comprador2.setNome("Sicrano de Tal");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-6312");
		comprador2.setEmail("sicrano@tal.com.br");
		comprador2.setDocumento(4321);

		try {
			controllerAdministrador.cadastrarComprador(comprador);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(comprador2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		try {
			controllerAdministrador.removerComprador(101066);
			fail();
		} catch (CompradorNaoEncontradoException cause) {
			assertTrue(true);
		} catch (RemocaoNaoPermitidaException e) {
			fail();
		}
	}

	@Test
	public void testRemoverCompradorFanSucesso() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Sicrano de Tal");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-6312");
		compradorFan2.setEmail("sicrano@tal.com.br");
		compradorFan2.setDocumento(4321);
		compradorFan2.setRegistro(987);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(compradorFan2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		CompradorFan compradorFanRemovido = null;

		try {
			compradorFanRemovido = (CompradorFan)controllerAdministrador.removerComprador(compradorFan.getDocumento());
		} catch (CompradorNaoEncontradoException cause) {
			fail();
		} catch (RemocaoNaoPermitidaException e) {
			fail();
		}

		assertNotNull(compradorFanRemovido);
		assertEquals(compradorFanRemovido.getDocumento(), compradorFan.getDocumento());

		try {
			controllerAdministrador.recuperarComprador(compradorFan.getDocumento());
			fail();
		} catch (CompradorNaoEncontradoException cause) {
			assertTrue(true);
		}
	}

	@Test
	public void testRemoverCompradorFanInexistente() {

		Endereco end = CriarObjetos.criarEndereco();

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(2098);

		CompradorFan compradorFan2 = new CompradorFan();
		compradorFan2.setNome("Sicrano de Tal");
		compradorFan2.setEndereco(end);
		compradorFan2.setTelefone("(75)3489-6312");
		compradorFan2.setEmail("sicrano@tal.com.br");
		compradorFan2.setDocumento(4321);
		compradorFan2.setRegistro(987);

		try {
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		try {
			controllerAdministrador.cadastrarComprador(compradorFan2);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		try {
			controllerAdministrador.removerComprador(101066);
			fail();
		} catch (CompradorNaoEncontradoException cause) {
			assertTrue(true);
		} catch (RemocaoNaoPermitidaException e) {
			fail();
		}
	}

	@Test
	public void testRemoverCompradorComIngresso() {

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
			controllerAdministrador.removerComprador(comprador.getDocumento());
			fail();
		} catch (RemocaoNaoPermitidaException cause) {
			assertTrue(true);
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}
	}

	@Test
	public void testRemoverCompradorFanComIngresso() {

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

		CompradorFan compradorFan = new CompradorFan();
		compradorFan.setNome("Fulano de Tal");
		compradorFan.setEndereco(end);
		compradorFan.setTelefone("(75)3489-1263");
		compradorFan.setEmail("fulano@tal.com.br");
		compradorFan.setDocumento(1234);
		compradorFan.setRegistro(1234);

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
			controllerAdministrador.cadastrarComprador(compradorFan);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		int qtdIngressos = 3;

		CompradorController controllerComprador = CompradorController.getInstance();

		try {
			controllerComprador.comprarIngresso(compradorFan.getDocumento(),cine.getId(),1,15,qtdIngressos,60.00);
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
			controllerAdministrador.removerComprador(compradorFan.getDocumento());
			fail();
		} catch (RemocaoNaoPermitidaException cause) {
			assertTrue(true);
		} catch (CompradorNaoEncontradoException e) {
			fail();
		}
	}
}