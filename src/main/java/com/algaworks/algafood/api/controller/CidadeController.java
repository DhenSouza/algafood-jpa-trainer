package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.exception.NegocioException;
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
	public Cidade buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PostMapping
	public ResponseEntity<Cidade> incluir(@RequestBody Cidade cidade) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(cidade));
			}catch (EntidadeNaoEncontradaException e){
				throw new NegocioException(e.getMessage());
			}
		}

		@PutMapping("{id}")
		public Cidade alterar(@RequestBody Cidade cidade, @PathVariable Long id) {
			try {
				return service.alterar(cidade, id);
			}catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage());
			}
		}

		@DeleteMapping("{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deletar(@PathVariable Long id) {
			service.delete(id);
		}

}
