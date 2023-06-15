package model.persistence;

import model.Jogo;
import model.JogoPlataforma;
import controller.DatabaseManager;
import model.repository.JogoRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional
    public List<Jogo> buscarInformacoesJogoPorNome(String nomeJogo, LocalDate dataReserva) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT DISTINCT j FROM Jogo j " +
                    "JOIN FETCH j.jogoPlataformas jp " +
                    "JOIN FETCH jp.plataforma p " +
                    "LEFT JOIN jp.reserva r " +
                    "WHERE j.titulo = :nomeJogo " +
                    "AND (r.id IS NULL OR r.dataReserva <> :dataReserva)";

            TypedQuery<Jogo> query = entityManager.createQuery(jpql, Jogo.class);
            query.setParameter("nomeJogo", nomeJogo);
            query.setParameter("dataReserva", dataReserva);
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
    public Map<String, Jogo> buscarInformacoesJogoPorNome2(String nomeJogo) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT j FROM Jogo j " +
                    "WHERE j.titulo = :nome AND NOT EXISTS (" +
                    "   SELECT il FROM ItemLocacao il " +
                    "   WHERE il.jogoPlataforma.jogo = j)";
            TypedQuery<Jogo> query = entityManager.createQuery(jpql, Jogo.class);
            query.setParameter("nome", nomeJogo);
            List<Jogo> jogos = query.getResultList();

            databaseManager.commitTransaction(entityManager);

            Map<String, Jogo> mapaJogos = new HashMap<>();
            for (Jogo jogo : jogos) {
                for (JogoPlataforma jogoPlataforma : jogo.getJogoPlataformas()) {
                    String chave = jogo.getTitulo() + "-" + jogoPlataforma.getPlataforma().getNome();
                    mapaJogos.put(chave, jogo);
                }
            }

            return mapaJogos;
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
            return null;
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }
}