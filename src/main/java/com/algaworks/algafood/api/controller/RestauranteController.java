package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		if(restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
		return ResponseEntity.notFound().build();
	}
	
	
}