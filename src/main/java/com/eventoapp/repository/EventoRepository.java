package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Evento;
//CrudRepository<Entidade que vai ser usada, Long que é o tipo do id do objeto
public interface EventoRepository extends CrudRepository<Evento, Long> {
	

}
