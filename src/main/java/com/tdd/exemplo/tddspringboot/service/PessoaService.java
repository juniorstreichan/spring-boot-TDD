package com.tdd.exemplo.tddspringboot.service;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.repository.PessoaRepository;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueTelephoneException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;

import java.util.Optional;

public class PessoaService implements IPessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(final Pessoa pessoa) throws UniqueCpfException, UniqueTelephoneException {
        Optional<Pessoa> pessoaCpfOptional = pessoaRepository.findByCpf(pessoa.getCpf());

        if (pessoaCpfOptional.isPresent()) throw new UniqueCpfException();

        Optional<Pessoa> pessoaTelephoneOptional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(
                pessoa.getTelefone(0).getDdd(),
                pessoa.getTelefone(0).getNumero()
        );

        if (pessoaTelephoneOptional.isPresent()) throw new UniqueTelephoneException();

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(final Telefone telefone) throws PessoaNotFoundException {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return pessoaOptional.orElseThrow(() -> new PessoaNotFoundException());
    }
}
