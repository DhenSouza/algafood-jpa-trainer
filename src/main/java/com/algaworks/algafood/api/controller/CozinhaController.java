package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@GetMapping(path = "/listar")
	public List<Cozinha> listar() {
		return cozinhaService.listar();
	}

	@GetMapping(path = "/buscar/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
		Cozinha cozinha = cozinhaService.buscaCozinhaId(id);

		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Cozinha> insertCozinha(@RequestBody Cozinha cozinha) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaService.salvar(cozinha));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> updateCozinha(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		if (cozinha == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(cozinhaService.alterarCozinha(id, cozinha));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> deleteCoziunha(@PathVariable Long id) {
		try {

			cozinhaService.deletarCozinha(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (EntidadeEmUsoException ex) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException ex) {
			
			return ResponseEntity.notFound().build();
		}
	}
}
