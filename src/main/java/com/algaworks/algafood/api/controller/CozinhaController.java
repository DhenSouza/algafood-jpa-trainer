package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public Cozinha buscarPorId(@PathVariable Long id) {
		return cozinhaService.buscarCozinhaPorId(id);
	}

	@PostMapping
	public ResponseEntity<Cozinha> insertCozinha(@RequestBody Cozinha cozinha) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaService.salvar(cozinha));
	}

	@PutMapping("/{id}")
	public Cozinha updateCozinha(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		return cozinhaService.alterarCozinha(id, cozinha);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCozinha(@PathVariable Long id) {
		cozinhaService.deletarCozinha(id);
	}

	@GetMapping("/por-nome")
	public List<Cozinha> findByName(@RequestParam("nome") String nome){
		return cozinhaService.findByName(nome);
	}
}