package parte01.model.repository;

import parte01.model.Console;

import java.util.List;

public interface ConsoleRepository {
    public List<Console> buscarInformacoesJogoPorNome(String nomeConsole);
    public Console verificarDisponibilidadeConsoleAcessorio(Long idConsole, Long idAcessorio);
}
