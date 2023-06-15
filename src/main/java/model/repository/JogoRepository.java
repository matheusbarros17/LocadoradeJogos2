package model.repository;

import model.Jogo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface JogoRepository {
    public void salvar(Jogo jogo);
    public List<Jogo> listarJogos();
    public List<Jogo> buscarInformacoesJogoPorNome(String nomeJogo, LocalDate dataReserva);
    public Map<String, Jogo> buscarInformacoesJogoPorNome2(String nomeJogo);
}
