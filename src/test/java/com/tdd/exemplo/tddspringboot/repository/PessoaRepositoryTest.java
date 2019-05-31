package com.tdd.exemplo.tddspringboot.repository;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository sut;


    @Test
    public void deveBuscarPessoaPorCpf() {
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
}
