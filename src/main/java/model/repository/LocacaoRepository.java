package model.repository;

import model.JogoPlataforma;
import model.Locacao;
import java.time.LocalDate;

public interface LocacaoRepository {
    public void salvar (Locacao locacao);
    public boolean isJogoLocadoEmData(JogoPlataforma jogoPlataforma, LocalDate data);

}
