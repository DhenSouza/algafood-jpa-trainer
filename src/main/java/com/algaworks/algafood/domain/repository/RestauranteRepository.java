package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    /* O segundo Join so resolve o problema do n+1 de toMany, usando o left join fech pois, se fosse so o join o modo seria EASY e nao EAGER (hibernate deixa automatico)*/
    // Caso queira a solução definitiva do n+1, somente personalizando a query com JPQL caso use o JPA
    @Query("FROM Restaurante r JOIN FETCH r.cozinha LEFT JOIN FETCH r.formasPagamento")
    List<Restaurante> findAll();

	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id ")
    List<Restaurante> consultaPorNome(String nome, @Param("id") Long cozinha);

}
