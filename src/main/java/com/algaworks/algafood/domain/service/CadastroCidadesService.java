package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadesService {

	public static final String CADASTRO_CIDADE_NAO_ENCONTRADO = "nao existe um cadastro da cidade com codigo %d ";
	public static final String CIDADE_EM_USO = "Cidade de código %d ao pode ser removida, pois, esta e uso";
	@Autowired
	private CidadeRepository repository;

	@Autowired
	private CadastroEstadosService cadastroEstado;

	public List<Cidade> listar() {
		return repository.findAll();
	}

	public Cidade buscarPorId(Long cidadeId) {
		return repository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(CADASTRO_CIDADE_NAO_ENCONTRADO, cidadeId)));
	}

	public Cidade salvar(Cidade cidade) {
		Long idEstado = cidade.getEstado().getId();
		Estado estado = cadastroEstado.buscarPorId(idEstado);

		cidade.setEstado(estado);

		return repository.save(cidade);
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
					String.format(CIDADE_EM_USO, id));
		}
	}
}
