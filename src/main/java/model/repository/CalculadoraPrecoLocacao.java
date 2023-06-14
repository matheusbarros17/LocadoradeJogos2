package model.repository;

import model.JogoPlataforma;

import java.math.BigDecimal;

public interface CalculadoraPrecoLocacao {
    public BigDecimal calcularPrecoLocacao(JogoPlataforma jogoPlataforma, int dias);
}
