package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.FormaDePagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository repository;

    public List<FormaPagamento> findAll (){
        return repository.findAll();
    }

    public Optional<FormaPagamento> findById(Long id){
        return repository.findById(id);
    }

    public FormaPagamento save(FormaPagamento pgto) {
        return repository.save(pgto);
    }

    public FormaPagamento changeFormaPagamento(Long id, FormaPagamento pgto){
        Optional<FormaPagamento> pgtoAlter = this.findById(id);

        BeanUtils.copyProperties(pgto, pgtoAlter.get(), "id");

        return repository.save(pgtoAlter.get());
    }

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new FormaDePagamentoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("FormaPagamento de código %d ao pode ser removida, pois, esta e uso", id));
        }
    }
}
