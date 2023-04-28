package com.eventoapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {

	// fazendo injeção de dependencia com os repositorys para fazer as operações
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;

	// retorna um formulario para cadastro do evento
	@GetMapping("/cadastrarEvento")
	public String form() {
		return "evento/formEvento";
	}

	// ao receber uma requisição POST salva o evento no banco de dados e redireciona
	// para o formulario(view) de cadastrar evento
	@PostMapping("/cadastrarEvento")
	public String form(Evento evento) {
		// salva o evento passado como parametro no BD
		eventoRepository.save(evento);
		return "redirect:/cadastrarEvento";
	}

	// funcao que vai retornar a lista de todos eventos ao acessar a url /eventos
	@GetMapping("/eventos")
	public ModelAndView listaEventos() {
		// recebe a pagina(template) que vai renderizar com os dados do evento
		ModelAndView mv = new ModelAndView("index");

		// fazendo busca dos eventos
		// tipo Iterable porque vai ser uma lista de eventos. Usando funcao findAll de
		// repository
		Iterable<Evento> eventos = eventoRepository.findAll();

		// adicionando lista ao objeto ModelAndView que vai ser passado para o html com
		// os valores obtidos
		// (nome dado ao objeto no html, lista de objetos)
		mv.addObject("eventos", eventos);

		return mv;
	}

	// funcao que vai retornar detalhes do evento
	// ao receber o CODIGO do evento Long pela url vai chamar a funcao

	// recebe o codigo do evento como parametro e a partir dele vai fazer uma busca
	@GetMapping(value = "/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {

		// chamando funcao, procurando pelo codigo e passando valor retornado para
		// evento
		Optional<Evento> evento = eventoRepository.findById(codigo);

		// recebe a pagina(template) que vai renderizar com os dados do evento
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");

		// adicionando lista ao objeto ModelAndView que vai ser passado para o html com
		// os valores obtidos
		// (nome dado ao objeto no html, objeto)
		mv.addObject("evento", evento);

		return mv;

	}

	//à partir de uma requisicao post vai executar e salvar o convidado no BD
	@PostMapping(value = "/{codigo}")
	public String cadastrarConvidado(@PathVariable("codigo") long codigo, Convidado convidado) {
		//buscando pelo codigo do evento e atribuindo à var
		Optional<Evento> eventoBusca = eventoRepository.findById(codigo);
		
		//extraindo o valor do Optional
		Evento evento = eventoBusca.get();
		
		//setando o evento para convidado
		convidado.setEvento(evento);

		//salvando convidado no BD
		
		convidadoRepository.save(convidado);
		
		return "redirect:/{codigo}";

	}
}
