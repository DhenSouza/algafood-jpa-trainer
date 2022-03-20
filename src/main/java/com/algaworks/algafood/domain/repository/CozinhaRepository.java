package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository {

	List<Cozinha> todos();

	Cozinha buscarPorId(Long id);

	Cozinha adicionar(Cozinha cozinha);

	void deletar(Cozinha cozinha);

}
