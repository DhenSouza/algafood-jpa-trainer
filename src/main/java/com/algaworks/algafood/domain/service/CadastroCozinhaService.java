package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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

	public List<Cozinha> listar() {
		return repository.todos();
	}

	public Cozinha buscaCozinhaId(Long id) {
		return repository.buscarPorId(id);
	}
	
	public void deletarCozinha(Long id) {
	try {
		
		repository.deletar(id);
	} catch (EmptyResultDataAccessException ex) {
		throw new EntidadeNaoEncontradaException(
				String.format("nao existe um cadastro de cozinha com codigo %d ", id));
	} catch (DataIntegrityViolationException e) {
		throw new EntidadeEmUsoException(
				String.format("Cozinha de código %d ao pode ser removida, pois, esta e uso", id));
	}
	}
}
