package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepo;

	@GetMapping(path = "/listar")
	public List<Estado> listar() {
		return estadoRepo.listar();
	}

	@GetMapping(path = "/buscar/{id}")
	public Estado buscarPorId(@PathVariable Long id) {
		return estadoRepo.buscar(id);
	}
}
