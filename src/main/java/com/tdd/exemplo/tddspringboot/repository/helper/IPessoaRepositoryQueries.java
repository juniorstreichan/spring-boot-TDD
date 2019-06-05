package com.tdd.exemplo.tddspringboot.repository.helper;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IPessoaRepositoryQueries {

    Collection<Pessoa> filtrar(PessoaFiltro filtro);
}
