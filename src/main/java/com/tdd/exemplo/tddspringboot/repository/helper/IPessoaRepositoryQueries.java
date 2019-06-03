package com.tdd.exemplo.tddspringboot.repository.helper;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPessoaRepositoryQueries {

    List<Pessoa> filtrar(PessoaFiltro filtro);
}
