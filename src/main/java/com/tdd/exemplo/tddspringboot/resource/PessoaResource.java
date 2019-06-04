package com.tdd.exemplo.tddspringboot.resource;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private IPessoaService pessoaService;

    @GetMapping("/{ddd}/{numero}")
    public ResponseEntity<Pessoa> findByTelephone(
            @PathVariable("ddd") String ddd,
            @PathVariable("numero") String numero
    ) throws PessoaNotFoundException {
        Pessoa pessoa = pessoaService.buscarPorTelefone(new Telefone(ddd, numero));
        return ResponseEntity.ok().body(pessoa);
    }

    @ExceptionHandler({PessoaNotFoundException.class})
    public ResponseEntity<Erro> handleError(PessoaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(e.getMessage()));
    }

    class Erro {
        private final String message;

        public Erro(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
