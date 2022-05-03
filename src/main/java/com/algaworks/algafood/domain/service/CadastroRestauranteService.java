package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository repository;

	public List<Restaurante> listar() {
		return repository.todos();
	}

	public Restaurante buscarPorId(Long id) {
		return repository.buscarPorId(id);
	}

	public Restaurante salvar(Restaurante restaurante) {
		return repository.adicionar(restaurante);
	}

	public Restaurante alterar(Long id, Restaurante restaurante) {
		Restaurante aux = repository.buscarPorId(id);

		BeanUtils.copyProperties(restaurante, aux, "id");

		return repository.adicionar(aux);
	}

	public void deletar(Long id) {
		try {
			repository.deletar(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("nao existe um cadastro de restaurante com codigo %d ", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d ao pode ser removida, pois, esta e uso", id));
		}
	}
}
