package com.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
