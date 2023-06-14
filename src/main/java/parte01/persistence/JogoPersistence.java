package parte01.persistence;

import parte01.model.Jogo;
import parte01.model.repository.DatabaseManager;
import parte01.model.repository.JogoRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class JogoPersistence implements JogoRepository {

    private DatabaseManager databaseManager;
    private static JogoPersistence instance;

    private JogoPersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static JogoPersistence getInstance() {
        if (instance == null) {
            instance = new JogoPersistence();
        }
        return instance;
    }

    @Override
    public void salvar(Jogo jogo) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(jogo, entityManager);

            jogo.getJogoPlataformas().forEach(jp -> jp.setJogo(jogo));

            databaseManager.commitTransaction(entityManager);
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public List<Jogo> listarJogos() {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT j FROM Jogo j LEFT JOIN FETCH j.jogoPlataformas jp LEFT JOIN FETCH jp.plataforma";
            TypedQuery<Jogo> query = entityManager.createQuery(jpql, Jogo.class);
            List<Jogo> jogos = query.getResultList();

            databaseManager.commitTransaction(entityManager);

            return jogos;
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public List<Jogo> buscarInformacoesJogoPorNome(String nomeJogo) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT DISTINCT j FROM Jogo j LEFT JOIN FETCH j.jogoPlataformas jp LEFT JOIN FETCH jp.plataforma WHERE j.titulo = :nome";
            TypedQuery<Jogo> query = entityManager.createQuery(jpql, Jogo.class);
            query.setParameter("nome", nomeJogo);
            List<Jogo> jogos = query.getResultList();

            databaseManager.commitTransaction(entityManager);

            return jogos;
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}