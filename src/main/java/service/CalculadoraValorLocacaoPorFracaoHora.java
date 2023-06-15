package service;

import model.repository.CalculadoraValorLocacao;
import model.Console;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class CalculadoraValorLocacaoPorFracaoHora implements CalculadoraValorLocacao {

    @Override
    public BigDecimal calcularValorLocacao(Console console, LocalDateTime dataInicio, LocalDateTime dataTermino) {
        long horasDeUso = Duration.between(dataInicio, dataTermino).toHours();
        BigDecimal valorPorHora = console.getPrecoPorHora();

        return valorPorHora.multiply(BigDecimal.valueOf(horasDeUso));
    }
}
