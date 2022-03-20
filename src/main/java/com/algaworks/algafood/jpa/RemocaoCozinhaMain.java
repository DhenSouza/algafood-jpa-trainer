package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class RemocaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinha5 = cadastroCozinha.buscar(5L);
		Cozinha cozinha6 = cadastroCozinha.buscar(6L);
		
		System.out.println(cozinha5.getNome());
		System.out.println(cozinha6.getNome());
		
		cadastroCozinha.removerCozinha(cozinha5);
		cadastroCozinha.removerCozinha(cozinha6);
		
		
	}

}
