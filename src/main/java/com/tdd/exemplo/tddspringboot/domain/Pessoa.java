package com.tdd.exemplo.tddspringboot.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "pessoa",
        uniqueConstraints = @UniqueConstraint(columnNames = "cpf", name = "pessoa_cpf_uk")
)
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(length = 80, nullable = false)
    private String nome;

    @Column(length = 11, nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa")
    private List<Telefone> telefones = new ArrayList<>();


    public Pessoa() {
    }

    public Pessoa(String nome, String cpf) {

        this.nome = nome;
        this.cpf = cpf;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public boolean addEndereco(Endereco endereco){
        return this.enderecos.add(endereco);
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public Telefone getTelefone(final int index) {
        return this.telefones.get(index);
    }

    public boolean addTelefone(Telefone telefone) {
        return this.telefones.add(telefone);
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pessoa pessoa = (Pessoa) o;

        return getCodigo() != null ? getCodigo().equals(pessoa.getCodigo()) : pessoa.getCodigo() == null;
    }

    @Override
    public int hashCode() {
        return getCodigo() != null ? getCodigo().hashCode() : 0;
    }
}
