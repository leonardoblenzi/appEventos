package com.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
//CrudRepository<Entidade que vai ser usada, Long que é o tipo do id do objeto
public interface ConvidadoRepository extends JpaRepository<Convidado, String> {
	 
	//lista(iterable) de convidado que recebe um evento como parametro e vai retornar somente convidados que estejam nesse evento
	Iterable<Convidado> findByEvento(Evento evento);
	
	//definindo metodo que retorna convidado procurando pelo rg
	Convidado findByRg(String rg);

}
