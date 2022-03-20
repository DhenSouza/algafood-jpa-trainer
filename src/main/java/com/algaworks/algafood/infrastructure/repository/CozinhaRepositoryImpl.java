package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> todos() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public Cozinha buscarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}

	@Override
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}

	@Override
	@Transactional
	public void deletar(Cozinha cozinha) {
		cozinha = buscarPorId(cozinha.getId());
		 manager.remove(cozinha);
	}


}
