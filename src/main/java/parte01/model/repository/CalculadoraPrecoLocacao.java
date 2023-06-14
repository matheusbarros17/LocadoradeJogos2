package parte01.model.repository;

import parte01.model.JogoPlataforma;

import java.math.BigDecimal;

public interface CalculadoraPrecoLocacao {
    public BigDecimal calcularPrecoLocacao(JogoPlataforma jogoPlataforma, int dias);
}
