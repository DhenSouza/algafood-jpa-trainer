package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String msg) {
		super(msg);
	}

	public EstadoNaoEncontradaException(Long estadoId){
		this(String.format("Nao existe um cadastro de estado com codigo %d", estadoId));
	}

}