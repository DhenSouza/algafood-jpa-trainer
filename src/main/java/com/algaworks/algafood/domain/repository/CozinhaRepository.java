package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository {

	List<Cozinha> listar();

	Cozinha buscar();

	Cozinha salvar();

	void removerCozinha();

}
