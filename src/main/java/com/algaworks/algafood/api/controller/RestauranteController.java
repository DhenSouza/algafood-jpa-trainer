package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService service;

	@GetMapping("/listar")
	public ResponseEntity<List<Restaurante>> listarRestaurantes() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping("/buscar/{id}")
	public ResponseEntity<Restaurante> buscarRestaurantePorId(@PathVariable Long id) {
		Restaurante restaurante = service.buscarPorId(id);
		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Restaurante inserirRestaurante(@RequestBody Restaurante restaurante) {
		return service.salvar(restaurante);
	}

	@PutMapping("/{id}")
	public Restaurante updateRestaurante(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		return service.alterar(id, restaurante);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRestaurante(@PathVariable Long id) {
		service.deletar(id);
	}
	@GetMapping("/por-nome")
	public ResponseEntity<List<Restaurante>> findByNomeAndId(@RequestParam("nome") String nome, @RequestParam("cozinhaId")Long id){
		List<Restaurante> restaurantes = service.findByNomeAndIdCozinha(nome, id);
		if(restaurantes.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(restaurantes);
	}

	@GetMapping("/por-nome-taxaInicial-taxaFinal")
	public ResponseEntity<List<Restaurante>> findByNomeAndTaxaInicialAndFinal(@RequestParam("nome") String nome,
																			  @RequestParam("taxaInicial") BigDecimal taxaInicial, @RequestParam("taxaFinal") BigDecimal taxaFinal ){
		List<Restaurante> restaurantes = service.find(nome, taxaInicial, taxaFinal);
		if(restaurantes.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(restaurantes);
	}

	@GetMapping("/com-frete-gratis")
	public ResponseEntity<List<Restaurante>> restauranteComFreteGratis(String nome){

		List<Restaurante> restaurantes = service.listarComEspecificação(nome);

		if(restaurantes.isEmpty()){
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(restaurantes);
	}
	@GetMapping("/primeiro")
	public ResponseEntity<Optional<Restaurante>> buscarPrimeiroRestauranteLista(){
		return ResponseEntity.ok(service.buscarOPrimeiroRestaurante());
	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
										@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = service.buscarPorId(restauranteId);

		this.merge(campos, restauranteAtual);

		return this.updateRestaurante(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
