package parte01.persistence;

import parte01.model.JogoPlataforma;
import parte01.model.repository.DatabaseManager;
import parte01.model.repository.JogoPlataformaRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class JogoPlataformaPersistence implements JogoPlataformaRepository {

    private DatabaseManager databaseManager;
    private static JogoPlataformaPersistence instance;

    private JogoPlataformaPersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static JogoPlataformaPersistence getInstance() {
        if (instance == null) {
            instance = new JogoPlataformaPersistence();
        }
        return instance;
    }

    @Override
    public void salvar(JogoPlataforma jogoPlataforma) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(jogoPlataforma, entityManager);

            databaseManager.commitTransaction(entityManager);
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public JogoPlataforma obterJogoPlataforma(String jogo, String plataforma) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT jp FROM JogoPlataforma jp JOIN FETCH jp.jogo j JOIN FETCH jp.plataforma p WHERE j.titulo = :jogoTitulo AND p.nome = :plataformaNome";
            TypedQuery<JogoPlataforma> query = entityManager.createQuery(jpql, JogoPlataforma.class);
            query.setParameter("jogoTitulo", jogo);
            query.setParameter("plataformaNome", plataforma);
            JogoPlataforma jogoPlataforma = query.getSingleResult();

            databaseManager.commitTransaction(entityManager);

            return jogoPlataforma;
        } catch (NoResultException e) {
            return null; // Jogo e plataforma não encontrados
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public List<JogoPlataforma> obterLocacoesRealizadasJogos() {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT jp FROM JogoPlataforma jp " +
                    "JOIN FETCH jp.jogo j " +
                    "JOIN FETCH jp.plataforma p " +
                    "LEFT JOIN FETCH jp.itensLocacao il " +
                    "LEFT JOIN FETCH il.locacao l " +
                    "LEFT JOIN FETCH l.cliente c";
            TypedQuery<JogoPlataforma> query = entityManager.createQuery(jpql, JogoPlataforma.class);
            List<JogoPlataforma> jogoPlataformas = query.getResultList();

            // Carregar a coleção itensLocacao de forma antecipada para evitar o LazyInitializationException
            for (JogoPlataforma jogoPlataforma : jogoPlataformas) {
                jogoPlataforma.getItensLocacao().size();
            }

            databaseManager.commitTransaction(entityManager);

            return jogoPlataformas;
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