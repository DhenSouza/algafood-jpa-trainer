package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		Cidade cidade = service.buscarPorId(id);
		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(service.buscarPorId(id));
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
	public ResponseEntity<Cidade> deletar(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (EntidadeEmUsoException ex) {

			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException ex) {

			return ResponseEntity.notFound().build();
		}
	}

}
