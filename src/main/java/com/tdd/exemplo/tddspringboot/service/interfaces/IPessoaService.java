package com.tdd.exemplo.tddspringboot.service.interfaces;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;

public interface IPessoaService {
    Pessoa salvar(Pessoa pessoa) throws UniqueCpfException;
}
