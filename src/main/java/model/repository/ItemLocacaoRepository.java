package model.repository;

import model.ItemLocacao;

import java.util.List;

public interface ItemLocacaoRepository {
    public void salvar (ItemLocacao itemLocacao);
    public List<ItemLocacao> obterLocacoesRealizadasJogos();
}
