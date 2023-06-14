package controller;

import model.JogoPlataforma;
import model.repository.CalculadoraPrecoLocacao;

import java.math.BigDecimal;

public class CalculadoraPrecoLocacaoPorTempo implements CalculadoraPrecoLocacao {

    public BigDecimal calcularPrecoLocacao(JogoPlataforma jogoPlataforma, int dias) {

        BigDecimal precoDiario = jogoPlataforma.getPrecoDiario();
        BigDecimal precoTotal = precoDiario.multiply(BigDecimal.valueOf(dias));

        return precoTotal;
    }
}
