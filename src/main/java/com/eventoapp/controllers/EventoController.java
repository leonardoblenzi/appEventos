package com.eventoapp.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		// Verifica se há erros de validação nos campos preenchidos
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			// redirecionando para pagina do formulario de novo
			return "redirect:/cadastrarEvento}";
		}

		// salva o evento passado como parametro no BD
		eventoRepository.save(evento);
		// se salvar com sucesso exibe mensagem para o usuario
		attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");

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
		mv.addObject("evento", evento.get());

		// pegando lista de convidados do evento especifico
		Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento.get());

		// enviando essa lista de convidados para a view

		mv.addObject("convidados", convidados);

		return mv;

	}

	// à partir de uma requisicao post vai executar e salvar o convidado no BD
	@PostMapping(value = "/{codigo}")
	public String cadastrarConvidado(@PathVariable("codigo") long codigo, @Valid Convidado convidado,
			BindingResult result, RedirectAttributes attributes) {

		// Verifica se há erros de validação nos campos preenchidos
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			// redirecionando para pagina do formulario de novo
			return "redirect:/{codigo}";
		}

		// buscando pelo codigo do evento e atribuindo à var
		Optional<Evento> eventoBusca = eventoRepository.findById(codigo);

		// Verifica se o evento existe antes de prosseguir
		if (!eventoBusca.isPresent()) {
			attributes.addFlashAttribute("mensagem", "Evento não encontrado");
			return "redirect:/";
		}

		// extraindo o valor do Optional
		Evento evento = eventoBusca.get();

		// setando o evento para convidado
		convidado.setEvento(evento);

		// salvando convidado no BD

		convidadoRepository.save(convidado);

		// se salvar com sucesso exibe mensagem para o usuario
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");

		return "redirect:/{codigo}";

	}

	@GetMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		// buscando evento
		Optional<Evento> eventoBusca = eventoRepository.findById(codigo);
		// acessando elemento Optional(lista) para obter valor de evento
		Evento evento = eventoBusca.get();

		// deletando evento
		eventoRepository.delete(evento);

		return "redirect:/eventos";
	}

	@GetMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		// buscando convidado
		Convidado convidado = convidadoRepository.findByRg(rg);

		// deletando convidado
		convidadoRepository.delete(convidado);

		// buscando evento a que convidado pertence
		Evento evento = convidado.getEvento();
		long codigoEvento = evento.getCodigo();

		// passando para string
		String codigo = String.valueOf(codigoEvento);

		return "redirect:/" + codigo;
	}

	//formulario de edição evento
	@GetMapping("/editarEvento")
	public ModelAndView editarEvento(long codigo) {
		// buscando evento
		Optional<Evento> eventoBusca = eventoRepository.findById(codigo);
		// acessando elemento Optional(lista) para obter valor de evento
		Evento evento = eventoBusca.get();
		
		ModelAndView mv = new ModelAndView("evento/update-evento");
		mv.addObject("evento", evento);
		return mv;
	}
	
	
	@PostMapping("/editar-evento")
	public String updateEvento(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
	    // Verificar se há erros de validação nos campos preenchidos
	    if (result.hasErrors()) {
	        // Lógica para lidar com os erros de validação
	        return "eventos";
	    }
	    
	    // Carregar o evento existente do banco de dados usando o id recebido
	    Optional<Evento> eventoExistente = eventoRepository.findById(evento.getCodigo());
	    
	    // Verificar se o evento existe
	    if (eventoExistente.isPresent()) {
	        //pegando as informações de Optional atualizadas para o evento existente
	        Evento eventoAtualizado = eventoExistente.get();
	        
	        //setando novos valores atualizados para o evento
	        eventoAtualizado.setNome(evento.getNome());
	        eventoAtualizado.setLocal(evento.getLocal());
	        eventoAtualizado.setData(evento.getData());
	        eventoAtualizado.setHorario(evento.getHorario());
	        
	        //salvando o evento atualizado no banco de dados
	        eventoRepository.save(eventoAtualizado);
	        
	        attributes.addFlashAttribute("success", "Evento alterado com sucesso!");
	    } else {
	        //Lidar com o caso em que o evento não foi encontrado
	        attributes.addFlashAttribute("error", "Evento não encontrado!");
	    }
	    
	    return "redirect:/eventos";
	}

}
