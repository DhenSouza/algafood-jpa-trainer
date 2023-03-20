package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;

	// Caso precise não ignorar cozinha é necessario o uso da anotação abaixo!
	//@JsonIgnoreProperties("hibernateLazyInitializer")
	//@JsonIgnore
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@JsonIgnore
	@Embedded
	private Endereco endereco;
/* Codigo esta comentado pois, por conta de nao estar intalado o BD na maquina da erro ao criar as datas*/
	@CreationTimestamp
	@Column(name = "data_cadastro", columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

	/* Solução para usar formasPagamento sem problema n+1 aula 6.14 AW */
	//@JsonIgnore
	@ManyToMany  //(fetch = FetchType.EAGER): Muito perigoso utilizar pois fara um select para cada restaurante
	@JoinTable(name="restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name="restaurante_id"),
			inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

}