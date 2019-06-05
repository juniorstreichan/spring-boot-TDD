package com.tdd.exemplo.tddspringboot.resource;

import com.tdd.exemplo.tddspringboot.TddSpringbootApplicationTests;
import com.tdd.exemplo.tddspringboot.domain.Endereco;
import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class PessoaResourceTest extends TddSpringbootApplicationTests {

    @Test
    public void deveProcurarPessoaPorDddETelefone() throws Exception {
        RestAssured.given()
                .pathParam("ddd", "86")
                .pathParam("numero", "35006330")
                .get("/pessoas/{ddd}/{numero}")
                .then().log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo("Cauê"), "cpf", Matchers.equalTo("38767897100"));
    }

    @Test
    public void deveRetornarErroNaoEncontradoQuandoBuscarComTelefoneInexistente() throws Exception {
        RestAssured.given()
                .pathParam("ddd", "99")
                .pathParam("numero", "9998989898")
                .get("/pessoas/{ddd}/{numero}")
                .then().log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", Matchers.notNullValue());
    }

    @Test
    public void deveSalvarNovaPessoa() {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("Cláudio Daniel Moraes");
        pessoa.setCpf("22612078592");
        pessoa.addTelefone(new Telefone("67", "8635197671"));

        Endereco endereco = new Endereco();
        endereco.setCidade("Timon");
        endereco.setEstado("MA");
        endereco.setNumero(570);
//        endereco.setPessoa(pessoa);
        endereco.setLogradouro("Rua Dois");

        pessoa.addEndereco(endereco);

        RestAssured.given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
                .post("/pessoas")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and().statusCode(HttpStatus.CREATED.value())
                .header("Location", Matchers.notNullValue())
                .body(
                        "codigo", Matchers.notNullValue(),
                        "nome", Matchers.equalTo(pessoa.getNome()),
                        "cpf", Matchers.equalTo(pessoa.getCpf())
                );
    }

    @Test
    public void naoDeveSalvarPessoaComOMesmoCpf() {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("Cláudio Daniel Moraes");
        pessoa.setCpf("38767897100");
        pessoa.addTelefone(new Telefone("67", "8635197671"));

        Endereco endereco = new Endereco();
        endereco.setCidade("Timon");
        endereco.setEstado("MA");
        endereco.setNumero(570);
//        endereco.setPessoa(pessoa);
        endereco.setLogradouro("Rua Dois");

        pessoa.addEndereco(endereco);

        RestAssured.given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
                .post("/pessoas")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and().body("message", Matchers.notNullValue());
    }

    @Test
    public void naoDeveSalvarPessoaComOMesmoTelefone() {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("Cláudio Daniel Moraes");
        pessoa.setCpf("38767897100");
        pessoa.addTelefone(new Telefone("41", "999570146"));

        Endereco endereco = new Endereco();
        endereco.setCidade("Timon");
        endereco.setEstado("MA");
        endereco.setNumero(570);
//        endereco.setPessoa(pessoa);
        endereco.setLogradouro("Rua Dois");

        pessoa.addEndereco(endereco);

        RestAssured.given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
                .post("/pessoas")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and().body("message", Matchers.notNullValue());
    }

    @Test
    public void deveFiltrarPessoasPeloNome() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");

        RestAssured.given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .queryParam("nome", filtro.getNome())
                .get("/pessoas")
                .then()
                .log().body()
                .and().statusCode(HttpStatus.OK.value())
                .body(Matchers.notNullValue());
    }
}
