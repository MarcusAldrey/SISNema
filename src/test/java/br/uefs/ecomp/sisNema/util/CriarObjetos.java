package br.uefs.ecomp.sisNema.util;

import br.uefs.ecomp.sisNema.controller.AdministradorController;
import br.uefs.ecomp.sisNema.exceptions.CampoObrigatorioInexistenteException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNaoEncontradoException;
import br.uefs.ecomp.sisNema.exceptions.CinemaNuloException;
import br.uefs.ecomp.sisNema.exceptions.IntervaloMinimoInsuficienteException;
import br.uefs.ecomp.sisNema.exceptions.LimiteSalasExcedidoException;
import br.uefs.ecomp.sisNema.exceptions.SalaNaoEncontradaException;
import br.uefs.ecomp.sisNema.exceptions.SalaNulaException;
import br.uefs.ecomp.sisNema.exceptions.SessaoNulaException;
import br.uefs.ecomp.sisNema.model.Cinema;
import br.uefs.ecomp.sisNema.model.Endereco;
import br.uefs.ecomp.sisNema.model.Sala;
import br.uefs.ecomp.sisNema.model.Sessao;

public class CriarObjetos {

	public static Endereco criarEndereco(){
		Endereco end = new Endereco();
		end.setRua("Av. João Durval");
		end.setBairro("Centro");
		end.setNumero(123);
		end.setCep("44.000-000");
		end.setCidade("Feira de Santana");
		end.setEstado("Bahia");
		end.setComplemento("Shopping Boulevard");
		return end;
	}
	
	public static int criarCinema(AdministradorController controller) throws CinemaNuloException, CampoObrigatorioInexistenteException{
		Endereco end = CriarObjetos.criarEndereco();
		
		Cinema cine = new Cinema();
		cine.setNome("ORIENT Cineplace Boulevard Shopping");
		cine.setQtdSalas(9);
		cine.setEndereco(end);
		
		cine = controller.cadastrarCinema(cine);
		return cine.getId();
	}

	public static void criarSala(Cinema cine, AdministradorController controller) throws CinemaNaoEncontradoException, CinemaNuloException, CampoObrigatorioInexistenteException, SalaNulaException, LimiteSalasExcedidoException{
		Sala sala = new Sala();
		sala.setNumeroSala(1);
		sala.setQtdCadeiras(300);
		
		controller.cadastrarSala(cine,sala);
	}
	
	public static void criarSessao(Cinema cine, AdministradorController controller) throws SessaoNulaException, CampoObrigatorioInexistenteException, IntervaloMinimoInsuficienteException, CinemaNaoEncontradoException, SalaNaoEncontradaException, CinemaNuloException, SalaNulaException{
		Sala sala = null;
		Iterador i = cine.getSalas().iterador();
		while(i.temProximo()){
			sala = (Sala)i.obterProximo();
		}
		
		Sessao sessao = new Sessao();
		sessao.setHoraInicio(15);
		sessao.setHoraFim(17);
		
		controller.cadastrarSessao(cine,sala,sessao);
	}
}
