package parte01.model.repository;

import parte01.model.Console;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CalculadoraValorLocacao {
    public BigDecimal calcularValorLocacao(Console console, LocalDateTime dataInicio, LocalDateTime dataFim);
}
