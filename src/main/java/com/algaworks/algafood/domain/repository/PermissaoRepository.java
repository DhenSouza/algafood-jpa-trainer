package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Permissao;

@Repository
public interface PermissaoRepository {

	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao inserir(Permissao permissao);
	void remover(Permissao permissao);
}
