package com.tdd.exemplo.tddspringboot.resource;

import com.tdd.exemplo.tddspringboot.TddSpringbootApplicationTests;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class PessoaResourceTest extends TddSpringbootApplicationTests {

    @Test
    public void deveProcurarPessoaPorDddETelefone() {
        RestAssured.given()
                .pathParam("ddd", "86")
                .pathParam("numero", "35006330")
                .get("/pessoas/{ddd}/{numero}")
                .then().log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo("Cauê"),"cpf",Matchers.equalTo("38767897100"));
    }
}
