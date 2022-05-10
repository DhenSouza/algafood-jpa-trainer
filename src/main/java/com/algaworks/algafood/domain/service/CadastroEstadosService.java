package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadosService {

	@Autowired
	private EstadoRepository repository;

	public List<Estado> listar() {
		return repository.listar();
	}

	public Estado buscarPorId(Long id) {
		return repository.buscar(id);
	}

	public Estado salvar(Estado estado) {
		Long idEstado = estado.getId();
		Estado estadoAux = repository.incluir(estado);

		if (estadoAux == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com o codigo %d", idEstado));
		}

		return repository.incluir(estadoAux);
	}
	
	public Estado alterar(Estado estado, Long id) {
		Estado estadoAux = buscarPorId(id);
		
		BeanUtils.copyProperties(estado, estadoAux, "id");
		
		return repository.incluir(estadoAux);
	}
	
	public void delete(Long id) {
	try {
		 repository.remover(id);
	} catch (EmptyResultDataAccessException ex) {
		throw new EntidadeNaoEncontradaException(
				String.format("nao existe um cadastro de estado com codigo %d ", id));
	} catch (DataIntegrityViolationException e) {
		throw new EntidadeEmUsoException(
				String.format("Estado de código %d ao pode ser removida, pois, esta e uso", id));
	}
	}
}
