package service;

import model.repository.CalculadoraValorLocacao;
import model.Console;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class CalculadoraValorLocacaoPorHora implements CalculadoraValorLocacao {

    @Override
    public BigDecimal calcularValorLocacao(Console console, LocalDateTime dataInicio, LocalDateTime dataTermino) {

        long fracoesDeHoraDeUso = Duration.between(dataInicio, dataTermino).toMinutes() / 15;
        BigDecimal valorPorHora = console.getPrecoPorHora();
        BigDecimal valorPorFracaoHora = valorPorHora.divide(BigDecimal.valueOf(4));

        return valorPorFracaoHora.multiply(BigDecimal.valueOf(fracoesDeHoraDeUso));
    }

}
