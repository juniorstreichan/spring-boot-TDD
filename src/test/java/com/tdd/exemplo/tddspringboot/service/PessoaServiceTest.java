package com.tdd.exemplo.tddspringboot.service;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.PessoaRepository;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    @MockBean
    private PessoaRepository pessoaRepository;

    private IPessoaService sut;

    private Pessoa pessoa;

    private final String CPF = "05581041180";
    private final String NOME = "teste nelson";

    @Before
    public void setUp() throws Exception {
        sut = new PessoaService(pessoaRepository);

        pessoa = new Pessoa(
                NOME,
                CPF
        );

        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
    }

    @Test
    public void deveSalvarPessoaNoRepositorio() throws Exception {

        sut.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = UniqueCpfException.class)
    public void naoDeveSalvarDuasPessoasComMesmoCPF() throws Exception {
        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
        sut.salvar(pessoa);
    }
}
