package com.tdd.exemplo.tddspringboot.resource;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.domain.Telefone;
import com.tdd.exemplo.tddspringboot.service.exception.PessoaNotFoundException;
import com.tdd.exemplo.tddspringboot.service.interfaces.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
