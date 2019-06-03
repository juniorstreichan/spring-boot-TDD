package com.tdd.exemplo.tddspringboot.repository.filtros;

public class PessoaFiltro {

    private String nome = "";
    private String cpf = "";
    private String ddd = "";
    private String telefone = "";

    public PessoaFiltro() {
    }

    public PessoaFiltro(String nome, String cpf, String ddd, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.ddd = ddd;
        this.telefone = telefone;
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

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isValid() {
        return !nome.equals("") || !cpf.equals("") || !ddd.equals("") || !telefone.equals("");
    }
}
