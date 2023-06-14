package parte01.model.repository;

import parte01.model.UtilizacaoDoConsolePeloCliente;
import java.util.List;

public interface UtilizacaoDoConsolePeloClienteRepository {
    public void salvar(UtilizacaoDoConsolePeloCliente utilizacaoDoConsolePeloCliente);
    public List<UtilizacaoDoConsolePeloCliente> obterUtilizacoesConsolesRealizadas();
    public List<UtilizacaoDoConsolePeloCliente> obterLocacoesRealizadasConsoles();
}
