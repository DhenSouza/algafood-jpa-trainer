package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadesService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadesService service;

	@GetMapping(path = "/listar")
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
		Optional<Cidade> cidade = service.buscarPorId(id);
		if (cidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(service.buscarPorId(id).get());
	}

	@PostMapping
	public ResponseEntity<Cidade> incluir(@RequestBody Cidade cidade) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(cidade));
	}

	@PutMapping("{id}")
	public ResponseEntity<Cidade> alterar(@RequestBody Cidade cidade, @PathVariable Long id) {
		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.alterar(cidade, id));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		service.delete(id);
	}

}
