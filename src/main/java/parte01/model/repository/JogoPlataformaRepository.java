package parte01.model.repository;

import parte01.model.JogoPlataforma;

import java.util.List;

public interface JogoPlataformaRepository {
    public void salvar(JogoPlataforma jogoPlataforma);
    public JogoPlataforma obterJogoPlataforma(String jogo, String plataforma);
    public List<JogoPlataforma> obterLocacoesRealizadasJogos();
}
