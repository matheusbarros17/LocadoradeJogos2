package model;

import model.repository.CalculadoraValorLocacao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class UtilizacaoDoConsolePeloCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Console console;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private BigDecimal valorLocacaoConsole;

    @Transient
    private CalculadoraValorLocacao calculadoraValorLocacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public void setCalculadoraValorLocacao(CalculadoraValorLocacao calculadoraValorLocacao) {
        this.calculadoraValorLocacao = calculadoraValorLocacao;
    }

    public BigDecimal getValorLocacaoConsole() {
        return valorLocacaoConsole;
    }

    public void setValorLocacaoConsole(BigDecimal valorLocacaoConsole) {
        this.valorLocacaoConsole = valorLocacaoConsole;
    }

    public BigDecimal calcularValorLocacao() {
        return calculadoraValorLocacao.calcularValorLocacao(console, dataInicio, dataFim);
    }
}
