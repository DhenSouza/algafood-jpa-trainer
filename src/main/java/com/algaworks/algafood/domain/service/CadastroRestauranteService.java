package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositoryImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private RestauranteRepositoryImpl restauranteImpl;
	
	@Autowired
	private CozinhaRepository cRepository;

	public List<Restaurante> listar() {
		return repository.findAll();
	}

	/* Metodo para a listagem dos restaurantes espeficicando utilizando o nome*/
	public List<Restaurante> listarComEspecificação(String nomeRestaurante){ return restauranteImpl.findComFreteGratis(nomeRestaurante); }

	public Restaurante buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	public Restaurante salvar(Restaurante restaurante) {
		Long idcozinha = restaurante.getCozinha().getId();
		Cozinha cozinha = cRepository.findById(idcozinha).orElseThrow(() -> 
		new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o codigo %d", idcozinha)));
		
		restaurante.setCozinha(cozinha);
		return repository.save(restaurante);
	}

	public Restaurante alterar(Long id, Restaurante restaurante) {
		Restaurante aux = repository.findById(id).orElseThrow(() ->
			new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o codigo %d", restaurante.getCozinha().getId())));

		BeanUtils.copyProperties(restaurante, aux, "id");

		return repository.save(aux);
	}

	public void deletar(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("nao existe um cadastro de restaurante com codigo %d ", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d ao pode ser removida, pois, esta e uso", id));
		}
	}

	public List<Restaurante> findByNomeAndIdCozinha(String nome, Long id){
		return repository.consultaPorNome(nome, id);
	}

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		return repository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	public Optional<Restaurante> buscarOPrimeiroRestaurante(){
		return repository.buscarPrimeiro();
	}
}
