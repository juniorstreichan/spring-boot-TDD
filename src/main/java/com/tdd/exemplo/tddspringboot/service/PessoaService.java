package com.tdd.exemplo.tddspringboot.service;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.PessoaRepository;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;

import java.util.Optional;

public class PessoaService implements IPessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UniqueCpfException {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(pessoa.getCpf());
        if (pessoaOptional.isPresent()){
            throw  new UniqueCpfException();
        }
        return pessoaRepository.save(pessoa);
    }
}
