package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;

@Repository
public interface EstadoRepository {
	
	List<Estado> listar();
	Estado buscar(Long id);
	Estado incluir(Estado estado);
	void remover(Estado estado);
}
