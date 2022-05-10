package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadosService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadosService service;

	@GetMapping(path = "/listar")
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping(path = "/buscar/{id}")
	public Estado buscarPorId(@PathVariable Long id) {
		return null;
	}
	
}
