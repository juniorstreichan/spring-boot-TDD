package com.tdd.exemplo.tddspringboot.repository;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.helper.IPessoaRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByCpf(String cpf);

    /*GERA ESSE SCRIPT
    select pessoa0_.codigo as codigo1_1_, pessoa0_.cpf as cpf2_1_, pessoa0_.nome as nome3_1_
    from pessoa pessoa0_
    inner join telefone telefones1_ on pessoa0_.codigo=telefones1_.codigo_pessoa
    where telefones1_.ddd=? and telefones1_.numero=?
    */
    @Query("SELECT bean FROM Pessoa bean JOIN bean.telefones tels WHERE tels.ddd = :ddd AND tels.numero = :numero")
    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);

    /*GERA ESSE SCRIPT
    select pessoa0_.codigo as codigo1_1_, pessoa0_.cpf as cpf2_1_, pessoa0_.nome as nome3_1_
    from pessoa pessoa0_
    left outer join telefone telefones1_ on pessoa0_.codigo=telefones1_.codigo_pessoa
    where telefones1_.ddd=? and telefones1_.numero=?
    */
//    Optional<Pessoa> findByTelefonesDddAndTelefonesNumero(String ddd, String numero);

    Collection<Pessoa> findByNomeLike(String name);
}
