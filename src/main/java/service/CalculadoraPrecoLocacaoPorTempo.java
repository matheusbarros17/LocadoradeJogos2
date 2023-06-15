package service;

import model.repository.CalculadoraPrecoLocacao;
import model.JogoPlataforma;

import java.math.BigDecimal;

public class CalculadoraPrecoLocacaoPorTempo implements CalculadoraPrecoLocacao {

    public BigDecimal calcularPrecoLocacao(JogoPlataforma jogoPlataforma, int dias) {

        BigDecimal precoDiario = jogoPlataforma.getPrecoDiario();
        BigDecimal precoTotal = precoDiario.multiply(BigDecimal.valueOf(dias));

        return precoTotal;
    }
}
