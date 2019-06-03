package com.tdd.exemplo.tddspringboot.repository.helper;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;

import java.util.List;

public interface IPessoaRepositoryQueries {

    List<Pessoa> filtrar(PessoaFiltro filtro);
}
