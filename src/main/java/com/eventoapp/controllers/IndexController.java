package com.eventoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	//quando o controller receber a requisicao '/' vai retornar a pagina index.html
	@GetMapping("/")
	public String index() {
		return "index";
	}
}
