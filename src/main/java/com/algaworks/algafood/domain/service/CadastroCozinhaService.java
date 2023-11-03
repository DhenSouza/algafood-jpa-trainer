package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO
			= "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public Cozinha alterarCozinha(Long id, Cozinha cozinha) {
			Cozinha cozinhaAtual = this.buscarCozinhaPorId(id);

			// Ele pega o objeto atual e copia e joga no Objeto alvo
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			return repository.save(cozinhaAtual);
	}

	public List<Cozinha> listar() {
		return repository.findAll();
	}

	public Cozinha buscarCozinhaPorId(Long cozinhaId) {
		return repository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

	public void deletarCozinha(Long id) {
		try {

			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id));
		}
	}

	public List<Cozinha> findByName(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome);
	}
}