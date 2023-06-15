package model.repository;

import model.UtilizacaoDoConsolePeloCliente;
import java.util.List;

public interface UtilizacaoDoConsolePeloClienteRepository {
    public void salvar(UtilizacaoDoConsolePeloCliente utilizacaoDoConsolePeloCliente);
    public List<UtilizacaoDoConsolePeloCliente> obterUtilizacoesConsolesRealizadas();
    public List<UtilizacaoDoConsolePeloCliente> obterLocacoesRealizadasConsoles();
}
