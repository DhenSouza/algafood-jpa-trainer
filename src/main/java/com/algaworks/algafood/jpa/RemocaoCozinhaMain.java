package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepositoryImpl;

public class RemocaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CozinhaRepositoryImpl cadastroCozinha = applicationContext.getBean(CozinhaRepositoryImpl.class);
		
		Cozinha cozinha5 = cadastroCozinha.buscarPorId(5L);
		Cozinha cozinha6 = cadastroCozinha.buscarPorId(6L);
		
		System.out.println(cozinha5.getNome());
		System.out.println(cozinha6.getNome());
		
		cadastroCozinha.deletar(cozinha5);
		cadastroCozinha.deletar(cozinha6);
		
		
	}

}
