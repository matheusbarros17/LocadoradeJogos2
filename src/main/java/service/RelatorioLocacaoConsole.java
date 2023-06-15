package service;

import model.Acessorio;
import model.UtilizacaoDoConsolePeloCliente;
import model.repository.RelatorioLocacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        relatorio.append("\nRelatório de Locação de Consoles:\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (UtilizacaoDoConsolePeloCliente utilizacao : utilizacoesConsoles) {
            relatorio.append("Cliente: ").append(utilizacao.getCliente().getNome()).append("\n");

            LocalDateTime dataLocacao = utilizacao.getDataInicio();
            LocalDateTime dataTermino = utilizacao.getDataFim();

            String dataLocacaoFormatada = dataLocacao.format(formatter);
            String dataTerminoFormatada = dataTermino.format(formatter);

            relatorio.append("Data de Locação: ").append(dataLocacaoFormatada).append("\n");
            relatorio.append("Data de Término: ").append(dataTerminoFormatada).append("\n");

            relatorio.append("Console: ").append(utilizacao.getConsole().getNome()).append("\n");

            relatorio.append("Acessórios do Console:\n");
            for (Acessorio acessorio : utilizacao.getConsole().getAcessorios()) {
                relatorio.append("- ").append(acessorio.getNome()).append("\n");
            }

            relatorio.append("--------------------------------------\n");
        }

        return relatorio.toString();
    }
}