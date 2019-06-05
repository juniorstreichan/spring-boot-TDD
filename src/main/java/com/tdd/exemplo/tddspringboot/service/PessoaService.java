package com.tdd.exemplo.tddspringboot.service;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.repository.PessoaRepository;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import com.tdd.exemplo.tddspringboot.repository.helper.IPessoaRepositoryQueries;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueTelephoneException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class PessoaService implements IPessoaService {
    private final PessoaRepository pessoaRepository;

    private final IPessoaRepositoryQueries queries;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, IPessoaRepositoryQueries queries) {
        this.pessoaRepository = pessoaRepository;
        this.queries = queries;
    }

    @Transactional
    @Override
    public Pessoa salvar(final Pessoa pessoa) throws UniqueCpfException, UniqueTelephoneException {
        Optional<Pessoa> pessoaCpfOptional = pessoaRepository.findByCpf(pessoa.getCpf());

        if (pessoaCpfOptional.isPresent())
            throw new UniqueCpfException("Já existe pessoa cadastrada com o CPF '" + pessoa.getCpf() + "'");

        Optional<Pessoa> pessoaTelephoneOptional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(
                pessoa.getTelefone(0).getDdd(),
                pessoa.getTelefone(0).getNumero()
        );

        if (pessoaTelephoneOptional.isPresent())
            throw new UniqueTelephoneException("Este telefone já está cadastrado");

        return pessoaRepository.save(pessoa);
    }

    @Transactional(readOnly = true)
    @Override
    public Pessoa buscarPorTelefone(final Telefone telefone) throws PessoaNotFoundException {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return pessoaOptional.orElseThrow(() -> new PessoaNotFoundException(
                        "Não existe pessoa com o telefone (" + telefone.getDdd() + ") " + telefone.getNumero()
                )
        );
    }

    @Override
    public Collection<Pessoa> filtrar(PessoaFiltro filtro) {
        return queries.filtrar(filtro);
    }
}
