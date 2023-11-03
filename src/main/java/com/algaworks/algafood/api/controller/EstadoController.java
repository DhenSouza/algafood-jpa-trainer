package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService service;

	@GetMapping(path = "/listar")
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping(path = "/{id}")
	public Estado buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PostMapping
	public ResponseEntity<Estado> incluir(@RequestBody Estado estado) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(estado));
	}

	@PutMapping("{id}")
	public ResponseEntity<Estado> alterar(@RequestBody Estado estado, @PathVariable Long id) {
		if (estado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.alterar(estado, id));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCidade(@PathVariable Long id) {
		service.delete(id);
	}

}
