package com.algaworks.algafood.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class RestauranteConsultaTodos {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		BigDecimal big = new BigDecimal(20.54);
		
		List<Restaurante> restaurantes = restauranteRepository.todos();
		
		restaurantes.forEach(restaurante -> System.out.println("nomes: " + restaurante.getNome() + " Frete: " + restaurante.getTaxaFrete()
		+ " Cozinha: " + restaurante.getCozinha().getNome()));
	}

}
