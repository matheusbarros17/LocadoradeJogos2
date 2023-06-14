package parte01.persistence;

import parte01.model.Cliente;
import parte01.model.repository.ClienteRepository;
import parte01.model.repository.DatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ClientePersistence implements ClienteRepository {

    private DatabaseManager databaseManager;
    private static ClientePersistence instance;

    private ClientePersistence() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static ClientePersistence getInstance() {
        if (instance == null) {
            instance = new ClientePersistence();
        }
        return instance;
    }

    @Override
    public void salvar(Cliente cliente) {
        EntityManager entityManager = null;

        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);

            databaseManager.persist(cliente, entityManager);

            databaseManager.commitTransaction(entityManager);

            System.out.println("\nCliente salvo com sucesso");
        } catch (Exception e) {
            databaseManager.rollbackTransaction(entityManager);
        } finally {
            databaseManager.closeEntityManager(entityManager);
        }
    }

    public Cliente obterClientePorEmail(String email) {
        EntityManager entityManager = null;
        try {
            entityManager = databaseManager.getEntityManager();
            databaseManager.beginTransaction(entityManager);
            TypedQuery<Cliente> query = entityManager.createQuery(
                    "SELECT e FROM Cliente e WHERE e.email = :email", Cliente.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}