package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

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
	public ResponseEntity<?> inserirRestaurante(@RequestBody Restaurante restaurante) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(restaurante));
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurante> updateRestaurante(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		if (restaurante == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.alterar(id, restaurante));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> deleteRestaurante(@PathVariable Long id) {
		try {
			service.deletar(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (EntidadeEmUsoException ex) {

			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException ex) {

			return ResponseEntity.notFound().build();
		}
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
}
