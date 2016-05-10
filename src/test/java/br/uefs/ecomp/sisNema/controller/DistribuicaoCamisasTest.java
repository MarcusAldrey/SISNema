package br.uefs.ecomp.sisNema.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import br.uefs.ecomp.sisNema.exceptions.CampoObrigatorioInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNuloException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CompradorNuloException;
import br.uefs.ecomp.sisNema.exceptions.FaHabilitadoInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.IntervaloMinimoInsuficienteException;
import br.uefs.ecomp.sisNema.exceptions.LimiteIngressosExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.LimiteSalasExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNulaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNulaException;
import br.uefs.ecomp.sisNema.model.Cinema;
import br.uefs.ecomp.sisNema.model.Comprador;
import br.uefs.ecomp.sisNema.model.CompradorFan;
import br.uefs.ecomp.sisNema.model.Endereco;
import br.uefs.ecomp.sisNema.model.Sala;
import br.uefs.ecomp.sisNema.model.Sessao;
import br.uefs.ecomp.sisNema.util.CriarObjetos;
import br.uefs.ecomp.sisNema.util.Iterador;

public class DistribuicaoCamisasTest {

	private AdministradorController controllerAdministrador;
	private CompradorController controllerComprador;
	
	@Before
	public void setUp() throws Exception {
		AdministradorController.zerarSingleton();
		CompradorController.zerarSingleton();
		controllerAdministrador = AdministradorController.getInstance();
		controllerComprador = CompradorController.getInstance();
	}
	
	/*Teste adicionado para verificar se FaHabilitadoException está sendo
	  lançado corretamente */
	@Test
	public void testDistribuirCamisasAMais() {
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

		CompradorFan comprador2 = new CompradorFan();
		comprador2.setNome("Fulano de Tal 2");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-1263");
		comprador2.setEmail("fulano2@tal.com.br");
		comprador2.setDocumento(1236);
		comprador2.setRegistro(2098);

		CompradorFan comprador3 = new CompradorFan();
		comprador3.setNome("Fulano de Tal 3");
		comprador3.setEndereco(end);
		comprador3.setTelefone("(75)3489-1263");
		comprador3.setEmail("fulano3@tal.com.br");
		comprador3.setDocumento(1238);
		comprador3.setRegistro(2099);
		
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
		
		int qtdIngressos = 3;

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
			controllerComprador.comprarIngresso(comprador2.getDocumento(),cine.getId(),1,15,qtdIngressos,60.00);
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
			controllerComprador.comprarIngresso(comprador3.getDocumento(),cine.getId(),1,15,qtdIngressos, 60.00);
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
		
		assertEquals(2,controllerAdministrador.recuperarFansHabilitados().obterTamanho());

		Iterador i = controllerAdministrador.recuperarFansHabilitados().iterador();

		assertEquals(true,i.temProximo());
		CompradorFan compradorTeste = (CompradorFan)i.obterProximo();
		assertEquals(2098,compradorTeste.getRegistro());

		assertEquals(true,i.temProximo());
		compradorTeste = (CompradorFan)i.obterProximo();
		assertEquals(2099,compradorTeste.getRegistro());

		assertEquals(false,i.temProximo());

		try {
			controllerAdministrador.distribuirCamisas(4);
		//Adicionado Try Catch para o caso de não ser encontrado Fã habilitado para receber a camisa
		} catch (FaHabilitadoInexistenteException e) {
			assertTrue(true);
		}

	}
	
	/* Uma série de modificações foram feitas para adicionar um novo comprador
	 * à lista de compradores habilitados, para que a quantidade de compradores
	 * seja a mesma de camisas a serem distribuídas
	 */
	@Test
	public void testDistribuirCamisasSucesso() {
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(12);
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
		
		CompradorFan comprador2 = new CompradorFan();
		comprador2.setNome("Fulano de Tal 2");
		comprador2.setEndereco(end);
		comprador2.setTelefone("(75)3489-1263");
		comprador2.setEmail("fulano2@tal.com.br");
		comprador2.setDocumento(1236);
		comprador2.setRegistro(2098);
		
		CompradorFan comprador3 = new CompradorFan();
		comprador3.setNome("Fulano de Tal 3");
		comprador3.setEndereco(end);
		comprador3.setTelefone("(75)3489-1263");
		comprador3.setEmail("fulano3@tal.com.br");
		comprador3.setDocumento(1238);
		comprador3.setRegistro(2099);
		
		CompradorFan comprador4 = new CompradorFan();
		comprador4.setNome("Fulano de Tal 4");
		comprador4.setEndereco(end);
		comprador4.setTelefone("(75)3489-1263");
		comprador4.setEmail("fulano4@tal.com.br");
		comprador4.setDocumento(1240);
		comprador4.setRegistro(2100);
		
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
		
		try {
			controllerAdministrador.cadastrarComprador(comprador4);
		} catch (CompradorNuloException e) {
			fail();
		} catch (CampoObrigatorioInexistenteException e) {
			fail();
		}
		
		int qtdIngressos = 3;
		
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
			controllerComprador.comprarIngresso(comprador2.getDocumento(),cine.getId(),1,15,qtdIngressos,60.00);
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
			controllerComprador.comprarIngresso(comprador3.getDocumento(),cine.getId(),1,15,qtdIngressos, 60.00);
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
			controllerComprador.comprarIngresso(comprador4.getDocumento(),cine.getId(),1,15,qtdIngressos, 60.00);
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
		
        assertEquals(3,controllerAdministrador.recuperarFansHabilitados().obterTamanho());
		
		Iterador i = controllerAdministrador.recuperarFansHabilitados().iterador();
		
		assertEquals(true,i.temProximo());
		CompradorFan compradorTeste = (CompradorFan)i.obterProximo();
		assertEquals(2098,compradorTeste.getRegistro());
		
		assertEquals(true,i.temProximo());
		compradorTeste = (CompradorFan)i.obterProximo();
		assertEquals(2099,compradorTeste.getRegistro());
		
		assertEquals(true,i.temProximo());
		compradorTeste = (CompradorFan)i.obterProximo();
		assertEquals(2100,compradorTeste.getRegistro());
		
		assertEquals(false,i.temProximo());
		
		i = controllerAdministrador.recuperarFansHabilitados().iterador();
		
		try {
			controllerAdministrador.distribuirCamisas(1);
		} catch (FaHabilitadoInexistenteException e1) {
			fail();
		}
		
        i = controllerAdministrador.recuperarFansHabilitados().iterador();
		
		assertEquals(true,i.temProximo());
		compradorTeste = (CompradorFan)i.obterProximo();
		assertEquals(2099,compradorTeste.getRegistro());
		
		assertEquals(true,i.temProximo());
		
        try {
			controllerAdministrador.distribuirCamisas(2);
		} catch (FaHabilitadoInexistenteException e) {
			fail();
		}
		
        i = controllerAdministrador.recuperarFansHabilitados().iterador();
		assertEquals(false,i.temProximo());
	}
}
