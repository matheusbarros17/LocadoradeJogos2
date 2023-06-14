package persistence;

import model.ItemLocacao;
import model.repository.DatabaseManager;
import model.repository.ItemLocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ItemLocacaoPersistence implements ItemLocacaoRepository {

    private DatabaseManager databaseManager;
    private static ItemLocacaoPersistence instance;

    private ItemLocacaoPersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static ItemLocacaoPersistence getInstance() {
        if (instance == null) {
            instance = new ItemLocacaoPersistence();
        }
        return instance;
    }

    @Override
    public void salvar(ItemLocacao itemLocacao) {

        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(itemLocacao, entityManager);

            databaseManager.commitTransaction(entityManager);
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public List<ItemLocacao> obterLocacoesRealizadasJogos() {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT il FROM ItemLocacao il " +
                    "JOIN FETCH il.jogoPlataforma jp " +
                    "JOIN FETCH jp.jogo j " +
                    "JOIN FETCH jp.plataforma p " +
                    "JOIN FETCH il.locacao l " +
                    "JOIN FETCH l.cliente c";
            TypedQuery<ItemLocacao> query = entityManager.createQuery(jpql, ItemLocacao.class);
            List<ItemLocacao> itensLocacao = query.getResultList();

            databaseManager.commitTransaction(entityManager);

            return itensLocacao;
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}
