package com.tdd.exemplo.tddspringboot.repository;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import com.tdd.exemplo.tddspringboot.repository.helper.IPessoaRepositoryQueries;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository sut;

    @Autowired
    private IPessoaRepositoryQueries pessoaRepositoryQueries;


    @Test
    public void deveEncontrarPessoaPorCpf() {
        Optional<Pessoa> optional = sut.findByCpf("72788740417");

        Assertions.assertThat(optional.isPresent()).isTrue();

        Pessoa pessoa = optional.get();

        Assertions.assertThat(pessoa.getNome()).isEqualTo("Thiago");
        Assertions.assertThat(pessoa.getCpf()).isEqualTo("72788740417");
    }

    @Test
    public void naoDeveEncontrarPessoaSemCpf() {
        Optional<Pessoa> optional = sut.findByCpf("72788740400");
        Assertions.assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void deveEncontrarPessoaPeloDddENumeroDeTelefone() {
        Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("41", "999570146");

        Assertions.assertThat(optional.isPresent()).isTrue();

        Pessoa pessoa = optional.get();

        Assertions.assertThat(pessoa.getNome()).isEqualTo("Iago");
        Assertions.assertThat(pessoa.getCpf()).isEqualTo("86730543540");
    }

    @Test
    public void naoDeveEncontrarPorDddETelefoneNaoCadastrado() {
        Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("99", "999999999");

        Assertions.assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void deveFiltrarPessoasPorParteDoNome() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");
        Collection<Pessoa> pessoas = pessoaRepositoryQueries.filtrar(filtro);

        Assertions.assertThat(pessoas.size()).isEqualTo(3);
    }

    @Test
    public void deveFiltrarPessoasPorParteDoCpf() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setCpf("78");
        Collection<Pessoa> pessoas = pessoaRepositoryQueries.filtrar(filtro);

        Assertions.assertThat(pessoas.size()).isEqualTo(3);
    }
}
