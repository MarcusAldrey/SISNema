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
import br.uefs.ecomp.sisNema.exceptions.LimiteSalasExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNulaException;
import br.uefs.ecomp.sisNema.util.CriarObjetos;
import br.uefs.ecomp.sisNema.util.Iterador;

public class SalaTest {

	private AdministradorController controllerAdministrador;
	
	@Before
	public void setUp() throws Exception {
		AdministradorController.zerarSingleton();
		controllerAdministrador = AdministradorController.getInstance();
	}
	
	@Test
	public void testListarSalasSucesso() {
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
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		} catch (CinemaNuloException e1) {
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
		
		/* Teste alterado para receber um iterador e não uma lista encadeada */

		//assertEquals(1,controllerAdministrador.listarSalas(cine).obterTamanho());

		int x;
		Iterador iterador = controllerAdministrador.listarSalas(cine);
		for(x = 0; iterador.temProximo(); x++)
			iterador.obterProximo();
		
		assertEquals(1,x);
		
	}
	
	@Test
	public void testCadastrarSalaSucesso() {
		
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
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		} catch (CinemaNuloException e1) {
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
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(300,salaTeste.getQtdCadeiras());
	}
	
	@Test
	public void testCadastrarSalaEmCinemaNaoCadastrado() {
		
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);
		
		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.cadastrarSala(cine,sala);
			fail();
		}catch(CinemaNaoEncontradoException cause){
			assertTrue(true);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}
	}
	
	@Test
	public void testCadastrarSalaEmCinemaNulo() {
		
		Cinema cine = null;
		
		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.cadastrarSala(cine,sala);
			fail();
		}catch(CinemaNuloException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}
		
	}
	
	@Test
	public void testCadastrarSalaSemNumero() {
		
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);
		
		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		}
		
		Sala sala = new Sala();
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.cadastrarSala(cine,sala);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}
		
	}
	
	@Test
	public void testCadastrarSalaSemQtdCadeiras() {
		
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);
		
		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		}
		
		Sala sala = new Sala();
		sala.setNumeroSala(1);
		
		try{
			controllerAdministrador.cadastrarSala(cine,sala);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}
	}
	@Test
	public void testCadastrarSalaNula() {
		
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);
		
		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		}
		
		Sala sala = null;
		
		try{
			controllerAdministrador.cadastrarSala(cine,sala);
			fail();
		}catch(SalaNulaException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (LimiteSalasExcedidoException e) {
			fail();
		}
	}
	
	@Test
	public void testCadastrarMaisSalasQueOPermitido() {
		
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(1);
		cine.setEndereco(end);
		
		try {
			cine = controllerAdministrador.cadastrarCinema(cine);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}

		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);
		
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
		
		Sala sala2 = new Sala();
		sala.setNumeroSala(2);
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.cadastrarSala(cine,sala2);
			fail();
		}catch(LimiteSalasExcedidoException cause){
			assertTrue(true);
		} catch (SalaNulaException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
	}
	
	@Test
	public void testAlterarSalaSucesso() {
		
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
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Sala salaTeste = null;;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(300,salaTeste.getQtdCadeiras());
		
		Sala salaAlterar = new Sala();
		salaAlterar.setNumeroSala(salaTeste.getNumeroSala());
		salaAlterar.setQtdCadeiras(200);
		
		try {
			controllerAdministrador.alterarSala(cine,salaAlterar);
		} catch (CampoObrigatorioInexistenteException e1) {
			fail();
		} catch (SalaNaoEncontradaException e1) {
			fail();
		} catch (CinemaNuloException e1) {
			fail();
		} catch (CinemaNaoEncontradoException e1) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
		
        try {
			cine = controllerAdministrador.recuperarCinema(cine.getId());
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Iterador i2 = cine.getSalas().iterador();
		while(i2.temProximo()){
			salaTeste = (Sala)i2.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(200,salaTeste.getQtdCadeiras());
	}
	
	@Test
	public void testAlterarSalaEmCinemaNaoCadastrado() {
		
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);
		
		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.alterarSala(cine,sala);
			fail();
		}catch(CinemaNaoEncontradoException cause){
			assertTrue(true);
		} catch (CampoObrigatorioInexistenteException e) {
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
	public void testAlterarSalaEmCinemaNulo() {
		
		Cinema cine = null;
		
		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.alterarSala(cine,sala);
			fail();
		}catch(CinemaNuloException cause){
			assertTrue(true);
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
		
	}
	
	@Test
	public void testAlterarSalaInexistente() {
		
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
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(300,salaTeste.getQtdCadeiras());
		
		Sala sala = new Sala();
		sala.setNumeroSala(2);
		sala.setQtdCadeiras(300);
		
		try{
			controllerAdministrador.alterarSala(cine,sala);
			fail();
		}catch(SalaNaoEncontradaException cause){
			assertTrue(true);
		} catch (CinemaNuloException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
	}
	
	@Test
	public void testAlterarSalaSemNumero() {
		
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
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(300,salaTeste.getQtdCadeiras());
		
		Sala sala = new Sala();
		sala.setQtdCadeiras(200);
		
		try{
			controllerAdministrador.alterarSala(cine,sala);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
		
	}
	
	@Test
	public void testAlterarSalaSemQtdCadeiras() {
		
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
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(300,salaTeste.getQtdCadeiras());
		
		Sala salaAlterar = new Sala();
		salaAlterar.setNumeroSala(salaTeste.getNumeroSala());
		salaAlterar.setQtdCadeiras(0);
		
		try{
			controllerAdministrador.alterarSala(cine,salaAlterar);
			fail();
		}catch(CampoObrigatorioInexistenteException cause){
			assertTrue(true);
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		} catch (SalaNulaException e) {
			fail();
		}
		
	}
	@Test
	public void testAlterarSalaNula() {
		
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
		
		assertEquals(1,cine.getSalas().obterTamanho());
		
		Sala salaTeste = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			salaTeste = (Sala)i.obterProximo();
		}
		assertEquals(1,salaTeste.getNumeroSala());
		assertEquals(300,salaTeste.getQtdCadeiras());
		
		Sala sala = null;
		
		try{
			controllerAdministrador.alterarSala(cine,sala);
			fail();
		}catch(SalaNulaException cause){
			assertTrue(true);
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		} catch (SalaNaoEncontradaException e) {
			fail();
		} catch (CinemaNuloException e) {
			fail();
		} catch (CinemaNaoEncontradoException e) {
			fail();
		}
	}
}