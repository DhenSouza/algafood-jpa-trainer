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
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadesService {

	public static final String CADASTRO_CIDADE_NAO_ENCONTRADO = "nao existe um cadastro de estado com codigo %d ";
	public static final String ESTADO_EM_USO = "Estado de código %d ao pode ser removida, pois, esta e uso";
	@Autowired
	private CidadeRepository repository;

	public List<Cidade> listar() {
		return repository.findAll();
	}

	public Cidade buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(CADASTRO_CIDADE_NAO_ENCONTRADO, id)));
	}

	public Cidade salvar(Cidade cidade) {
		Long idCidade = cidade.getId();
		Cidade cidadeAux = repository.save(cidade);

		if (cidadeAux == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com o codigo %d", idCidade));
		}

		return repository.save(cidadeAux);
	}
	
	public Cidade alterar(Cidade cidade, Long id) {
		Cidade cidadeAux = this.buscarPorId(id);
		
		BeanUtils.copyProperties(cidade, cidadeAux, "id");
		
		return repository.save(cidadeAux);
	}
	
	public void delete(Long id) {
		try{
		 repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(CADASTRO_CIDADE_NAO_ENCONTRADO, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(ESTADO_EM_USO, id));
		}
	}
}
