package parte01.model.repository;

import parte01.model.JogoPlataforma;
import parte01.model.Locacao;
import java.time.LocalDate;

public interface LocacaoRepository {
    public void salvar (Locacao locacao);
    public boolean isJogoLocadoEmData(JogoPlataforma jogoPlataforma, LocalDate data);

}
