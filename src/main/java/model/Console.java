package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Console {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal precoPorHoraBigDecimal;

    @OneToMany(mappedBy = "console")
    private List<UtilizacaoDoConsolePeloCliente> utilizacoes = new ArrayList<>();

    @OneToMany(mappedBy = "console")
    private List<Acessorio> acessorios = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoPorHoraBigDecimal() {
        return precoPorHoraBigDecimal;
    }

    public void setPrecoPorHoraBigDecimal(BigDecimal precoPorHoraBigDecimal) {
        this.precoPorHoraBigDecimal = precoPorHoraBigDecimal;
    }

    public List<UtilizacaoDoConsolePeloCliente> getUtilizacoes() {
        return utilizacoes;
    }

    public void setUtilizacoes(List<UtilizacaoDoConsolePeloCliente> utilizacoes) {
        this.utilizacoes = utilizacoes;
    }

    public List<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(List<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }
}
