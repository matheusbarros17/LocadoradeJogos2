package model.repository;

import model.Jogo;

import java.util.List;

public interface JogoRepository {
    public void salvar(Jogo jogo);
    public List<Jogo> listarJogos();
    public List<Jogo> buscarInformacoesJogoPorNome(String nomeJogo);
}
