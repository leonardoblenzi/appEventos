package com.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Evento;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {

	// fazendo ligação com o repository para fazer as operações
	@Autowired
	private EventoRepository eventoRepository;

	// retorna um formulario para cadastro do evento
	@GetMapping("/cadastrarEvento")
	public String form() {
		return "evento/formEvento";
	}

	//ao receber uma requisição POST salva o evento no banco de dados e redireciona para o formulario(view) de cadastrar evento
	@PostMapping("/cadastrarEvento")
	public String form(Evento evento) {
		// salva o evento passado como parametro no BD
		eventoRepository.save(evento);

		return "redirect:/cadastrarEvento";
	}
	
	//funcao que vai retornar a lista de todos eventos ao acessar a url /eventos
	@GetMapping("/eventos")
	public ModelAndView listaEventos() {
		//recebe a pagina(template) que vai renderizar com os dados do evento
		ModelAndView mv = new ModelAndView("index");
		
		//fazendo busca dos eventos
		//tipo Iterable porque vai ser uma lista de eventos. Usando funcao findAll de repository 
		Iterable<Evento> eventos = eventoRepository.findAll();
		
		//adicionando lista ao objeto ModelAndView que vai ser passado para o html com os valores obtidos 
		//(nome dado ao objeto no html, lista de objetos)
		mv.addObject("eventos", eventos);
		
		return mv;
	}
}
