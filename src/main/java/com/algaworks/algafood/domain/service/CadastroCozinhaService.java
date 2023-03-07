package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

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
		return repository.save(cozinha);
	}

	public Cozinha alterarCozinha(Long id, Cozinha cozinha) {
		try {
			Optional<Cozinha> cozinhaDif = repository.findById(id);

			// Ele pega o objeto atual e copia e joga no Objeto alvo
			BeanUtils.copyProperties(cozinha, cozinhaDif.get(), "id");

			return repository.save(cozinhaDif.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<Cozinha> listar() {
		return repository.findAll();
	}

	public Cozinha buscaCozinhaId(Long id) {

		Optional<Cozinha> obj = repository.findById(id);
		//.orElseThrow(() -> new Exception("Erro ao tentar buscar Por Nome"));
		return obj.get(); 
	}

	public void deletarCozinha(Long id) {
		try {

			repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("nao existe um cadastro de cozinha com codigo %d ", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d ao pode ser removida, pois, esta e uso", id));
		}
	}

	public List<Cozinha> findByName(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome);
	}
}
