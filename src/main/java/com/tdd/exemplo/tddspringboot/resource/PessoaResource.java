package com.tdd.exemplo.tddspringboot.resource;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueCpfException;
import com.tdd.exemplo.tddspringboot.service.exception.UniqueTelephoneException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private IPessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Collection<Pessoa>> filter(
            @RequestParam(name = "nome", defaultValue = "") String nome,
            @RequestParam(name = "cpf", defaultValue = "") String cpf,
            @RequestParam(name = "ddd", defaultValue = "") String ddd,
            @RequestParam(name = "telefone", defaultValue = "") String telefone
    ) throws Exception {
        PessoaFiltro filtro = new PessoaFiltro(nome, cpf, ddd, telefone);
        Collection<Pessoa> pessoas = pessoaService.filtrar(filtro);
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{ddd}/{numero}")
    public ResponseEntity<Pessoa> findByTelephone(
            @PathVariable("ddd") String ddd,
            @PathVariable("numero") String numero
    ) throws PessoaNotFoundException {
        final Pessoa pessoa = pessoaService.buscarPorTelefone(new Telefone(ddd, numero));
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> saveNew(
            @RequestBody Pessoa pessoa,
            HttpServletResponse response
    ) throws UniqueCpfException, UniqueTelephoneException {

        final Pessoa newPessoa = pessoaService.salvar(pessoa);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
                        .buildAndExpand(pessoa.getTelefone(0).getDdd(), pessoa.getTelefone(0).getNumero()).toUri()
        ).body(newPessoa);
    }


    ///////////////////////////////////////////////////////

    @ExceptionHandler({PessoaNotFoundException.class})
    public ResponseEntity<Erro> handleError(PessoaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(e.getMessage()));
    }

    @ExceptionHandler({UniqueCpfException.class})
    public ResponseEntity<Erro> handleError(UniqueCpfException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro(e.getMessage()));
    }

    @ExceptionHandler({UniqueTelephoneException.class})
    public ResponseEntity<Erro> handleError(UniqueTelephoneException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro(e.getMessage()));
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
