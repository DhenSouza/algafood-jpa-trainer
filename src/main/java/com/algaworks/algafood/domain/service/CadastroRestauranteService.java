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

	public static final String RESTAURANTE_SEM_CADASTRO = "nao existe um cadastro de restaurante com codigo %d ";
	public static final String COZINHA_EM_USO = "Cozinha de código %d ao pode ser removida, pois, esta e uso";
	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private RestauranteRepositoryImpl restauranteImpl;

	@Autowired
	private CozinhaRepository cRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	public List<Restaurante> listar() {
		return repository.findAll();
	}

	/* Metodo para a listagem dos restaurantes espeficicando utilizando o nome*/
	public List<Restaurante> listarComEspecificação(String nomeRestaurante){ return restauranteImpl.findComFreteGratis(nomeRestaurante); }

	public Restaurante buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(RESTAURANTE_SEM_CADASTRO, id)));
	}

	public Restaurante salvar(Restaurante restaurante) {
		Long idCozinha = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaService.buscarCozinhaPorId(idCozinha);

		restaurante.setCozinha(cozinha);
		return repository.save(restaurante);
	}

	public Restaurante alterar(Long id, Restaurante restaurante) {
		Restaurante dadosRestaurante = this.buscarPorId(id);
		Cozinha cozinha = restaurante.getCozinha();

		if(cozinha.getId() != null){
			cozinha = this.cozinhaService.buscarCozinhaPorId(restaurante.getCozinha().getId());

			if(cozinha != null){
				restaurante.setCozinha(cozinha);
			}
		}

		BeanUtils.copyProperties(restaurante, dadosRestaurante, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

		return repository.save(dadosRestaurante);
	}

	public void deletar(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format(RESTAURANTE_SEM_CADASTRO, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(COZINHA_EM_USO, id));
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
