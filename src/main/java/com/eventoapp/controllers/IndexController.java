package com.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Evento;
import com.eventoapp.repository.EventoRepository;

@Controller
public class IndexController {

	// fazendo injeção de dependencia com os repositorys para fazer as operações
	@Autowired
	private EventoRepository eventoRepository;
	
	@GetMapping("/")
	public ModelAndView index() {
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
	
}
