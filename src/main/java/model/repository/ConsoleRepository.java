package model.repository;

import model.Console;

import java.util.List;

public interface ConsoleRepository {
    public List<Console> buscarInformacoesJogoPorNome(String nomeConsole);
    public Console verificarDisponibilidadeConsoleAcessorio(Long idConsole, Long idAcessorio);
}
