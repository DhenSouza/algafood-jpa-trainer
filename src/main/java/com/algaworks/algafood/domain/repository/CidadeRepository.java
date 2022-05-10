package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cidade;

@Repository
public interface CidadeRepository {

	List<Cidade> listar();

	Cidade buscar(Long id);

	Cidade incluir(Cidade cidade);

	void remover(Long id);

}
