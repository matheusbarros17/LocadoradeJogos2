package parte01.model.repository;

import javax.persistence.*;

public class DatabaseManager {

    private static DatabaseManager instance;
    private EntityManagerFactory entityManagerFactory;

    private DatabaseManager() {
        // Configurar a inicialização do EntityManagerFactory aqui
        entityManagerFactory = Persistence.createEntityManagerFactory("BancoPU");
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void closeEntityManager(EntityManager entityManager) {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    public void beginTransaction(EntityManager entityManager) {
        entityManager.getTransaction().begin();
    }

    public void commitTransaction(EntityManager entityManager) {
        entityManager.getTransaction().commit();
    }

    public void rollbackTransaction(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    public void persist(Object object, EntityManager entityManager) {
        entityManager.persist(object);
    }
}