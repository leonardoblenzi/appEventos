package com.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventoapp.models.Convidado;
//CrudRepository<Entidade que vai ser usada, Long que é o tipo do id do objeto
public interface ConvidadoRepository extends JpaRepository<Convidado, String> {
	 
	

}
