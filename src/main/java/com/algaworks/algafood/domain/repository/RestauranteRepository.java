package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository {
	
	List<Restaurante> todos();

	Restaurante buscarPorId(Long id);

	Restaurante adicionar(Restaurante restaurante);

	void deletar(Long id);
	
}
