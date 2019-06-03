package com.tdd.exemplo.tddspringboot.repository.helper;

import com.tdd.exemplo.tddspringboot.domain.Pessoa;
import com.tdd.exemplo.tddspringboot.repository.filtros.PessoaFiltro;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PessoaRepositoryQueries implements IPessoaRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Pessoa> filtrar(PessoaFiltro filtro) {
        if (filtro.isValid()) {

            final StringBuilder sb = new StringBuilder();
            final Map<String, Object> params = new HashMap<>();

            sb.append(" SELECT bean FROM Pessoa bean JOIN bean.telefones tels WHERE 1=1 ");
            makeQuery(filtro, sb, params);

            Query query = manager.createQuery(sb.toString(), Pessoa.class);

            insertParams(params, query);
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    private void makeQuery(PessoaFiltro filtro, StringBuilder sb, Map<String, Object> params) {
        if (StringUtils.hasText(filtro.getNome())) {
            sb.append(" AND bean.nome LIKE :nome ");
            params.put("nome", "%" + filtro.getNome() + "%");
        }

        if (StringUtils.hasText(filtro.getCpf())) {
            sb.append(" AND bean.cpf LIKE :cpf ");
            params.put("cpf", "%" + filtro.getCpf() + "%");
        }

        if (StringUtils.hasText(filtro.getDdd())) {
            sb.append(" AND tels.ddd = :ddd ");
            params.put("ddd", filtro.getDdd());
        }

        if (StringUtils.hasText(filtro.getTelefone())) {
            sb.append(" AND tels.numero = :telefone ");
            params.put("telefone", filtro.getTelefone());
        }
    }

    private void insertParams(Map<String, Object> params, Query query) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
    }


}
