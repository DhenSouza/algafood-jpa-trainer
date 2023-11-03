package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadesService service;

	@GetMapping(path = "/listar")
	public ResponseEntity<List<Cidade>> listarCidades() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping(path = "/{id}")
	public Cidade buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PostMapping
	public ResponseEntity<Cidade> adicionarCidade(@RequestBody Cidade cidade) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(cidade));
			}catch (EstadoNaoEncontradaException e){
				throw new NegocioException(e.getMessage(), e);
			}
		}

		@PutMapping("{id}")
		public Cidade alterarCidade(@RequestBody Cidade cidade, @PathVariable Long id) {
			try {
				return service.alterar(cidade, id);
			}catch (EstadoNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
		}

		@DeleteMapping("{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deletarCidade(@PathVariable Long id) {
			service.delete(id);
		}

}
