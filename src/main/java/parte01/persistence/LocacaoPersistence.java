package parte01.persistence;

import parte01.model.JogoPlataforma;
import parte01.model.Locacao;
import parte01.model.repository.DatabaseManager;
import parte01.model.repository.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

public class LocacaoPersistence implements LocacaoRepository {

    private DatabaseManager databaseManager;
    private static LocacaoPersistence instance;

    private LocacaoPersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static LocacaoPersistence getInstance() {
        if (instance == null) {
            instance = new LocacaoPersistence();
        }
        return instance;
    }

    @Override
    public void salvar(Locacao locacao) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(locacao, entityManager);

            databaseManager.commitTransaction(entityManager);
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public boolean isJogoLocadoEmData(JogoPlataforma jogoPlataforma, LocalDate data) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();

            String jpql = "SELECT COUNT(l) FROM Locacao l JOIN l.itensLocacao il WHERE il.jogoPlataforma = :jogoPlataforma AND l.dataLocacao = :data";
            TypedQuery<Long> query = ((EntityManager) entityManager).createQuery(jpql, Long.class);
            query.setParameter("jogoPlataforma", jogoPlataforma);
            query.setParameter("data", data);
            Long count = query.getSingleResult();

            return count > 0;
        } catch (Exception e) {
            return false;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}
