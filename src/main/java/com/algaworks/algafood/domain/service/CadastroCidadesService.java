package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadesService {

	@Autowired
	private CidadeRepository repository;

	public List<Cidade> listar() {
		return repository.listar();
	}

	public Cidade buscarPorId(Long id) {
		return repository.buscar(id);
	}

	public Cidade salvar(Cidade cidade) {
		Long idCidade = cidade.getId();
		Cidade cidadeAux = repository.incluir(cidade);

		if (cidadeAux == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com o codigo %d", idCidade));
		}

		return repository.incluir(cidadeAux);
	}
	
	public Cidade alterar(Cidade cidade, Long id) {
		Cidade cidadeAux = buscarPorId(id);
		
		BeanUtils.copyProperties(cidade, cidadeAux, "id");
		
		return repository.incluir(cidadeAux);
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
