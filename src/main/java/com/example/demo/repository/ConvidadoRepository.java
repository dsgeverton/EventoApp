package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Convidado;
import com.example.demo.model.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	Iterable<Convidado> findByEvento(Evento evento);
}
