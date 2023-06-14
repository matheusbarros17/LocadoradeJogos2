package model.repository;

import model.Console;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CalculadoraValorLocacao {
    public BigDecimal calcularValorLocacao(Console console, LocalDateTime dataInicio, LocalDateTime dataFim);
}
