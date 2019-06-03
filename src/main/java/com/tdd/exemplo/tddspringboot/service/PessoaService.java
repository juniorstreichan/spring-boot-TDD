package com.tdd.exemplo.tddspringboot.service;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.repository.PessoaRepository;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueTelephoneException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService implements IPessoaService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PessoaRepository pessoaRepository;


    public PessoaService(PessoaRepository pessoaRepository) {

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

    public List<Pessoa> filtrar(PessoaFiltro filtro) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT bean FROM Pessoa bean WHERE 1=1 ");
//        sb.append("");
        return manager.createQuery(sb.toString(), Pessoa.class).getResultList();

    }


}
