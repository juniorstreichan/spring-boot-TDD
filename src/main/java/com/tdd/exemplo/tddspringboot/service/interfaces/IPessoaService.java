package com.tdd.exemplo.tddspringboot.service.interfaces;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueTelephoneException;

public interface IPessoaService {
    Pessoa salvar(Pessoa pessoa) throws UniqueCpfException, UniqueTelephoneException;

    Pessoa buscarPorTelefone(Telefone telefone) throws PessoaNotFoundException;
}
