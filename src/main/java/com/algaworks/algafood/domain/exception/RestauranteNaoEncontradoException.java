package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String msg) {
		super(msg);
	}

	public RestauranteNaoEncontradoException(Long restauranteId){
		this(String.format("nao existe um cadastro de restaurante com codigo %d", restauranteId));
	}

}