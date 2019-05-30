package com.tdd.exemplo.tddspringboot.repository;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;

import java.util.Optional;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);
}
