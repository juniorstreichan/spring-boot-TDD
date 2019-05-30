package com.tdd.exemplo.tddspringboot.service;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.repository.PessoaRepository;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueTelephoneException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.assertj.core.api.Assertions;
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

    private static final String DDD = "65";
    private static final String NUMERO = "30551351";
    private static final String CPF = "05581041180";
    private static final String NOME = "teste nelson";

    @MockBean
    private PessoaRepository pessoaRepository;
    private IPessoaService sut;

    private Pessoa pessoa;
    private Telefone telefone;

    @Before
    public void setUp() throws Exception {
        sut = new PessoaService(pessoaRepository);

        pessoa = new Pessoa(
                NOME,
                CPF
        );

        telefone = new Telefone(DDD, NUMERO);
        pessoa.addTelefone(telefone);

        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
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

    @Test(expected = UniqueTelephoneException.class)
    public void naoDeveSalvarDuasPessoasComMesmoTelefone() throws Exception {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }

    @Test
    public void deveProcurarPeloTelefone() throws Exception {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaTest = sut.buscarPorTelefone(telefone);

        verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

        Assertions.assertThat(pessoaTest).isNotNull();
        Assertions.assertThat(pessoaTest.getNome()).isEqualTo(NOME);
        Assertions.assertThat(pessoaTest.getCpf()).isEqualTo(CPF);
    }

    @Test(expected = PessoaNotFoundException.class)
    public void deveRetornarErroSeNaoEncontrarComOTelefone() throws Exception {
        sut.buscarPorTelefone(telefone);
    }
}
