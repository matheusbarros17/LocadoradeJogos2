package model.persistence;

import model.UtilizacaoDoConsolePeloCliente;
import controller.DatabaseManager;
import model.repository.UtilizacaoDoConsolePeloClienteRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class UtilizacaoDoConsolePeloClientePersistence implements UtilizacaoDoConsolePeloClienteRepository {

    private DatabaseManager databaseManager;
    private static UtilizacaoDoConsolePeloClientePersistence instance;

    private UtilizacaoDoConsolePeloClientePersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static UtilizacaoDoConsolePeloClientePersistence getInstance() {
        if (instance == null) {
            instance = new UtilizacaoDoConsolePeloClientePersistence();
        }
        return instance;
    }

    @Override
    public void salvar(UtilizacaoDoConsolePeloCliente utilizacaoDoConsolePeloCliente) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(utilizacaoDoConsolePeloCliente, entityManager);

            databaseManager.commitTransaction(entityManager);

            //System.out.println("\nCliente salvo com sucesso");
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public List<UtilizacaoDoConsolePeloCliente> obterUtilizacoesConsolesRealizadas() {
        EntityManager entityManager = null;
        try {
            entityManager = databaseManager.getEntityManager();

            String jpql = "SELECT u FROM UtilizacaoDoConsolePeloCliente u WHERE u.console IS NOT NULL";
            TypedQuery<UtilizacaoDoConsolePeloCliente> query = entityManager.createQuery(jpql, UtilizacaoDoConsolePeloCliente.class);
            return query.getResultList();
        } catch (Exception e) {
            // Tratar exceções ou retornar um valor padrão em caso de erro
            return Collections.emptyList();
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    @Override
    public List<UtilizacaoDoConsolePeloCliente> obterLocacoesRealizadasConsoles() {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            String jpql = "SELECT uc FROM UtilizacaoDoConsolePeloCliente uc " +
                    "JOIN FETCH uc.cliente c " +
                    "JOIN FETCH uc.console con";
            TypedQuery<UtilizacaoDoConsolePeloCliente> query = entityManager.createQuery(jpql, UtilizacaoDoConsolePeloCliente.class);
            List<UtilizacaoDoConsolePeloCliente> utilizacoes = query.getResultList();

            databaseManager.commitTransaction(entityManager);

            return utilizacoes;
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
