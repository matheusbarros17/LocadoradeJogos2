package parte01.model.repository;

import parte01.model.ItemLocacao;

import java.util.List;

public interface ItemLocacaoRepository {
    public void salvar (ItemLocacao itemLocacao);
    public List<ItemLocacao> obterLocacoesRealizadasJogos();
}
