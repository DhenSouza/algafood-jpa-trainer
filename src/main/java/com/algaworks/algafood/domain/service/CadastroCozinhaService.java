package com.algaworks.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return repository.adicionar(cozinha);
	}

	public Cozinha alterarCozinha(Long id, Cozinha cozinha) {
		Cozinha cozinhaDif = repository.buscarPorId(id);

		// Ele pega o objeto atual e copia e joga no Objeto alvo
		BeanUtils.copyProperties(cozinha, cozinhaDif, "id");

		return repository.adicionar(cozinhaDif);
	}
}
