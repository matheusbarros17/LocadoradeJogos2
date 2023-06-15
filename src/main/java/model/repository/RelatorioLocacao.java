package model.repository;

import java.util.List;

public abstract class RelatorioLocacao {
    protected List<?> locacoes; // Lista genérica de locações

    public RelatorioLocacao(List<?> locacoes) {
        this.locacoes = locacoes;
    }

    public abstract String gerarRelatorio();
}