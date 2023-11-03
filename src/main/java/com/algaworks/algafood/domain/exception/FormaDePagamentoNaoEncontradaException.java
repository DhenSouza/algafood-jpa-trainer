package com.algaworks.algafood.domain.exception;

public class FormaDePagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public FormaDePagamentoNaoEncontradaException(String msg) {
		super(msg);
	}

	public FormaDePagamentoNaoEncontradaException(Long pagamentoId){
		this(String.format("nao existe um cadastro de FormaPagamento com codigo %d", pagamentoId));
	}

}