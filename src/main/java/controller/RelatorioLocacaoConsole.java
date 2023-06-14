package controller;

import model.UtilizacaoDoConsolePeloCliente;
import model.repository.RelatorioLocacao;

import java.util.List;

public class RelatorioLocacaoConsole extends RelatorioLocacao {

    private List<UtilizacaoDoConsolePeloCliente> utilizacoesConsoles;

    public RelatorioLocacaoConsole(List<UtilizacaoDoConsolePeloCliente> utilizacoes) {
        super(utilizacoes);
        this.utilizacoesConsoles = utilizacoes;
    }

    @Override
    public String gerarRelatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório de Locação de Consoles:\n");

        for (UtilizacaoDoConsolePeloCliente utilizacao : utilizacoesConsoles) {
            relatorio.append("Cliente: ").append(utilizacao.getCliente().getNome()).append("\n");
            relatorio.append("Data de Locação: ").append(utilizacao.getDataInicio()).append("\n");
            // Adicione outras informações relevantes da utilização de consoles
            relatorio.append("--------------------------------------\n");
        }

        return relatorio.toString();
    }
}