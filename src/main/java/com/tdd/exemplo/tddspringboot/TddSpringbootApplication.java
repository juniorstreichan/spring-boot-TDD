package com.tdd.exemplo.tddspringboot;

import com.tdd.exemplo.tddspringboot.repository.helper.IPessoaRepositoryQueries;
import com.tdd.exemplo.tddspringboot.repository.helper.PessoaRepositoryQueries;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TddSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TddSpringbootApplication.class, args);
	}

	@Bean
	public IPessoaRepositoryQueries pessoaRepositoryQueries(){
		return new PessoaRepositoryQueries();
	}
}
