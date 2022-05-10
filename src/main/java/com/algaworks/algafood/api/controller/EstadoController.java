package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping(path = "/{id}")
	public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
		Estado estado = service.buscarPorId(id);
		if (estado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<Estado> incluir(@RequestBody Estado estado) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(estado));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Estado> alterar(@RequestBody Estado estado, @PathVariable Long id) {
		if(estado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.alterar(estado, id));
	}

}
