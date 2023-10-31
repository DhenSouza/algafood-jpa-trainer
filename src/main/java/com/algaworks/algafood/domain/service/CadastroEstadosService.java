package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadosService {

	public static final String ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
	public static final String ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	@Autowired
	private EstadoRepository repository;

	public List<Estado> listar() {
		return repository.findAll();
	}

	public Estado buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradaException(String.format(ESTADO_NAO_ENCONTRADO, id)));
	}

	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}
	
	public Estado alterar(Estado estado, Long id) {
		Estado estadoAux = this.buscarPorId(id);
		
		BeanUtils.copyProperties(estado, estadoAux, "id");
		
		return repository.save(estadoAux);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(
					String.format(ESTADO_NAO_ENCONTRADO, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(ESTADO_EM_USO, id));
		}
	}
}
