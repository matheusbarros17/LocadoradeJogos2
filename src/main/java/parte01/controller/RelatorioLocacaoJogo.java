package parte01.controller;

import parte01.model.Cliente;
import parte01.model.ItemLocacao;
import parte01.model.Locacao;
import parte01.model.repository.DatabaseManager;
import parte01.model.repository.RelatorioLocacao;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioLocacaoJogo extends RelatorioLocacao {

    private List<ItemLocacao> itensLocacao;
    private DatabaseManager databaseManager;

    public RelatorioLocacaoJogo(List<ItemLocacao> itensLocacao) {
        super(itensLocacao);
        this.itensLocacao = itensLocacao;
        this.databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public String gerarRelatorio() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\nRelatório de Locação de Jogos:\n");

        EntityManager entityManager = databaseManager.getEntityManager();

        Map<Long, BigDecimal> totalLocacoes = new HashMap<>(); // Mapa para armazenar o total de locações por ID

        for (ItemLocacao itemLocacao : itensLocacao) {
            Locacao locacao = itemLocacao.getLocacao();
            Long locacaoId = locacao.getId();

            BigDecimal valorLocacao = itemLocacao.getValorLocacaoJogo();

            // Verifica se o ID da locação já está presente no mapa
            if (totalLocacoes.containsKey(locacaoId)) {
                BigDecimal totalAtual = totalLocacoes.get(locacaoId);
                totalLocacoes.put(locacaoId, totalAtual.add(valorLocacao)); // Soma o valor da locação ao total existente
            } else {
                totalLocacoes.put(locacaoId, valorLocacao); // Adiciona o novo ID da locação com o valor inicial
            }

            Cliente cliente = locacao.getCliente();

            relatorio.append("Cliente: ").append(cliente.getNome()).append("\n");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataLocacaoFormatted = locacao.getDataLocacao().format(dateFormatter);
            relatorio.append("Data de Locação: ").append(dataLocacaoFormatted).append("\n");
            relatorio.append("Jogo: ").append(itemLocacao.getJogoPlataforma().getJogo().getTitulo()).append("\n");
            relatorio.append("Plataforma: ").append(itemLocacao.getJogoPlataforma().getPlataforma().getNome()).append("\n");
            relatorio.append("Preço Diária: ").append(itemLocacao.getJogoPlataforma().getPrecoDiario()).append("\n");
            relatorio.append("Dias: ").append(itemLocacao.getDias()).append("\n");
            relatorio.append("Quantidade: ").append(itemLocacao.getQuantidade()).append("\n");
            relatorio.append("Valor total da locação: ").append(valorLocacao).append("\n");
            relatorio.append("--------------------------------------\n");
        }

        // Adiciona os totais de locações ao relatório
        for (Map.Entry<Long, BigDecimal> entry : totalLocacoes.entrySet()) {
            Long locacaoId = entry.getKey();
            BigDecimal totalLocacao = entry.getValue();

            relatorio.append("ID da Locação: ").append(locacaoId).append("\n");
            relatorio.append("Total da Locação: ").append(totalLocacao).append("\n");
            relatorio.append("--------------------------------------\n");
        }

        entityManager.close(); // Fecha o EntityManager

        return relatorio.toString();
    }
}